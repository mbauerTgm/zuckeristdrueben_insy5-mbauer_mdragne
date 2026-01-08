package com.mbauer_mdragne.vue_crud.Repositories;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import com.mbauer_mdragne.vue_crud.Projections.DayReportView;
import com.mbauer_mdragne.vue_crud.Entities.Sample;


import java.time.LocalDate;
import java.util.List;

public interface ReportRepository extends Repository<Sample, Long> {
    @Query(value = "SELECT * FROM venlab.get_day_reports(:start_date, :end_date)",
        nativeQuery = true)
    List<DayReportView> getDayReports(@Param("start_date") LocalDate startDate, @Param("end_date") LocalDate endDate);
}