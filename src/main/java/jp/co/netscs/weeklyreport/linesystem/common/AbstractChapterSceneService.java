package jp.co.netscs.weeklyreport.linesystem.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.linecorp.bot.model.message.Message;

import jp.co.netscs.weeklyreport.linesystem.common.annotation.Scene;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.ChapterResultDto;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.common.exception.WeeklyReportException;
import jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant;

/**
 * このクラスを継承して一つのチャプター（Chapter)を作ることができる
 * このクラスに作ったメソッドに{@code @Sccene}を付与することでシーンとして扱うことができる
 * 
 * @author SCS036
 *
 */
public abstract class AbstractChapterSceneService {
	
	protected AbstractChapterSceneService(ChapterManager manager) {
		if (manager == null) {
			throw new NullPointerException("マネージャーがnull");
		}
		manager.registSection(this);
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public ChapterResultDto execute(String scene, LinePostInfoDto lineInfo) {
		//TODO ストリーム微妙
		List<Method> targetScene = Arrays.asList(this.getClass().getDeclaredMethods())
			.stream()
			.filter(method -> method.isAnnotationPresent(Scene.class))
			.filter(method -> ((Scene)method.getAnnotation(Scene.class)).name().equals(scene))
			.collect(Collectors.toList());
		
		if (targetScene.isEmpty()) {
			throw new RuntimeException("指定されたシーン名の対象メソッドが存在しません。");
		}
		
		if (targetScene.size() > 1) {
			throw new RuntimeException("シーン名が重複したメソッドが存在します : " + scene);
		}
		
		Method targetMethod = targetScene.get(0);
		
		List<Message> sceneResult = null;
		try {
			//TODO 戻り値の型検査
			sceneResult = (List<Message>) targetMethod.invoke(this, lineInfo);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			System.out.println(e.getMessage());
			throw new WeeklyReportException("シーンメソッドの呼び出しに失敗しました。");
		}
		
		Scene sceneOption = targetMethod.getAnnotation(Scene.class);
		String nextScene = sceneOption.next().equals(LineBotConstant.CHAPTER_END) ? 
				LineBotConstant.CHAPTER_END : sceneOption.next();
		
		return ChapterResultDto.builder().nextScene(nextScene).messages(sceneResult).build();
	}
	
}
