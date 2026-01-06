package com.mbauer_mdragne.vue_crud;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.OffsetDateTime;

public class DateUtils {
    public static Timestamp parseAny(Object input) {
        if (input == null) return null;
        if (input instanceof Timestamp) return (Timestamp) input;
        
        String str = input.toString();
        if (str.isEmpty()) return null;

        try {
            // Versuche ISO-8601 (z.B. 2026-01-06T17:41:33Z)
            return Timestamp.from(Instant.parse(str));
        } catch (Exception e) {
            try {
                // Versuche OffsetDateTime (für lokale Offsets)
                return Timestamp.from(OffsetDateTime.parse(str).toInstant());
            } catch (Exception e2) {
                // Letzter Versuch: Klassisches SQL Format
                try { return Timestamp.valueOf(str.replace("T", " ")); }
                catch (Exception e3) { return null; }
            }
        }
    }
}