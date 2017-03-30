package jp.co.netscs.weeklyreport.linesystem.commons;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LineSectionDto;

/**
 * 
 * @author SCS036
 *
 */
@Service
public class WeeklyReportMessageServiceImpl implements WeeklyReportMessageService {

	/**
	 * 
	 */
	@Transactional
	@Override
	public List<Message> execute(LinePostInfoDto lineInfo, LineSectionDto section) {
		return Arrays.asList(
				new TextMessage(section.toString())
				);
	}

}
