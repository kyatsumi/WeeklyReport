package jp.co.netscs.weeklyreport.linesystem.regist;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linecorp.bot.model.message.Message;

import jp.co.netscs.weeklyreport.linesystem.common.ChapterManager;
import jp.co.netscs.weeklyreport.linesystem.common.annotation.Chapter;
import jp.co.netscs.weeklyreport.linesystem.common.annotation.Scene;
import jp.co.netscs.weeklyreport.linesystem.common.daos.UserDao;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.common.entitis.UserEntity;
import jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant;
import jp.co.netscs.weeklyreport.linesystem.common.util.LineMessageUtils;

@Service
@Chapter(name = LineBotConstant.CHAPTER_REGIST, startScene = LineBotConstant.REGIST_SCENE_START)
public class RegistServiceImpl extends RegistService {

	@Autowired
	UserDao userDao;
	
	public RegistServiceImpl(@Autowired ChapterManager manager) {
		super(manager);
	}

	@Override
	@Scene(name = LineBotConstant.REGIST_SCENE_START, next = LineBotConstant.REGIST_SCENE_GROUPSELECT)
	protected List<Message> start(LinePostInfoDto lineInfo) {
		UserEntity userInfo = UserEntity.builder().admin(false).group(null).lineId(lineInfo.getUserId()).name(null).build();
		userDao.save(userInfo);
		Message message = LineMessageUtils.confirm("ユーザ登録", "管理者権限が必要ですか？", "はい", "いいえ");
		return Arrays.asList(message);
	}

	@Override
	@Scene(name = LineBotConstant.REGIST_SCENE_GROUPSELECT, next = LineBotConstant.REGIST_SCENE_INPUTNAME)
	protected List<Message> groupSelect(LinePostInfoDto lineInfo) {
		lineInfo.getText().equals("はい");
		Message message = LineMessageUtils.confirm("グループ選択", "所属グループを選択してください。", "第１グループ", "第２グループ");
		return Arrays.asList(message);
	}

	@Override
	@Scene(name = LineBotConstant.REGIST_SCENE_INPUTNAME, next = LineBotConstant.REGIST_SCENE_CONFIRMREGIST)
	protected List<Message> inputName(LinePostInfoDto lineInfo) {
		
		return null;
	}

	@Override
	@Scene(name = LineBotConstant.REGIST_SCENE_CONFIRMREGIST, next = LineBotConstant.REGIST_SCENE_REGISTCOMP)
	protected List<Message> confrimRegist(LinePostInfoDto lineInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Scene(name = LineBotConstant.REGIST_SCENE_REGISTCOMP)
	protected List<Message> registComplite(LinePostInfoDto lineInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
