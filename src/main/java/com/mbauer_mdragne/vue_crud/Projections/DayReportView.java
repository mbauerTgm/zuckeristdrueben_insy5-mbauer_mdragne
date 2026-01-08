package com.mbauer_mdragne.vue_crud.Projections;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface DayReportView {

    LocalDate getDay();

    Long getProbenFrueh();
    Long getProbenSpaet();
    Long getProbenNacht();
    Long getProbenSumme();

    Long getAnalysenFrueh();
    Long getAnalysenSpaet();
    Long getAnalysenNacht();
    Long getAnalysenSumme();

    BigDecimal getAvgPol();
    BigDecimal getAvgKal();
    BigDecimal getAvgNat();
    BigDecimal getAvgAn();
    BigDecimal getAvgDry();
    BigDecimal getAvgGlu();
}
