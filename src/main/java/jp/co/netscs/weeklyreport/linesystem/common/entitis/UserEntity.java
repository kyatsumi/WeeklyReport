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
 * 
 * @author katsumi
 *
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Builder
@Table(name="users")
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
	@Column(name = "scsGroup", nullable = true)
	private String group;
	
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
