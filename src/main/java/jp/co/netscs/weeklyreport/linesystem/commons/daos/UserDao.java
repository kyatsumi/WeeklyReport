package jp.co.netscs.weeklyreport.linesystem.commons.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jp.co.netscs.weeklyreport.linesystem.commons.entitis.UserEntity;

@Transactional
@Repository
public interface UserDao extends JpaRepository<UserEntity, String> {

}
