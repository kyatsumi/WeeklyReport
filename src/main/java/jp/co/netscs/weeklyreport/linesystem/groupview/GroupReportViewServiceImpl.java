package jp.co.netscs.weeklyreport.linesystem.groupview;

import static jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant.LAST_WEEKS_VIEW;
import static jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant.NEXT_WEEKS_VIEW;
import static jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant.ONE_WEEK_DAYS;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linecorp.bot.model.message.Message;

import jp.co.netscs.weeklyreport.linesystem.common.ChapterManager;
import jp.co.netscs.weeklyreport.linesystem.common.BaseChapterService.ResponseStatusCode;
import jp.co.netscs.weeklyreport.linesystem.common.annotation.ResponseScene;
import jp.co.netscs.weeklyreport.linesystem.common.annotation.Scene;
import jp.co.netscs.weeklyreport.linesystem.common.daos.DayReportDao;
import jp.co.netscs.weeklyreport.linesystem.common.daos.UserDao;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.ResponseSceneResultDto;
import jp.co.netscs.weeklyreport.linesystem.common.entitis.DayReportEntity;
import jp.co.netscs.weeklyreport.linesystem.common.entitis.UserEntity;
import jp.co.netscs.weeklyreport.linesystem.common.util.DateUtils;
import jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant;
import jp.co.netscs.weeklyreport.linesystem.common.util.LineMessageUtils;

@Service
public class GroupReportViewServiceImpl extends GroupReportViewService {

	Map<String,String> selectUserMap = new HashMap<>();
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	DayReportDao dayReportDao;
	
	public GroupReportViewServiceImpl(@Autowired ChapterManager manager) {
		super(manager);
	}

	@Scene(sceneName = LineBotConstant.GROUP_REPORTVIEW_SCENE_SELECTUSER, next = LineBotConstant.GROUP_REPORTVIEW_SCENE_SELECT_DATE)
	public List<Message> selectViewMember(LinePostInfoDto lineInfo) {
		List<UserEntity> users = userDao.findAllByOrderByLineId();
		Message message = LineMessageUtils.generateMenbersCarousel(users);
		return Arrays.asList(message);
	}

	@ResponseScene(target = LineBotConstant.GROUP_REPORTVIEW_SCENE_SELECTUSER, postbackOnly = true)
	public ResponseSceneResultDto selectViewMemberAfter(LinePostInfoDto lineInfo) {
		selectUserMap.put(lineInfo.getUserId(), lineInfo.getText());
		return RESULT_NEXT;
	}

	@Override
	@Scene(sceneName = LineBotConstant.GROUP_REPORTVIEW_SCENE_SELECT_DATE, next = LineBotConstant.GROUP_REPORTVIEW_SCENE_VIEW)
	public List<Message> selectViewWeek(LinePostInfoDto lineInfo, UserEntity userInfo) {
		String text = lineInfo.getText();
		String selectText = text.split(",")[0];
		Message weeks = null;
		switch (selectText) {
			case NEXT_WEEKS_VIEW: {
				Integer offset = Integer.parseInt(text.split(",")[1]);
				weeks = LineMessageUtils.generateWeeksCarousel(LocalDate.now(),  offset - (ONE_WEEK_DAYS * 2));
				break;
			}
			case LAST_WEEKS_VIEW: {
				Integer offset = Integer.parseInt(text.split(",")[1]);
				weeks = LineMessageUtils.generateWeeksCarousel(LocalDate.now(), offset + (ONE_WEEK_DAYS * 2));
				break;
			}
			default: {
				weeks = LineMessageUtils.generateWeeksCarousel(LocalDate.now(), 0);
			}
		}
		return Arrays.asList(weeks);
	}

	@Override
	@ResponseScene(target = LineBotConstant.GROUP_REPORTVIEW_SCENE_SELECT_DATE, postbackOnly = true)
	public ResponseSceneResultDto selectViewWeekAfter(LinePostInfoDto lineInfo, UserEntity userInfo) {
		String text = lineInfo.getText();
		String selectText = text.split(",")[0];
		switch (selectText) {
			case NEXT_WEEKS_VIEW: 
			case LAST_WEEKS_VIEW:
				return ResponseSceneResultDto.builder().dummy(lineInfo).statusCode(ResponseStatusCode.LOOP).build();
			default: {
				return RESULT_NEXT;
			}
		}
	}

	@Override
	@Scene(sceneName = LineBotConstant.GROUP_REPORTVIEW_SCENE_VIEW)
	public List<Message> viewMemberReport(LinePostInfoDto lineInfo, UserEntity userInfo) {
		LocalDate startDate = DateUtils.string2LocalDate(lineInfo.getText());
		String targetUser = selectUserMap.get(userInfo.getLineId());
		List<DayReportEntity> oneWeekReports = dayReportDao.findByOneWeekReport(targetUser, Date.valueOf(startDate),
				Date.valueOf(startDate.plusDays(LineBotConstant.ONE_WEEK_DAYS - 1)));
		UserEntity user = userDao.getOne(targetUser);
		return LineMessageUtils.convertOneWeekReports(oneWeekReports, "日報記入者："+ user.getName() + "\n");
	}

}
