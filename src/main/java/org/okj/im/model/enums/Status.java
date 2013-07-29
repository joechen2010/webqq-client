/**
 * @(#)Status.java 2013-1-23
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
package org.okj.im.model.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 用户状态
 * @author Administrator
 * @version $Id: Status.java, v 0.1 2013-1-23 上午9:14:55 Administrator Exp $
 */
public enum Status {
    ONLINE("online", "我在线上"),

    OFFLINE("offline", "离线"),

    CALL_ME("callme", "Q我吧"),

    BUSY("busy", "忙碌"),

    HIDDEN("hidden", "隐身"),

    AWAY("away", "离线"),

    SILENT("silent", "请勿打扰"),

    ;

    /* 编码 */
    private String code;

    /* 描述 */
    private String text;

    /**
     * 构造函数
     * @param code
     * @param text
     */
    Status(String code, String text) {
        this.code = code;
        this.text = text;
    }

    /**
     * Getter method for property <tt>code</tt>.
     * 
     * @return property value of code
     */
    public String getCode() {
        return code;
    }

    /**
     * Getter method for property <tt>text</tt>.
     * 
     * @return property value of text
     */
    public String getText() {
        return text;
    }

    /**
     * 
     * @param code
     * @return
     */
    public static Status getEnum(String code) {
        for (Status enums : values()) {
            if (StringUtils.equalsIgnoreCase(code, enums.getCode())) {
                return enums;
            }
        }
        return null;
    }
}
