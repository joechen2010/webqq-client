/**
 * @(#)CompletedFindFriendsAction.java 2013-1-23
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
package org.okj.im.core.actions;

import java.util.List;

import org.okj.commons.service.action.ActionContext;
import org.okj.commons.service.action.ActionException;
import org.okj.im.core.constants.ActionContextKeys;
import org.okj.im.model.Member;
import org.springframework.beans.BeanUtils;

/**
 * 查询好友列表完成的action
 * @author Administrator
 * @version $Id: CompletedFindFriendsAction.java, v 0.1 2013-1-23 下午4:48:46 Administrator Exp $
 */
public class CompletedFindFriendsAction extends DefaultBizAction<Void> {

    /** 
     * @see org.okj.im.core.actions.DefaultBizAction#skip(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    public boolean skip(ActionContext context) {
        List<Member> friends = getContextAttribute(context, ActionContextKeys.FRIENDS_KEY);
        if (friends == null || clientContext.isLoadedFriends()) {
            return true;
        }
        return false;
    }

    /** 
     * @see org.storevm.commons.service.action.AbstractBizAction#doExecute(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    protected Void doExecute(ActionContext context) throws ActionException {
        List<Member> friends = getContextAttribute(context, ActionContextKeys.FRIENDS_KEY);
        for (Member friend : friends) {
            //将好友信息放入容器中管理
            Member nFriend = new Member();
            BeanUtils.copyProperties(friend, nFriend); //复制对象并放入容器中进行全局管理
            clientContext.get().addFriend(nFriend); //添加好友列表
        }
        return null;
    }

}
