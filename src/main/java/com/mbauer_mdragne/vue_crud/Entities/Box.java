package com.mbauer_mdragne.vue_crud.Entities;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty; // Import hinzugefügt
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
    @JsonProperty("b_id") // Für das Frontend
    private String bId;

    private String name;

    @Column(name = "num_max")
    @JsonProperty("num_max") // Für das Frontend
    private Integer numMax;

    private Integer type;

    private String comment;

    @Column(name = "date_exported")
    @JsonProperty("date_exported") // Für das Frontend
    private Timestamp dateExported;

    @OneToMany(mappedBy = "box") 
    private List<BoxPos> boxPositions;
}