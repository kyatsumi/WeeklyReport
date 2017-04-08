package jp.co.netscs.weeklyreport.linesystem.common;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.persister.walking.spi.WalkingException;
import org.springframework.stereotype.Component;

import jp.co.netscs.weeklyreport.linesystem.common.annotation.Chapter;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LineChapterDto;
import jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant;

@Component
public class ChapterManagerImpl implements ChapterManager {
	
	private List<AbstractChapterSceneService> chapterList = new ArrayList<>();

	@Override
	public AbstractChapterSceneService targetSection(String targetChapter) {
		AbstractChapterSceneService service = chapterList.stream()
			.filter(section -> section.getClass().getDeclaredAnnotation(Chapter.class).name().equals(targetChapter))
			.findAny()
			.orElseThrow( () -> new WalkingException("対象のChapterが存在しません。 " + targetChapter));
		return service;
	}

	@Override
	public void registSection(AbstractChapterSceneService target) {
		
		Chapter targetAnno = target.getClass().getAnnotation(Chapter.class);
		
		if (targetAnno == null) {
			throw new WalkingException("Chapterアノテーションが付与されていません。 " + target.getClass().getName());
		}
		
		boolean isOverlap  = chapterList.stream()
			.anyMatch(src -> src.getClass().getAnnotation(Chapter.class).name().equals(targetAnno.name()));
		
		if(isOverlap) {
			throw new WalkingException("登録されるセクションが重複しています。 " + targetAnno.name());
		}
		
		chapterList.add(target);
	}

	/**
	 * キーワードに一致するチャプターが存在する場合は状態を返す
	 * チャプターが存在しない場合はnullを返す
	 */
	@Override
	public LineChapterDto keywordMatchCapchar(String keyword) {
		Chapter chapterInfo = chapterList.stream()
			.map(chapter -> chapter.getClass().getAnnotation(Chapter.class))
			.filter(anno -> anno.name().equals(keyword))
			.findFirst()
			.orElse(null);
		
		if (chapterInfo == null) {
			return null;
		}
		
		return LineChapterDto.builder().chapter(chapterInfo.name()).scene(chapterInfo.startScene()).sceneAfter(LineBotConstant.CHAPTER_END).build();
	}

}
