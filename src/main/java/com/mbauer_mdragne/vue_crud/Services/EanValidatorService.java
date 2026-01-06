package com.mbauer_mdragne.vue_crud.Services;

import org.springframework.stereotype.Service;

@Service
public class EanValidatorService {

    public boolean isValidEan13(String ean) {
        if (ean == null || ean.length() != 13 || !ean.matches("\\d+")) {
            return false;
        }
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = Character.getNumericValue(ean.charAt(i));
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        int checksum = 10 - (sum % 10);
        if (checksum == 10) checksum = 0;
        
        return checksum == Character.getNumericValue(ean.charAt(12));
    }
}