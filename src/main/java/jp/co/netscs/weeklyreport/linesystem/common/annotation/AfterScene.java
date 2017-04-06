package jp.co.netscs.weeklyreport.linesystem.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * シーン処理実行後に入力があった場合の情報を受け取る
 * @author SCS036
 *
 */

@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AfterScene {

	/**
	 * 
	 * @return
	 */
	String after();
	
}
