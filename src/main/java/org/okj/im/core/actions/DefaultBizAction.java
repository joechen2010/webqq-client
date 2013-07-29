/**
 * @(#)DefaultBizAction.java 2013-1-18
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

import java.text.MessageFormat;

import org.apache.log4j.Logger;
import org.okj.commons.service.action.AbstractBizAction;
import org.okj.commons.service.action.ActionContext;
import org.okj.im.core.WebQQClinetContext;
import org.okj.im.core.constants.ActionContextKeys;
import org.okj.im.core.constants.HttpMethod;
import org.okj.im.core.service.ExParam;
import org.okj.im.core.service.QQHttpService;
import org.okj.im.model.Member;

/**
 * 默认实现
 * @author Administrator
 * @version $Id: DefaultBizAction.java, v 0.1 2013-1-18 下午5:35:00 Administrator Exp $
 */
public abstract class DefaultBizAction<T> extends AbstractBizAction<T> {
    /* logger */
    protected static final Logger LOGGER        = Logger.getLogger(DefaultBizAction.class);

    /* httpclient */
    protected QQHttpService       httpService;

    /* 客户端的上下文环境 */
    protected WebQQClinetContext  clientContext = WebQQClinetContext.getInstance();

    /** 
     * @see org.storevm.commons.service.action.AbstractBizAction#skip(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    public boolean skip(ActionContext context) {
        Member member = getContextAttribute(context, ActionContextKeys.MEMBER_KEY);
        if (member == null) {
            return true;
        }
        return super.skip(context);
    }

    /**
     * Setter method for property <tt>httpService</tt>.
     * 
     * @param httpService value to be assigned to property httpService
     */
    public void setHttpService(QQHttpService httpService) {
        this.httpService = httpService;
    }

    /**
     * 格式化URL
     * @param url
     * @param params
     * @return
     */
    protected String parseUrl(String url, Object... params) {
        if (params != null && params.length > 0) {
            return new MessageFormat(url).format(params);
        }
        return url;
    }

    protected String invokeHttpService(String url, String method, ExParam exParam) {
        String result = httpService.sendHttpMessage(url, method, exParam);
        return result;
    }

    protected String invokeGetHttpService(String url, ExParam exParam) {
        return invokeHttpService(url, HttpMethod.GET, exParam);
    }

    protected String invokePostHttpService(String url, ExParam exParam) {
        return invokeHttpService(url, HttpMethod.POST, exParam);
    }
}
