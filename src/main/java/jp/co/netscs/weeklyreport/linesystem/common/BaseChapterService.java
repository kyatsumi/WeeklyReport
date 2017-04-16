package jp.co.netscs.weeklyreport.linesystem.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.linecorp.bot.model.message.Message;

import jp.co.netscs.weeklyreport.linesystem.common.annotation.Chapter;
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
	 * 章の状態から実行するシーンを決定する
	 * ただし以下の場合はユーザ登録が実行される
	 * 
	 * @param chapterInfo
	 * @param lineInfo
	 * @return
	 */
	public ChapterResultDto execute(LineChapterDto chapterInfo, LinePostInfoDto lineInfo) {
		System.out.println("chapterInfo = " + chapterInfo + " lineInfo = " + lineInfo);
		
		if (chapterInfo.getScene().equals(this.getStartSceneName()) || chapterInfo.getResponseScene().equals(LineBotConstant.CHAPTER_END)) {
			return executeScene(chapterInfo.getScene(), lineInfo);
		}
		
		ResponseSceneResultDto afterResult = executeResponseScene(chapterInfo.getResponseScene(), lineInfo);
		switch(afterResult.getResult()) {
			case NEXT:
				return executeScene(chapterInfo.getScene(), lineInfo);
			case LOOP:
				return executeScene(chapterInfo.getResponseScene(), afterResult.getDummy());
			default:
				throw new WeeklyReportException("シーン処理結果に不正な値が入っています。");	
		}
	}
	
	private ResponseSceneResultDto executeResponseScene(String responseSceneName, LinePostInfoDto lineInfo) {
		List<Method> targetScene = Arrays.asList(this.getClass().getDeclaredMethods())
				.stream()
				.filter(method -> method.isAnnotationPresent(ResponseScene.class))
				.filter(method -> ((ResponseScene)method.getAnnotation(ResponseScene.class)).target().equals(responseSceneName))
				.collect(Collectors.toList());
		
		if (targetScene.isEmpty()) {
			return AFTER_RESULT_NEXT;
		}
		
		if (targetScene.size() > 1) {
			throw new RuntimeException("結果処理シーン名が重複して存在します : " + responseSceneName);
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
	private ChapterResultDto executeScene(String sceneName, LinePostInfoDto lineInfo) {
		List<Method> targetScene = Arrays.asList(this.getClass().getDeclaredMethods())
				.stream()
				.filter(method -> method.isAnnotationPresent(Scene.class))
				.filter(method -> ((Scene)method.getAnnotation(Scene.class)).sceneName().equals(sceneName))
				.collect(Collectors.toList());
			
		if (targetScene.isEmpty()) {
			throw new RuntimeException("指定されたシーンメソッドが存在しません。");
		}
		
		if (targetScene.size() > 1) {
			throw new RuntimeException("シーン名が重複したメソッドが存在します : " + sceneName);
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
		
		Scene sceneMetaData = targetMethod.getAnnotation(Scene.class);
		String nextSceneName = sceneMetaData.next();
		String responseSceneName = nextSceneName.equals(LineBotConstant.CHAPTER_END) ?
				LineBotConstant.CHAPTER_END : sceneName;
		
		return ChapterResultDto.builder().responseScene(responseSceneName).nextScene(nextSceneName).messages(sceneResult).build();
	}
	
	
	private final String getStartSceneName() {
		return this.getClass().getDeclaredAnnotation(Chapter.class).startScene();
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
