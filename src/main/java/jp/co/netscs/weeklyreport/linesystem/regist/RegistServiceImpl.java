package jp.co.netscs.weeklyreport.linesystem.regist;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.linecorp.bot.model.message.Message;

import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LinePostInfoDto;

@Service
public class RegistServiceImpl extends RegistService {

	@Override
	public List<Message> start(LinePostInfoDto lineInfo) {
		System.out.println("ようこそ");
		return Collections.emptyList();
	}

	@Override
	public List<Message> groupSelect(LinePostInfoDto lineInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> inputName(LinePostInfoDto lineInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> confrimRegist(LinePostInfoDto lineInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Message> registComplite(LinePostInfoDto lineInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
