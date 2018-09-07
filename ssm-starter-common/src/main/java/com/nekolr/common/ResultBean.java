package com.nekolr.common;

import lombok.Data;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用消息返回 bean
 */
@Data
public class ResultBean {

    /**
     * 失败或成功
     */
    private Boolean status;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private Map<String, Object> data = new HashMap<>();


    public ResultBean success(String message) {
        this.status = Boolean.TRUE;
        this.code = 200;
        this.message = message;
        return this;
    }

    public ResultBean fail(String message) {
        this.status = Boolean.FALSE;
        this.code = 500;
        this.message = message;
        return this;
    }

    public ResultBean fail(Integer code, String message) {
        this.status = Boolean.FALSE;
        this.code = code;
        this.message = message;
        return this;
    }


    public ResultBean addData(String key, Object value) {
        this.data.put(key, value);
        return this;
    }
}
