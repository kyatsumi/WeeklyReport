package jp.co.netscs.weeklyreport.linesystem.report;

import java.util.List;

import com.linecorp.bot.model.message.Message;

import jp.co.netscs.weeklyreport.linesystem.common.AbstractChapterSceneService;
import jp.co.netscs.weeklyreport.linesystem.common.ChapterManager;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.common.entitis.UserEntity;

public abstract class ReportRegistService extends AbstractChapterSceneService {

	protected ReportRegistService(ChapterManager manager) {
		super(manager);
	}

	protected abstract List<Message> selectDate(LinePostInfoDto lineInfo, UserEntity userInfo);
	
	protected abstract List<Message> inputReport(LinePostInfoDto lineInfo);
	
	protected abstract List<Message> confrimReport(LinePostInfoDto lineInfo);
	
	protected abstract List<Message> registComplite(LinePostInfoDto lineInfo);
}
