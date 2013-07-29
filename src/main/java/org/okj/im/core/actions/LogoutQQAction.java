/**
 * @(#)LogoutQQAction.java 2013-1-22
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

import org.okj.commons.logger.LogUtils;
import org.okj.commons.service.action.ActionContext;
import org.okj.commons.service.action.ActionException;
import org.okj.im.core.constants.QQClientErrorCode;
import org.okj.im.core.service.ExParam;

/**
 * WebQQ登出的action
 * @author Administrator
 * @version $Id: LogoutQQAction.java, v 0.1 2013-1-22 上午10:26:58 Administrator Exp $
 */
public class LogoutQQAction extends DefaultBizAction<Void> {
    //QQ登出的服务URL
    private static final String SERVICE_URL = "http://d.web2.qq.com/channel/logout2?ids=&clientid={0}&psessionid={1}&t={2}";

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
        if (clientContext.get() == null) {
            LogUtils.warn(LOGGER, "WebQQ尚未登录，无法执行登出");
            throw new ActionException(QQClientErrorCode.QQ_NOT_LOGIN);
        }
    }

    /** 
     * @see org.okj.im.core.actions.DefaultBizAction#doExecute(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    protected Void doExecute(ActionContext context) throws ActionException {
        //拼装URL
        String reqUrl = parseUrl(SERVICE_URL, clientContext.getClientId(),
            clientContext.getPSessionId(), String.valueOf(System.currentTimeMillis()));
        LogUtils.info(LOGGER, "QQ登出的请求URL, url={0}", reqUrl);

        //调用服务
        ExParam exParam = new ExParam();
        exParam.setCookie(clientContext.getCookies());
        String result = invokeGetHttpService(reqUrl, exParam);
        LogUtils.info(LOGGER, "获取QQ登出的响应, result={0}", result);

        return null;
    }

    /** 
     * @see org.storevm.commons.service.action.AbstractBizAction#after(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    public void after(ActionContext context) throws ActionException {
        clientContext.clear();
    }

}
