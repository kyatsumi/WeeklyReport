package jp.co.netscs.weeklyreport.linesystem.common.entitis;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name="day_report")
public class DayReportEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Line Messageing APIのユーザID
	 */
	@EmbeddedId
	private ReportEntity reportEntity;
	
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
	
	/**
	 * レポートを出力する
	 * @return
	 */
	public String viewDayReport() {
		return "日付:" + this.reportEntity.getDate().toLocalDate().toString() + "\n" + this.report;
	}
}
