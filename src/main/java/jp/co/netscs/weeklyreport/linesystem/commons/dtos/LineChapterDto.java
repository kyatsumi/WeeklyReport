package jp.co.netscs.weeklyreport.linesystem.commons.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Line Botの処理状態を受け渡すクラス
 * @author SCS036
 *
 */
@AllArgsConstructor
@Builder
@Getter
@ToString
@EqualsAndHashCode
public class LineChapterDto {

	/**
	 * チャプター名を格納
	 */
	private String chapter;
	
	/**
	 * 機能の状態を格納する
	 */
	private String scene;
}