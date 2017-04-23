package jp.co.netscs.weeklyreport.linesystem.common.daos;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jp.co.netscs.weeklyreport.linesystem.common.entitis.DayReportEntity;
import jp.co.netscs.weeklyreport.linesystem.common.entitis.ReportEntity;

@Transactional
@Repository
public interface DayReportDao extends JpaRepository<DayReportEntity, ReportEntity> {
	
	@Query(value = "SELECT * FROM day_report WHERE date BETWEEN :startDate AND :endDate AND line_id = :lineid ORDER BY date", nativeQuery = true)
	List<DayReportEntity> findByLineidAndDateBetweenOrderByDate(@Param("startDate") Date startDate, @Param("endDate")Date endDate, @Param("lineid")String lineid);
	
	List<DayReportEntity> findByLineIdAndDateBetweenOrderByDate(String lineid, Date startDate, Date endDate);


}
