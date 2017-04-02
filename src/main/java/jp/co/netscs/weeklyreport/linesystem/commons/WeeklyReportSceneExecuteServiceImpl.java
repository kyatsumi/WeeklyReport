package jp.co.netscs.weeklyreport.linesystem.commons;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linecorp.bot.model.message.Message;

import jp.co.netscs.weeklyreport.linesystem.commons.daos.LineSceneDao;
import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LineChapterDto;
import jp.co.netscs.weeklyreport.linesystem.commons.dtos.SectionResultDto;
import jp.co.netscs.weeklyreport.linesystem.commons.entitis.LineSceneEntity;
import jp.co.netscs.weeklyreport.linesystem.commons.util.DateUtils;

/**
 * 
 * @author SCS036
 *
 */

@Service
public class WeeklyReportSceneExecuteServiceImpl implements WeeklyReportSceneExecuteService {

	@Autowired
	LineSceneDao lineSeceneDao;
	
	@Autowired
	SectionManager manager;
	
	/**
	 * 
	 */
	@Transactional
	@Override
	public List<Message> execute(LinePostInfoDto lineInfo, LineChapterDto chapter) {
		System.out.println(lineInfo.toString() + " " + chapter.toString());
		AbstractSectionService target = manager.targetSection(chapter.getChapter());
		SectionResultDto result = target.execute(chapter.getScene(), lineInfo);
		
		LineSceneEntity nextScene = LineSceneEntity.builder()
				.lineId(lineInfo.getUserId())
				.periodTime(DateUtils.generatePeriodTime())
				.chapter(chapter.getChapter())
				.scene(result.getNextScene())
				.build();
		
		System.out.println(nextScene);
		
		lineSeceneDao.save(nextScene);
		
		return result.getMessages();
	}

}
