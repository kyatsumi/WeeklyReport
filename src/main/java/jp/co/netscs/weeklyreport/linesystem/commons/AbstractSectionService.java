package jp.co.netscs.weeklyreport.linesystem.commons;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.linecorp.bot.model.message.Message;

import jp.co.netscs.weeklyreport.linesystem.commons.annot.Scene;
import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.commons.dtos.SectionResultDto;
import jp.co.netscs.weeklyreport.linesystem.commons.exce.WeeklyReportException;
import jp.co.netscs.weeklyreport.linesystem.commons.util.LineBotConstant;

/**
 * このクラスはセクションの基底クラス
 * @author SCS036
 *
 */
public abstract class AbstractSectionService {
	
	@Autowired
	protected SectionManager manager;
	
	
	protected AbstractSectionService() {
		if (manager == null) {
			System.out.println("マネージャーがnull");
		}
		manager.registSection(this);
		System.out.println("インスタンス化されました" + this.getClass().getName());
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public SectionResultDto execute(String scene, LinePostInfoDto lineInfo) {
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
		Scene sceneOption = targetMethod.getAnnotation(Scene.class);
		List<Message> sceneResult = null;
		try {
			//TODO 戻り値の型検査
			sceneResult = (List<Message>) targetMethod.invoke(this, lineInfo);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new WeeklyReportException("シーンメソッドの呼び出しに失敗しました。");
		}
		
		String nextScene = sceneOption.next().equals(LineBotConstant.UNKNOWN) ? 
				LineBotConstant.UNKNOWN : sceneOption.next();
		
		return SectionResultDto.builder().nextScene(nextScene).messages(sceneResult).build();
	}
	
}
