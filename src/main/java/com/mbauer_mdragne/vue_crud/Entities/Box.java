package com.mbauer_mdragne.vue_crud.Entities;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "box", schema = "venlab")
public class Box {

    @Id
    private String b_id;
    private String name;
    private Integer num_max;
    private Integer type;
    private String comment;
    private Timestamp date_exported;
    @OneToMany(mappedBy = "box") // kein Cascade REMOVE
    private List<BoxPos> positions;

    public String getB_id() {
        return b_id;
    }

    public void setB_id(String b_id) {
        this.b_id = b_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNum_max() {
        return num_max;
    }

    public void setNum_max(Integer num_max) {
        this.num_max = num_max;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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
