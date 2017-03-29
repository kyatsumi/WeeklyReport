package jp.co.netscs.weeklyreport.linesystem.commons.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.netscs.weeklyreport.linesystem.commons.entitis.DayReportEntity;

public interface DayReportDao extends JpaRepository<DayReportEntity, Long> {

}
