package jp.co.netscs.weeklyreport.linesystem.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.netscs.weeklyreport.linesystem.common.daos.LineSceneDao;
import jp.co.netscs.weeklyreport.linesystem.common.daos.UserDao;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LineChapterDto;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LineChapterDto.LineChapterDtoBuilder;
import jp.co.netscs.weeklyreport.linesystem.common.entitis.LineSceneEntity;
import jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant;

@Service
@Transactional
public class WeeklyReportChapterServiceImpl implements WeeklyReportChapterService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private LineSceneDao lineSceneDao;
	
	@Autowired
	private ChapterManager manager;

	/** 
	 * TODO 本番実装
	 * 
	 */
	@Override
	public LineChapterDto fetchUserChapter(LinePostInfoDto lineInfo) {
		LineChapterDtoBuilder result = LineChapterDto.builder();
		
		if (!userDao.exists(lineInfo.getUserId())) {
			return result.chapter(LineBotConstant.CHAPTER_REGIST).scene(LineBotConstant.REGIST_SCENE_START).sceneAfter(LineBotConstant.CHAPTER_REGIST).build();
		}
		
		LineChapterDto keyword = manager.keywordMatchCapchar(lineInfo.getText());
		if (keyword != null) {
			return keyword;
		}
		
		LineSceneEntity chapterInfo =  lineSceneDao.getOne(lineInfo.getUserId());
		result.scene(chapterInfo.getScene()).chapter(chapterInfo.getChapter()).sceneAfter(chapterInfo.getAfterScene());
		return result.build();
	}

}
