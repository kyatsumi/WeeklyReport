package jp.co.netscs.weeklyreport.linesystem.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.stereotype.Service;

/**
 * このアノテーションが付与されたクラスはチャプターであることを示す<br>
 * 使用方法のサンプル<br>
 * <blockquote><pre>
 * {@code @Chapter(name= チャプター名を指定, startScene= 最初のシーン名を指定)}
 * public class SampleChapterImpl extends SampleChapter {
 * 		{@code @Scene(sceneName= 最初のシーン名を指定)}
 * 		public List{@code <Message>} 最初のシーン() {
 * 		//なんらかのコードを書く
 * 		} 
 * }
 * </pre></blockquote>
 * 最初のシーン名に指定したメソッドが存在しない場合はエラーが発生する<br>
 * @see jp.co.netscs.weeklyreport.linesystem.common.BaseChapterService
 * @author SCS036
 *
 */

@Inherited
@Service
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Chapter {
	/**
	 * Lineで入力された内容が等しい場合この章の{@code startScene}に指定したのが呼び出される
	 * @return　チャプター名
	 */
	public String name();
	
	/**
	 * 章の１番最初のシーン<br>
	 * 前の章が終了した場合やLineでチャプター名が入力された場合にこの名前のシーンが実行される
	 * @return
	 */
	public String startScene();
}
