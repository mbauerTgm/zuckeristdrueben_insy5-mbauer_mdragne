package com.mbauer_mdragne.vue_crud;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "log", schema = "venlab")
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long log_id;

    private Timestamp date_created;
    private String level;
    private String info;
    private String s_id;
    private Timestamp s_stamp;
    private Long a_id;
    private Timestamp date_exported;

    public Long getLog_id() {
        return log_id;
    }

    public void setLog_id(Long log_id) {
        this.log_id = log_id;
    }

    public Timestamp getDate_created() {
        return date_created;
    }

    public void setDate_created(Timestamp date_created) {
        this.date_created = date_created;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
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

    public Long getA_id() {
        return a_id;
    }

    public void setA_id(Long a_id) {
        this.a_id = a_id;
    }

    public Timestamp getDate_exported() {
        return date_exported;
    }

    public void setDate_exported(Timestamp date_exported) {
        this.date_exported = date_exported;
    }
}