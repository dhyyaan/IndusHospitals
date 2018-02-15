package com.indushospitals.validator.rules;


import com.indushospitals.validator.Rule;

public abstract class AbstractRule<T> implements Rule<T> {

    private final String error;

    protected AbstractRule(String error) {
        this.error = error;
    }

    @Override
    public String getErrorMessage() {
        return error;
    }
}
