package jp.co.netscs.weeklyreport.linesystem.commons;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.netscs.weeklyreport.linesystem.commons.daos.LineSceneDao;
import jp.co.netscs.weeklyreport.linesystem.commons.daos.UserDao;
import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LineSectionDto;
import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LineSectionDto.LineSectionDtoBuilder;
import jp.co.netscs.weeklyreport.linesystem.commons.entitis.LineSceneEntity;
import jp.co.netscs.weeklyreport.linesystem.commons.util.LineBotConstant;
import lombok.NonNull;

@Service
public class WeeklyReportSectionServiceImpl implements WeeklyReportSectionService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private LineSceneDao lineSceneDao;

	/** 
	 * TODO 本番実装
	 * 
	 */
	@Override
	public LineSectionDto fetchUserSection(LinePostInfoDto lineInfo, boolean isPostBack) {
		LineSectionDtoBuilder result = LineSectionDto.builder();
		
		if (!userDao.exists(lineInfo.getUserId())) {
			result.section(LineBotConstant.SCTION_REGIST).scene(LineBotConstant.REGIST_SCENE_START);
		}
		
		LineSectionDto keyword = fetchKeyWordSection(lineInfo.getText());
		if (keyword != null) {
			return keyword;
		}
		
		LineSceneEntity sectionInfo =  lineSceneDao.getOne(lineInfo.getUserId());
		result.scene(sectionInfo.getScene()).section(sectionInfo.getSection());
		return result.build();
	}
	
	protected LineSectionDto fetchKeyWordSection(@NonNull String text) {
		LineSectionDtoBuilder result = LineSectionDto.builder();
		
		switch (text) {
			case LineBotConstant.SCTION_REPORT:
				return result.section(LineBotConstant.SCTION_REPORT).scene(LineBotConstant.REPORT_SCENE_DATE).build();
			case LineBotConstant.SCTION_REPORTVIEW:
				return result.section(LineBotConstant.SCTION_REPORTVIEW).scene(LineBotConstant.REPORTVIEW_SCENE_VIEW).build();
			case LineBotConstant.SCTION_TEAMREPORTVIEW:
				return result.section(LineBotConstant.SCTION_TEAMREPORTVIEW).scene(LineBotConstant.TEAMREPORTVIEW_SCENE_SELECTUSER).build();
		}
		return null;
	}

}
