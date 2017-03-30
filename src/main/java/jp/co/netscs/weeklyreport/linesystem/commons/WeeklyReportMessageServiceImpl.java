package jp.co.netscs.weeklyreport.linesystem.commons;

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
		System.out.println(section);
		List<AbstractSectionService> executeService = Arrays.asList(this.getClass().getDeclaredFields())
			.stream()
			.map(field -> {
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
			})
			.filter(object -> object instanceof AbstractSectionService)
			.map(object -> AbstractSectionService.class.cast(object))
			.peek(System.out::println)
			.filter(service -> service.getClass().getDeclaredAnnotation(Section.class).name().equals(section.getSection()))
			.collect(Collectors.toList());
		

		if (executeService.isEmpty()) {
			throw new RuntimeException("対象のセクションがありません。 ");
		}
		
		if (executeService.size() > 1) {
			throw new RuntimeException("対象のセクションが重複しています。");
		}
		
		AbstractSectionService target = executeService.get(0);
		List<Message> result = target.execute(section.getScene(), lineInfo);
		return result;
	}

}
