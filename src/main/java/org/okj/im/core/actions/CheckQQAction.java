/**
 * @(#)CheckQQAction.java 2013-1-21
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
import org.okj.im.core.service.ExParam;
import org.okj.im.model.Member;

/**
 * 检查QQ账户信息的action
 * @author Administrator
 * @version $Id: CheckQQAction.java, v 0.1 2013-1-21 下午4:04:31 Administrator Exp $
 */
public class CheckQQAction extends DefaultBizAction<Void> {
    /* QQ服务的URL */
    private static final String SERVICE_URL = "http://check.ptlogin2.qq.com/check?uin={0}&appid=1003903&r={1}";

    /** 
     * @see org.storevm.commons.service.action.AbstractBizAction#doExecute(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    protected Void doExecute(ActionContext context) throws ActionException {
        //返回上下文中的参数
        Member member = getContextAttribute(context, ActionContextKeys.MEMBER_KEY);
        LogUtils.info(LOGGER, "QQ登录账户信息, member={0}", member);

        //解析URL
        String reqUrl = parseUrl(SERVICE_URL, member.getAccount().getAccount(),
            String.valueOf(Math.random()));
        LogUtils.info(LOGGER, "检查QQ登录账户的请求URL, url={0}", reqUrl);

        //准备参数，并请求服务
        ExParam exParam = new ExParam();
        String result = invokeGetHttpService(reqUrl, exParam);
        LogUtils.info(LOGGER, "获取检查QQ登录账户的响应, result={0}", result);

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
        String result = getContextAttribute(context, "result");

        // 检查账号验证码返回结果
        String checkAccountVCRegex = "\\'(.+)\\'\\,\\'(.+)\\'\\,\\'(.+)\\'";
        Pattern p = Pattern.compile(checkAccountVCRegex);
        Matcher m = p.matcher(result);
        if (m.find()) {
            String checkType = m.group(1);
            String verifycode = m.group(2);
            String verifycodeHex = m.group(3);
            LogUtils.info(LOGGER,
                "解析账号验证码返回结果,CheckType={0}, VerifyCode={1}, VerifyCodeHex={2}, groupCount={3}",
                checkType, verifycode, verifycodeHex, m.groupCount());

            //重置验证令牌的各项属性
            clientContext.resetToken(checkType, verifycode, verifycodeHex);
        }
    }
}
