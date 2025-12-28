package com.mbauer_mdragne.vue_crud;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "sample", schema = "venlab")
@IdClass(SampleId.class)
public class Sample {

    @Id
    private String s_id;

    @Id
    private Timestamp s_stamp;

    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING) private BigDecimal weight_net;
    @JsonFormat(shape = JsonFormat.Shape.STRING) private BigDecimal weight_bru;
    @JsonFormat(shape = JsonFormat.Shape.STRING) private BigDecimal weight_tar;
    private Integer quantity;
    @JsonFormat(shape = JsonFormat.Shape.STRING) private BigDecimal distance;
    private Timestamp date_crumbled;
    private String s_flags;
    private Integer lane;
    private String comment;
    private Timestamp date_exported;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getWeight_net() {
        return weight_net;
    }

    public void setWeight_net(BigDecimal weight_net) {
        this.weight_net = weight_net;
    }

    public BigDecimal getWeight_bru() {
        return weight_bru;
    }

    public void setWeight_bru(BigDecimal weight_bru) {
        this.weight_bru = weight_bru;
    }

    public BigDecimal getWeight_tar() {
        return weight_tar;
    }

    public void setWeight_tar(BigDecimal weight_tar) {
        this.weight_tar = weight_tar;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getDistance() {
        return distance;
    }

    public void setDistance(BigDecimal distance) {
        this.distance = distance;
    }

    public Timestamp getDate_crumbled() {
        return date_crumbled;
    }

    public void setDate_crumbled(Timestamp date_crumbled) {
        this.date_crumbled = date_crumbled;
    }

    public String getS_flags() {
        return s_flags;
    }

    public void setS_flags(String s_flags) {
        this.s_flags = s_flags;
    }

    public Integer getLane() {
        return lane;
    }

    public void setLane(Integer lane) {
        this.lane = lane;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getDate_exported() {
        return date_exported;
    }

    public void setDate_exported(Timestamp date_exported) {
        this.date_exported = date_exported;
    }
}