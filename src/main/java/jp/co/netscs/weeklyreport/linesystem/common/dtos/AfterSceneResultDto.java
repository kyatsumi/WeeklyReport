package jp.co.netscs.weeklyreport.linesystem.common.dtos;

import jp.co.netscs.weeklyreport.linesystem.common.AbstractChapterSceneService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Setter
@Getter
public class AfterSceneResultDto {
	
	/**
	 * シーン実行後の処理結果
	 */
	private AbstractChapterSceneService.AfterResult result;
	
	/**
	 * 処理結果がLOOPの場合に使用するLineからのダミー情報を指定できる
	 */
	private LinePostInfoDto dummy;
	
}
