/**
 * @(#)WebQQEventListener.java 2013-1-24
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
package org.okj.im.core.event;


/**
 * WebQQ事件发布器接口
 * @author Administrator
 * @version $Id: WebQQEventListener.java, v 0.1 2013-1-24 上午11:48:50 Administrator Exp $
 */
public interface WebQQEventPublisher {
    /**
     * 发布一个事件
     * @param event
     */
    void publish(WebQQEvent event);

    /**
     * 注册一个消息处理器
     * @param listener
     */
    void register(WebQQEventListener listener);

    /**
     * 注销一个消息处理器
     * @param handler
     */
    void unregister(WebQQEventListener listener);

    /**
     * 注销所有的消息处理器
     */
    void unregisterAll();
}
