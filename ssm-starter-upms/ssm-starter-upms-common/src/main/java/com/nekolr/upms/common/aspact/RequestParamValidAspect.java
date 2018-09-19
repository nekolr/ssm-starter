package com.nekolr.upms.common.aspact;

import com.nekolr.upms.common.FieldError;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 请求参数校验切面
 *
 * @author nekolr
 */
@Component
@Aspect
@Slf4j
public class RequestParamValidAspect {

    /**
     * 默认的校验工厂类
     */
    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    /**
     * 校验器
     */
    private final ExecutableValidator validator = factory.getValidator().forExecutables();

    /**
     * 参数名发现
     */
    private ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    /**
     * 切点
     */
    @Pointcut("execution(* com.nekolr.upms.server.controller.*.*(..))")
    public void controllerBefore() {
    }

    @Before("controllerBefore()")
    public void before(JoinPoint joinPoint) {
        // 目标对象
        Object target = joinPoint.getTarget();
        // 获取切入方法参数
        Object[] args = joinPoint.getArgs();
        // 获取切入方法
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        // 执行校验
        Set<ConstraintViolation<Object>> validResult = validMethodParams(target, method, args);
        if (validResult.isEmpty()) {
            // 获取方法的参数名
            String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
            List<FieldError> errors = validResult.stream().map(constraintViolation -> {
                PathImpl path = (PathImpl) constraintViolation.getPropertyPath();
                int paramIndex = path.getLeafNode().getParameterIndex();
                String paramName = parameterNames[paramIndex];
                String message = constraintViolation.getMessage();
                FieldError error = new FieldError();
                error.setName(paramName);
                error.setMessage(message);
                return error;
            }).collect(Collectors.toList());

            throw new ValidationException(toValidationMessage(errors));
        }
    }

    /**
     * 参数校验
     *
     * @param obj
     * @param method
     * @param params
     * @param <T>
     * @return
     */
    private <T> Set<ConstraintViolation<T>> validMethodParams(T obj, Method method, Object[] params) {
        return validator.validateParameters(obj, method, params);
    }

    /**
     * 输出错误信息
     *
     * @param fieldErrors
     * @return
     */
    private String toValidationMessage(List<FieldError> fieldErrors) {
        StringBuilder builder = new StringBuilder();
        fieldErrors.forEach(e -> builder.append(e));
        return builder.toString();
    }
}
