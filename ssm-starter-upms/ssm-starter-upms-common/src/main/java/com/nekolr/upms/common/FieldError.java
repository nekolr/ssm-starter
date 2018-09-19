package com.nekolr.upms.common;

import lombok.Data;

/**
 * 封装参数字段校验错误
 *
 * @author nekolr
 */
@Data
public class FieldError {
    /**
     * 参数名
     */
    private String name;
    /**
     * 校验错误信息
     */
    private String message;
}
