package jp.co.netscs.weeklyreport.linesystem.report;

import static jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import jp.co.netscs.weeklyreport.linesystem.common.ChapterManager;
import jp.co.netscs.weeklyreport.linesystem.common.annotation.Chapter;
import jp.co.netscs.weeklyreport.linesystem.common.annotation.ResponseScene;
import jp.co.netscs.weeklyreport.linesystem.common.annotation.Scene;
import jp.co.netscs.weeklyreport.linesystem.common.daos.DayReportDao;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.ResponseSceneResultDto;
import jp.co.netscs.weeklyreport.linesystem.common.entitis.DayReportEntity;
import jp.co.netscs.weeklyreport.linesystem.common.entitis.ReportEntity;
import jp.co.netscs.weeklyreport.linesystem.common.entitis.UserEntity;
import jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant;
import jp.co.netscs.weeklyreport.linesystem.common.util.LineMessageUtils;

@Chapter(name = LineBotConstant.CHAPTER_REPORT, startScene = LineBotConstant.REPORT_SCENE_DATE)
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
				oneWeek = LineMessageUtils.generateOneWeekCarousel(LocalDate.now(), offset + ONE_WEEK_DAYS);
			}
			case LAST_WEEK_VIEW: {
				Integer offset = Integer.parseInt(text.split(",")[1]);
				oneWeek = LineMessageUtils.generateOneWeekCarousel(LocalDate.now(), offset - ONE_WEEK_DAYS);
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
				return ResponseSceneResultDto.builder().dummy(lineInfo).result(ResponseResult.LOOP).build();
			default: {
				DayReportEntity dayReport = DayReportEntity.builder().reportEntity(ReportEntity.builder().date(lineInfo.getText()).lineId(lineInfo.getUserId()).build()).build();
				reportMap.put(lineInfo.getUserId(), dayReport);
				return AFTER_RESULT_NEXT;
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
		dayReport.setReport(lineInfo.getText());
		return AFTER_RESULT_NEXT;
	}

	@Scene(sceneName = LineBotConstant.REPORT_SCENE_CONFIRMREGIST, next = LineBotConstant.REPORT_SCENE_REGISTCOMP)
	public List<Message> confrimReport(LinePostInfoDto lineInfo) {
		String report = LineMessageUtils.generateDayReportMessage(reportMap.get(lineInfo.getUserId()));
		Message message = new TextMessage(report);
		Message message2 = LineMessageUtils.generateConfirm("上記の内容で登録しますか？", "上記の内容で登録しますか？", "登録", "キャンセル");
		return Arrays.asList(message, message2);
	}
	
	@ResponseScene(target = LineBotConstant.REPORT_SCENE_CONFIRMREGIST)
	public ResponseSceneResultDto confrimReportAfter(LinePostInfoDto lineInfo, UserEntity userInfo) {
		
		if (lineInfo.getText().equals("登録")) {
			dayReportDao.save(reportMap.get(lineInfo.getUserId()));
		}
		
		return AFTER_RESULT_NEXT;
	}

	@Scene(sceneName = LineBotConstant.REPORT_SCENE_REGISTCOMP)
	public List<Message> registComplite(LinePostInfoDto lineInfo) {
		String message = lineInfo.getText().equals("登録") ? "登録しました。" : "最初からやり直してください。";
		return Arrays.asList( new TextMessage(message));
	}
}
