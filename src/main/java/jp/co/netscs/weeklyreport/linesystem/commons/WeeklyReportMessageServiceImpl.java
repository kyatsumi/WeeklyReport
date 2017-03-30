package jp.co.netscs.weeklyreport.linesystem.commons;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linecorp.bot.model.message.Message;

import jp.co.netscs.weeklyreport.linesystem.commons.daos.LineSceneDao;
import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LineSectionDto;
import jp.co.netscs.weeklyreport.linesystem.regist.RegistService;
import sun.print.resources.serviceui;

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
		
		System.out.println(fieldList);
		
		List<Object> fieldObject = fieldList.stream().map(field -> {
			try {
				return field.get(this);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			throw new RuntimeException("フィールドを取得できません" + " " + this.getClass());
		}).collect(Collectors.toList());
		
		System.out.println(fieldObject);
		
		List<AbstractSectionService> sectionList = fieldObject.stream()
				.filter(object -> object instanceof AbstractSectionService)
				.map(object -> AbstractSectionService.class.cast(object))
				.collect(Collectors.toList());
		
		System.out.println(sectionList);

		if (sectionList.isEmpty()) {
			throw new RuntimeException("対象のセクションがありません。 " + fieldList.toString());
		}
		
		if (sectionList.size() > 1) {
			throw new RuntimeException("対象のセクションが重複しています。");
		}
		
		/**
		AbstractSectionService target = fieldList.get(0);
		List<Message> result = target.execute(section.getScene(), lineInfo);
		return result;
		*/
		return null;
	}

}
