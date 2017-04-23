package jp.co.netscs.weeklyreport.linesystem.groupview;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.linecorp.bot.model.message.Message;

import jp.co.netscs.weeklyreport.linesystem.common.ChapterManager;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.ResponseSceneResultDto;
import jp.co.netscs.weeklyreport.linesystem.common.entitis.UserEntity;

public class GroupReportViewServiceImpl extends GroupReportViewService {

	public GroupReportViewServiceImpl(@Autowired ChapterManager manager) {
		super(manager);
	}

	@Override
	public List<Message> selectViewMember(LinePostInfoDto lineInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseSceneResultDto selectViewMemberAfter(LinePostInfoDto lineInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> selectViewWeek(LinePostInfoDto lineInfo, UserEntity userInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseSceneResultDto selectViewWeekAfter(LinePostInfoDto lineInfo, UserEntity userInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> viewMemberReport(LinePostInfoDto lineInfo, UserEntity userInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
