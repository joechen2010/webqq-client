/**
 * @(#)HeartbeatTaskExecutorImpl.java 2013-1-21
 *
 * Copyright (c) 2004-2013 Lakala, Inc.
 * zhongjiang Road, building 22, Lane 879, shanghai, china 
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Lakala, Inc.  
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Lakala.
 */
package org.okj.im.core.task;

import org.apache.log4j.Logger;
import org.okj.commons.logger.LogUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

/**
 * 实现
 * @author Administrator
 * @version $Id: HeartbeatTaskExecutorImpl.java, v 0.1 2013-1-21 下午12:35:24 Administrator Exp $
 */
public class HeartbeatTaskExecutorImpl implements HeartbeatTaskExecutor {
    /* logger */
    private static final Logger     LOGGER = Logger.getLogger(HeartbeatTaskExecutor.class);

    /* 调度任务线程池 */
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    /* 执行的任务 */
    private HeartbeatTask           heartbeatTask;

    /** 
     * @see org.okj.im.core.task.HeartbeatTaskExecutor#startup()
     */
    @Override
    public void startup() {
        //检查任务是否为空
        if (heartbeatTask == null) {
            LogUtils.warn(LOGGER, "心跳任务对象为空");
            return;
        }

        //打印日志
        LogUtils.info(LOGGER, "开始执行定时心跳任务, cron={0}", heartbeatTask.getCronExpression());

        //实例化cron表达式
        CronTrigger trigger = new CronTrigger(heartbeatTask.getCronExpression());

        //执行
        threadPoolTaskScheduler.schedule(heartbeatTask, trigger);
    }

    /** 
     * @see org.okj.im.core.task.HeartbeatTaskExecutor#shutdown()
     */
    @Override
    public void shutdown() {
        threadPoolTaskScheduler.shutdown();
        LogUtils.info(LOGGER, "定时心跳任务已关闭");
    }

    /**
     * Setter method for property <tt>threadPoolTaskScheduler</tt>.
     * 
     * @param threadPoolTaskScheduler value to be assigned to property threadPoolTaskScheduler
     */
    public void setThreadPoolTaskScheduler(ThreadPoolTaskScheduler threadPoolTaskScheduler) {
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
    }

    /**
     * Setter method for property <tt>heartbeatTask</tt>.
     * 
     * @param heartbeatTask value to be assigned to property heartbeatTask
     */
    public void setHeartbeatTask(HeartbeatTask heartbeatTask) {
        this.heartbeatTask = heartbeatTask;
    }

    /**
     * 设置定时任务的执行间隔表达式
     * @param exp
     */
    public void setCronExpression(String exp) {
        heartbeatTask.setCronExpression(exp);
    }

}
