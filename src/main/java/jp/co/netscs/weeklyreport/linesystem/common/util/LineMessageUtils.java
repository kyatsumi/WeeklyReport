package jp.co.netscs.weeklyreport.linesystem.common.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;

import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.linecorp.bot.model.message.template.ConfirmTemplate;

import jp.co.netscs.weeklyreport.linesystem.common.entitis.DayReportEntity;

/**
 * このクラスはLineに返却するメッセージの作成を行うユーティリテクラスです
 * @author SCS036
 *
 */
public final class LineMessageUtils {
	
	/**
	 * 
	 * @param title
	 * @param subTitle
	 * @param yes
	 * @param no
	 * @return
	 */
	public static Message generateConfirm(String title, String subTitle, String yes, String no) {
		
        ConfirmTemplate confirmTemplate = new ConfirmTemplate(
        		subTitle,
                new MessageAction(yes, yes),
                new MessageAction(no, no)
        );
        TemplateMessage templateMessage = new TemplateMessage(title, confirmTemplate);
		
		return templateMessage;
	}
	
	public static Message generateOneWeekCarousel(LocalDate date, int offset) {
		LocalDate startDay = date.minusDays(offset).with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
		CarouselTemplate oneWeek = new CarouselTemplate(
				Arrays.asList(
                        new CarouselColumn(null, null, "登録する日付を選んでください。", Arrays.asList(
                                new PostbackAction(startDay.toString(),
                    					startDay.toString()),
                                new PostbackAction(startDay.plusDays(1).toString(),
                    					startDay.plusDays(1).toString()),
                                new PostbackAction(startDay.plusDays(2).toString(),
                    					startDay.plusDays(2).toString())
                        )),
                        new CarouselColumn(null, null, "登録する日付を選んでください。", Arrays.asList(
                                new PostbackAction(startDay.plusDays(3).toString(),
                    					startDay.plusDays(3).toString()),
                                new PostbackAction(startDay.plusDays(4).toString(),
                    					startDay.plusDays(4).toString()),
			                     new PostbackAction("先週の日付を表示",
			         								"先週の日付を表示")
                		))
				));
		
		return new TemplateMessage("登録する日付を選んでください。", oneWeek);
	}
	
	public static String generateDayReportMessage(DayReportEntity report) {
		return "日付:" + report.getReportEntity().getDate() + "\n" + report.getReport();
	}
}
