/**
 * @(#)WebQQEventCode.java 2013-1-24
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
package org.okj.im.core.constants;

import org.apache.commons.lang.StringUtils;

/**
 * QQ的事件码枚举
 * @author Administrator
 * @version $Id: WebQQEventCode.java, v 0.1 2013-1-24 上午11:38:58 Administrator Exp $
 */
public enum CommandCode {
    SAY_HELLO("HELLO", "向在线好友发送消息"),
    RELOGIN("RELOGIN", "重新登录"),

    ;

    /* 码值 */
    private String code;

    /* 描述 */
    private String text;

    /**
     * 构造函数
     * @param code
     * @param text
     */
    CommandCode(String code, String text) {
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
     * 通过码值实例化枚举
     * @param code
     * @return
     */
    public static CommandCode getEnum(String code) {
        for (CommandCode enums : values()) {
            if (StringUtils.equalsIgnoreCase(code, enums.getCode())) {
                return enums;
            }
        }
        return null;
    }
}
