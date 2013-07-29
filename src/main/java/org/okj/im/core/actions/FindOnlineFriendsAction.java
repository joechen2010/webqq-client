/**
 * @(#)FindOnlineFriendsAction.java 2013-1-23
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
import org.okj.im.core.service.ExParam;
import org.okj.im.model.Member;
import org.okj.im.model.enums.Status;

/**
 * 查找在线QQ好友列表的Action
 * @author Administrator
 * @version $Id: FindOnlineFriendsAction.java, v 0.1 2013-1-23 下午5:57:33 Administrator Exp $
 */
public class FindOnlineFriendsAction extends DefaultBizAction<Void> {
    /* 服务的URL */
    private static final String SERVICE_URL = "http://d.web2.qq.com/channel/get_online_buddies2?clientid={0}&psessionid={1}&t={2}";

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
        //从管理容器中取出会员对象
        if (clientContext.get() == null) {
            LogUtils.warn(LOGGER, "WebQQ尚未登录，无法执行在线好友列表查询");
            throw new ActionException(QQClientErrorCode.QQ_NOT_LOGIN);
        }
    }

    /** 
     * @see org.storevm.commons.service.action.AbstractBizAction#doExecute(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    protected Void doExecute(ActionContext context) throws ActionException {
        List<Member> onlineFriends = findOnlineFriends();
        for (Member onlineFriend : onlineFriends) {
            /*Member friend = clientContext.findFriend(onlineFriend.getUin()); //查询好友
            if (friend != null) {
                //加入到在线好友列表中
                clientContext.addOnlineFriend(friend);
            }*/
        	clientContext.addOnlineFriend(onlineFriend);
        	clientContext.getSelf().addFriend(onlineFriend);
        }
        return null;
    }

    /**
     * 调用服务
     * @return
     */
    protected List<Member> findOnlineFriends() {
        String reqUrl = parseUrl(SERVICE_URL, clientContext.getClientId(),
            clientContext.getPSessionId(), String.valueOf(System.currentTimeMillis()));
        LogUtils.info(LOGGER, "查询在线好友列表的请求URL, url={0}", reqUrl);
        //调用服务
        ExParam exParam = new ExParam();
        exParam.setCookie(clientContext.getCookies());
        String result = invokeGetHttpService(reqUrl, exParam);
        LogUtils.info(LOGGER, "获取查询在线好友列表的响应, result={0}", result);
        return parseResult(result);
    }

    /**
     * 解析返回结果
     * @param result
     * @return
     */
    protected List<Member> parseResult(String result) {
        List<Member> onlines = new ArrayList<Member>();
        try {
            JSONObject retJson = JSONObject.fromObject(result);
            if (retJson.getInt("retcode") == 0) {
                JSONArray statusArray = retJson.getJSONArray("result");
                for (int i = 0, n = statusArray.size(); i < n; i++) {
                    long uin = statusArray.getJSONObject(i).getLong("uin");
                    String status = statusArray.getJSONObject(i).getString("status");
                    Member onlineFriend = new Member();
                    onlineFriend.setUin(uin);
                    onlineFriend.setStatus(Status.getEnum(status));
                    onlines.add(onlineFriend);
                }
            }
        } catch (JSONException ex) {
            LogUtils.error(LOGGER, "解析查询在线好友列表返回字符串时发生异常", ex);
        }
        return onlines;
    }
}
