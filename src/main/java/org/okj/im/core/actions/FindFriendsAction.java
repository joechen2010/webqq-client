/**
 * @(#)FindFriendsAction.java 2013-1-22
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
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.okj.commons.logger.LogUtils;
import org.okj.commons.service.action.ActionContext;
import org.okj.commons.service.action.ActionException;
import org.okj.im.core.constants.ActionContextKeys;
import org.okj.im.core.constants.QQClientErrorCode;
import org.okj.im.core.service.ExParam;
import org.okj.im.model.Member;

/**
 * 查找所有好友列表的action
 * @author Administrator
 * @version $Id: FindFriendsAction.java, v 0.1 2013-1-22 下午4:37:45 Administrator Exp $
 */
public class FindFriendsAction extends DefaultBizAction<Void> {
    /* 查找好友列表的URL */
    private static final String SERVICE_URL = "http://s.web2.qq.com/api/get_user_friends2";

    /** 
     * @see org.okj.im.core.actions.DefaultBizAction#skip(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    public boolean skip(ActionContext context) {
        if (clientContext.isLoadedFriends()) {
            return true;
        }
        return false;
    }

    /** 
     * @see org.storevm.commons.service.action.AbstractBizAction#before(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    public void before(ActionContext context) throws ActionException {
        //从管理容器中取出会员对象
        if (clientContext.get() == null) {
            LogUtils.warn(LOGGER, "WebQQ尚未登录，无法执行好友列表查询");
            throw new ActionException(QQClientErrorCode.QQ_NOT_LOGIN);
        }
    }

    /** 
     * @see org.storevm.commons.service.action.AbstractBizAction#doExecute(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    protected Void doExecute(ActionContext context) throws ActionException {
        //调用服务
        String contents = getContents(clientContext.get());
        ExParam exParam = new ExParam();
        exParam.setCookie(clientContext.getCookies());
        exParam.setContents("r=" + contents);
        String result = invokePostHttpService(SERVICE_URL, exParam);
        LogUtils.info(LOGGER, "获取QQ查询好友列表的响应, result={0}", result);

        //放入上下文
        context.addAttribute("result", result);

        return null;
    }

    /** 
     * @see org.storevm.commons.service.action.AbstractBizAction#after(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    public void after(ActionContext context) throws ActionException {
        //返回上下文中的参数
        String result = getContextAttribute(context, "result");

        try {
            JSONObject retJson = JSONObject.fromObject(result);
            if (retJson.getInt("retcode") == 0) {
                JSONObject body = retJson.getJSONObject("result");
                List<Member> friends = getFriends(body);
                if (!friends.isEmpty()) {
                    //加入上下文
                    context.addAttribute(ActionContextKeys.FRIENDS_KEY, friends);
                }
            }
        } catch (JSONException ex) {
            LogUtils.error(LOGGER, "解析查询好友列表返回字符串时发生异常", ex);
        }
    }

    /**
     * 解析JSON
     * @param body
     */
    protected List<Member> getFriends(JSONObject body) {
        List<Member> friendList = new ArrayList<Member>();
        JSONArray friends = body.getJSONArray("friends");
        JSONArray info = body.getJSONArray("info");
        for (int j = 0; j < friends.size(); j++) {
            Member friend = new Member();
            friend.setUin(friends.getJSONObject(j).getLong("uin"));
            friend.setFlag(friends.getJSONObject(j).getInt("flag"));
            friend.getName().setNickname(info.getJSONObject(j).getString("nick"));
            JSONArray marknames = body.getJSONArray("marknames");
            for (int k = 0; k < marknames.size(); k++) {
                if (friend.getUin() == marknames.getJSONObject(k).getLong("uin")) {
                    friend.getName().setMarkname(marknames.getJSONObject(k).getString("markname"));
                }
            }

            //加入容器中
            friendList.add(friend);
        }
        return friendList;
    }

    /**
     * 拼装渠道登录所需的参数字段内容
     * @param member
     * @return
     */
    protected String getContents(Member member) {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"h\":\"hello\",\"vfwebqq\":\"").append(clientContext.getVfwebqq())
            .append("\"}");
        try {
            return URLEncoder.encode(sb.toString(), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            LogUtils.error(LOGGER, "编码contents时发生异常", ex);
        }
        return sb.toString();
    }
}
