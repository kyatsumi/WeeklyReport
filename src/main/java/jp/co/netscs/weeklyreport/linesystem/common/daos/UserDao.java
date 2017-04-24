package jp.co.netscs.weeklyreport.linesystem.common.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jp.co.netscs.weeklyreport.linesystem.common.entitis.UserEntity;

@Transactional
@Repository
public interface UserDao extends JpaRepository<UserEntity, String> {
	
	List<UserEntity> findAllByOrderBylineId();

}
