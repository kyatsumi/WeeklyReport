package jp.co.netscs.weeklyreport.linesystem.commons;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.linecorp.bot.model.message.Message;

import jp.co.netscs.weeklyreport.linesystem.commons.annot.Scene;
import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LinePostInfoDto;

/**
 * このクラスはセクションの基底クラス
 * @author SCS036
 *
 */
public abstract class AbstractSectionService {
	
	@SuppressWarnings("unchecked")
	public List<Message> execute(String scene, LinePostInfoDto lineInfo) {
		
		List<Method> methodList = Arrays.asList(this.getClass().getDeclaredMethods());
		List<Method> targetScene = methodList.stream().filter(method -> method.isAnnotationPresent(Scene.class))
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
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return result == null ? (List<Message>) result : null;
	}
	
}
