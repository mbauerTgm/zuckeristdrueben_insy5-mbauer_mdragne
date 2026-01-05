package com.mbauer_mdragne.vue_crud.Entities;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "threshold", schema = "venlab")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Threshold {

    @Id
    @Column(name = "th_id")
    private String thId;

    @Column(name = "value_min")
    private BigDecimal valueMin;

    @Column(name = "value_max")
    private BigDecimal valueMax;

    @Column(name = "date_changed")
    private Timestamp dateChanged;
}