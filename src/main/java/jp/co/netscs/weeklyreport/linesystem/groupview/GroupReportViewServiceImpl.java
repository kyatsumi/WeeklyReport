package jp.co.netscs.weeklyreport.linesystem.groupview;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linecorp.bot.model.message.Message;

import jp.co.netscs.weeklyreport.linesystem.common.ChapterManager;
import jp.co.netscs.weeklyreport.linesystem.common.annotation.ResponseScene;
import jp.co.netscs.weeklyreport.linesystem.common.annotation.Scene;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.ResponseSceneResultDto;
import jp.co.netscs.weeklyreport.linesystem.common.entitis.UserEntity;
import jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant;

@Service
public class GroupReportViewServiceImpl extends GroupReportViewService {

	public GroupReportViewServiceImpl(@Autowired ChapterManager manager) {
		super(manager);
	}

	@Scene(sceneName = LineBotConstant.GROUP_REPORTVIEW_SCENE_SELECTUSER, next = LineBotConstant.GROUP_REPORTVIEW_SCENE_SELECT_DATE)
	public List<Message> selectViewMember(LinePostInfoDto lineInfo) {
		
		return Arrays.asList();
	}

	@ResponseScene(target = LineBotConstant.GROUP_REPORTVIEW_SCENE_SELECTUSER, postbackOnly = true)
	public ResponseSceneResultDto selectViewMemberAfter(LinePostInfoDto lineInfo) {
		return null;
	}

	@Override
	@Scene(sceneName = LineBotConstant.GROUP_REPORTVIEW_SCENE_SELECT_DATE, next = LineBotConstant.GROUP_REPORTVIEW_SCENE_VIEW)
	public List<Message> selectViewWeek(LinePostInfoDto lineInfo, UserEntity userInfo) {
		
		return null;
	}

	@Override
	@ResponseScene(target = LineBotConstant.GROUP_REPORTVIEW_SCENE_SELECT_DATE, postbackOnly = true)
	public ResponseSceneResultDto selectViewWeekAfter(LinePostInfoDto lineInfo, UserEntity userInfo) {
		return null;
	}

	@Override
	@Scene(sceneName = LineBotConstant.GROUP_REPORTVIEW_SCENE_VIEW)
	public List<Message> viewMemberReport(LinePostInfoDto lineInfo, UserEntity userInfo) {
		return null;
	}

}
