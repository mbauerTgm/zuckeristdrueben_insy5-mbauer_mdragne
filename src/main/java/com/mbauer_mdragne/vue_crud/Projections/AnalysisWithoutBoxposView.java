package com.mbauer_mdragne.vue_crud.Projections;

import java.sql.Timestamp;

public interface AnalysisWithoutBoxposView {
    String getSId();
    Timestamp getSStamp();
    Long getAId();
}