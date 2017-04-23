package jp.co.netscs.weeklyreport.linesystem.regist;

import java.util.List;

import com.linecorp.bot.model.message.Message;

import jp.co.netscs.weeklyreport.linesystem.common.BaseChapterService;
import jp.co.netscs.weeklyreport.linesystem.common.ChapterManager;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.ResponseSceneResultDto;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.common.entitis.UserEntity;
import jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant;

public abstract class RegistService extends BaseChapterService{

	protected RegistService(ChapterManager manager) {
		super(manager);
	}
	
	@Override
	public String getChapterName() {
		return LineBotConstant.CHAPTER_REGIST;
	}

	@Override
	public String getStartSceneName() {
		return LineBotConstant.REGIST_SCENE_GROUPSELECT;
	}
	
	/**
	 * グループ選択メソッド
	 * @param lineInfo
	 * @return
	 */
	protected abstract List<Message> groupSelect(LinePostInfoDto lineInfo);
	
	/**
	 * グループ選択の返答処理メソッド
	 * @param lineInfo
	 * @return
	 */
	protected abstract ResponseSceneResultDto groupSelectAfter(LinePostInfoDto lineInfo, UserEntity userInfo);
	
	/**
	 * ユーザ名入力メソッド
	 * @param lineInfo
	 * @return
	 */
	protected abstract List<Message> inputName(LinePostInfoDto lineInfo, UserEntity userInfo);
	
	/**
	 * ユーザ名入力の返答処理メソッド
	 * @param lineInfo
	 * @return
	 */
	protected abstract ResponseSceneResultDto inputNameAfter(LinePostInfoDto lineInfo, UserEntity userInfo);
	
	/**
	 * ユーザ入力内容確認メソッド
	 * @param lineInfo
	 * @return
	 */
	protected abstract List<Message> confrimRegist(LinePostInfoDto lineInfo, UserEntity userInfo);
	
	/**
	 * ユーザ入力内容確認メソッド
	 * @param lineInfo
	 * @return
	 */
	protected abstract ResponseSceneResultDto confrimRegistAfter(LinePostInfoDto lineInfo, UserEntity userInfo);
	
	/**
	 * ユーザ登録完了メソッド
	 * @param lineInfo
	 * @return
	 */
	protected abstract List<Message> registComplite(LinePostInfoDto lineInfo, UserEntity userInfo);
	
	
	
}
