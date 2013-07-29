/**
 * @(#)CompletedLoginAction.java 2013-1-22
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

import org.okj.commons.service.action.ActionContext;
import org.okj.commons.service.action.ActionException;
import org.okj.im.core.constants.ActionContextKeys;
import org.okj.im.model.Member;
import org.springframework.beans.BeanUtils;

/**
 * 
 * @author Administrator
 * @version $Id: CompletedLoginAction.java, v 0.1 2013-1-22 上午9:51:41 Administrator Exp $
 */
public class CompletedLoginAction extends DefaultBizAction<Void> {

    /** 
     * @see org.storevm.commons.service.action.AbstractBizAction#doExecute(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    protected Void doExecute(ActionContext context) throws ActionException {
        //返回上下文中的参数
        Member member = getContextAttribute(context, ActionContextKeys.MEMBER_KEY);

        //将登录会员信息放入容器中管理
        Member self = new Member();
        BeanUtils.copyProperties(member, self); //复制对象并放入容器中进行全局管理
        clientContext.put(self);
        return null;
    }
}
