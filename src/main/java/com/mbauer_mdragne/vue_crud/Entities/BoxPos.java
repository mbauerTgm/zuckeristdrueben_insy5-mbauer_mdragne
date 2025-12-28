package com.mbauer_mdragne.vue_crud.Entities;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "boxpos", schema = "venlab")
@IdClass(BoxPosId.class)
public class BoxPos {

    @Id
    private String b_id;

    @Id
    private Integer bpos_id;

    private String s_id;
    private Timestamp s_stamp;
    private Timestamp date_exported;
    @ManyToOne
    @JoinColumn(name = "b_id", insertable = false, updatable = false) //Beziehung zu Box, damit Box nicht gelöscht werden kann falls Boxpos vorhanden ist
    private Box box;
    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "s_id", referencedColumnName = "s_id", insertable = false, updatable = false),
            @JoinColumn(name = "s_stamp", referencedColumnName = "s_stamp", insertable = false, updatable = false)
    })
    private Sample sample;

    public String getB_id() {
        return b_id;
    }

    public void setB_id(String b_id) {
        this.b_id = b_id;
    }

    public Integer getBpos_id() {
        return bpos_id;
    }

    public void setBpos_id(Integer bpos_id) {
        this.bpos_id = bpos_id;
    }

    public String getS_id() {
        return s_id;
    }

    public void setS_id(String s_id) {
        this.s_id = s_id;
    }

    public Timestamp getS_stamp() {
        return s_stamp;
    }

    public void setS_stamp(Timestamp s_stamp) {
        this.s_stamp = s_stamp;
    }

    public Timestamp getDate_exported() {
        return date_exported;
    }

    public void setDate_exported(Timestamp date_exported) {
        this.date_exported = date_exported;
    }
}
