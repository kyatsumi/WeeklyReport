package jp.co.netscs.weeklyreport.linesystem.commons.daos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.co.netscs.weeklyreport.linesystem.commons.entitis.DayReportEntity;

@Repository
public interface DayReportDao extends JpaRepository<DayReportEntity, Long> {

}
