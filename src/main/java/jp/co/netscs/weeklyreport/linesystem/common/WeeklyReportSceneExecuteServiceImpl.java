package jp.co.netscs.weeklyreport.linesystem.common;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import jp.co.netscs.weeklyreport.linesystem.common.daos.LineSceneDao;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.ChapterResultDto;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LineChapterDto;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.common.entitis.LineSceneEntity;
import jp.co.netscs.weeklyreport.linesystem.common.util.DateUtils;
import jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant;

/**
 * 
 * @author SCS036
 *
 */

@Service
public class WeeklyReportSceneExecuteServiceImpl implements WeeklyReportSceneExecuteService {


	@Autowired
	WeeklyReportChapterService chapterService;
	
	@Autowired
	LineSceneDao lineSeceneDao;
	
	@Autowired
	ChapterManager manager;
	
	/**
	 * 
	 */
	@Transactional
	@Override
	public List<Message> execute(LinePostInfoDto lineInfo) {
		LineChapterDto chapter = chapterService.fetchUserChapter(lineInfo);
		if (chapter.getScene().equals(LineBotConstant.CHAPTER_END)) {
			return Arrays.asList(new TextMessage("チェックから操作を選択してください。"));
		}
		AbstractChapterSceneService target = manager.targetSection(chapter.getChapter());
		ChapterResultDto result = target.execute(chapter, lineInfo);
		
		LineSceneEntity nextScene = LineSceneEntity.builder()
				.lineId(lineInfo.getUserId())
				.periodTime(DateUtils.generatePeriodTime())
				.chapter(chapter.getChapter())
				.scene(result.getNextScene())
				.build();
		
		lineSeceneDao.save(nextScene);
		
		return result.getMessages();
	}

}
