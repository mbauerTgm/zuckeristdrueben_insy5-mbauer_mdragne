package com.mbauer_mdragne.vue_crud.DTOs;

import lombok.Data;
import java.time.LocalDate;

@Data
public class AnalysisFilterDto {
    private Range<Long> aId;

    private Range<String> sId;

    private Range<LocalDate> dateIn;
    private Range<LocalDate> dateOut;

    private Range<String> aFlags;
}