package com.mbauer_mdragne.vue_crud.Entities;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "box", schema = "venlab")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Box {

    @Id
    @Column(name = "b_id")
    private String bId;

    private String name;

    @Column(name = "num_max")
    private Integer numMax;

    private Integer type;

    private String comment;

    @Column(name = "date_exported")
    private Timestamp dateExported;

    @OneToMany(mappedBy = "box") // kein Cascade REMOVE
    private List<BoxPos> positions;
}