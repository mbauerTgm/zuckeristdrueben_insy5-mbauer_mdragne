package com.mbauer_mdragne.vue_crud.Projections;

import java.sql.Timestamp;

public interface SampleMultipleAnalysisView {
    String getSId();
    Timestamp getSStamp();
    Long getCount();
}