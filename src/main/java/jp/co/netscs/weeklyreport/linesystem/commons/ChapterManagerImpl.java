package jp.co.netscs.weeklyreport.linesystem.commons;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.persister.walking.spi.WalkingException;
import org.springframework.stereotype.Component;

import jp.co.netscs.weeklyreport.linesystem.commons.annot.Chapter;

@Component
public class ChapterManagerImpl implements ChapterManager {
	
	private List<AbstractChapterSceneService> chapterList = new ArrayList<>();

	@Override
	public AbstractChapterSceneService targetSection(String targetChapter) {
		AbstractChapterSceneService service = chapterList.stream()
			.filter(section -> section.getClass().getDeclaredAnnotation(Chapter.class).name().equals(targetChapter))
			.findAny()
			.orElseThrow( () -> new WalkingException("対象のセクションが存在しません。 " + targetChapter));
		return service;
	}

	@Override
	public void registSection(AbstractChapterSceneService target) {
		
		Chapter targetAnno = target.getClass().getDeclaredAnnotation(Chapter.class);
		
		if (targetAnno == null) {
			throw new WalkingException("セクションアノテーションが付与されていません。 " + target.getClass().getName());
		}
		
		boolean isOverlap  = chapterList.stream()
			.anyMatch(src -> src.getClass().getDeclaredAnnotation(Chapter.class).name().equals(targetAnno.name()));
		
		if(isOverlap) {
			throw new WalkingException("登録されるセクションが重複しています。 " + targetAnno.name());
		}
		
		chapterList.add(target);
	}

}
