/**
 * @(#)HeartbeatAction.java 2013-1-22
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

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.okj.commons.logger.LogUtils;
import org.okj.commons.service.action.ActionContext;
import org.okj.commons.service.action.ActionException;
import org.okj.im.core.constants.QQClientErrorCode;
import org.okj.im.core.constants.WebQQEventCode;
import org.okj.im.core.event.WebQQEvent;
import org.okj.im.core.event.WebQQEventPublisher;
import org.okj.im.core.service.ExParam;

/**
 * QQ心跳检查的action
 * @author Administrator
 * @version $Id: HeartbeatAction.java, v 0.1 2013-1-22 下午2:45:42 Administrator Exp $
 */
public class HeartbeatAction extends DefaultBizAction<Void> {
    /* 推送消息的URL */
    private static final String SERVICE_URL = "http://d.web2.qq.com/channel/poll2?clientid={0}&psessionid={1}";

    /* 事件发布器 */
    private WebQQEventPublisher eventPublisher;

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
            LogUtils.warn(LOGGER, "WebQQ尚未登录，心跳检测失败");
            throw new ActionException(QQClientErrorCode.QQ_NOT_LOGIN);
        }
    }

    /** 
     * @see org.storevm.commons.service.action.AbstractBizAction#doExecute(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    protected Void doExecute(ActionContext context) throws ActionException {
        //请求URL
        String reqUrl = parseUrl(SERVICE_URL, clientContext.getClientId(),
            clientContext.getPSessionId());
        LogUtils.info(LOGGER, "QQ心跳检测的请求URL, url={0}", reqUrl);

        //调用服务
        ExParam exParam = new ExParam();
        exParam.setCookie(clientContext.getCookies());
        String result = invokeGetHttpService(reqUrl, exParam);
        LogUtils.info(LOGGER, "获取QQ心跳检测的响应, result={0}", result);

        //解析返回结果
        List<WebQQEvent> events = parseResult(result);
        for (WebQQEvent event : events) {
            //发布事件
            eventPublisher.publish(event);
        }
        return null;
    }

    /**
     * 解析响应
     * @param result
     * @return
     */
    protected List<WebQQEvent> parseResult(String result) {
        List<WebQQEvent> events = new ArrayList<WebQQEvent>();
        try {
            JSONObject retJ = JSONObject.fromObject(result);
            int retcode = retJ.getInt("retcode");
            if (retcode == 0) {
                JSONArray results = retJ.getJSONArray("result");
                for (int i = 0, n = results.size(); i < n; i++) {
                    WebQQEvent event = getWebQQEvent(results.getJSONObject(i));
                    if (event != null) {
                        events.add(event); //添加一个有效的事件
                    }
                }
            }
        } catch (JSONException ex) {
            LogUtils.error(LOGGER, "解析QQ心跳检测响应时发生异常", ex);
        }
        return events;
    }

    /**
     * 生成一个事件对象
     * @param result
     * @return
     */
    protected WebQQEvent getWebQQEvent(JSONObject result) {
        String pollType = result.getString("poll_type");
        JSONObject value = result.getJSONObject("value");
        WebQQEventCode eventCode = WebQQEventCode.getEnum(pollType);
        if (eventCode != null) {
            //实例化事件对象
            return new WebQQEvent(eventCode, value);

        }
        return null;
    }

    /**
     * Setter method for property <tt>eventPublisher</tt>.
     * 
     * @param eventPublisher value to be assigned to property eventPublisher
     */
    public void setEventPublisher(WebQQEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
}
