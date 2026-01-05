package com.mbauer_mdragne.vue_crud.Entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "analysis", schema = "venlab")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Analysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "a_id")
    private Long aId;

    @Column(name = "s_id")
    private String sId;

    @Column(name = "s_stamp")
    private Timestamp sStamp;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal pol;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal nat;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal kal;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal an;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal glu;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal dry;

    @Column(name = "date_in")
    private Timestamp dateIn;

    @Column(name = "date_out")
    private Timestamp dateOut;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "weight_mea")
    private BigDecimal weightMea;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "weight_nrm")
    private BigDecimal weightNrm;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "weight_cur")
    private BigDecimal weightCur;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "weight_dif")
    private BigDecimal weightDif;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal density;

    @Column(name = "a_flags")
    private String aFlags;

    private Integer lane;
    
    private String comment;

    @Column(name = "date_exported")
    private Timestamp dateExported;
}