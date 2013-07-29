/**
 * @(#)AbstractLoadFriendAction.java 2013-1-23
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

import org.apache.commons.lang.StringUtils;
import org.okj.commons.logger.LogUtils;
import org.okj.commons.service.action.ActionContext;
import org.okj.commons.service.action.ActionException;
import org.okj.im.core.constants.ActionContextKeys;
import org.okj.im.core.constants.QQClientErrorCode;
import org.okj.im.model.Member;

/**
 * 
 * @author Administrator
 * @version $Id: AbstractLoadFriendAction.java, v 0.1 2013-1-23 下午5:26:48 Administrator Exp $
 */
public abstract class AbstractLoadFriendAction extends DefaultBizAction<Void> {

    /** 
     * @see org.okj.im.core.actions.DefaultBizAction#skip(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    public boolean skip(ActionContext context) {
        return false;
    }

    /** 
     * @see org.storevm.commons.service.action.AbstractBizAction#before(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    public void before(ActionContext context) throws ActionException {
        //进行参数检查
        if (clientContext.get() == null) {
            LogUtils.warn(LOGGER, "WebQQ尚未登录，无法执行加载好友详情");
            throw new ActionException(QQClientErrorCode.QQ_NOT_LOGIN);
        }

        Member sample = getContextAttribute(context, ActionContextKeys.MEMBER_KEY);
        if (StringUtils.isBlank(sample.getAccount().getAccount())) {
            LogUtils.warn(LOGGER, "QQ号码为空，无法执行加载好友详情");
            throw new ActionException(QQClientErrorCode.QQ_NUMBER_IS_EMPTY);
        }
    }

}
