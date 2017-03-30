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

@Service
public class WeeklyReportSectionServiceImpl implements WeeklyReportSectionService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private LineSceneDao lineSceneDao;

	/** 
	 * TODO 仮実装の廃止
	 * 
	 */
	@Override
	public LineSectionDto fetchUserSection(LinePostInfoDto lineInfo, boolean isPostBack) {
		LineSectionDtoBuilder result = LineSectionDto.builder();
		
		if (userDao.exists(lineInfo.getUserId())) {
			result.section(LineBotConstant.SCTION_REGIST).scene(LineBotConstant.REGIST_SCENE_START);
		}
		
		LineSceneEntity sectionInfo =  lineSceneDao.getOne(lineInfo.getUserId());
		result.scene(sectionInfo.getScene()).section(sectionInfo.getSection());
		return result.build();
	}

}
