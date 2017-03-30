package jp.co.netscs.weeklyreport.linesystem.commons;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LineSectionDto;

@Service
public class WeeklyReportMessageServiceImpl implements WeeklyReportMessageService {

	@Override
	public List<Message> execute(LinePostInfoDto lineInfo, LineSectionDto section) {
		return Arrays.asList(
				new TextMessage(lineInfo.getText())
				);
	}

}
