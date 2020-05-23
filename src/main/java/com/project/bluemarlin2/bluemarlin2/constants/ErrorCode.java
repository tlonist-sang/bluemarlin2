package com.project.bluemarlin2.bluemarlin2.constants;

public enum  ErrorCode {
    ACCESS_TOKEN_EXPIRE(600, "token expired."),

    PASSWORD_CONFIRM_MISMATCH(700, "password confirm doesn't match."),
    PASSWORD_LENGTH_ERROR(701, "password confirm doesn't match."),
    PASSWORD_NOT_SECURE(702, "password is not secure."),
    USER_ALREADY_EXISTS(703, "user already exists.");

    private final int value;
    private final String reasonPhrase;

    ErrorCode(int value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public int getValue() {
        return value;
    }

    public String getReasonPhrase() {
        return reasonPhrase;
    }
}
