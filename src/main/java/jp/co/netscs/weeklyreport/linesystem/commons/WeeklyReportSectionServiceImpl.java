package jp.co.netscs.weeklyreport.linesystem.commons;

import org.springframework.stereotype.Service;

import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LineSectionDto;
import jp.co.netscs.weeklyreport.linesystem.commons.util.LineBotConstant;

@Service
public class WeeklyReportSectionServiceImpl implements WeeklyReportSectionService {

	/** 
	 * TODO 仮実装の廃止
	 * 
	 */
	@Override
	public LineSectionDto fetchUserSection(LinePostInfoDto lineInfo, boolean isPostBack) {
		return LineSectionDto.builder()
				.scene(LineBotConstant.SCTION_REGIST)
				.section(LineBotConstant.REGIST_SCENE_START).build();
	}

}
