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

/**
 * 
 * @author SCS036
 *
 */

@Service
public class WeeklyReportMessageServiceImpl implements WeeklyReportMessageService {

	@Autowired
	LineSceneDao lineSeceneDao;
	
	@Autowired
	RegistService registService;
	
	/**
	 * 
	 */
	@Transactional
	@Override
	public List<Message> execute(LinePostInfoDto lineInfo, LineSectionDto section) {
		List<Field> fieldList = Arrays.asList(this.getClass().getFields());
		List<Field> targetField = fieldList.stream().filter(field -> field.getType().getSuperclass().equals(AbstractSectionService.class))
				.collect(Collectors.toList());
		
		System.out.println(targetField);
		
		List<AbstractSectionService> targetList = targetField.stream().map(field -> {
				try {
					return ((AbstractSectionService)field.get(this));
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				throw new RuntimeException("対象のセクションがありません。");
			})
			.filter(sectionService -> ((Section)sectionService.getClass().getAnnotation(Section.class)).name().equals(section.getSection()))
			.collect(Collectors.toList());
		
		System.out.println(targetList);
		
		if (targetList.isEmpty()) {
			throw new RuntimeException("対象のセクションがありません。 " + targetList.toString());
		}
		
		if (targetList.size() > 1) {
			throw new RuntimeException("対象のセクションが重複しています。");
		}
		
		AbstractSectionService target = targetList.get(0);
		List<Message> result = target.execute(section.getScene(), lineInfo);
		return result;
	}

}
