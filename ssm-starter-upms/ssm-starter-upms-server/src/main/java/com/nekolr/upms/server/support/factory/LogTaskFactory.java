package com.nekolr.upms.server.support.factory;

import com.nekolr.support.SpringContextHolder;
import com.nekolr.upms.api.entity.AccountLog;
import com.nekolr.upms.api.entity.OperationLog;
import com.nekolr.upms.api.rpc.AccountLogService;
import com.nekolr.upms.api.rpc.OperationLogService;
import lombok.extern.slf4j.Slf4j;

import java.util.TimerTask;

/**
 * 日志任务工厂，生产日志任务对象
 *
 * @author nekolr
 */
@Slf4j
public class LogTaskFactory {

    private static AccountLogService accountLogService;
    private static OperationLogService operationLogService;

    static {
        accountLogService = SpringContextHolder.getBean(AccountLogService.class);
        operationLogService = SpringContextHolder.getBean(OperationLogService.class);
    }

    private LogTaskFactory() {

    }

    /**
     * 写入用户登录日志
     *
     * @param username
     * @param userId
     * @param ip
     * @param status
     * @param message
     * @return
     */
    public static TimerTask loginLog(String username, String userId, String ip, Boolean status, String message) {
        return new TimerTask() {
            @Override
            public void run() {
                AccountLog accountLog = LogFactory.createAccountLog(username, userId, "用户登录", ip, status, message);
                if (!accountLogService.save(accountLog)) {
                    log.error("写入用户登录日志异常：{}", accountLog.toString());
                }
            }
        };
    }

    /**
     * 写入用户登出日志
     *
     * @param username
     * @param userId
     * @param ip
     * @param status
     * @param message
     * @return
     */
    public static TimerTask logoutLog(String username, String userId, String ip, Boolean status, String message) {
        return new TimerTask() {
            @Override
            public void run() {
                AccountLog accountLog = LogFactory.createAccountLog(
                        username, userId, "用户登出", ip, status, message
                );
                if (!accountLogService.save(accountLog)) {
                    log.error("写入用户登出日志异常：{}", accountLog.toString());
                }
            }
        };
    }

    /**
     * 写入用户业务操作日志
     *
     * @param username
     * @param userId
     * @param api
     * @param method
     * @param status
     * @param message
     * @return
     */
    public static TimerTask businessLog(String username, String userId, String api,
                                        String method, Boolean status, String message) {
        return new TimerTask() {
            @Override
            public void run() {
                OperationLog operationLog = LogFactory.createOperationLog(
                        username, userId, "业务操作日志", api, method, status, message
                );
                if (!operationLogService.save(operationLog)) {
                    log.error("写入用户业务操作日志异常：{}", operationLog.toString());
                }
            }
        };
    }

    /**
     * 写入业务异常日志
     *
     * @param username
     * @param userId
     * @param api
     * @param method
     * @param status
     * @param message
     * @return
     */
    public static TimerTask exceptionLog(String username, String userId, String api,
                                         String method, Boolean status, String message) {
        return new TimerTask() {
            @Override
            public void run() {
                OperationLog operationLog = LogFactory.createOperationLog(
                        username, userId, "业务异常日志", api, method, status, message
                );
                if (!operationLogService.save(operationLog)) {
                    log.error("写入业务异常日志异常：{}", operationLog.toString());
                }
            }
        };
    }
}
