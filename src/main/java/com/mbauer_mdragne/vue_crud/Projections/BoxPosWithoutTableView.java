package com.mbauer_mdragne.vue_crud.Projections;

import java.sql.Timestamp;

public interface BoxPosWithoutTableView {
    String getBId();
    Integer getBposId();
    String getSId();
    Timestamp getSStamp();
}