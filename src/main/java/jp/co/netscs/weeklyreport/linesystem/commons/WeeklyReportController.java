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
 * ���̃N���X�ł�
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
	 * �e�L�X�g���b�Z�[�W�����͂��ꂽ���ɔ�������C�x���g
	 * @param event
	 */
	@EventMapping
	public void handleTextMessageEvent(MessageEvent<TextMessageContent> event) {
		String replyToken = event.getReplyToken();
		this.replyText(replyToken, "�e�X�g���b�Z�[�W");
	}

	/**
	 * �|�X�g�o�b�N�C�x���g�������������ɌĂяo�����
	 * @param event
	 */
	@EventMapping
	public void handlePostBackEvent(PostbackEvent event) {

	}
	
	/**
	 * ���[�U�Ƀt�H���[���ꂽ���iLINE�ŗF�B�ǉ��j�ɔ�������C�x���g
	 * @param event 
	 */
    @EventMapping
    public void handleFollowEvent(FollowEvent event) {
        String replyToken = event.getReplyToken();
        this.replyText(replyToken, "Got followed event");
    }

	/**
	 * �Ή����Ă��Ȃ��C�x���g�������ꍇ�ɔ�������C�x���g
	 * @param event
	 */
	@EventMapping
	public void defaultMessageEvent(Event event) {
		log.info("Received message(Ignored): {}", event);
	}
	
	/**
	 * �Ώۂ̃��b�Z�[�W�����[�U�ɕԂ��B<br>
	 * 1000�����ȏ�̏ꍇ�͂P�O�O�O�����ȏ�������ĕԂ��B<br>
	 * @param replyToken �L���ȃ��v���C�g�[�N���ł��邱��
	 * @param message ���[�U�ɕԂ����b�Z�[�W
	 */
	private void replyText(@NonNull String replyToken,@NonNull String message) {
		if (replyToken.isEmpty()) {
			throw new IllegalArgumentException("replyToken must not be empty"); 
		}
		
		if (message.length() > 1000) { 
			message = message.substring(0, 1000 - 2) + "�c�c"; 
		}
		
		this.reply(replyToken, new TextMessage(message)); 
    }
	
    private void reply(@NonNull String replyToken, @NonNull Message message) {
        reply(replyToken, Collections.singletonList(message));
    }
    
    /**
     * ���b�Z�[�W�����[�U�ɕԂ��i�ő�T���܂ő��邱�Ƃ��\�j
     * @param replyToken �L���ȃ��v���C�g�[�N��
     * @param messages ���[�U�ɕԂ��������b�Z�[�W
     */
    private void reply(@NonNull String replyToken, @NonNull List<Message> messages) {
        try {
            lineMessagingService.replyMessage(new ReplyMessage(replyToken, messages)).get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
    

}
