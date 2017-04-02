package jp.co.netscs.weeklyreport.linesystem.commons;

import java.util.List;

import com.linecorp.bot.model.message.Message;

import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LineChapterDto;

/**
 * 役割：ユーザのセクションのサービスに振り分ける
 * セクションは現在以下の通り
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
	public List<Message> execute(LinePostInfoDto lineInfo, LineChapterDto section);
}
