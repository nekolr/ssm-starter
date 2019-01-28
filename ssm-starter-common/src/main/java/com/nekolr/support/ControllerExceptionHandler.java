package com.nekolr.support;

import com.nekolr.common.ResultBean;
import com.nekolr.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ValidationException;

/**
 * 控制层异常处理类（也可以自定义切面类来处理）
 *
 * @author nekolr
 */
@ControllerAdvice
@Slf4j
@ResponseBody
public class ControllerExceptionHandler {

    /**
     * 不要使用最大的异常来处理，要细化，这是错误的处理方式
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResultBean handleException(Throwable e) {
        log.warn(e.getMessage());
        return new ResultBean().setCode(ResultCode.UNKNOWN_ERROR.getCode());
    }

    /**
     * 请求参数校验抛出异常统一处理
     */
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResultBean handleValidationException(Throwable e) {
        return new ResultBean().setCode(ResultCode.INVALID_PARAMETER.getCode());
    }
}
