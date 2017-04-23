package jp.co.netscs.weeklyreport.linesystem.report;

import static jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant.CANCEL;
import static jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant.LAST_WEEK_VIEW;
import static jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant.NEXT_WEEK_VIEW;
import static jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant.ONE_WEEK_DAYS;
import static jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant.REGIST;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import jp.co.netscs.weeklyreport.linesystem.common.ChapterManager;
import jp.co.netscs.weeklyreport.linesystem.common.annotation.ResponseScene;
import jp.co.netscs.weeklyreport.linesystem.common.annotation.Scene;
import jp.co.netscs.weeklyreport.linesystem.common.daos.DayReportDao;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.ResponseSceneResultDto;
import jp.co.netscs.weeklyreport.linesystem.common.entitis.DayReportEntity;
import jp.co.netscs.weeklyreport.linesystem.common.entitis.ReportEntity;
import jp.co.netscs.weeklyreport.linesystem.common.entitis.UserEntity;
import jp.co.netscs.weeklyreport.linesystem.common.util.DateUtils;
import jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant;
import jp.co.netscs.weeklyreport.linesystem.common.util.LineMessageUtils;

/**
 * 週報を登録する章
 * @author katumi
 *
 */

@Transactional
@Service
public class ReportRegistServiceImpl extends ReportRegistService {
	
	private Map<String,DayReportEntity> reportMap = new HashMap<>();
	
	@Autowired
	DayReportDao dayReportDao;

	public ReportRegistServiceImpl(@Autowired ChapterManager manager) {
		super(manager);
	}

	@Scene(sceneName = LineBotConstant.REPORT_SCENE_DATE, next = LineBotConstant.REPORT_SCENE_INPUTREPORT)
	public List<Message> selectDate(LinePostInfoDto lineInfo, UserEntity userInfo) {
		String text = lineInfo.getText();
		String selectText = text.split(",")[0];
		Message oneWeek = null;
		switch (selectText) {
			case NEXT_WEEK_VIEW: {
				Integer offset = Integer.parseInt(text.split(",")[1]);
				oneWeek = LineMessageUtils.generateOneWeekCarousel(LocalDate.now(), offset - ONE_WEEK_DAYS);
				break;
			}
			case LAST_WEEK_VIEW: {
				Integer offset = Integer.parseInt(text.split(",")[1]);
				oneWeek = LineMessageUtils.generateOneWeekCarousel(LocalDate.now(), offset + ONE_WEEK_DAYS);
				break;
			}
			default: {
				oneWeek = LineMessageUtils.generateOneWeekCarousel(LocalDate.now(), 0);
			}
		}
		return Arrays.asList(oneWeek);
	}
	
	@ResponseScene(target = LineBotConstant.REPORT_SCENE_DATE)
	public ResponseSceneResultDto selectDateAfter(LinePostInfoDto lineInfo, UserEntity userInfo) {
		String text = lineInfo.getText();
		String selectText = text.split(",")[0];
		switch (selectText) {
			case NEXT_WEEK_VIEW: 
			case LAST_WEEK_VIEW:
				return ResponseSceneResultDto.builder().dummy(lineInfo).statusCode(ResponseStatusCode.LOOP).build();
			default: {
				ReportEntity reportInfo = ReportEntity.builder()
						.date(DateUtils.string2Date(lineInfo.getText())).lineId(lineInfo.getUserId()).build();
				DayReportEntity dayReport = DayReportEntity.builder().reportEntity(reportInfo).build();
				reportMap.put(lineInfo.getUserId(), dayReport);
				return RESULT_NEXT;
			}
		}
	}

	@Scene(sceneName = LineBotConstant.REPORT_SCENE_INPUTREPORT, next = LineBotConstant.REPORT_SCENE_CONFIRMREGIST)
	public List<Message> inputReport(LinePostInfoDto lineInfo) {
		return Arrays.asList(new TextMessage("内容を入力してください。"));
	}
	
	@ResponseScene(target = LineBotConstant.REPORT_SCENE_INPUTREPORT)
	public ResponseSceneResultDto inputReportAfter(LinePostInfoDto lineInfo, UserEntity userInfo) {
		DayReportEntity dayReport = reportMap.get(lineInfo.getUserId());
		DayReportEntity updateEntity = DayReportEntity.builder()
				.report(lineInfo.getText()).adminComment(null).reportEntity(dayReport.getReportEntity()).build();
		reportMap.put(lineInfo.getUserId(), updateEntity);
		return RESULT_NEXT;
	}

	@Scene(sceneName = LineBotConstant.REPORT_SCENE_CONFIRMREGIST, next = LineBotConstant.REPORT_SCENE_REGISTCOMP)
	public List<Message> confrimReport(LinePostInfoDto lineInfo) {
		String report = reportMap.get(lineInfo.getUserId()).viewDayReport();
		Message message = new TextMessage(report);
		Message message2 = LineMessageUtils.generateConfirm("上記の内容で登録しますか？", "上記の内容で登録しますか？", REGIST, CANCEL);
		return Arrays.asList(message, message2);
	}
	
	@ResponseScene(target = LineBotConstant.REPORT_SCENE_CONFIRMREGIST)
	public ResponseSceneResultDto confrimReportAfter(LinePostInfoDto lineInfo, UserEntity userInfo) {
		if (lineInfo.getText().equals(REGIST)) {
			DayReportEntity report = reportMap.get(lineInfo.getUserId());
			dayReportDao.save(report);
		}
		return RESULT_NEXT;
	}

	@Scene(sceneName = LineBotConstant.REPORT_SCENE_REGISTCOMP)
	public List<Message> registComplite(LinePostInfoDto lineInfo) {
		String message = lineInfo.getText().equals(REGIST) ? "登録しました。" : "最初からやり直してください。";
		return Arrays.asList( new TextMessage(message));
	}

	@Override
	public String getChapterName() {
		return LineBotConstant.CHAPTER_REPORT;
	}

	@Override
	public String getStartSceneName() {
		return LineBotConstant.REPORT_SCENE_DATE;
	}
}
