package jp.co.netscs.weeklyreport.linesystem.common.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

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
}
