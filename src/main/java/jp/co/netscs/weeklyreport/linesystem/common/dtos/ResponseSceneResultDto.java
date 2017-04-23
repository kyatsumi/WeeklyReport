package jp.co.netscs.weeklyreport.linesystem.common.dtos;

import java.util.List;

import com.linecorp.bot.model.message.Message;

import jp.co.netscs.weeklyreport.linesystem.common.BaseChapterService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ResponseSceneResultDto {
	
	/**
	 * シーン実行後の処理結果
	 */
	private BaseChapterService.ResponseStatusCode statusCode;
	
	/**
	 * 処理結果がLOOPの場合に使用するLineからのダミー情報を指定できる
	 */
	private LinePostInfoDto dummy;
	
	/**
	 * 処理結果がNON_POSTBACKの場合にユーザに返すメッセージ
	 */
	private List<Message> message;
	
}
