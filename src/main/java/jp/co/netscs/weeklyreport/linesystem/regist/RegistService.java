package jp.co.netscs.weeklyreport.linesystem.regist;

import java.util.List;

import com.linecorp.bot.model.message.Message;

import jp.co.netscs.weeklyreport.linesystem.common.AbstractChapterSceneService;
import jp.co.netscs.weeklyreport.linesystem.common.ChapterManager;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.common.entitis.UserEntity;

public abstract class RegistService extends AbstractChapterSceneService{

	protected RegistService(ChapterManager manager) {
		super(manager);
	}

	/**
	 * 新規登録メソッド
	 * @param lineInfo
	 * @return
	 */
	protected abstract List<Message> start(LinePostInfoDto lineInfo);
	
	/**
	 * グループ選択メソッド
	 * @param lineInfo
	 * @return
	 */
	protected abstract List<Message> groupSelect(LinePostInfoDto lineInfo, UserEntity userInfo);
	
	/**
	 * ユーザ名入力メソッド
	 * @param lineInfo
	 * @return
	 */
	protected abstract List<Message> inputName(LinePostInfoDto lineInfo, UserEntity userInfo);
	
	/**
	 * ユーザ入力内容確認メソッド
	 * @param lineInfo
	 * @return
	 */
	protected abstract List<Message> confrimRegist(LinePostInfoDto lineInfo, UserEntity userInfo);
	
	/**
	 * ユーザ登録完了メソッド
	 * @param lineInfo
	 * @return
	 */
	protected abstract List<Message> registComplite(LinePostInfoDto lineInfo, UserEntity userInfo);
	
	
	
}
