package com.mbauer_mdragne.vue_crud.DTOs;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class Range<T> {
    // T kann später String, Integer oder LocalDate sein
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) // Wirkt nur, wenn T ein Datum ist
    private T from;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private T to;
}