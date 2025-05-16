package com.azid.auth.backend.AZ.Auth.model.enums;

public enum GenderEnum {
    MALE("M"),
    FEMALE("F");

    private final String code;

    GenderEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static GenderEnum fromString(String value) {
        return switch (value.toLowerCase()) {
            case "male" -> MALE;
            case "female" -> FEMALE;
            default -> throw new IllegalArgumentException("Invalid gender: " + value);
        };
    }
}