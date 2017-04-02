package jp.co.netscs.weeklyreport.linesystem.common.util;

import com.linecorp.bot.model.action.MessageAction;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TemplateMessage;
import com.linecorp.bot.model.message.template.ConfirmTemplate;

/**
 * このクラスはLineに返却するメッセージの作成を行うユーティリテクラスです
 * @author SCS036
 *
 */
public final class LineMessageUtils {
	
	public static Message confirm(String title, String subTitle, String yes, String no) {
		
        ConfirmTemplate confirmTemplate = new ConfirmTemplate(
        		subTitle,
                new MessageAction(yes, yes),
                new MessageAction(no, no)
        );
        TemplateMessage templateMessage = new TemplateMessage(title, confirmTemplate);
		
		return templateMessage;
	}
	
	public static Message generateRegistGroup() {
		return null;
	}
}
