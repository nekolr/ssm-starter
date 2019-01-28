package com.nekolr.common;

/**
 * 业务状态
 */
public enum ResultCode {

    SUCCESS("0", "success"),
    ERROR_JWT("", "error jwt"),
    ERROR_REQUEST("", "error request");

    private String code;
    private String reason;

    public String getCode() {
        return code;
    }

    public String getReason() {
        return reason;
    }

    ResultCode(String code, String reason) {
        this.code = code;
        this.reason = reason;
    }
}
