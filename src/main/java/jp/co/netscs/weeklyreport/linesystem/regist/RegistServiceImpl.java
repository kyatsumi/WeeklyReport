package jp.co.netscs.weeklyreport.linesystem.regist;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import jp.co.netscs.weeklyreport.linesystem.commons.annot.Scene;
import jp.co.netscs.weeklyreport.linesystem.commons.annot.Section;
import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.commons.util.LineBotConstant;

@Service
@Section(name = LineBotConstant.SCTION_REGIST)
public class RegistServiceImpl extends RegistService {

	@Override
	@Scene(name = LineBotConstant.REGIST_SCENE_START)
	public List<Message> start(LinePostInfoDto lineInfo) {
		return Arrays.asList( new TextMessage(lineInfo.toString()));
	}

	@Override
	@Scene(name = LineBotConstant.REGIST_SCENE_GROUPSELECT)
	public List<Message> groupSelect(LinePostInfoDto lineInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Scene(name = LineBotConstant.REGIST_SCENE_INPUTNAME)
	public List<Message> inputName(LinePostInfoDto lineInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Scene(name = LineBotConstant.REGIST_SCENE_CONFIRMREGIST)
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
