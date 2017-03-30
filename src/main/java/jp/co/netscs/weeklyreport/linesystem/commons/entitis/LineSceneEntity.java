package jp.co.netscs.weeklyreport.linesystem.commons.entitis;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * シーン管理のマッピングクラス
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
@Table(name="LineScene")
public class LineSceneEntity {
	
	/**
	 * Line Messageing APIのユーザID
	 */
	@Id
	@Column(length=32, nullable = false)
	private String lineId;
	
	/**
	 * シーンの有効期限
	 */
	@Version
	private Long periodTime;
	
	/**
	 * 機能
	 */
	private String section;
	
	/**
	 * 機能状態
	 */
	private String scene;
	
}
