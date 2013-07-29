/**
 * @(#)ChannelLoginAction.java 2013-1-22
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

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.okj.commons.logger.LogUtils;
import org.okj.commons.service.action.ActionContext;
import org.okj.commons.service.action.ActionException;
import org.okj.im.core.constants.ActionContextKeys;
import org.okj.im.core.service.ExParam;
import org.okj.im.model.Member;
import org.okj.im.model.enums.Status;

/**
 * 渠道登录的action
 * @author Administrator
 * @version $Id: ChannelLoginAction.java, v 0.1 2013-1-22 上午9:17:05 Administrator Exp $
 */
public class ChannelLoginAction extends DefaultBizAction<Void> {
    // 进入登录渠道
    private static final String SERVICE_URL = "http://d.web2.qq.com/channel/login2";

    /** 
     * @see org.storevm.commons.service.action.AbstractBizAction#doExecute(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    protected Void doExecute(ActionContext context) throws ActionException {
        //返回上下文中的参数
        Member member = getContextAttribute(context, ActionContextKeys.MEMBER_KEY);

        //调用服务
        String contents = getContents(member);
        ExParam exParam = new ExParam();
        exParam.setCookie(clientContext.getCookies());
        exParam.setContents("r=" + contents);
        String result = invokePostHttpService(SERVICE_URL, exParam);
        LogUtils.info(LOGGER, "获取QQ渠道登录的响应, result={0}", result);

        //放入上下文
        context.addAttribute("result", result);

        //追加cookies
        clientContext.addCookie(exParam.getCookie());

        return null;
    }

    /** 
     * @see org.storevm.commons.service.action.AbstractBizAction#after(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    public void after(ActionContext context) throws ActionException {
        //返回上下文中的参数
        Member member = getContextAttribute(context, ActionContextKeys.MEMBER_KEY);
        String result = getContextAttribute(context, "result");

        try {
            JSONObject retJson = JSONObject.fromObject(result);
            if (retJson.getInt("retcode") == 0) {
                JSONObject obj = retJson.getJSONObject("result");
                member.setCip(obj.getInt("cip"));
                member.setStatus(Status.getEnum(obj.getString("status")));
                member.setUin(obj.getLong("uin"));
                clientContext.getToken().setPsessionid(obj.getString("psessionid"));
                clientContext.getToken().setVfwebqq(obj.getString("vfwebqq"));
            }
        } catch (JSONException ex) {
            LogUtils.error(LOGGER, "解析渠道登录返回字符串时发生异常", ex);
        }

    }

    /**
     * 拼装渠道登录所需的参数字段内容
     * @param member
     * @return
     */
    protected String getContents(Member member) {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"status\":\"").append(member.getStatus().getCode())
            .append("\",\"ptwebqq\":\"").append(clientContext.getToken().getPtwebqq())
            .append("\",\"passwd_sig\":\"\",\"clientid\":\"")
            .append(clientContext.getToken().getClientid()).append("\"}");
        try {
            return URLEncoder.encode(sb.toString(), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            LogUtils.error(LOGGER, "编码contents时发生异常", ex);
        }
        return sb.toString();
    }
}
