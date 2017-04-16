package jp.co.netscs.weeklyreport.linesystem.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.linecorp.bot.model.message.Message;

import jp.co.netscs.weeklyreport.linesystem.common.annotation.ResponseScene;
import jp.co.netscs.weeklyreport.linesystem.common.annotation.Scene;
import jp.co.netscs.weeklyreport.linesystem.common.daos.UserDao;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.ResponseSceneResultDto;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.ChapterResultDto;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LineChapterDto;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.common.entitis.UserEntity;
import jp.co.netscs.weeklyreport.linesystem.common.exception.WeeklyReportException;
import jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant;

/**
 * このクラスを継承して一つのチャプター（章)を作ることができる
 * このクラスに作ったメソッドに{@code @Sccene}を付与することでシーンとして扱うことができる
 * 
 * @author SCS036
 *
 */
@Transactional
public abstract class BaseChapterService {
	
	protected static final ResponseSceneResultDto AFTER_RESULT_NEXT = new ResponseSceneResultDto(ResponseResult.NEXT, null);
	
	@Autowired
	UserDao userDao;
	
	protected BaseChapterService(ChapterManager manager) {
		if (manager == null) {
			throw new NullPointerException("マネージャーがnull");
		}
		manager.registSection(this);
	}
	
	/**
	 * 呼び出しメソッド
	 * @param scene
	 * @param lineInfo
	 * @return
	 */
	public ChapterResultDto execute(LineChapterDto scene, LinePostInfoDto lineInfo) {
		System.out.println("scene = " + scene + " lineInfo = " + lineInfo);
		if (scene.getResponseScene().equals(LineBotConstant.CHAPTER_REGIST) || scene.getResponseScene().equals(LineBotConstant.UNKNOWN_SCENE)) {
			return executeScene(scene.getScene(), lineInfo);
		}
		
		ResponseSceneResultDto afterResult = executeSceneAfter(scene.getResponseScene(), lineInfo);
		switch(afterResult.getResult()) {
			case NEXT:
				return executeScene(scene.getScene(), lineInfo);
			case LOOP:
				return executeScene(scene.getResponseScene(), afterResult.getDummy());
			default:
				throw new WeeklyReportException("シーン処理結果で問題が発生しました");	
		}
	}
	
	private ResponseSceneResultDto executeSceneAfter(String afterScene, LinePostInfoDto lineInfo) {
		List<Method> targetScene = Arrays.asList(this.getClass().getDeclaredMethods())
				.stream()
				.filter(method -> method.isAnnotationPresent(ResponseScene.class))
				.filter(method -> ((ResponseScene)method.getAnnotation(ResponseScene.class)).scene().equals(afterScene))
				.collect(Collectors.toList());
		
		if (targetScene.isEmpty()) {
			return AFTER_RESULT_NEXT;
		}
		
		if (targetScene.size() > 1) {
			throw new RuntimeException("アフターシーン名が重複して存在します : " + afterScene);
		}
		
		Method targetMethod = targetScene.get(0);
		
		UserEntity userInfo = new UserEntity();
		if (userDao.exists(lineInfo.getUserId())) {
			userInfo = userDao.getOne(lineInfo.getUserId());
		}
		
		ResponseSceneResultDto sceneResult = null;
		try {
			//TODO 戻り値の型検査
			if (targetMethod.getParameterCount() == 2) {
				sceneResult = (ResponseSceneResultDto) targetMethod.invoke(this, lineInfo, userInfo);
			} else {
				sceneResult = (ResponseSceneResultDto) targetMethod.invoke(this, lineInfo);
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			System.out.println(e.getMessage());
			throw new WeeklyReportException("シーンメソッドの呼び出しに失敗しました。");
		}
		
		return sceneResult;
	}
	

	@SuppressWarnings("unchecked")
	private ChapterResultDto executeScene(String scene, LinePostInfoDto lineInfo) {
		List<Method> targetScene = Arrays.asList(this.getClass().getDeclaredMethods())
				.stream()
				.filter(method -> method.isAnnotationPresent(Scene.class))
				.filter(method -> ((Scene)method.getAnnotation(Scene.class)).name().equals(scene))
				.collect(Collectors.toList());
			
		if (targetScene.isEmpty()) {
			throw new RuntimeException("指定されたシーンメソッドが存在しません。");
		}
		
		if (targetScene.size() > 1) {
			throw new RuntimeException("シーン名が重複したメソッドが存在します : " + scene);
		}
		
		Method targetMethod = targetScene.get(0);
		
		UserEntity userInfo = new UserEntity();
		if (userDao.exists(lineInfo.getUserId())) {
			userInfo = userDao.getOne(lineInfo.getUserId());
		}
		
		List<Message> sceneResult = null;
		try {
			//TODO 戻り値の型検査
			if (targetMethod.getParameterCount() == 2) {
				sceneResult = (List<Message>) targetMethod.invoke(this, lineInfo, userInfo);
			} else {
				sceneResult = (List<Message>) targetMethod.invoke(this, lineInfo);
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			System.out.println(e.getMessage());
			throw new WeeklyReportException("シーンメソッドの呼び出しに失敗しました。");
		}
		
		Scene sceneOption = targetMethod.getAnnotation(Scene.class);
		String nextScene = sceneOption.next().equals(LineBotConstant.CHAPTER_END) ? 
				LineBotConstant.CHAPTER_END : sceneOption.next();
		String afterScene = nextScene.equals(LineBotConstant.CHAPTER_END) ?
				LineBotConstant.UNKNOWN_SCENE : scene;
		
		return ChapterResultDto.builder().responseScene(afterScene).nextScene(nextScene).messages(sceneResult).build();
	}
	
	/**
	 * AfterSceneの実行結果
	 * @author katumi
	 *
	 */
	public enum ResponseResult {
		/**
		 * 次のシーンへ進む
		 */
		NEXT,
		/**
		 * ダミー情報を使い前回のシーンを再度実行
		 */
		LOOP
	}
}
