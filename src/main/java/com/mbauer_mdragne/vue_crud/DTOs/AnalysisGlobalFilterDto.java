package com.mbauer_mdragne.vue_crud.DTOs;

import lombok.Data;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

@Data
public class AnalysisGlobalFilterDto {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Range<LocalDateTime> globalDateIn;

}