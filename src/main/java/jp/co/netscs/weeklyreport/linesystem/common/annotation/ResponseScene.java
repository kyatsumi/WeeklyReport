package jp.co.netscs.weeklyreport.linesystem.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * シーン処理実行後に入力があった場合の情報を受け取る<br>
 * このアノテーションを付与したメソッドの戻り値は必ず{@code ResponseSceneResultDto}であること<br>
 * @author SCS036
 *
 */

@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ResponseScene {

	/**
	 * targetの表示後ユーザから返答があった場合に呼び出される。
	 */
	String target();
	
	/**
	 * このパラメタをtrueにした場合postback以外から要求が来た場合に<br>
	 * messageの文言をユーザに返信する。<br>
	 */
	boolean postbackOnly() default false;
	
	/**
	 * postbackOnlyがtrueの場合にpostback以外からきた時に表示するメッセージ
	 */
	String message() default "選択肢から選び直してください。";
}
