/**
 * @(#)BizCode.java 2013-1-21
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

/**
 * 执行业务码
 * @author Administrator
 * @version $Id: BizCode.java, v 0.1 2013-1-21 下午4:06:01 Administrator Exp $
 */
public interface BizCode {
    /* QQ登录 */
    static final String QQ_LOGIN                  = "QQ_LOGIN";

    /* QQ登出 */
    static final String QQ_LOGOUT                 = "QQ_LOGOUT";

    /* 检测QQ心跳 */
    static final String QQ_CHECK_HEARTBEAT        = "QQ_CHECK_HEARTBEAT";

    /* 查询QQ好友列表 */
    static final String QQ_FIND_FRIENDS           = "QQ_FIND_FRIENDS";

    /* 查询在线的QQ好友列表 */
    static final String QQ_FIND_ONLINE_FRIENDS    = "QQ_FIND_ONLINE_FRIENDS";

    /* 发送消息给好友 */
    static final String QQ_SEND_MESSAGE_TO_FRIEND = "QQ_SEND_MESSAGE_TO_FRIEND";

    /* 发送消息给好友群 */
    static final String QQ_SEND_MESSAGE_TO_GROUP  = "QQ_SEND_MESSAGE_TO_GROUP";

    /* 加载好友详情 */
    static final String QQ_LOAD_FRIEND            = "QQ_LOAD_FRIEND";

    /* 加载好友个性化签名 */
    static final String QQ_LOAD_FRIEND_SIGNATURE  = "QQ_LOAD_FRIEND_SIGNATURE";

    /* 加载好友等级 */
    static final String QQ_LOAD_FRIEND_LEVEL      = "QQ_LOAD_FRIEND_LEVEL";

    /* 查询QQ群列表 */
    static final String QQ_FIND_GROUPS            = "QQ_FIND_GROUPS";

    /* 变更状态 */
    static final String QQ_CHANGE_STATUS          = "QQ_CHANGE_STATUS";
    
    static final String RECEIVE_MESSAGE          = "RECEIVE_MESSAGE";
}
