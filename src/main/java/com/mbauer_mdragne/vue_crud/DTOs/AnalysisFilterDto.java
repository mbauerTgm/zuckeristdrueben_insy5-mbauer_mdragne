package com.mbauer_mdragne.vue_crud.DTOs;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class AnalysisFilterDto {
    private Range<Long> aId;

    private Range<String> sId;

    private Range<LocalDateTime> dateIn;
    private Range<LocalDateTime> dateOut;

    private Range<String> aFlags;
}