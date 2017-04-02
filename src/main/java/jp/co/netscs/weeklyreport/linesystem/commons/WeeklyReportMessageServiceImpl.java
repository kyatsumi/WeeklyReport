package jp.co.netscs.weeklyreport.linesystem.commons;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linecorp.bot.model.message.Message;

import jp.co.netscs.weeklyreport.linesystem.commons.daos.LineSceneDao;
import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LineSectionDto;
import jp.co.netscs.weeklyreport.linesystem.commons.dtos.SectionResultDto;
import jp.co.netscs.weeklyreport.linesystem.commons.entitis.LineSceneEntity;
import jp.co.netscs.weeklyreport.linesystem.commons.util.DateUtils;

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
	SectionManager manager;
	
	/**
	 * 
	 */
	@Transactional
	@Override
	public List<Message> execute(LinePostInfoDto lineInfo, LineSectionDto section) {
		System.out.println(lineInfo.toString() + " " + section.toString());
		AbstractSectionService target = manager.targetSection(section.getSection());
		SectionResultDto result = target.execute(section.getScene(), lineInfo);
		
		LineSceneEntity nextScene = LineSceneEntity.builder()
				.lineId(lineInfo.getUserId())
				.periodTime(DateUtils.generatePeriodTime())
				.section(section.getSection())
				.scene(result.getNextScene())
				.build();
		
		System.out.println(nextScene);
		
		lineSeceneDao.save(nextScene);
		
		return result.getMessages();
	}

}
