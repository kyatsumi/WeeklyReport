package jp.co.netscs.weeklyreport.linesystem.commons;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * このクラスでは
 * 
 * @author SCS036
 *
 */
@Slf4j
@LineMessageHandler
public final class WeeklyReportController {

	@Autowired
	private LineMessagingClient lineMessagingService;

	/**
	 * テキストメッセージが入力された時に発生するイベント
	 * @param event
	 */
	@EventMapping
	public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
		String replyToken = event.getReplyToken();
		this.replyText(replyToken, "テストメッセージ");
	}

	/**
	 * ポストバックイベントが発生した時に呼び出される
	 * @param event
	 */
	@EventMapping
	public void handlePostBackEvent(PostbackEvent event) {

	}
	
	/**
	 * ユーザにフォローされた時（LINEで友達追加）に発生するイベント
	 * @param event 
	 */
    @EventMapping
    public void handleFollowEvent(FollowEvent event) {
        String replyToken = event.getReplyToken();
        this.replyText(replyToken, "Got followed event");
    }

	/**
	 * 対応していないイベントが来た場合に発生するイベント
	 * @param event
	 */
	@EventMapping
	public void defaultMessageEvent(Event event) {
		log.info("Received message(Ignored): {}", event);
	}
	
	/**
	 * 対象のメッセージをユーザに返す。<br>
	 * 1000文字以上の場合は１０００文字以上を消して返す。<br>
	 * @param replyToken 有効なリプライトークンであること
	 * @param message ユーザに返すメッセージ
	 */
	private void replyText(@NonNull String replyToken,@NonNull String message) {
		if (replyToken.isEmpty()) {
			throw new IllegalArgumentException("replyToken must not be empty"); 
		}
		
		if (message.length() > 1000) { 
			message = message.substring(0, 1000 - 2) + "……"; 
		}
		
		this.reply(replyToken, new TextMessage(message)); 
    }
	
    private void reply(@NonNull String replyToken, @NonNull Message message) {
        reply(replyToken, Collections.singletonList(message));
    }
    
    /**
     * メッセージをユーザに返す（最大５件まで送ることが可能）
     * @param replyToken 有効なリプライトークン
     * @param messages ユーザに返したいメッセージ
     */
    private void reply(@NonNull String replyToken, @NonNull List<Message> messages) {
        try {
            lineMessagingService.replyMessage(new ReplyMessage(replyToken, messages)).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    

}
