package com.mbauer_mdragne.vue_crud;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "threshold", schema = "venlab")
public class Threshold {

    @Id
    private String th_id;

    private BigDecimal value_min;
    private BigDecimal value_max;
    private Timestamp date_changed;

    public String getTh_id() {
        return th_id;
    }

    public void setTh_id(String th_id) {
        this.th_id = th_id;
    }

    public BigDecimal getValue_min() {
        return value_min;
    }

    public void setValue_min(BigDecimal value_min) {
        this.value_min = value_min;
    }

    public BigDecimal getValue_max() {
        return value_max;
    }

    public void setValue_max(BigDecimal value_max) {
        this.value_max = value_max;
    }

    public Timestamp getDate_changed() {
        return date_changed;
    }

    public void setDate_changed(Timestamp date_changed) {
        this.date_changed = date_changed;
    }
}