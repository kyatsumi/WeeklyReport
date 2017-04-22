package jp.co.netscs.weeklyreport.linesystem.common.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public final class DateUtils {

	/**
	 * シーンの有効期限を発行する
	 * @return
	 */
	public static Long generatePeriodTime() {
		LocalDateTime now = LocalDateTime.now();
		Long preriodTime = now.plusMinutes(LineBotConstant.PERIOD_BASE)
				.atZone(ZoneId.systemDefault())
				.toEpochSecond();
		return preriodTime;
	}
	
	static final private String DATE_FORMAT = "yyyy-MM-dd";

	public static LocalDate string2LocalDate(String date) {
	  return LocalDate.parse(date, DateTimeFormatter.ofPattern(DATE_FORMAT));
	}
	
	public static Date localDate2Date(LocalDate localDate) {
		  return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	
	public static Date string2Date(String date) {
		return localDate2Date(string2LocalDate(date));
	}
}
