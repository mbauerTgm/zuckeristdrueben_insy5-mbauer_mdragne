package com.mbauer_mdragne.vue_crud.DTOs;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AnalysisGlobalFilterDto {

    private Range<LocalDateTime> globalDateIn;

}