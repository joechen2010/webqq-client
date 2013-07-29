/**
 * Storevm.org Inc.
 * Copyright (c) 2004-2013 All Rights Reserved.
 */
package org.okj.im.core.actions;

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
 * 变更会员登录状态的action
 * @author Administrator
 * @version $Id: ChangeMemberStatusAction.java, v 0.1 2013-1-23 下午8:52:36 Administrator Exp $
 */
public class ChangeMemberStatusAction extends DefaultBizAction<Void> {
    /* 服务URL */
    private static final String SERVICE_URL = "http://d.web2.qq.com/channel/change_status2?newstatus={0}&clientid={1}&psessionid={2}&t={3}";

    /** 
     * @see org.storevm.commons.service.action.AbstractBizAction#doExecute(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    protected Void doExecute(ActionContext context) throws ActionException {
        Member member = getContextAttribute(context, ActionContextKeys.MEMBER_KEY);

        //请求URL
        String reqUrl = parseUrl(SERVICE_URL, member.getStatus().getCode(),
            clientContext.getClientId(), clientContext.getPSessionId(),
            String.valueOf(System.currentTimeMillis()));
        LogUtils.info(LOGGER, "变更登录会员状态的请求URL, url={0}", reqUrl);

        //调用服务
        ExParam exParam = new ExParam();
        String result = invokeGetHttpService(reqUrl, exParam);
        LogUtils.info(LOGGER, "获取变更登录会员状态的响应, result={0}", result);

        boolean success = parseResult(result);
        if (!success) {
            throw new ActionException(QQClientErrorCode.QQ_CHANGE_STATUS_FAIL);
        }
        return null;
    }

    /**
     * 解析结果
     * @param result
     * @return
     */
    protected boolean parseResult(String result) {
        try {
            JSONObject retJson = JSONObject.fromObject(result);
            if (retJson.getInt("retcode") == 0) {
                String ok = retJson.getString("result");
                if (ok != null && ok.equals("ok")) {
                    return true;
                }
            }
        } catch (JSONException ex) {
            LogUtils.error(LOGGER, "解析变更登录会员状态返回字符串时发生异常", ex);
        }
        return false;
    }
}
