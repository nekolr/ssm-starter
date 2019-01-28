package com.nekolr.common;

/**
 * 业务状态
 */
public enum ResultCode {

    SUCCESS("000000", "Success"),
    INVALID_PARAMETER("", "Invalid request parameter"),
    UNKNOWN_ERROR("999999", "Unknown error");

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
