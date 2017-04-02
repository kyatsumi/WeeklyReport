package jp.co.netscs.weeklyreport.linesystem.commons;

import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LineChapterDto;
import lombok.NonNull;

/**
 * ユーザのセクション管理を行うサービス
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
	public LineChapterDto fetchUserSection(@NonNull LinePostInfoDto lineInfo, boolean isPostBack);
	
	/**
	 * LineInfoを元にユーザのセクション情報を取得する
	 * PostBackからの情報の場合は{@link WeeklyReportChapterService#fetchUserSection(LinePostInfoDto, boolean)}を使う
	 * @param lineInfo Line情報
	 * @return セクション情報
	 */
	default LineChapterDto fetchUserSection(@NonNull LinePostInfoDto lineInfo) {
		return this.fetchUserSection(lineInfo,false);
	}
}
