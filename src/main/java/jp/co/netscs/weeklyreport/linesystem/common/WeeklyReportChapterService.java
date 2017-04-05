package jp.co.netscs.weeklyreport.linesystem.common;

import jp.co.netscs.weeklyreport.linesystem.common.dtos.LineChapterDto;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LinePostInfoDto;
import lombok.NonNull;

/**
 * ユーザのチャプター管理を行うサービス
 * @author SCS036
 *
 */
public interface WeeklyReportChapterService {
	
	/**
	 * LineInfoを元にユーザのセクション情報を取得する
	 * postbackの場合{@link LinePostInfoDto#getText()}を固定文言として処理する
	 * @param lineInfo Line情報
	 * @param isPostBack postbackから届いたか
	 * @return セクション情報
	 */
	public LineChapterDto fetchUserChapter(@NonNull LinePostInfoDto lineInfo);
}
