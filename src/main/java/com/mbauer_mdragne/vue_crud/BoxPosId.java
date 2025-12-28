package com.mbauer_mdragne.vue_crud;

import java.io.Serializable;
import java.util.Objects;

public class BoxPosId implements Serializable {
    private String b_id;
    private Integer bpos_id;

    public BoxPosId() {}
    public BoxPosId(String b_id, Integer bpos_id) {
        this.b_id = b_id;
        this.bpos_id = bpos_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoxPosId)) return false;
        BoxPosId that = (BoxPosId) o;
        return Objects.equals(b_id, that.b_id) && Objects.equals(bpos_id, that.bpos_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(b_id, bpos_id);
    }
}