package jp.co.netscs.weeklyreport.linesystem.common.util;

import static jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant.LAST_WEEK_VIEW;
import static jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant.NEXT_WEEK_VIEW;
import static jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant.LAST_WEEKS_VIEW;
import static jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant.NEXT_WEEKS_VIEW;

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
	
	/**
	 * 指定された日付で日曜日を起点に１週間分の日付と先週、来週をいれた９個の選択肢を表示する
	 * {@code offset}に入れた値分日数を引く
	 * {@code offset}が7なら１週間前の日付を表示する
	 * @param date 指定された日付
	 * @param offset　
	 * @return Line用のメッセージ
	 */
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
                                new PostbackAction(startDay.plusDays(5).toString(),
                    					startDay.plusDays(5).toString())
                		)),
                        new CarouselColumn(null, null, "登録する日付を選んでください。", Arrays.asList(
                                new PostbackAction(startDay.plusDays(6).toString(),
                    					startDay.plusDays(6).toString()),
			                     new PostbackAction(LAST_WEEK_VIEW,
			                    		 LAST_WEEK_VIEW + "," + offset),
			                     new PostbackAction(NEXT_WEEK_VIEW,
			                    		 NEXT_WEEK_VIEW + "," + offset)
                		))
				));
		return new TemplateMessage("登録する日付を選んでください。", oneWeek);
	}
	
	/**
	 * 
	 * @param date
	 * @param offset
	 * @return
	 */
	public static Message generateWeeksCarousel(LocalDate date, int offset) {
		LocalDate startDay = date.minusDays(offset).with(TemporalAdjusters.previous(DayOfWeek.SUNDAY));
		CarouselTemplate oneWeek = new CarouselTemplate(
				Arrays.asList(
                        new CarouselColumn(null, null, "日報を表示する週を選んでください。", Arrays.asList(
                                new PostbackAction(startDay.plusDays(8).toString() + "~" +  startDay.plusDays(14).toString(),
                                		startDay.plusDays(7).toString()),
                                new PostbackAction(startDay.toString() + "~" +  startDay.plusDays(7).toString(),
                                		startDay.toString()),
                                new PostbackAction(startDay.minusDays(7).toString() + "~" +  startDay.minusDays(1).toString(),
                                		startDay.minusDays(7).toString())
                        )),
                        new CarouselColumn(null, null, "日報を表示する週を選んでください。", Arrays.asList(
                                new PostbackAction(startDay.minusDays(14).toString() + "~" +  startDay.minusDays(8).toString(),
                                		startDay.minusDays(14).toString()),
			                     new PostbackAction(LAST_WEEKS_VIEW,
			                    		 LAST_WEEKS_VIEW + "," + offset),
			                     new PostbackAction(NEXT_WEEKS_VIEW,
			                    		 NEXT_WEEKS_VIEW + "," + offset)
                		))
				));
		return new TemplateMessage("表示する週を選んでください。", oneWeek);
	}
	
}
