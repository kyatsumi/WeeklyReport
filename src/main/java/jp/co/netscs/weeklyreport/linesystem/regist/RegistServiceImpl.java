package jp.co.netscs.weeklyreport.linesystem.regist;

import static jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant.REGIST;
import static jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant.CANCEL;

import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import jp.co.netscs.weeklyreport.linesystem.common.ChapterManager;
import jp.co.netscs.weeklyreport.linesystem.common.annotation.ResponseScene;
import jp.co.netscs.weeklyreport.linesystem.common.annotation.Scene;
import jp.co.netscs.weeklyreport.linesystem.common.daos.UserDao;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.ResponseSceneResultDto;
import jp.co.netscs.weeklyreport.linesystem.common.entitis.UserEntity;
import jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant;
import jp.co.netscs.weeklyreport.linesystem.common.util.LineMessageUtils;

@Transactional
@Service
public class RegistServiceImpl extends RegistService {

	@Autowired
	UserDao userDao;
	
	public RegistServiceImpl(@Autowired ChapterManager manager) {
		super(manager);
	}

	@Scene(sceneName = LineBotConstant.REGIST_SCENE_GROUPSELECT, next = LineBotConstant.REGIST_SCENE_INPUTNAME)
	public List<Message> groupSelect(LinePostInfoDto lineInfo) {
		UserEntity userInfo = UserEntity.builder().admin(false).group(null).lineId(lineInfo.getUserId()).name(null).build();
		userDao.save(userInfo);
		Message welcome = new TextMessage("ようこそエス・シー・エス週報BOTへ\nユーザ登録を行います");
		Message message = LineMessageUtils.generateConfirm("グループ選択", "所属グループを選択してください。", LineBotConstant.SCS_GROUP1, LineBotConstant.SCS_GROUP2);
		return Arrays.asList(welcome, message);
	}
	
	@ResponseScene(target = LineBotConstant.REGIST_SCENE_GROUPSELECT, postbackOnly = true)
	public ResponseSceneResultDto groupSelectAfter(LinePostInfoDto lineInfo, UserEntity userInfo) {
		UserEntity updateUser = UserEntity.builder()
				.lineId(userInfo.getLineId()).name(null).group(lineInfo.getText()).admin(false).build();
		userDao.save(updateUser);
		return RESULT_NEXT;
	}

	@Scene(sceneName = LineBotConstant.REGIST_SCENE_INPUTNAME, next = LineBotConstant.REGIST_SCENE_CONFIRMREGIST)
	public List<Message> inputName(LinePostInfoDto lineInfo, UserEntity userInfo) {
		return Arrays.asList(new TextMessage("名前を入力してください。"));
	}
	
	@ResponseScene(target = LineBotConstant.REGIST_SCENE_INPUTNAME)
	public ResponseSceneResultDto inputNameAfter(LinePostInfoDto lineInfo, UserEntity userInfo) {
		UserEntity updateUser = UserEntity.builder()
				.lineId(userInfo.getLineId()).name(lineInfo.getText()).group(userInfo.getGroup()).admin(false).build();
		userDao.save(updateUser);
		return RESULT_NEXT;
	}

	@Scene(sceneName = LineBotConstant.REGIST_SCENE_CONFIRMREGIST, next = LineBotConstant.REGIST_SCENE_REGISTCOMP)
	public List<Message> confrimRegist(LinePostInfoDto lineInfo, UserEntity userInfo) {
		Message message = LineMessageUtils.generateConfirm("登録内容確認", "ユーザ名:" + userInfo.getName() + "\nグループ:" + userInfo.getGroup() , REGIST, CANCEL);
		return Arrays.asList(message);
	}
	
	@ResponseScene(target = LineBotConstant.REGIST_SCENE_CONFIRMREGIST, postbackOnly = true)
	public ResponseSceneResultDto confrimRegistAfter(LinePostInfoDto lineInfo, UserEntity userInfo) {
		return RESULT_NEXT;
	}

	@Scene(sceneName = LineBotConstant.REGIST_SCENE_REGISTCOMP)
	public List<Message> registComplite(LinePostInfoDto lineInfo, UserEntity userInfo) {
		TextMessage text = null;
		if (lineInfo.getText().equals("登録")) {
			text = new TextMessage("登録が完了しました。");
		} else {
			text = new TextMessage("最初からやり直してください。");
			userDao.delete(userInfo);
		}
		return Arrays.asList(text);
	}

	@Override
	public String getChapterName() {
		return LineBotConstant.CHAPTER_REGIST;
	}

	@Override
	public String getStartSceneName() {
		return LineBotConstant.REGIST_SCENE_GROUPSELECT;
	}

}
