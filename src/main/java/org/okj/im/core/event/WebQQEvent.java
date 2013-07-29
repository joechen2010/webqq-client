/**
 * @(#)WebQQEvent.java 2013-1-24
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

import java.util.EventObject;

import net.sf.json.JSONObject;

import org.okj.im.core.constants.WebQQEventCode;

/**
 * WebQQ客户端的事件对象
 * @author Administrator
 * @version $Id: WebQQEvent.java, v 0.1 2013-1-24 上午11:55:43 Administrator Exp $
 */
public class WebQQEvent extends EventObject {
    /** UID */
    private static final long serialVersionUID = 507371712413722581L;

    /* 事件码 */
    private WebQQEventCode    eventCode;

    /* 消息体*/
    private JSONObject        body;

    /**
     * 构造函数
     * @param arg0
     */
    protected WebQQEvent(Object source) {
        super(source);
    }

    /**
     * 构造函数
     * @param eventCode
     * @param source
     */
    public WebQQEvent(WebQQEventCode eventCode, JSONObject body) {
        this(body);
        this.eventCode = eventCode;
        this.body = body;
    }

    /**
     * Getter method for property <tt>eventCode</tt>.
     * 
     * @return property value of eventCode
     */
    public WebQQEventCode getEventCode() {
        return eventCode;
    }

    /**
     * Getter method for property <tt>body</tt>.
     * 
     * @return property value of body
     */
    public JSONObject getBody() {
        return body;
    }
}
