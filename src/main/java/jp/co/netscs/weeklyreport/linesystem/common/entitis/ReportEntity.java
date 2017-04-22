package jp.co.netscs.weeklyreport.linesystem.common.entitis;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
@Embeddable
public class ReportEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5816435950322651953L;

	/**
	 * Line Messageing APIのユーザID
	 */
	@Column(length=33, nullable = false)
	private String lineId;
	
	/**
	 * 登録日付
	 */
	@Column(nullable = false)
	private Date date;
}
