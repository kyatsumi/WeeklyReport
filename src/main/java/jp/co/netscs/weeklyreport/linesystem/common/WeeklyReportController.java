package jp.co.netscs.weeklyreport.linesystem.common;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;

import com.linecorp.bot.client.LineMessagingClient;
import com.linecorp.bot.model.ReplyMessage;
import com.linecorp.bot.model.event.Event;
import com.linecorp.bot.model.event.FollowEvent;
import com.linecorp.bot.model.event.JoinEvent;
import com.linecorp.bot.model.event.MessageEvent;
import com.linecorp.bot.model.event.PostbackEvent;
import com.linecorp.bot.model.event.message.TextMessageContent;
import com.linecorp.bot.model.event.source.GroupSource;
import com.linecorp.bot.model.event.source.RoomSource;
import com.linecorp.bot.model.event.source.Source;
import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;
import com.linecorp.bot.spring.boot.annotation.EventMapping;
import com.linecorp.bot.spring.boot.annotation.LineMessageHandler;

import jp.co.netscs.weeklyreport.linesystem.common.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.common.exception.LineValidatException;
import jp.co.netscs.weeklyreport.linesystem.common.exception.LineValidatException.Validate;
import jp.co.netscs.weeklyreport.linesystem.common.exception.WeeklyReportException;
import jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * LineMessageingAPI仕様<br>
 * {@link https://devdocs.line.me/ja}
 * @author SCS036
 *
 */
@Slf4j
@LineMessageHandler
public final class WeeklyReportController {

	@Autowired
	private LineMessagingClient lineMessagingClient;
	
	@Autowired
	private WeeklyReportSceneExecuteService messageService;

	/**
	 * テキストメッセージが入力された時に発生するイベント
	 * @param event
	 */
	@EventMapping
	public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
		String replyToken = event.getReplyToken();
		try {
			Source source = event.getSource();
	        LinePostInfoDto lineInfo = LinePostInfoDto.builder()
	            	.periodTime(event.getTimestamp().getEpochSecond())
	            	.text(event.getMessage().getText())
	            	.userId(source.getUserId())
	            	.postback(false)
	            	.build();
	        this.replyMessage(replyToken, lineInfo);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * PostBackイベントが発生したときに呼び出される
	 * @param event
	 */
	@EventMapping
	public void handlePostBackEvent(PostbackEvent event) {
		String replyToken = event.getReplyToken();
		Source source = event.getSource();
        LinePostInfoDto lineInfo = LinePostInfoDto.builder()
            	.periodTime(event.getTimestamp().getEpochSecond())
            	.text(event.getPostbackContent().getData())
            	.userId(source.getUserId())
            	.postback(true)
            	.build();
		
		this.replyMessage(replyToken, lineInfo);
	}
	
	/**
	 * Lineでユーザにフォローされた時（LINEで友達追加）に発生するイベント
	 * @param event フォローされた時の情報
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
    @EventMapping
    public void handleFollowEvent(FollowEvent event) throws InterruptedException, ExecutionException {
        String replyToken = event.getReplyToken();
        Source source = event.getSource();
        LinePostInfoDto lineInfo = LinePostInfoDto.builder()
            	.periodTime(event.getTimestamp().getEpochSecond())
            	.text(LineBotConstant.CHAPTER_REGIST)
            	.userId(source.getUserId())
            	.postback(false)
            	.build();
        this.replyMessage(replyToken, lineInfo);
    }

    /**
     * チャットルームに招待された場合に発生するイベント
     * 個人チャット以外の場合は自動退出をする
     * @param event
     */
    @EventMapping
    public void handleJoinEvent(JoinEvent event) throws InterruptedException, ExecutionException {
        String replyToken = event.getReplyToken();
        Source source = event.getSource();
        if (source instanceof GroupSource) {
            this.replyText(replyToken, "個人チャットのみ対応しています。");
            lineMessagingClient.leaveGroup(((GroupSource) source).getGroupId()).get();
        } else if (source instanceof RoomSource) {
            this.replyText(replyToken, "個人チャットのみ対応しています。");
            lineMessagingClient.leaveRoom(((RoomSource) source).getRoomId()).get();
        }
    }
    
	/**
	 * 対応していないイベントが来た場合に発生するイベント
	 * @param event
	 */
	@EventMapping
	public void defaultMessageEvent(Event event) {
		log.info("Received message(Ignored): {}", event);
	}

    protected void replyMessage(String replyToken, LinePostInfoDto lineInfo) {
        try {
	        List<Message> replyMessages = this.messageService.execute(lineInfo);
            this.reply(replyToken, replyMessages);
        } catch (WeeklyReportException ex) {
        	this.replyText(replyToken, "管理者に連絡してください　エラーメッセージ:" + ex.getMessage());
        }
    }
    
    private void replyText(@NonNull String replyToken, @NonNull String message) {
        this.reply(replyToken, new TextMessage(message));
    }
	
    private void reply(@NonNull String replyToken, @NonNull Message message) {
        this.reply(replyToken, Collections.singletonList(message));
    }
    
    /**
     * メッセージをユーザに返す（最大５件まで送ることが可能）
     * @param replyToken 有効なリプライトークン
     * @param messages ユーザに返したいメッセージ
     */
    private void reply(@NonNull String replyToken, @NonNull List<Message> messages) {
    	
    	if (messages.size() > LineBotConstant.MESSAGE_MAX) {
    		throw new LineValidatException(Validate.MESSAGEMAX);
    	}
    	
        try {
            lineMessagingClient.replyMessage(new ReplyMessage(replyToken, messages)).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    

}
