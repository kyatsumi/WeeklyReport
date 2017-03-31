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
import jp.co.netscs.weeklyreport.linesystem.commons.dtos.SectionResultDto;
import jp.co.netscs.weeklyreport.linesystem.commons.entitis.LineSceneEntity;
import jp.co.netscs.weeklyreport.linesystem.commons.util.DateUtils;
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
		//TODO ストリーム複雑すぎる
		List<AbstractSectionService> executeService = Arrays.asList(this.getClass().getDeclaredFields())
			.stream()
			.map(field -> {
				try {
					return field.get(this);
				} catch (IllegalArgumentException | IllegalAccessException e) {
					throw new RuntimeException("フィールドを取得できません" + " " + field.getName());
				}
			})
			.filter(object -> object instanceof AbstractSectionService)
			.map(object -> AbstractSectionService.class.cast(object))
			.filter(service -> service.getClass().getDeclaredAnnotation(Section.class).name().equals(section.getSection()))
			.collect(Collectors.toList());
		

		if (executeService.isEmpty()) {
			throw new RuntimeException("対象のセクションがありません。 ");
		}
		
		if (executeService.size() > 1) {
			throw new RuntimeException("対象のセクションが重複しています。");
		}
		
		AbstractSectionService target = executeService.get(0);
		SectionResultDto result = target.execute(section.getScene(), lineInfo);
		
		LineSceneEntity nextScene = LineSceneEntity.builder()
				.lineId(lineInfo.getUserId())
				.periodTime(DateUtils.generatePeriodTime())
				.section(section.getSection())
				.scene(result.getNextScene())
				.build();
		
		lineSeceneDao.save(nextScene);
		
		return result.getMessages();
	}

}
