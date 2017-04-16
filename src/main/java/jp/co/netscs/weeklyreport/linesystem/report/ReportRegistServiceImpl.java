package jp.co.netscs.weeklyreport.linesystem.report;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import jp.co.netscs.weeklyreport.linesystem.common.ChapterManager;
import jp.co.netscs.weeklyreport.linesystem.common.annotation.ResponseScene;
import jp.co.netscs.weeklyreport.linesystem.common.annotation.Chapter;
import jp.co.netscs.weeklyreport.linesystem.common.annotation.Scene;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.ResponseSceneResultDto;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.common.entitis.UserEntity;
import jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant;
import jp.co.netscs.weeklyreport.linesystem.common.util.LineMessageUtils;

@Transactional
@Chapter(name = LineBotConstant.CHAPTER_REPORT, startScene = LineBotConstant.REPORT_SCENE_DATE)
public class ReportRegistServiceImpl extends ReportRegistService {

	public ReportRegistServiceImpl(@Autowired ChapterManager manager) {
		super(manager);
	}

	
	@Override
	@Scene(sceneName = LineBotConstant.REPORT_SCENE_DATE, next = LineBotConstant.REPORT_SCENE_INPUTREPORT)
	public List<Message> selectDate(LinePostInfoDto lineInfo, UserEntity userInfo) {
		int offset = lineInfo.getText().equals("先週の日付を表示") ? 7 : 0;
		Message oneWeek = LineMessageUtils.generateOneWeekCarousel(LocalDate.now(), offset);
		return Arrays.asList(oneWeek);
	}
	
	@Override
	@ResponseScene(target = LineBotConstant.REPORT_SCENE_DATE)
	public ResponseSceneResultDto selectDateAfter(LinePostInfoDto lineInfo, UserEntity userInfo) {
		if (lineInfo.getText().equals("先週の日付を表示")) {
			return ResponseSceneResultDto.builder().dummy(lineInfo).result(ResponseResult.LOOP).build();
		}
		return AFTER_RESULT_NEXT;
	}

	@Override
	@Scene(sceneName = LineBotConstant.REPORT_SCENE_INPUTREPORT, next = LineBotConstant.REPORT_SCENE_CONFIRMREGIST)
	public List<Message> inputReport(LinePostInfoDto lineInfo) {
		return Arrays.asList(new TextMessage("内容を入力してください。"));
	}
	
	@Override
	@ResponseScene(target = LineBotConstant.REPORT_SCENE_INPUTREPORT)
	public ResponseSceneResultDto inputReportAfter(LinePostInfoDto lineInfo, UserEntity userInfo) {
		return AFTER_RESULT_NEXT;
	}

	@Override
	@Scene(sceneName = LineBotConstant.REPORT_SCENE_CONFIRMREGIST, next = LineBotConstant.REPORT_SCENE_REGISTCOMP)
	public List<Message> confrimReport(LinePostInfoDto lineInfo) {
		Message message = LineMessageUtils.generateConfirm("登録内容確認", "日付:YYYY/MM/DD\n内容:" + lineInfo.getText(), "登録", "キャンセル");
		return Arrays.asList(message);
	}
	
	@Override
	public ResponseSceneResultDto confrimReportAfter(LinePostInfoDto lineInfo, UserEntity userInfo) {
		return AFTER_RESULT_NEXT;
	}

	@Override
	@Scene(sceneName = LineBotConstant.REPORT_SCENE_REGISTCOMP)
	public List<Message> registComplite(LinePostInfoDto lineInfo) {
		return Arrays.asList( new TextMessage("登録しました。"));
	}
}
