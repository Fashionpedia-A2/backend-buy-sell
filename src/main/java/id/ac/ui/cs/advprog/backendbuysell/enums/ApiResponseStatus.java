package id.ac.ui.cs.advprog.backendbuysell.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ApiResponseStatus {
    SUCCESS,
    FAILED;

    @JsonCreator
    public static ApiResponseStatus fromString(String value) {
        return value == null ? null : ApiResponseStatus.valueOf(value.toUpperCase());
    }

    @JsonValue
    public String toJson() {
        return this.name().toLowerCase();
    }
}