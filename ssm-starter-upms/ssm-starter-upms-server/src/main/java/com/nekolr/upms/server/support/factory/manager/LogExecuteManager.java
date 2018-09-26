package com.nekolr.upms.server.support.factory.manager;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import java.util.TimerTask;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 日志执行管理器
 *
 * @author nekolr
 */
@Component
@Slf4j
public class LogExecuteManager implements DisposableBean {

    /**
     * 核心池大小
     */
    private static final int CORE_POOL_SIZE = 10;

    /**
     * 任务延迟执行时间，单位微秒
     */
    private static final int DELAY_TIME = 10;

    private ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(CORE_POOL_SIZE);

    public void executeLogTask(TimerTask timerTask) {
        executor.schedule(timerTask, DELAY_TIME, TimeUnit.MICROSECONDS);
    }

    public void destroy() {
        log.info("Destroying bean [logExecuteManager]...");
        executor.shutdown();
    }
}
