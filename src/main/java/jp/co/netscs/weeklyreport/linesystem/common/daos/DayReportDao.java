package jp.co.netscs.weeklyreport.linesystem.common.daos;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jp.co.netscs.weeklyreport.linesystem.common.entitis.DayReportEntity;
import jp.co.netscs.weeklyreport.linesystem.common.entitis.ReportEntity;

@Transactional
@Repository
public interface DayReportDao extends JpaRepository<DayReportEntity, ReportEntity> {
	
	@Query("select report from DayReportEntity report where report.date between ?1 and ?2 and report.lineid = ?3 order by report.date")
	List<DayReportEntity> findByLineidAndDateBetweenOrderByDate(Date startDate, Date endDate, String lineid);

}
