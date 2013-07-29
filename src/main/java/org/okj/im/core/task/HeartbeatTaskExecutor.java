/**
 * @(#)HeartbeatTaskExecutor.java 2013-1-21
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

/**
 * QQ心跳包任务执行器接口定义
 * @author Administrator
 * @version $Id: HeartbeatTaskExecutor.java, v 0.1 2013-1-21 下午12:33:25 Administrator Exp $
 */
public interface HeartbeatTaskExecutor {
    /**
     * 启动
     */
    void startup();

    /**
     * 关闭
     */
    void shutdown();
}
