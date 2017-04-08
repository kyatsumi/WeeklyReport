package jp.co.netscs.weeklyreport.linesystem.report;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.linecorp.bot.model.message.Message;

import jp.co.netscs.weeklyreport.linesystem.common.ChapterManager;
import jp.co.netscs.weeklyreport.linesystem.common.annotation.AfterScene;
import jp.co.netscs.weeklyreport.linesystem.common.annotation.Chapter;
import jp.co.netscs.weeklyreport.linesystem.common.annotation.Scene;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.AfterSceneResultDto;
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
	@Scene(name = LineBotConstant.REPORT_SCENE_DATE, next = LineBotConstant.REPORT_SCENE_INPUTREPORT)
	public List<Message> selectDate(LinePostInfoDto lineInfo, UserEntity userInfo) {
		Message oneWeek = LineMessageUtils.generateOneWeekCarousel(LocalDate.now());
		return Arrays.asList(oneWeek);
	}
	
	@Override
	@AfterScene(after = LineBotConstant.REPORT_SCENE_DATE)
	protected AfterSceneResultDto selectDateAfter(LinePostInfoDto lineInfo, UserEntity userInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Scene(name = LineBotConstant.REPORT_SCENE_INPUTREPORT, next = LineBotConstant.REPORT_SCENE_CONFIRMREGIST)
	public List<Message> inputReport(LinePostInfoDto lineInfo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected AfterSceneResultDto inputReportAfter(LinePostInfoDto lineInfo, UserEntity userInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Scene(name = LineBotConstant.REPORT_SCENE_CONFIRMREGIST, next = LineBotConstant.REPORT_SCENE_REGISTCOMP)
	public List<Message> confrimReport(LinePostInfoDto lineInfo) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected AfterSceneResultDto confrimReportAfter(LinePostInfoDto lineInfo, UserEntity userInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Scene(name = LineBotConstant.REPORT_SCENE_REGISTCOMP)
	public List<Message> registComplite(LinePostInfoDto lineInfo) {
		// TODO Auto-generated method stub
		return null;
	}
}
