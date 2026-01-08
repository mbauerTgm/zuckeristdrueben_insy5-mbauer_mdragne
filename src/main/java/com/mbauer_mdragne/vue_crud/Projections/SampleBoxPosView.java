package com.mbauer_mdragne.vue_crud.Projections;

import java.sql.Timestamp;

public interface SampleBoxPosView {
    String getSId();
    Timestamp getSStamp();
    String getBoxpos();
}