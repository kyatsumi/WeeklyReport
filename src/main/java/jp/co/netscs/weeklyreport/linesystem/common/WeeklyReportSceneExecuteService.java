package jp.co.netscs.weeklyreport.linesystem.common;

import java.util.List;

import com.linecorp.bot.model.message.Message;

import jp.co.netscs.weeklyreport.linesystem.common.dtos.LinePostInfoDto;

/**
 * 役割：ラインポスト情報からチャプターサービスに振り分ける
 * キャプチャーは現在以下の通り
 * <li>
 * ユーザ登録
 * 週報登録
 * 週報確認
 * 全体の週報確認
 * </li>
 * @author SCS036
 *
 */
public interface WeeklyReportSceneExecuteService {
	
	/**
	 * セクション情報にあったサービスを呼び出し返却用メッセージを生成する
	 * @param lineInfo Lineからの情報
	 * @param section セクション情報
	 * @return　返却用メッセージ
	 */
	public List<Message> execute(LinePostInfoDto lineInfo);
}
