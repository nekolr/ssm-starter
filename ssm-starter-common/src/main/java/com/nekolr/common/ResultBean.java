package com.nekolr.common;

import lombok.Data;

/**
 * 通用消息返回 bean
 */
@Data
public class ResultBean<T> {

    /**
     * 业务状态码
     * {@link ResultCode}
     */
    private String code;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    public ResultBean setCode(String code) {
        this.code = code;
        return this;
    }

    public ResultBean setMessage(String message) {
        this.message = message;
        return this;
    }

    public ResultBean setData(T data) {
        this.data = data;
        return this;
    }
}
