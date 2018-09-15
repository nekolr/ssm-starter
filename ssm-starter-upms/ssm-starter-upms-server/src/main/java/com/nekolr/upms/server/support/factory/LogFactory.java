package com.nekolr.upms.server.support.factory;

import com.nekolr.upms.api.entity.AccountLog;
import com.nekolr.upms.api.entity.OperationLog;
import com.nekolr.util.DateTimeUtils;

/**
 * 日志工厂，生产日志对象
 *
 * @author nekolr
 */
public class LogFactory {

    private LogFactory() {

    }

    /**
     * 创建账户操作日志对象
     *
     * @param username
     * @param logName
     * @param ip
     * @param status
     * @param message
     * @return
     */
    public static AccountLog createAccountLog(String username, String logName, String ip,
                                              Boolean status, String message) {
        AccountLog accountLog = new AccountLog();
        accountLog.setUsername(username);
        accountLog.setLogName(logName);
        accountLog.setIp(ip);
        accountLog.setStatus(status);
        accountLog.setMessage(message);
        accountLog.setCreateTime(DateTimeUtils.getCurrentDateTime());
        return accountLog;
    }

    /**
     * 创建操作日志对象
     *
     * @param username
     * @param logName
     * @param api
     * @param method
     * @param status
     * @param message
     * @return
     */
    public static OperationLog createOperationLog(String username, String logName, String api,
                                                  String method, Boolean status, String message) {
        OperationLog operationLog = new OperationLog();
        operationLog.setUsername(username);
        operationLog.setLogName(logName);
        operationLog.setApi(api);
        operationLog.setMethod(method);
        operationLog.setStatus(status);
        operationLog.setMessage(message);
        operationLog.setCreateTime(DateTimeUtils.getCurrentDateTime());
        return operationLog;
    }
}
