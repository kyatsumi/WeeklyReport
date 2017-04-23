package jp.co.netscs.weeklyreport.linesystem.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant;

/**
 * このアノテーションは１つのシーン処理であることを示す<br>
 * このアノテーションを付与したメソッドの戻り値は{@code List<Message>}であること<br>
 * このアノテーションを付与したメソッドの引数の型は{@code LinePostInfoDto,UserEntity}どちらか及び両方であること<br>
 * サンプルは以下
 * <blockquote><pre>
 * {@code @Service}
 * public class SampleServiceImpl extends SampleService{<br>
 * 
 *  {@code @Scene(LineBotConstant.REGIST_SCENE_START) }
 *  public List＜Message＞ start(LinePostInfoDto dto, UserEntity user) {
 *  	//more code....
 *  }
 * }
 * </pre></blockquote>
 * @author SCS036
 *
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Scene {
	/**
	 * メソッドのシーン名を設定する。
	 */
	String sceneName();
	
	/**
	 * 次に実行するシーン名を設定する。
	 * 初期値は{@code LineBotConstant.CHAPTER_END}
	 */
	String next() default LineBotConstant.CHAPTER_END;
}
