/**
 * @(#)LoadQQLevelAction.java 2013-1-23
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

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.okj.commons.logger.LogUtils;
import org.okj.commons.service.action.ActionContext;
import org.okj.commons.service.action.ActionException;
import org.okj.im.core.constants.ActionContextKeys;
import org.okj.im.core.service.ExParam;
import org.okj.im.model.Level;
import org.okj.im.model.Member;

/**
 * 加载QQ好友等级的action
 * @author Administrator
 * @version $Id: LoadQQLevelAction.java, v 0.1 2013-1-23 下午4:54:47 Administrator Exp $
 */
public class LoadQQLevelAction extends AbstractLoadFriendAction {
    /* 服务URL */
    private static final String SERVICE_URL = "http://s.web2.qq.com/api/get_qq_level2?tuin={0}&vfwebqq={1}&t={2}";

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

        Level level = getQQLevel(friend);
        if (level != null) {
            friend.setLevel(level); //设置QQ等级
        }

        //放入上下文中
        context.addAttribute(ActionContextKeys.FRIEND_KEY, friend);
        return null;
    }

    protected Level getQQLevel(Member friend) {
        String reqUrl = parseUrl(SERVICE_URL, String.valueOf(friend.getUin()),
            clientContext.getVfwebqq(), String.valueOf(System.currentTimeMillis()));
        LogUtils.info(LOGGER, "查询好友等级的请求URL, url={0}", reqUrl);
        //调用服务
        ExParam exParam = new ExParam();
        exParam.setCookie(clientContext.getCookies());
        String result = invokeGetHttpService(reqUrl, exParam);
        LogUtils.info(LOGGER, "获取查询好友等级的响应, result={0}", result);

        return parseResult(result);
    }

    /**
     * 解析JSON
     * @param result
     * @return
     */
    protected Level parseResult(String result) {
        try {
            JSONObject retJson = JSONObject.fromObject(result);
            if (retJson.getInt("retcode") == 0) {
                JSONObject obj = retJson.getJSONObject("result");
                Level level = new Level();
                level.setLevel(obj.getInt("level"));
                level.setActiveDays(obj.getInt("days"));
                level.setRemainDays(obj.getInt("remainDays"));
                return level;
            }
        } catch (JSONException ex) {
            LogUtils.error(LOGGER, "解析查询好友等级返回字符串时发生异常", ex);
        }
        return null;
    }
}
