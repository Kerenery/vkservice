package com.gazprom.app.tool;

public class UserNotFoundException extends Exception {
    private String username;

    public static UserNotFoundException createWith(String username) {
        return new UserNotFoundException(username);
    }

    public UserNotFoundException() {
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return "User with username " + username + " not found";
    }
}
