package com.mbauer_mdragne.vue_crud;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "analysis", schema = "venlab")
public class Analysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long a_id;

    private String s_id;
    private Timestamp s_stamp;
    @JsonFormat(shape = JsonFormat.Shape.STRING) private BigDecimal pol;
    @JsonFormat(shape = JsonFormat.Shape.STRING) private BigDecimal nat;
    @JsonFormat(shape = JsonFormat.Shape.STRING) private BigDecimal kal;
    @JsonFormat(shape = JsonFormat.Shape.STRING) private BigDecimal an;
    @JsonFormat(shape = JsonFormat.Shape.STRING) private BigDecimal glu;
    @JsonFormat(shape = JsonFormat.Shape.STRING) private BigDecimal dry;
    private Timestamp date_in;
    private Timestamp date_out;
    @JsonFormat(shape = JsonFormat.Shape.STRING) private BigDecimal weight_mea;
    @JsonFormat(shape = JsonFormat.Shape.STRING) private BigDecimal weight_nrm;
    @JsonFormat(shape = JsonFormat.Shape.STRING) private BigDecimal weight_cur;
    @JsonFormat(shape = JsonFormat.Shape.STRING) private BigDecimal weight_dif;
    @JsonFormat(shape = JsonFormat.Shape.STRING) private BigDecimal density;
    private String a_flags;
    private Integer lane;
    private String comment;
    private Timestamp date_exported;

    public Long getA_id() {
        return a_id;
    }

    public void setA_id(Long a_id) {
        this.a_id = a_id;
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

    public BigDecimal getPol() {
        return pol;
    }

    public void setPol(BigDecimal pol) {
        this.pol = pol;
    }

    public BigDecimal getNat() {
        return nat;
    }

    public void setNat(BigDecimal nat) {
        this.nat = nat;
    }

    public BigDecimal getKal() {
        return kal;
    }

    public void setKal(BigDecimal kal) {
        this.kal = kal;
    }

    public BigDecimal getAn() {
        return an;
    }

    public void setAn(BigDecimal an) {
        this.an = an;
    }

    public BigDecimal getGlu() {
        return glu;
    }

    public void setGlu(BigDecimal glu) {
        this.glu = glu;
    }

    public BigDecimal getDry() {
        return dry;
    }

    public void setDry(BigDecimal dry) {
        this.dry = dry;
    }

    public Timestamp getDate_in() {
        return date_in;
    }

    public void setDate_in(Timestamp date_in) {
        this.date_in = date_in;
    }

    public Timestamp getDate_out() {
        return date_out;
    }

    public void setDate_out(Timestamp date_out) {
        this.date_out = date_out;
    }

    public BigDecimal getWeight_mea() {
        return weight_mea;
    }

    public void setWeight_mea(BigDecimal weight_mea) {
        this.weight_mea = weight_mea;
    }

    public BigDecimal getWeight_nrm() {
        return weight_nrm;
    }

    public void setWeight_nrm(BigDecimal weight_nrm) {
        this.weight_nrm = weight_nrm;
    }

    public BigDecimal getWeight_cur() {
        return weight_cur;
    }

    public void setWeight_cur(BigDecimal weight_cur) {
        this.weight_cur = weight_cur;
    }

    public BigDecimal getWeight_dif() {
        return weight_dif;
    }

    public void setWeight_dif(BigDecimal weight_dif) {
        this.weight_dif = weight_dif;
    }

    public BigDecimal getDensity() {
        return density;
    }

    public void setDensity(BigDecimal density) {
        this.density = density;
    }

    public String getA_flags() {
        return a_flags;
    }

    public void setA_flags(String a_flags) {
        this.a_flags = a_flags;
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