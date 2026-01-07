package com.mbauer_mdragne.vue_crud.DTOs;

import java.sql.Timestamp;

public class SampleAnalysisCount {
    private String sId;
    private Timestamp sStamp;
    private long count;

    public SampleAnalysisCount(String sId, Timestamp sStamp, long count) {
        this.sId = sId;
        this.sStamp = sStamp;
        this.count = count;
    }

    public String getSId() { return sId; }
    public void setSId(String sId) { this.sId = sId; }
    public Timestamp getSStamp() { return sStamp; }
    public void setSStamp(Timestamp sStamp) { this.sStamp = sStamp; }
    public long getCount() { return count; }
    public void setCount(long count) { this.count = count; }
}