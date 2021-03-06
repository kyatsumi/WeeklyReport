package jp.co.netscs.weeklyreport.linesystem.report;

import java.util.List;

import com.linecorp.bot.model.message.Message;

import jp.co.netscs.weeklyreport.linesystem.common.BaseChapterService;
import jp.co.netscs.weeklyreport.linesystem.common.ChapterManager;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.ResponseSceneResultDto;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.common.entitis.UserEntity;
import jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant;

public abstract class ReportRegistService extends BaseChapterService {

	protected ReportRegistService(ChapterManager manager) {
		super(manager);
	}
	
	@Override
	public String getChapterName() {
		return LineBotConstant.CHAPTER_REPORT;
	}

	@Override
	public String getStartSceneName() {
		return LineBotConstant.REPORT_SCENE_DATE;
	}

	protected abstract List<Message> selectDate(LinePostInfoDto lineInfo, UserEntity userInfo);
	
	protected abstract ResponseSceneResultDto selectDateAfter(LinePostInfoDto lineInfo, UserEntity userInfo);
	
	protected abstract List<Message> inputReport(LinePostInfoDto lineInfo);
	
	protected abstract ResponseSceneResultDto inputReportAfter(LinePostInfoDto lineInfo, UserEntity userInfo);
	
	protected abstract List<Message> confrimReport(LinePostInfoDto lineInfo);
	
	protected abstract ResponseSceneResultDto confrimReportAfter(LinePostInfoDto lineInfo, UserEntity userInfo);
	
	protected abstract List<Message> registComplite(LinePostInfoDto lineInfo);
}
