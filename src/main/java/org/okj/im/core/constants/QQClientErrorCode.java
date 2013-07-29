/**
 * @(#)QQClientErrorCode.java 2013-1-22
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

import org.okj.commons.service.action.ActionErrorCode;

/**
 * 异常码
 * @author Administrator
 * @version $Id: QQClientErrorCode.java, v 0.1 2013-1-22 上午8:57:16 Administrator Exp $
 */
public enum QQClientErrorCode implements ActionErrorCode {
    QQ_LOGIN_FAIL("QQ_LOGIN_FAIL", "WebQQ登录失败"),

    QQ_LOGOUT_FAIL("QQ_LOGOUT_FAIL", "WebQQ登出失败"),

    QQ_NOT_LOGIN("QQ_NOT_LOGIN", "WebQQ未登录"),

    QQ_SEND_MSG_FAIL("QQ_SEND_MSG_FAIL", "WebQQ发送消息失败"),

    QQ_NUMBER_IS_EMPTY("QQ_NUMBER_IS_EMPTY", "QQ号码为空"),

    QQ_NUMBER_IS_WRONG("QQ_NUMBER_IS_WRONG", "不是好友的QQ号码"),

    QQ_GROUP_NUMBER_IS_EMPTY("QQ_GROUP_NUMBER_IS_EMPTY", "QQ群号码为空"),

    QQ_GROUP_NUMBER_IS_WRONG("QQ_GROUP_NUMBER_IS_WRONG", "不是好友群的群号码"),

    QQ_CHANGE_STATUS_FAIL("QQ_CHANGE_STATUS_FAIL", "变更QQ用户状态失败"),

    QQ_NOT_SEND_EMPTY_MSG("QQ_NOT_SEND_EMPTY_MSG", "不能发送空消息"),

    QQ_MAKE_MESSAGE_IS_WRONG("QQ_MAKE_MESSAGE_IS_WRONG", "组装发送的消息对象失败"),

    ;

    private String code;

    private String text;

    /**
     * 构造函数
     * @param code
     * @param text
     */
    QQClientErrorCode(String code, String text) {
        this.code = code;
        this.text = text;
    }

    /** 
     * @see org.storevm.commons.service.action.ActionErrorCode#getDescription()
     */
    @Override
    public String getDescription() {
        return text;
    }

    /** 
     * @see org.storevm.commons.service.action.ActionErrorCode#getCode()
     */
    @Override
    public String getCode() {
        return code;
    }

}
