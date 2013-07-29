/**
 * @(#)SimpleWebQQEventPublisher.java 2013-1-24
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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.okj.commons.logger.LogUtils;

/**
 * 实现
 * @author Administrator
 * @version $Id: SimpleWebQQEventPublisher.java, v 0.1 2013-1-24 下午12:03:47 Administrator Exp $
 */
public class SimpleWebQQEventPublisher implements WebQQEventPublisher {
    /* logger */
    private static final Logger     LOGGER    = Logger.getLogger(WebQQEventPublisher.class);

    /* 监听器集合 */
    private Set<WebQQEventListener> listeners = Collections
                                                  .synchronizedSet(new HashSet<WebQQEventListener>());

    /**
     * 构造函数
     */
    public SimpleWebQQEventPublisher() {
    }

    /** 
     * @see org.okj.im.core.event.WebQQEventPublisher#publish(org.okj.im.core.event.WebQQEvent)
     */
    @Override
    public void publish(WebQQEvent event) {
        if (event != null) {
            for (WebQQEventListener listener : listeners) {
                listener.onWebQQEvent(event);
            }
        }
    }

    /** 
     * @see org.okj.im.core.event.WebQQEventPublisher#register(org.okj.im.core.event.WebQQEventListener)
     */
    @Override
    public void register(WebQQEventListener listener) {
        if (listener != null) {
            listeners.add(listener);
        }
        LogUtils.info(LOGGER, "注册监听器完成, 当前监听器集合大小, listener.size={0}", listeners.size());
    }

    /** 
     * @see org.okj.im.core.event.WebQQEventPublisher#unregister(org.okj.im.core.event.WebQQEventListener)
     */
    @Override
    public void unregister(WebQQEventListener listener) {
        if (listener != null) {
            listeners.remove(listener);
        }
        LogUtils.info(LOGGER, "注销监听器完成, 当前监听器集合大小, listener.size={0}", listeners.size());
    }

    /** 
     * @see org.okj.im.core.event.WebQQEventPublisher#unregisterAll()
     */
    @Override
    public void unregisterAll() {
        listeners.clear();
    }

	public Set<WebQQEventListener> getListeners() {
		return listeners;
	}

	public void setListeners(Set<WebQQEventListener> listeners) {
		this.listeners = listeners;
	}
}
