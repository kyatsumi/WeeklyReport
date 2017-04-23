package jp.co.netscs.weeklyreport.linesystem.view;

import java.util.List;

import com.linecorp.bot.model.message.Message;

import jp.co.netscs.weeklyreport.linesystem.common.BaseChapterService;
import jp.co.netscs.weeklyreport.linesystem.common.ChapterManager;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.ResponseSceneResultDto;
import jp.co.netscs.weeklyreport.linesystem.common.entitis.UserEntity;
import jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant;

public abstract class ReportViewService extends BaseChapterService {

	protected ReportViewService(ChapterManager manager) {
		super(manager);
	}
	
	@Override
	public String getChapterName() {
		return LineBotConstant.CHAPTER_REPORTVIEW;
	}

	@Override
	public String getStartSceneName() {
		return LineBotConstant.REPORTVIEW_SCENE_SELECT_WEEKS_VIEW;
	}
	
	protected abstract List<Message> selectViewWeek(LinePostInfoDto lineInfo, UserEntity userInfo);
	
	protected abstract ResponseSceneResultDto selectViewWeekAfter(LinePostInfoDto lineInfo, UserEntity userInfo);
	
	protected abstract List<Message> myReportView(LinePostInfoDto lineInfo, UserEntity userInfo);

}
