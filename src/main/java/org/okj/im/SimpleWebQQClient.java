/**
 * @(#)SimpleWebQQClient.java 2013-1-23
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

import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.okj.commons.logger.LogUtils;
import org.okj.commons.service.action.ActionContext;
import org.okj.commons.service.action.ActionException;
import org.okj.commons.service.action.ActionsExecutor;
import org.okj.im.core.WebQQClinetContext;
import org.okj.im.core.constants.ActionContextKeys;
import org.okj.im.core.constants.BizCode;
import org.okj.im.model.Group;
import org.okj.im.model.Member;
import org.okj.im.model.enums.Status;
import org.springframework.beans.BeanUtils;

/**
 * 简单的WebQQ客户端实现
 * @author Administrator
 * @version $Id: SimpleWebQQClient.java, v 0.1 2013-1-23 上午10:40:28 Administrator Exp $
 */
public class SimpleWebQQClient implements WebQQClient {
    /* logger */
    private static final Logger LOGGER = Logger.getLogger(WebQQClient.class);

    /* Actions执行器 */
    private ActionsExecutor     actionsExecutor;
    
    

    /**
     * 构造函数
     */
    public SimpleWebQQClient() {
    }

    /** 
     * @see org.okj.im.WebQQClient#login(org.okj.im.model.Member)
     */
    @Override
    public boolean login(Member member) {
        ActionContext context = new ActionContext(BizCode.QQ_LOGIN);
        context.addAttribute(ActionContextKeys.MEMBER_KEY, member);
        return executeActions(context);
    }

    /**
     * @see org.okj.im.WebQQClient#loadFriend(java.lang.String)
     */
    @Override
    public Member loadFriend(String account) {
        ActionContext context = new ActionContext(BizCode.QQ_LOAD_FRIEND);
        Member sample = new Member();
        sample.getAccount().setAccount(account);
        context.addAttribute(ActionContextKeys.MEMBER_KEY, sample);
        boolean success = executeActions(context);
        if (success) {
            Member friend = (Member) context.getAttribute(ActionContextKeys.FRIEND_KEY);
            //为了释放ActionContext,所以通过复制bean并返回副本的方式
            Member nFriend = new Member();
            BeanUtils.copyProperties(friend, nFriend);
            return nFriend;
        }
        return null;
    }

    /** 
     * @see org.okj.im.WebQQClient#loadFriendSignature(java.lang.String)
     */
    @Override
    public Member loadFriendSignature(String account) {
        ActionContext context = new ActionContext(BizCode.QQ_LOAD_FRIEND_SIGNATURE);
        Member sample = new Member();
        sample.getAccount().setAccount(account);
        context.addAttribute(ActionContextKeys.MEMBER_KEY, sample);
        boolean success = executeActions(context);
        if (success) {
            Member friend = (Member) context.getAttribute(ActionContextKeys.FRIEND_KEY);
            //为了释放ActionContext,所以通过复制bean并返回副本的方式
            Member nFriend = new Member();
            BeanUtils.copyProperties(friend, nFriend);
            return nFriend;
        }
        return null;
    }

    /** 
     * @see org.okj.im.WebQQClient#loadFriendLevel(java.lang.String)
     */
    @Override
    public Member loadFriendLevel(String account) {
        ActionContext context = new ActionContext(BizCode.QQ_LOAD_FRIEND_LEVEL);
        Member sample = new Member();
        sample.getAccount().setAccount(account);
        context.addAttribute(ActionContextKeys.MEMBER_KEY, sample);
        boolean success = executeActions(context);
        if (success) {
            Member friend = (Member) context.getAttribute(ActionContextKeys.FRIEND_KEY);
            //为了释放ActionContext,所以通过复制bean并返回副本的方式
            Member nFriend = new Member();
            BeanUtils.copyProperties(friend, nFriend);
            return nFriend;
        }
        return null;
    }

    /** 
     * @see org.okj.im.WebQQClient#changeStatus(org.okj.im.model.enums.Status)
     */
    @Override
    public void changeStatus(Status newStatus) {
        ActionContext context = new ActionContext(BizCode.QQ_CHANGE_STATUS);
        Member sample = new Member();
        sample.setStatus(newStatus);
        context.addAttribute(ActionContextKeys.MEMBER_KEY, sample);
        executeActions(context);
    }

    /**
     * @see org.okj.im.WebQQClient#sendMessageToFriend(java.lang.String, java.lang.String)
     */
    @Override
    public boolean sendMessageToFriend(String account, String message) {
        ActionContext context = new ActionContext(BizCode.QQ_SEND_MESSAGE_TO_FRIEND);
        Member friend = new Member();
        friend.getAccount().setAccount(account);
        context.addAttribute(ActionContextKeys.MEMBER_KEY, friend);
        context.addAttribute(ActionContextKeys.MESSAGE_KEY, message);
        return executeActions(context);
    }

    /**
     * @see org.okj.im.WebQQClient#sendMessageToGroup(java.lang.String, java.lang.String)
     */
    @Override
    public boolean sendMessageToGroup(String account, String message) {
        ActionContext context = new ActionContext(BizCode.QQ_SEND_MESSAGE_TO_GROUP);
        Group group = new Group();
        group.setNumber(NumberUtils.toLong(account, -1L));
        context.addAttribute(ActionContextKeys.GROUPS_KEY, group);
        context.addAttribute(ActionContextKeys.MESSAGE_KEY, message);
        return executeActions(context);
    }

    /** 
     * @see org.okj.im.WebQQClient#logout()
     */
    @Override
    public boolean logout() {
        ActionContext context = new ActionContext(BizCode.QQ_LOGOUT);
        return executeActions(context);
    }

    /** 
     * @see org.okj.im.WebQQClient#findFriends()
     */
    @Override
    public List<Member> findFriends() {
        ActionContext context = new ActionContext(BizCode.QQ_FIND_FRIENDS);
        boolean success = executeActions(context);
        if (success) {
            return WebQQClinetContext.getInstance().get().getFriends();
        }
        return null;
    }

    /** 
     * @see org.okj.im.WebQQClient#findOnlineFriends()
     */
    @Override
    public List<Member> findOnlineFriends() {
        ActionContext context = new ActionContext(BizCode.QQ_FIND_ONLINE_FRIENDS);
        boolean success = executeActions(context);
        if (success) {
            return WebQQClinetContext.getInstance().getOnlineFriends();
        }
        return null;
    }

    /** 
     * @see org.okj.im.WebQQClient#findGroups()
     */
    @Override
    public List<Group> findGroups() {
        ActionContext context = new ActionContext(BizCode.QQ_FIND_GROUPS);
        boolean success = executeActions(context);
        if (success) {
            return WebQQClinetContext.getInstance().getGroups();
        }
        return null;
    }

    /**
     * 执行Actions
     * @param context 上下文
     * @return
     */
    protected boolean executeActions(ActionContext context) {
        try {
            actionsExecutor.execute(context);
            return true;
        } catch (ActionException ex) {
            LogUtils.error(LOGGER, "WebQQ Client调用发生异常", ex);
        } catch (Exception ex) {
            LogUtils.error(LOGGER, "WebQQ Client调用发生系统异常", ex);
        }
        return false;
    }

    /**
     * Setter method for property <tt>actionsExecutor</tt>.
     * 
     * @param actionsExecutor value to be assigned to property actionsExecutor
     */
    public void setActionsExecutor(ActionsExecutor actionsExecutor) {
        this.actionsExecutor = actionsExecutor;
    }

    
}
