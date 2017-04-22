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
	 * 週報確認:表示する週を選択
	 */
	public static final String REPORTVIEW_SCENE_SELECT_WEEKS_VIEW = "週報確認表示する週を選択";
	
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
	
	/**
	 * 不明なシーン
	 */
	public static final String UNKNOWN_SCENE = "不明なシーン";
	
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
	
	/**
	 *　Lineの選択肢用定数
	 */
	public static final String YES = "はい";
	
	/**
	 *　Lineの選択肢用定数
	 */
	public static final String NO = "いいえ";
	
	/**
	 * Lineの選択肢用定数:登録
	 */
	public static final String REGIST = "登録";
	
	/**
	 * Lineの選択肢用定数:キャンセル
	 */
	public static final String CANCEL = "キャンセル";
	
	/**
	 * 来週
	 */
	public static final String NEXT_WEEK_VIEW = "来週の日付を表示";
	
	/**
	 * 先週
	 */
	public static final String LAST_WEEK_VIEW = "先週の日付を表示";
	
	/**
	 * １週間の長さ
	 */
	public static final Integer ONE_WEEK_DAYS = 7;
}
