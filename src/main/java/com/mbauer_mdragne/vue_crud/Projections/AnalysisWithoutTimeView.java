package com.mbauer_mdragne.vue_crud.Projections;

import java.sql.Timestamp;

public interface AnalysisWithoutTimeView {
    String getSId();
    Timestamp getSStamp();
    Long getAId();
}