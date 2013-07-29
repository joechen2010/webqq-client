/**
 * @(#)WebQQClient.java 2013-1-21
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
package org.okj.im;

import java.util.List;

import org.okj.im.model.Group;
import org.okj.im.model.Member;
import org.okj.im.model.enums.Status;

/**
 * Web QQ客户端接口
 * @author Administrator
 * @version $Id: WebQQClient.java, v 0.1 2013-1-21 下午2:58:32 Administrator Exp $
 */
public interface WebQQClient {
	
	
    /**
     * QQ登录
     * <blockquote>
     * QQ完成登录需要经过三次服务调用：
     * <ol>
     * <li>检查QQ账户，是否需要验证码输入；
     * <li>正式登录；
     * <li>渠道登录；
     * </ol>
     * </blockquote>
     * @param member 需要提供QQ号码和登录密码
     * @return true 登录成功 false 登录失败
     */
    boolean login(Member member);

    /**
     * 加载QQ好友详情
     * <blockquote>
     * 前置条件: 必须已经成功登录
     * </blockquote>
     * @param account 需要加载的好友QQ号码(QQ系统内部还有一个uin标识)
     * @return QQ会员详情
     */
    Member loadFriend(String account);

    /**
     * 加载QQ好友的个性化签名
     * <blockquote>
     * 前置条件: 必须已经成功登录
     * </blockquote>
     * @param account 需要加载的好友QQ号码(QQ系统内部还有一个uin标识)
     * @return
     */
    Member loadFriendSignature(String account);

    /**
     * 加载QQ好友的等级
     * <blockquote>
     * 前置条件: 必须已经成功登录
     * </blockquote>
     * @param account 需要加载的好友QQ号码(QQ系统内部还有一个uin标识)
     * @return
     */
    Member loadFriendLevel(String account);

    /**
     * 改变当前已登录QQ会员的状态
     * <blockquote>
     * 前置条件: 必须已经成功登录
     * </blockquote>
     * @param newStatus 当前的新状态
     */
    void changeStatus(Status newStatus);

    /**
     * 向指定的QQ好友发送消息
     * <blockquote>
     * 前置条件: 
     * <ol>
     * <li>必须已经成功登录;
     * <li>已经成功加载了好友列表，因为发送QQ消息需要好友的uin字段(一个内部的标识，每次登录该字段值都不一样)，而非QQ号码；
     * <li>已经成功启动QQ心跳检测任务，否则无法发送QQ消息；
     * </ol>
     * </blockquote>
     * @param account 发送消息的好友的QQ号码
     * @param message 发送的消息内容
     * @return true 发送成功 false 发送失败
     */
    boolean sendMessageToFriend(String account, String message);

    /**
     * 向指定的QQ群发送消息
     * <blockquote>
     * 前置条件: 
     * <ol>
     * <li>必须已经成功登录;
     * <li>已经成功加载了好友群列表，因为发送QQ消息需要好友群的uin字段(一个内部的标识，每次登录该字段值都不一样)，而非群号码；
     * <li>已经成功启动QQ心跳检测任务，否则无法发送QQ消息；
     * </ol>
     * </blockquote>
     * @param account 发送消息的QQ群号
     * @param message 发送的消息内容
     * @return
     */
    boolean sendMessageToGroup(String account, String message);

    /**
     * QQ登出
     * <blockquote>
     * 前置条件: 必须已经成功登录
     * </blockquote>
     * @return true 登出成功 false 登出失败
     */
    boolean logout();

    /**
     * 查询好友列表
     * <blockquote>
     * 前置条件: 必须已经成功登录
     * </blockquote>
     * @return 好友列表
     */
    List<Member> findFriends();

    /**
     * 查询在线的好友列表
     * <blockquote>
     * 前置条件: 必须已经成功登录
     * </blockquote>
     * @return 在线好友列表
     */
    List<Member> findOnlineFriends();

    /**
     * 查询QQ群列表
     * <blockquote>
     * 前置条件: 必须已经成功登录
     * </blockquote>
     * @return
     */
    List<Group> findGroups();
}
