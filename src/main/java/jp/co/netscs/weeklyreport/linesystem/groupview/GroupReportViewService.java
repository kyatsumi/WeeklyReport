package jp.co.netscs.weeklyreport.linesystem.groupview;

import java.util.List;

import com.linecorp.bot.model.message.Message;

import jp.co.netscs.weeklyreport.linesystem.common.BaseChapterService;
import jp.co.netscs.weeklyreport.linesystem.common.ChapterManager;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.ResponseSceneResultDto;
import jp.co.netscs.weeklyreport.linesystem.common.entitis.UserEntity;
import jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant;

public abstract class GroupReportViewService extends BaseChapterService {

	protected GroupReportViewService(ChapterManager manager) {
		super(manager);
	}
	
	protected abstract List<Message> selectViewMember(LinePostInfoDto lineInfo);
	
	protected abstract ResponseSceneResultDto selectViewMemberAfter(LinePostInfoDto lineInfo);
	
	protected abstract List<Message> selectViewWeek(LinePostInfoDto lineInfo, UserEntity userInfo);
	
	protected abstract ResponseSceneResultDto selectViewWeekAfter(LinePostInfoDto lineInfo, UserEntity userInfo);
	
	protected abstract List<Message> viewMemberReport(LinePostInfoDto lineInfo, UserEntity userInfo);


	@Override
	public String getChapterName() {
		return LineBotConstant.CHAPTER_TEAMREPORTVIEW;
	}

	@Override
	public String getStartSceneName() {
		return LineBotConstant.GROUP_REPORTVIEW_SCENE_SELECTUSER;
	}

}
