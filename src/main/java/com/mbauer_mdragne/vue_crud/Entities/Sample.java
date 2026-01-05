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
@Table(name = "sample", schema = "venlab")
@IdClass(SampleId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Sample {

    @Id
    @Column(name = "s_id")
    private String sId;

    @Id
    @Column(name = "s_stamp")
    private Timestamp sStamp;

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "weight_net")
    private BigDecimal weightNet;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "weight_bru")
    private BigDecimal weightBru;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @Column(name = "weight_tar")
    private BigDecimal weightTar;

    private Integer quantity;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal distance;

    @Column(name = "date_crumbled")
    private Timestamp dateCrumbled;

    @Column(name = "s_flags")
    private String sFlags;

    private Integer lane;

    private String comment;

    @Column(name = "date_exported")
    private Timestamp dateExported;
}