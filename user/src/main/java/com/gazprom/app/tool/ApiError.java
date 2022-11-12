package com.gazprom.app.tool;

import java.util.List;

public class ApiError extends Exception {
    private String message;
    private List<String> errors;

    public ApiError(List<String> errors) {
        this.errors = errors;
    }

    public static ApiError createWith(List<String> errors) {
        return new ApiError(errors);
    }

    public ApiError(String message) {
        super(message);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }
}
