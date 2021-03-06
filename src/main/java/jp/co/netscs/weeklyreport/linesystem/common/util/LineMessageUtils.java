package jp.co.netscs.weeklyreport.linesystem.common.util;

import static jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant.LAST_WEEKS_VIEW;
import static jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant.LAST_WEEK_VIEW;
import static jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant.NEXT_WEEKS_VIEW;
import static jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant.NEXT_WEEK_VIEW;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.linecorp.bot.model.action.Action;
import com.linecorp.bot.model.action.PostbackAction;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.model.message.template.CarouselColumn;
import com.linecorp.bot.model.message.template.CarouselTemplate;
import com.linecorp.bot.model.message.template.ConfirmTemplate;

import jp.co.netscs.weeklyreport.linesystem.common.entitis.DayReportEntity;
import jp.co.netscs.weeklyreport.linesystem.common.entitis.UserEntity;

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
                new PostbackAction(yes, yes),
                new PostbackAction(no, no)
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
	
	//Lineの仕様でpostbackのメッセージが２０文字以内にしなければならず、日付の終わりをMM-DDの形式にして文字数を範囲内にした
	private static final DateTimeFormatter MM_DD_FORMAT = DateTimeFormatter.ofPattern("MM-dd");
	
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
                                new PostbackAction(startDay.plusDays(7).toString() + "~" +  startDay.plusDays(13).format(MM_DD_FORMAT),
                                		startDay.plusDays(7).toString()),
                                new PostbackAction(startDay.toString() + "~" +  startDay.plusDays(6).format(MM_DD_FORMAT),
                                		startDay.toString()),
                                new PostbackAction(startDay.minusDays(7).toString() + "~" +  startDay.minusDays(1).format(MM_DD_FORMAT),
                                		startDay.minusDays(7).toString())
                        )),
                        new CarouselColumn(null, null, "日報を表示する週を選んでください。", Arrays.asList(
                                new PostbackAction(startDay.minusDays(14).toString() + "~" +  startDay.minusDays(8).format(MM_DD_FORMAT),
                                		startDay.minusDays(14).toString()),
			                     new PostbackAction(LAST_WEEKS_VIEW,
			                    		 LAST_WEEKS_VIEW + "," + offset),
			                     new PostbackAction(NEXT_WEEKS_VIEW,
			                    		 NEXT_WEEKS_VIEW + "," + offset)
                		))
				));
		return new TemplateMessage("表示する週を選んでください。", oneWeek);
	}
	
	public static List<Message> convertOneWeekReports(List<DayReportEntity> reports) {
		return convertOneWeekReports(reports, "");
	}
	
	public static List<Message> convertOneWeekReports(List<DayReportEntity> reports, String title) {
		if (reports == null || reports.isEmpty()) {
			return Arrays.asList(new TextMessage("内容が登録されていません"));
		}
		
		List<Message> messages = new ArrayList<>();
		String message = title;
		for(DayReportEntity report : reports) {
			message += report.viewDayReport();
			if (message.length() > 200) {
				messages.add(new TextMessage(message));
				message = "";
			} else {
				message += "\n\n";
			}
		}
		
		if(!message.isEmpty()) {
			messages.add(new TextMessage(message.substring(0, message.length() - 2)));
		}
		
		return messages;
	}
	
	public static Message generateMenbersCarousel(List<UserEntity> users) {
		List<Action> actions = users.stream().map(user -> new PostbackAction(user.getName(), user.getLineId())).collect(Collectors.toList());
		return new TemplateMessage("表示するユーザを選択してください。", new CarouselTemplate( generateCarousels("確認するユーザを選択してください。", actions)));
	}
	
	private static List<CarouselColumn> generateCarousels(String message,List<Action> actions) {
		if (actions.size() > 3) {
		}
		
		switch (actions.size()) {
			case 1:
			case 2:
			case 3:
				return Arrays.asList(new CarouselColumn(null, null, message, actions));
			case 4:
				return Arrays.asList(new CarouselColumn(null, null, message, actions.subList(0, 1)), new CarouselColumn(null, null, message, actions.subList(2, 3)));
			case 5:
				List<Action> subActions = actions.subList(3, 4);
				subActions.add(new PostbackAction("すべてのユーザ","すべてのユーザ"));
				return Arrays.asList(new CarouselColumn(null, null, message, actions.subList(0, 2)), new CarouselColumn(null, null, message, actions.subList(3, 4)));
			default :
				return Arrays.asList(new CarouselColumn(null, null, message, actions.subList(0, 2)));
		}
	}
	
}
