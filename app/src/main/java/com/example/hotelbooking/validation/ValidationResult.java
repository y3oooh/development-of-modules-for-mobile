package com.example.hotelbooking.validation;

import java.util.HashMap;
import java.util.Map;

public class ValidationResult {

    public boolean valid = true;
    public final Map<Integer, String> errors = new HashMap<>();

    public void addError(int viewId, String message) {
        errors.put(viewId, message);
        valid = false;
    }
}