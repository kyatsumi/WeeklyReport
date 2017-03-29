package jp.co.netscs.weeklyreport.linesystem.commons.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author SCS036
 *
 */
@Builder
@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class WeeklyReportDto {

	private Long periodTime;
	private String text;
	private Long userId;
}
