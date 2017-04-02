package jp.co.netscs.weeklyreport.linesystem.common.entitis;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import jp.co.netscs.weeklyreport.linesystem.common.util.ScsGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
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
@Table(name="Users")
public class UserEntity implements Serializable {
	
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
	 * 所属グループ
	 */
	@Enumerated(EnumType.STRING)
	@Column(name = "scsGroup", nullable = true)
	private ScsGroup group;
	
	/**
	 * ユーザ登録名
	 */
	@Column(nullable = true)
	private String name;
	
	/**
	 * 管理者権限
	 */
	@Column(nullable = true)
	private Boolean admin;
	
}