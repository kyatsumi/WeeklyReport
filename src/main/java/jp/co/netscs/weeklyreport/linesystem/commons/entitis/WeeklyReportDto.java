package jp.co.netscs.weeklyreport.linesystem.commons.entitis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 
 * @author SCS036
 *
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class WeeklyReportDto {

	private Long time;
	private String text;
	private Long userId;
}
