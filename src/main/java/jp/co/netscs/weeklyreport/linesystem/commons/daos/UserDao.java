package jp.co.netscs.weeklyreport.linesystem.commons.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.netscs.weeklyreport.linesystem.commons.entitis.UserEntity;

public interface UserDao extends JpaRepository<UserEntity, Long> {

}
