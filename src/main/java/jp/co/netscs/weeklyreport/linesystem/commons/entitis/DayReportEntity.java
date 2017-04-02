package jp.co.netscs.weeklyreport.linesystem.commons.entitis;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 日報テーブルのマッピングクラス
 * @author katumi
 *
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@Table(name="DayReport")
public class DayReportEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Line Messageing APIのユーザID
	 */
	@Id
	@Column(length=33, nullable = false)
	private String lineId;
	
	/**
	 * 登録日付
	 */
	@Column(nullable = false)
	private String date;
	
	/**
	 * 内容
	 */
	@Column(nullable = false)
	private String report;
	
	/**
	 * 管理者コメント
	 */
	@Column(nullable = true)
	private String adminComment;
}
