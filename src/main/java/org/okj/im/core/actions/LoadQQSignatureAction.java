/**
 * @(#)LoadQQSignatureAction.java 2013-1-23
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

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.okj.commons.logger.LogUtils;
import org.okj.commons.service.action.ActionContext;
import org.okj.commons.service.action.ActionException;
import org.okj.im.core.constants.ActionContextKeys;
import org.okj.im.core.service.ExParam;
import org.okj.im.model.Member;

/**
 * 加载QQ个性化签名的action
 * @author Administrator
 * @version $Id: LoadQQSignatureAction.java, v 0.1 2013-1-23 下午4:36:09 Administrator Exp $
 */
public class LoadQQSignatureAction extends AbstractLoadFriendAction {
    /* 服务URL */
    private static final String SERVICE_URL = "http://s.web2.qq.com/api/get_single_long_nick2?tuin={0}&vfwebqq={1}&t={2}";

    /** 
     * @see org.storevm.commons.service.action.AbstractBizAction#doExecute(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    protected Void doExecute(ActionContext context) throws ActionException {
        //根据QQ号码从管理容器中查找出好友
        Member sample = getContextAttribute(context, ActionContextKeys.MEMBER_KEY);
        Member friend = clientContext.findFriend(sample.getAccount().getAccount());
        if (friend == null) {
            LogUtils.warn(LOGGER, "没有找到指定的QQ好友, account={0}", sample.getAccount().getAccount());
            return null;
        }

        //查询QQ个性化签名
        String signature = getQQSignature(friend);
        if (StringUtils.isNotBlank(signature)) {
            friend.getName().setLnick(signature);
        }

        //放入上下文中
        context.addAttribute(ActionContextKeys.FRIEND_KEY, friend);
        return null;
    }

    /**
     * 返回QQ好友的个性化签名
     * @param friend
     * @return
     */
    protected String getQQSignature(Member friend) {
        String reqUrl = parseUrl(SERVICE_URL, String.valueOf(friend.getUin()),
            clientContext.getVfwebqq(), String.valueOf(System.currentTimeMillis()));
        LogUtils.info(LOGGER, "查询好友个性化签名的请求URL, url={0}", reqUrl);
        //调用服务
        ExParam exParam = new ExParam();
        exParam.setCookie(clientContext.getCookies());
        String result = invokeGetHttpService(reqUrl, exParam);
        LogUtils.info(LOGGER, "获取查询好友个性化签名的响应, result={0}", result);

        return parseResult(result);
    }

    /**
     * 解析JSON
     * @param result
     * @return
     */
    protected String parseResult(String result) {
        try {
            JSONObject retJson = JSONObject.fromObject(result);
            if (retJson.getInt("retcode") == 0) {
                JSONArray obj = retJson.getJSONArray("result");
                return obj.getJSONObject(0).getString("lnick");
            }
        } catch (JSONException ex) {
            LogUtils.error(LOGGER, "解析查询好友个性化签名返回字符串时发生异常", ex);
        }
        return null;
    }
}
