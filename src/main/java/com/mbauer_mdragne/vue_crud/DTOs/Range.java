package com.mbauer_mdragne.vue_crud.DTOs;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class Range<T> {
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
    private T from;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private T to;
}