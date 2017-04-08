package jp.co.netscs.weeklyreport.linesystem.view;

import java.util.List;

import com.linecorp.bot.model.message.Message;

import jp.co.netscs.weeklyreport.linesystem.common.AbstractChapterSceneService;
import jp.co.netscs.weeklyreport.linesystem.common.ChapterManager;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.common.entitis.UserEntity;

public abstract class ReportViewService extends AbstractChapterSceneService {

	protected ReportViewService(ChapterManager manager) {
		super(manager);
	}
	
	protected abstract List<Message> myReportView(LinePostInfoDto lineInfo, UserEntity userInfo);

}
