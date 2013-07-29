/**
 * @(#)FindFriendAccountAction.java 2013-1-22
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

import java.util.List;

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
 * 查找好友的账号
 * @author Administrator
 * @version $Id: FindFriendAccountAction.java, v 0.1 2013-1-22 下午4:53:03 Administrator Exp $
 */
public class FindFriendAccountAction extends DefaultBizAction<Void> {
    private static final String SERVICE_URL = "http://s.web2.qq.com/api/get_friend_uin2?tuin={0}&verifysession=&type=1&code=&vfwebqq={1}&t={2}";

    /** 
     * @see org.okj.im.core.actions.DefaultBizAction#skip(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    public boolean skip(ActionContext context) {
        List<Member> friends = getContextAttribute(context, ActionContextKeys.FRIENDS_KEY);
        if (friends == null || clientContext.isLoadedFriends()) {
            return true;
        }
        return false;
    }

    /** 
     * @see org.storevm.commons.service.action.AbstractBizAction#doExecute(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    protected Void doExecute(ActionContext context) throws ActionException {
        List<Member> friends = getContextAttribute(context, ActionContextKeys.FRIENDS_KEY);
        for (Member friend : friends) {
            String account = getAccount(friend);
            if (StringUtils.isNotBlank(account)) {
                friend.getAccount().setAccount(account); //设置QQ号
            }
        }
        return null;
    }

    /**
     * 查询好友的账号，即QQ号码
     * @param friend
     * @return
     */
    protected String getAccount(Member friend) {
        String reqUrl = parseUrl(SERVICE_URL, String.valueOf(friend.getUin()),
            clientContext.getVfwebqq(), String.valueOf(System.currentTimeMillis()));
        LogUtils.info(LOGGER, "查询好友账号的请求URL, url={0}", reqUrl);
        //调用服务
        ExParam exParam = new ExParam();
        exParam.setCookie(clientContext.getCookies());
        String result = invokeGetHttpService(reqUrl, exParam);
        LogUtils.info(LOGGER, "获取查询好友账号的响应, result={0}", result);

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
                JSONObject obj = retJson.getJSONObject("result");
                return obj.getString("account");
            }
        } catch (JSONException ex) {
            LogUtils.error(LOGGER, "解析查询好友账号返回字符串时发生异常", ex);
        }
        return null;
    }
}
