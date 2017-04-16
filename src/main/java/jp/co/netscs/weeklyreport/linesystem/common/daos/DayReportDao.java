package jp.co.netscs.weeklyreport.linesystem.common.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jp.co.netscs.weeklyreport.linesystem.common.entitis.DayReportEntity;
import jp.co.netscs.weeklyreport.linesystem.common.entitis.ReportEntity;

@Transactional
@Repository
public interface DayReportDao extends JpaRepository<DayReportEntity, ReportEntity> {

}
