package jp.co.netscs.weeklyreport.linesystem.common.util;

/**
 * LineBot用定数クラス
 * @author SCS036
 *
 */
public class LineBotConstant {
/********************************機能名定義ここから**********************************************************/
	
	/**
	 *　機能名：ユーザ登録
	 */
	public static final String CHAPTER_REGIST = "ユーザ登録";
	
	/**
	 * ユーザ登録：新規登録
	 */
	public static final String REGIST_SCENE_START = "ユーザ登録新規登録";
	
	/**
	 * ユーザ登録：グループ選択
	 */
	public static final String REGIST_SCENE_GROUPSELECT = "ユーザ登録グループ選択";
	
	/**
	 * ユーザ登録：名前入力
	 */
	public static final String REGIST_SCENE_INPUTNAME = "ユーザ登録名前入力";
	
	/**
	 * ユーザ登録:入力内容確認
	 */
	public static final String REGIST_SCENE_CONFIRMREGIST = "ユーザ登録内容確認";
	
	/**
	 * ユーザ登録:登録完了
	 */
	public static final String REGIST_SCENE_REGISTCOMP = "ユーザ登録完了";
	
	/**
	 * 機能名：週報登録
	 */
	public static final String CHAPTER_REPORT = "週報登録";
	
	/**
	 * 週報登録:週報登録日付選択
	 */
	public static final String REPORT_SCENE_DATE = "週報日付選択";
	
	/**
	 * 週報登録:週報内容入力
	 */
	public static final String REPORT_SCENE_INPUTREPORT = "週報内容入力";

	/**
	 * 週報登録:週報入力内容確認
	 */
	public static final String REPORT_SCENE_CONFIRMREGIST = "週報入力内容確認";
	
	/**
	 * 週報登録:週報登録結果
	 */
	public static final String REPORT_SCENE_REGISTCOMP = "週報登録完了";
	
	/**
	 * 機能名：週報確認
	 */
	public static final String CHAPTER_REPORTVIEW = "週報確認";
	
	/**
	 * 週報確認:週報内容表示
	 */
	public static final String REPORTVIEW_SCENE_VIEW = "週報確認(個人)";
	
	/**
	 * 機能名：全体の週報確認
	 */
	public static final String CHAPTER_TEAMREPORTVIEW = "全体の週報確認";
	
	/**
	 * 全体の週報確認:確認対象者一覧
	 */
	public static final String TEAMREPORTVIEW_SCENE_SELECTUSER = "週報確認対象一覧";
	
	/**
	 * 全体の週報確認:週報内容表示
	 */
	public static final String TEAMREPORTVIEW_SCENE_VIEW = "週報確認(全体)";
	
	/**
	 * 終了したチャプター
	 */
	public static final String CHAPTER_END = "終了したチャプター";
	
/********************************機能名定義ここまで**********************************************************/
	
	/**
	 * Line Messageing APIのメッセージ最大件数
	 */
	public static final int MESSAGE_MAX = 5;
	
	/**
	 * セクションの有効期限
	 */
	public static final int PERIOD_BASE = 30;
	
	/**
	 * 第１グループ
	 */
	public static final String SCS_GROUP1 = "第１グループ";
	
	/**
	 * 第２グループ
	 */
	public static final String SCS_GROUP2 = "第２グループ";
	
}
