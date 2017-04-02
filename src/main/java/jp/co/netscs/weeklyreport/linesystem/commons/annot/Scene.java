package jp.co.netscs.weeklyreport.linesystem.commons.annot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jp.co.netscs.weeklyreport.linesystem.commons.util.LineBotConstant;

/**
 * このアノテーションは１つのシーン処理であることを示す<br>
 * ユーザのセクションが登録でシーンが新規登録の場合startメソッドが呼び出されるように設定する使い方は以下<br>
 * {@code @Section(LineBotConstant.SCTION_REGIST)}<br>
 * public class RegstService {<br>
 * 
 *  {@code @Scene(LineBotConstant.REGIST_SCENE_START) }<br>
 *  public List＜Message＞ start(LinePostInfoDto dto) {<br>
 *  	//code<br>
 *  }<br>
 *  
 * }<br>
 * @author SCS036
 *
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Scene {
	/**
	 * 呼び出される場面名を登録する
	 */
	String name();
	
	/**
	 * 次に実行するシーンを決定する
	 */
	String next() default LineBotConstant.UNKNOWN;
}
