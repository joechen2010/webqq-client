/**
 * Storevm.org Inc.
 * Copyright (c) 2004-2013 All Rights Reserved.
 */
package org.okj.im.core.actions;

import java.util.List;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.okj.commons.logger.LogUtils;
import org.okj.commons.service.action.ActionContext;
import org.okj.commons.service.action.ActionException;
import org.okj.im.core.constants.ActionContextKeys;
import org.okj.im.core.service.ExParam;
import org.okj.im.model.Group;

/**
 * 加载QQ群列表的群号
 * @author Administrator
 * @version $Id: FindQQGroupsNumberAction.java, v 0.1 2013-1-23 下午8:20:53 Administrator Exp $
 */
public class LoadQQGroupsNumberAction extends DefaultBizAction<Void> {
    /* 服务URL */
    private static final String SERVICE_URL = "http://s.web2.qq.com/api/get_friend_uin2?tuin={0}&verifysession=&type=4&code=&vfwebqq={1}&t={2}";

    /** 
     * @see org.okj.im.core.actions.DefaultBizAction#skip(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    public boolean skip(ActionContext context) {
        List<Group> groups = getContextAttribute(context, ActionContextKeys.GROUPS_KEY);
        if (groups == null) {
            return true;
        }
        return false;
    }

    /** 
     * @see org.storevm.commons.service.action.AbstractBizAction#doExecute(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    protected Void doExecute(ActionContext context) throws ActionException {
        List<Group> groups = getContextAttribute(context, ActionContextKeys.GROUPS_KEY);
        for (Group group : groups) {
            long groupNumber = loadGroupNumber(group.getCode());
            group.setNumber(groupNumber);
        }
        return null;
    }

    /**
     * 加载QQ群号码
     * @param code
     * @return
     */
    protected long loadGroupNumber(long code) {
        //请求URL
        String reqUrl = parseUrl(SERVICE_URL, String.valueOf(code), clientContext.getVfwebqq(),
            String.valueOf(System.currentTimeMillis()));
        LogUtils.info(LOGGER, "加载QQ群号码的请求URL, url={0}", reqUrl);

        ExParam exParam = new ExParam();
        String result = invokeGetHttpService(reqUrl, exParam);
        LogUtils.info(LOGGER, "获取加载QQ群号码的响应, result={0}", result);

        return parseResult(result);
    }

    /**
     * 解析结果
     * @param result
     * @return
     */
    protected long parseResult(String result) {
        try {
            JSONObject retJson = JSONObject.fromObject(result);
            if (retJson.getInt("retcode") == 0) {
                JSONObject obj = retJson.getJSONObject("result");
                return obj.getLong("account");
            }
        } catch (JSONException ex) {
            LogUtils.error(LOGGER, "解析加载QQ群号码返回结果时发生异常", ex);
        }
        return -1;
    }
}
