/**
 * @(#)LoginQQAction.java 2013-1-21
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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.okj.commons.logger.LogUtils;
import org.okj.commons.service.action.ActionContext;
import org.okj.commons.service.action.ActionException;
import org.okj.im.core.constants.ActionContextKeys;
import org.okj.im.core.constants.QQClientErrorCode;
import org.okj.im.core.service.ExParam;
import org.okj.im.model.Member;

/**
 * 登录QQ的action
 * @author Administrator
 * @version $Id: LoginQQAction.java, v 0.1 2013-1-21 下午5:52:11 Administrator Exp $
 */
public class LoginQQAction extends DefaultBizAction<Void> {
    // 登录url
    private static final String SERVICE_URL = "http://ptlogin2.qq.com/login?u={0}&p={1}&verifycode={2}&webqq_type=10&remember_uin=1&login2qq=1&aid=1003903&u1={3}&h=1&ptredirect=0&ptlang=2052&from_ui=1&pttype=1&dumy=&fp=loginerroralert&action=7-24-1937704&mibao_css=m_webqq&t=1&g=1";

    /** 
     * @see org.storevm.commons.service.action.AbstractBizAction#doExecute(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    protected Void doExecute(ActionContext context) throws ActionException {
        //返回上下文中的参数
        Member member = getContextAttribute(context, ActionContextKeys.MEMBER_KEY);

        //拼装请求URL
        String loginStr = "http%3A%2F%2Fweb3.qq.com%2Floginproxy.html%3Flogin2qq%3D1%26webqq_type%3D10";
        String reqUrl = parseUrl(SERVICE_URL, member.getAccount().getAccount(),
            member.getEncodePassword(clientContext.getToken()), clientContext.getVerifyCode(),
            loginStr);
        LogUtils.info(LOGGER, "QQ登录的请求URL, url={0}", reqUrl);

        //调用服务
        ExParam exParam = new ExParam();
        exParam.setCookie(clientContext.getCookies());
        String result = invokeGetHttpService(reqUrl, exParam);
        LogUtils.info(LOGGER, "获取QQ登录的响应, result={0}", result);
        //设置上下文
        context.addAttribute("result", result);

        //追加cookies
        clientContext.addCookie(exParam.getCookie());

        //从cookie中提取ptwebqq,skey
        String cookies = clientContext.getCookies();
        LogUtils.info(LOGGER, "获取cookies值, cookies={0}", cookies);
        setPtwebQQValue(cookies, member);
        setSkeyValue(cookies, member);

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

        Pattern p = Pattern.compile("登录成功");
        Matcher m = p.matcher(result);
        if (m.find()) {
            //登录成功
            LogUtils.info(LOGGER, "WebQQ登录成功, account={0}", member.getAccount());
            //Log.println("Welcome QQ : " + loginParam.get("account") + " Login Success！");
        } else {
            //抛出异常
            throw new ActionException(QQClientErrorCode.QQ_LOGIN_FAIL);
        }

    }

    /**
     * 设置ptwebqq的值
     * @param cookies
     * @param member
     */
    protected void setPtwebQQValue(String cookies, Member member) {
        Pattern p = Pattern.compile("ptwebqq=(\\w+);");
        Matcher m = p.matcher(cookies);
        if (m.find()) {
            clientContext.getToken().setPtwebqq(m.group(1));
        }
    }

    /**
     * 设置skey的值
     * @param cookies
     * @param member
     */
    protected void setSkeyValue(String cookies, Member member) {
        Pattern p = Pattern.compile("skey=(@\\w+);");
        Matcher m = p.matcher(cookies);
        if (m.find()) {
            clientContext.getToken().setSkey(m.group(1));
        }
    }
}
