package com.nekolr.upms.server.support.manager;

import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 日志执行管理器
 *
 * @author nekolr
 */
public class LogExecuteManager {

    /**
     * 核心池大小
     */
    private static final int CORE_POOL_SIZE = 10;

    /**
     * 任务延迟执行时间，单位微秒
     */
    private static final int DELAY_TIME = 10;

    private static LogExecuteManager logExecuteManager = new LogExecuteManager();

    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(CORE_POOL_SIZE);

    private LogExecuteManager() {

    }

    public static LogExecuteManager getInstance() {
        return logExecuteManager;
    }

    public void executeLogTask(TimerTask timerTask) {
        executor.schedule(timerTask, DELAY_TIME, TimeUnit.MICROSECONDS);
    }
}
