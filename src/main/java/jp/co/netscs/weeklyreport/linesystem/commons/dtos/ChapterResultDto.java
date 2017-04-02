package jp.co.netscs.weeklyreport.linesystem.commons.dtos;

import java.util.List;

import com.linecorp.bot.model.message.Message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ChapterResultDto {
	/**
	 * 次のシーン
	 */
	private String nextScene;
	
	/**
	 * 今回返却するメッセージ
	 */
	private List<Message> messages;
}
