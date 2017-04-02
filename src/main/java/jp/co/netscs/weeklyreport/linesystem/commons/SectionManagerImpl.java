package jp.co.netscs.weeklyreport.linesystem.commons;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.persister.walking.spi.WalkingException;
import org.springframework.stereotype.Component;

import jp.co.netscs.weeklyreport.linesystem.commons.annot.Section;

@Component
public class SectionManagerImpl implements SectionManager {
	
	private List<AbstractSectionService> sectionList = new ArrayList<>();

	@Override
	public AbstractSectionService targetSection(String targetSection) {
		AbstractSectionService service = sectionList.stream()
			.filter(section -> section.getClass().getDeclaredAnnotation(Section.class).name().equals(targetSection))
			.findAny()
			.orElseThrow( () -> new WalkingException("対象のセクションが存在しません。 " + targetSection));
		return service;
	}

	@Override
	public void registSection(AbstractSectionService target) {
		
		Section targetAnno = target.getClass().getDeclaredAnnotation(Section.class);
		
		if (targetAnno == null) {
			throw new WalkingException("セクションアノテーションが付与されていません。 " + target.getClass().getName());
		}
		
		boolean isOverlap  = sectionList.stream()
			.anyMatch(src -> src.getClass().getDeclaredAnnotation(Section.class).name().equals(targetAnno.name()));
		
		if(isOverlap) {
			throw new WalkingException("登録されるセクションが重複しています。 " + targetAnno.name());
		}
		
		sectionList.add(target);
	}

}
