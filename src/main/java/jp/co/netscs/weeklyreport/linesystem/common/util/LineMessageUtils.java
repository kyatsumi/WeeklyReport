package jp.co.netscs.weeklyreport.linesystem.common.util;

import java.time.LocalDate;
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
	
	public static Message generateOneWeekCarousel(LocalDate date) {
		CarouselTemplate oneWeek = new CarouselTemplate(
				Arrays.asList(
                        new CarouselColumn(null, null, "登録する日付を選んでください。", Arrays.asList(
                                new PostbackAction("2017/04/08",
                                                   "2017/04/08")
                        )),
                        new CarouselColumn(null, null, "登録する日付を選んでください。", Arrays.asList(
                                new PostbackAction("言 hello2",
                                                   "hello こんにちは",
                                                   "hello こんにちは"),
                                new MessageAction("Say message",
                                                  "Rice=米")
))
				));
		
		return new TemplateMessage("表示できません。", oneWeek);
	}
}
