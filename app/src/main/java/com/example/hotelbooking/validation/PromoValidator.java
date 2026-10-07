package com.example.hotelbooking.validation;

public class PromoValidator {

    public static final String PROMO_HOTEL10 = "HOTEL10";
    public static final String PROMO_WEEKEND15 = "WEEKEND15";

    public static int discountPercent(String code) {
        if (code == null) return 0;
        switch (code.trim().toUpperCase()) {
            case PROMO_HOTEL10: return 10;
            case PROMO_WEEKEND15: return 15;
            default: return 0;
        }
    }

    public static boolean isValid(String code) {
        return discountPercent(code) > 0;
    }
}