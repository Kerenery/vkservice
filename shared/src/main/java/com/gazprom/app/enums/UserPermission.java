package com.gazprom.app.enums;

public enum UserPermission {

    USER_WRITE("user:write"),
    USER_READ("user:read");

    private final String permission;

    UserPermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
