package jp.co.netscs.weeklyreport.linesystem.commons;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.linecorp.bot.model.message.Message;

import jp.co.netscs.weeklyreport.linesystem.commons.annot.Scene;
import jp.co.netscs.weeklyreport.linesystem.commons.daos.LineSceneDao;
import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.commons.exce.WeeklyReportException;

/**
 * このクラスはセクションの基底クラス
 * @author SCS036
 *
 */
public abstract class AbstractSectionService {
	
	@Autowired
	LineSceneDao lineSceneDao;
	
	@SuppressWarnings("unchecked")
	public List<Message> execute(String scene, LinePostInfoDto lineInfo) {
		
		List<Method> methodList = Arrays.asList(this.getClass().getDeclaredMethods());
		List<Method> targetScene = methodList.stream()
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
		
		Object result = null;
		try {
			result = targetMethod.invoke(this, lineInfo);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new WeeklyReportException("シーンメソッドの呼び出しに失敗しました。");
		}
		
		return result != null ? (List<Message>) result : null;
	}
	
}
