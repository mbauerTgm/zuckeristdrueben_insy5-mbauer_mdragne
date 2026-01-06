package com.mbauer_mdragne.vue_crud.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.sql.Timestamp;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "boxpos", schema = "venlab")
@IdClass(BoxPosId.class)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoxPos {

    @Id
    @Column(name = "b_id")
    @JsonProperty("b_id")
    private String bId;

    @Id
    @Column(name = "bpos_id")
    @JsonProperty("bpos_id")
    private Integer bposId;

    @Column(name = "s_id")
    @JsonProperty("s_id")
    private String sId;

    @Column(name = "s_stamp")
    @JsonProperty("s_stamp")
    private Timestamp sStamp;

    @Column(name = "date_exported")
    @JsonProperty("date_exported")
    private Timestamp dateExported;

    @ManyToOne
    @JoinColumn(name = "b_id", insertable = false, updatable = false)
    @JsonIgnore
    private Box box;

    @ManyToOne
    @JoinColumns({
        @JoinColumn(name = "s_id", referencedColumnName = "s_id", insertable = false, updatable = false),
        @JoinColumn(name = "s_stamp", referencedColumnName = "s_stamp", insertable = false, updatable = false)
    })
    private Sample sample;
}