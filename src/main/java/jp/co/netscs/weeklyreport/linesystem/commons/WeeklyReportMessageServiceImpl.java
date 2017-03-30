package jp.co.netscs.weeklyreport.linesystem.commons;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linecorp.bot.model.message.Message;

import jp.co.netscs.weeklyreport.linesystem.commons.annot.Section;
import jp.co.netscs.weeklyreport.linesystem.commons.daos.LineSceneDao;
import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LineSectionDto;
import jp.co.netscs.weeklyreport.linesystem.regist.RegistService;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author SCS036
 *
 */

@Slf4j
@Service
public class WeeklyReportMessageServiceImpl implements WeeklyReportMessageService {

	@Autowired
	private LineSceneDao lineSeceneDao;
	
	@Autowired
	private RegistService registService;
	
	/**
	 * 
	 */
	@Transactional
	@Override
	public List<Message> execute(LinePostInfoDto lineInfo, LineSectionDto section) {
		log.debug(lineInfo.toString() + " " + section.toString());
		List<Field> fieldList = Arrays.asList(this.getClass().getFields());
		List<Field> targetField = fieldList.stream().filter(field -> field.isAnnotationPresent(Section.class))
			.filter(field -> ((Section)field.getAnnotation(Section.class)).name().equals(section.getSection()))
			.collect(Collectors.toList());
		if (targetField.isEmpty()) {
			throw new RuntimeException("対象のセクションがありません。");
		}
		
		if (targetField.size() > 1) {
			throw new RuntimeException("対象のセクションが重複しています。");
		}
		
		Field target = targetField.get(0);
		Object targetSection = null;
		try {
			targetSection = target.get(this);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		List<Message> result = null;
		if (targetSection instanceof AbstractSectionService) {
			AbstractSectionService sectionService = (AbstractSectionService) targetSection;
			result = sectionService.execute(section.getScene(), lineInfo);
		}
		
		return result;
	}

}
