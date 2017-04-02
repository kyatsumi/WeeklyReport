package jp.co.netscs.weeklyreport.linesystem.regist;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import jp.co.netscs.weeklyreport.linesystem.common.ChapterManager;
import jp.co.netscs.weeklyreport.linesystem.common.annotation.Chapter;
import jp.co.netscs.weeklyreport.linesystem.common.annotation.Scene;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant;

@Service
@Chapter(name = LineBotConstant.CHAPTER_REGIST, startScene = LineBotConstant.REGIST_SCENE_START)
public class RegistServiceImpl extends RegistService {

	protected RegistServiceImpl(@Autowired ChapterManager manager) {
		super(manager);
	}

	@Override
	@Scene(name = LineBotConstant.REGIST_SCENE_START, next = LineBotConstant.REGIST_SCENE_GROUPSELECT)
	public List<Message> start(LinePostInfoDto lineInfo) {
		return Arrays.asList( new TextMessage(lineInfo.toString()));
	}

	@Override
	@Scene(name = LineBotConstant.REGIST_SCENE_GROUPSELECT, next = LineBotConstant.REGIST_SCENE_INPUTNAME)
	public List<Message> groupSelect(LinePostInfoDto lineInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Scene(name = LineBotConstant.REGIST_SCENE_INPUTNAME, next = LineBotConstant.REGIST_SCENE_CONFIRMREGIST)
	public List<Message> inputName(LinePostInfoDto lineInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Scene(name = LineBotConstant.REGIST_SCENE_CONFIRMREGIST, next = LineBotConstant.REGIST_SCENE_REGISTCOMP)
	public List<Message> confrimRegist(LinePostInfoDto lineInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Scene(name = LineBotConstant.REGIST_SCENE_REGISTCOMP)
	public List<Message> registComplite(LinePostInfoDto lineInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
