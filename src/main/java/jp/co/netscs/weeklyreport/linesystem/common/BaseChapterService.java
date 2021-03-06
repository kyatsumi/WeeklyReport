package jp.co.netscs.weeklyreport.linesystem.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import jp.co.netscs.weeklyreport.linesystem.common.annotation.ResponseScene;
import jp.co.netscs.weeklyreport.linesystem.common.annotation.Scene;
import jp.co.netscs.weeklyreport.linesystem.common.daos.UserDao;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.ChapterResultDto;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LineChapterDto;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.ResponseSceneResultDto;
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
	
	/**レスポンスシーンのステータスコード：NEXT*/
	protected static final ResponseSceneResultDto RESULT_NEXT = new ResponseSceneResultDto(ResponseStatusCode.NEXT, null, null);
	
	@Autowired
	UserDao userDao;
	
	protected BaseChapterService(ChapterManager manager) {
		if (manager == null) {
			throw new NullPointerException("マネージャーがnull");
		}
		manager.registChapter(this);
	}
	
	/**
	 * 章の状態から実行するシーンを決定する
	 * 
	 * @param chapterInfo
	 * @param lineInfo
	 * @return
	 */
	public ChapterResultDto execute(LineChapterDto chapterInfo, LinePostInfoDto lineInfo) {
		System.out.println("chapterInfo = " + chapterInfo + " lineInfo = " + lineInfo);
		
		if (chapterInfo.getScene().equals(this.getStartSceneName()) 
				|| chapterInfo.getResponseScene().equals(LineBotConstant.CHAPTER_END)
				|| chapterInfo.getScene().equals("")) {
			return executeScene(this.getStartSceneName(), lineInfo);
		}
		
		ResponseSceneResultDto afterResult = executeResponseScene(chapterInfo.getResponseScene(), lineInfo);
		switch(afterResult.getStatusCode()) {
			case NEXT:
				return executeScene(chapterInfo.getScene(), lineInfo);
			case LOOP:
				return executeScene(chapterInfo.getResponseScene(), afterResult.getDummy());
			case NON_POSTBACK: {
				return ChapterResultDto.builder().messages(afterResult.getMessage()).nextScene(chapterInfo.getScene()).responseScene(chapterInfo.getResponseScene()).build();
			}
			default:
				throw new WeeklyReportException("シーン処理結果に不正な値が入っています。");	
		}
	}
	
	/**
	 * レスポンスシーンの呼び出しメソッド
	 * @param responseSceneName
	 * @param lineInfo
	 * @return
	 */
	private ResponseSceneResultDto executeResponseScene(String responseSceneName, LinePostInfoDto lineInfo) {
		List<Method> targetScene = Arrays.asList(this.getClass().getDeclaredMethods())
				.stream()
				.filter(method -> method.isAnnotationPresent(ResponseScene.class))
				.filter(method -> ((ResponseScene)method.getAnnotation(ResponseScene.class)).target().equals(responseSceneName))
				.collect(Collectors.toList());
		
		if (targetScene.isEmpty()) {
			return RESULT_NEXT;
		}
		
		if (targetScene.size() > 1) {
			throw new RuntimeException("結果処理シーン名が重複して存在します : " + responseSceneName);
		}
		
		//実行対象のメソッド
		Method targetMethod = targetScene.get(0);
		//postbackOnlyの処理
		ResponseScene metaData = targetMethod.getAnnotation(ResponseScene.class);
		if (metaData.postbackOnly() && metaData.postbackOnly() != lineInfo.isPostback()) {
			List<Message> message = Arrays.asList(new TextMessage(metaData.message()));
			return ResponseSceneResultDto.builder().message(message).statusCode(ResponseStatusCode.NON_POSTBACK).build();
		}
		
		
		UserEntity userInfo = new UserEntity();
		if (userDao.exists(lineInfo.getUserId())) {
			userInfo = userDao.getOne(lineInfo.getUserId());
		}
		
		try {
			ResponseSceneResultDto sceneResult = null;
			if (targetMethod.getParameterCount() == 2) {
				sceneResult = (ResponseSceneResultDto) targetMethod.invoke(this, lineInfo, userInfo);
			} else {
				sceneResult = (ResponseSceneResultDto) targetMethod.invoke(this, lineInfo);
			}
			return sceneResult;
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			System.out.println(e.getMessage());
			throw new WeeklyReportException("シーンメソッドの呼び出しに失敗しました。");
		}
		
	}
	
	/**
	 * シーンの呼び出しメソッド
	 * @param sceneName
	 * @param lineInfo
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private ChapterResultDto executeScene(String sceneName, LinePostInfoDto lineInfo) {
		List<Method> targetScene = Arrays.asList(this.getClass().getDeclaredMethods())
				.stream()
				.filter(method -> method.isAnnotationPresent(Scene.class))
				.filter(method -> ((Scene)method.getAnnotation(Scene.class)).sceneName().equals(sceneName))
				.collect(Collectors.toList());
			
		if (targetScene.isEmpty()) {
			throw new WeeklyReportException("指定されたシーンメソッドが存在しません。");
		}
		
		if (targetScene.size() > 1) {
			throw new WeeklyReportException("シーン名が重複したメソッドが存在します : " + sceneName);
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
	
	/**
	 * チャプター名を指定する
	 * @return
	 */
	public abstract String getChapterName();
	
	/**
	 * このチャプターの最初のシーン名を指定する
	 * @return
	 */
	public abstract String getStartSceneName();
	
	/**
	 * AfterSceneの実行結果
	 * @author katumi
	 *
	 */
	public enum ResponseStatusCode {
		/**
		 * 次のシーンへ進む
		 */
		NEXT,
		/**
		 * ダミー情報を使い前回のシーンを再度実行
		 */
		LOOP,
		/**
		 * postback以外の要求がきた場合に指定する<br>
		 */
		NON_POSTBACK
	}
}
