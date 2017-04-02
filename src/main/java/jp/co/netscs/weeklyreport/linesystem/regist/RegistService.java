package jp.co.netscs.weeklyreport.linesystem.regist;

import java.util.List;

import com.linecorp.bot.model.message.Message;

import jp.co.netscs.weeklyreport.linesystem.commons.AbstractSectionService;
import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LinePostInfoDto;

public abstract class RegistService extends AbstractSectionService{

	/**
	 * 新規登録メソッド
	 * @param lineInfo
	 * @return
	 */
	public abstract List<Message> start(LinePostInfoDto lineInfo);
	
	/**
	 * グループ選択メソッド
	 * @param lineInfo
	 * @return
	 */
	public abstract List<Message> groupSelect(LinePostInfoDto lineInfo);
	
	/**
	 * ユーザ名入力メソッド
	 * @param lineInfo
	 * @return
	 */
	public abstract List<Message> inputName(LinePostInfoDto lineInfo);
	
	/**
	 * ユーザ入力内容確認メソッド
	 * @param lineInfo
	 * @return
	 */
	public abstract List<Message> confrimRegist(LinePostInfoDto lineInfo);
	
	/**
	 * ユーザ登録完了メソッド
	 * @param lineInfo
	 * @return
	 */
	public abstract List<Message> registComplite(LinePostInfoDto lineInfo);
	
	
	
}
