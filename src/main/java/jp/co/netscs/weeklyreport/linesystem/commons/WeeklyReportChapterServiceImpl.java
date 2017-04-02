package jp.co.netscs.weeklyreport.linesystem.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.netscs.weeklyreport.linesystem.commons.daos.LineSceneDao;
import jp.co.netscs.weeklyreport.linesystem.commons.daos.UserDao;
import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LineChapterDto;
import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LineChapterDto.LineChapterDtoBuilder;
import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.commons.entitis.LineSceneEntity;
import jp.co.netscs.weeklyreport.linesystem.commons.util.LineBotConstant;
import lombok.NonNull;

@Service
@Transactional
public class WeeklyReportChapterServiceImpl implements WeeklyReportChapterService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private LineSceneDao lineSceneDao;

	/** 
	 * TODO 本番実装
	 * 
	 */
	@Override
	public LineChapterDto fetchUserSection(LinePostInfoDto lineInfo, boolean isPostBack) {
		LineChapterDtoBuilder result = LineChapterDto.builder();
		
		if (!userDao.exists(lineInfo.getUserId())) {
			return result.chapter(LineBotConstant.CHAPTER_REGIST).scene(LineBotConstant.REGIST_SCENE_START).build();
		}
		
		LineChapterDto keyword = fetchKeyWordSection(lineInfo.getText());
		if (keyword != null) {
			return keyword;
		}
		
		LineSceneEntity sectionInfo =  lineSceneDao.getOne(lineInfo.getUserId());
		result.scene(sectionInfo.getScene()).chapter(sectionInfo.getChapter());
		return result.build();
	}
	
	protected LineChapterDto fetchKeyWordSection(@NonNull String text) {
		LineChapterDtoBuilder result = LineChapterDto.builder();
		
		switch (text) {
			case LineBotConstant.CHAPTER_REPORT:
				return result.chapter(LineBotConstant.CHAPTER_REPORT).scene(LineBotConstant.REPORT_SCENE_DATE).build();
			case LineBotConstant.CHAPTER_REPORTVIEW:
				return result.chapter(LineBotConstant.CHAPTER_REPORTVIEW).scene(LineBotConstant.REPORTVIEW_SCENE_VIEW).build();
			case LineBotConstant.CHAPTER_TEAMREPORTVIEW:
				return result.chapter(LineBotConstant.CHAPTER_TEAMREPORTVIEW).scene(LineBotConstant.TEAMREPORTVIEW_SCENE_SELECTUSER).build();
		}
		return null;
	}

}
