package com.mbauer_mdragne.vue_crud.DTOs;

import lombok.Data;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

@Data
public class AnalysisFilterDto {
    private Range<Long> aId;

    private Range<String> sId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Range<LocalDateTime> dateIn;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Range<LocalDateTime> dateOut;

    private Range<String> aFlags;
}