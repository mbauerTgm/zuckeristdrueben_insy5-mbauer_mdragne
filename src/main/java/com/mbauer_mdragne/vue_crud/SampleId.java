package com.mbauer_mdragne.vue_crud;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public class SampleId implements Serializable {
    private String s_id;
    private Timestamp s_stamp;

    public SampleId() {}
    public SampleId(String s_id, Timestamp s_stamp) {
        this.s_id = s_id;
        this.s_stamp = s_stamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SampleId)) return false;
        SampleId that = (SampleId) o;
        return Objects.equals(s_id, that.s_id) && Objects.equals(s_stamp, that.s_stamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(s_id, s_stamp);
    }
}