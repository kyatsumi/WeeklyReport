package jp.co.netscs.weeklyreport.linesystem.common.entitis;

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
@ToString
@EqualsAndHashCode
@Builder
@Table(name="line_scene")
public class LineSceneEntity implements Serializable {
	
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
	 * シーンの有効期限
	 */
	@Column(nullable = false)
	private long periodTime;
	
	/**
	 * 機能
	 */
	@Column(length=32, nullable = false)
	private String chapter;
	
	/**
	 * 機能状態
	 */
	@Column(length=32, nullable = false)
	private String scene;
	
	/**
	 * 前の機能状態
	 */
	@Column(length=32, nullable = false)
	private String responseScene;
	
}
