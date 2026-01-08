package com.mbauer_mdragne.vue_crud.Projections;

import java.sql.Timestamp;

public interface SuspiciousEanSampleView {
    String getSId();
    Timestamp getSStamp();
    Boolean getIsEan13();
}