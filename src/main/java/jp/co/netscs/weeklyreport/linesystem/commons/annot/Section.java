package jp.co.netscs.weeklyreport.linesystem.commons.annot;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * このアノテーションはセクションサービスであることを示す<br>
 * ユーザのセクションが登録の場合このクラスが呼び出されるように設定ができる<br>
 * 使い方の例を以下に示す<br>
 * {@code @Section(LineBotConstant.SCTION_REGIST)}<br>
 * public class RegstService {<br>
 *  //code<br>
 * }<br>
 * @author SCS036
 *
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Section {
	/**
	 * @return　セクション名
	 */
	public String name();
}
