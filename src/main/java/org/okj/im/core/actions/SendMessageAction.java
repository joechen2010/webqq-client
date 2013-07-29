/**
 * @(#)SendMessageAction.java 2013-1-22
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.okj.commons.logger.LogUtils;
import org.okj.commons.service.action.ActionContext;
import org.okj.commons.service.action.ActionException;
import org.okj.im.core.constants.ActionContextKeys;
import org.okj.im.core.constants.QQClientErrorCode;
import org.okj.im.core.service.ExParam;

/**
 * 发送消息的抽象Action
 * @author Administrator
 * @version $Id: SendMessageAction.java, v 0.1 2013-1-22 下午5:55:40 Administrator Exp $
 */
public abstract class SendMessageAction extends DefaultBizAction<Void> {
    /** 
     * @see org.storevm.commons.service.action.AbstractBizAction#before(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    public void before(ActionContext context) throws ActionException {
        if (clientContext.get() == null) {
            LogUtils.warn(LOGGER, "WebQQ尚未登录，无法执行发送消息");
            throw new ActionException(QQClientErrorCode.QQ_NOT_LOGIN);
        }

        //检查发送的消息是否为空
        String msg = getContextAttribute(context, ActionContextKeys.MESSAGE_KEY);
        if (StringUtils.isBlank(msg)) {
            LogUtils.warn(LOGGER, "发送的QQ消息为空，无法执行发送消息给");
            throw new ActionException(QQClientErrorCode.QQ_NOT_SEND_EMPTY_MSG);
        }
    }

    /**
     * 解析返回结果
     * @param result
     */
    protected void parseResult(String result) {
        JSONObject rh = JSONObject.fromObject(result);
        if (!"ok".equals(rh.getString("result"))) {
            throw new ActionException(QQClientErrorCode.QQ_SEND_MSG_FAIL);
        }
    }

    /**
     * 返回编码的消息内容
     * @param message
     * @return
     */
    protected String getEncodeContent(String message) {
        try {
            return URLEncoder.encode(message, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }// 他要需要编码
        return message;
    }

    /**
     * 调用服务发送消息
     * @param serviceUrl
     * @param message
     */
    protected void sendMessage(String serviceUrl, long toUin, String message) {
        //返回JSON格式消息对象
        JSONObject jsonMsg = makeMessageObject(toUin, message);
        if (jsonMsg == null) {
            LogUtils.warn(LOGGER, "组装消息对象时发生异常, message={0}, toUin={1}", jsonMsg,
                String.valueOf(toUin));
            throw new ActionException(QQClientErrorCode.QQ_MAKE_MESSAGE_IS_WRONG);
        }
        String content = jsonMsg.toString();
        LogUtils.info(LOGGER, "获取需要发送的QQ消息, msg={0}", content);

        //调用服务
        ExParam exParam = new ExParam();
        //exParam.setCookie(clientContext.getCookies());
        exParam.setContents("r=" + getEncodeContent(content)); //消息内容需要先编码
        String result = invokePostHttpService(serviceUrl, exParam);
        LogUtils.info(LOGGER, "获取发送QQ消息的响应, result={0}", result);

        parseResult(result);
    }

    /**
     * 组装发送消息的json格式对象
     * @param toUin 标识
     * @param message 消息
     * @return
     */
    protected abstract JSONObject makeMessageObject(Long toUin, String message);
}
