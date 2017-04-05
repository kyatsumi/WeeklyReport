package jp.co.netscs.weeklyreport.linesystem.common.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * LineMessageingAPIから届いた情報を格納するクラス
 * @author SCS036
 *
 */
@Builder
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class LinePostInfoDto {

	/**
	 * POSTした時間(UNIX時間)
	 */
	private Long periodTime;
	
	/**
	 * LINEでの入力内容
	 */
	private String text;
	
	/**
	 * LINEユーザID
	 */
	private String userId;
	
	/**
	 * postbackかどうか
	 */
	private boolean postback;
}
