package jp.co.netscs.weeklyreport.linesystem.commons.entitis;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 日報テーブルのマッピングクラス
 * @author katumi
 *
 */
@Entity
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@Table(name="DayReport")
public class DayReportEntity {
	
	/**
	 * Line Messageing APIのユーザID
	 */
	@Id
	private Long lineId;
	
	/**
	 * 登録日付
	 */
	private LocalDate date;
	
	/**
	 * 内容
	 */
	private String report;
	
	/**
	 * 管理者コメント
	 */
	private String adminComment;
}
