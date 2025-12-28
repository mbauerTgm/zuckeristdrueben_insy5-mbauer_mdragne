package com.mbauer_mdragne.vue_crud.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mbauer_mdragne.vue_crud.Entities.BoxPos;
import com.mbauer_mdragne.vue_crud.Entities.BoxPosId;

public interface BoxPosRepository extends JpaRepository<BoxPos, BoxPosId> {}