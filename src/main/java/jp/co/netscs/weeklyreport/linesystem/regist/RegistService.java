package jp.co.netscs.weeklyreport.linesystem.regist;

import java.util.List;

import com.linecorp.bot.model.message.Message;

import jp.co.netscs.weeklyreport.linesystem.common.AbstractChapterSceneService;
import jp.co.netscs.weeklyreport.linesystem.common.ChapterManager;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LinePostInfoDto;

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
	protected abstract List<Message> groupSelect(LinePostInfoDto lineInfo);
	
	/**
	 * ユーザ名入力メソッド
	 * @param lineInfo
	 * @return
	 */
	protected abstract List<Message> inputName(LinePostInfoDto lineInfo);
	
	/**
	 * ユーザ入力内容確認メソッド
	 * @param lineInfo
	 * @return
	 */
	protected abstract List<Message> confrimRegist(LinePostInfoDto lineInfo);
	
	/**
	 * ユーザ登録完了メソッド
	 * @param lineInfo
	 * @return
	 */
	protected abstract List<Message> registComplite(LinePostInfoDto lineInfo);
	
	
	
}
