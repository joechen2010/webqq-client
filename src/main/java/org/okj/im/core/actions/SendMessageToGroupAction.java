/**
 * @(#)SendMessageToGroupAction.java 2013-1-24
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

import java.util.Random;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.okj.commons.logger.LogUtils;
import org.okj.commons.service.action.ActionContext;
import org.okj.commons.service.action.ActionException;
import org.okj.im.core.constants.ActionContextKeys;
import org.okj.im.core.constants.QQClientErrorCode;
import org.okj.im.model.Group;

/**
 * 发送消息到QQ群的action
 * @author Administrator
 * @version $Id: SendMessageToGroupAction.java, v 0.1 2013-1-24 上午10:20:37 Administrator Exp $
 */
public class SendMessageToGroupAction extends SendMessageAction {
    /* 服务地址 */
    private static final String SERVICE_URL = "http://d.web2.qq.com/channel/send_qun_msg2";

    /** 
     * @see org.okj.im.core.actions.DefaultBizAction#skip(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    public boolean skip(ActionContext context) {
        Group group = getContextAttribute(context, ActionContextKeys.GROUPS_KEY);
        if (group == null) {
            return true;
        }
        return false;
    }

    /** 
     * @see org.okj.im.core.actions.SendMessageAction#before(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    public void before(ActionContext context) throws ActionException {
        super.before(context);

        //检查群号是否存在
        Group group = getContextAttribute(context, ActionContextKeys.GROUPS_KEY);
        if (group.getNumber() == -1) {
            LogUtils.warn(LOGGER, "QQ群号为空，无法发送消息到QQ群, group.number={0}",
                String.valueOf(group.getNumber()));
            throw new ActionException(QQClientErrorCode.QQ_GROUP_NUMBER_IS_EMPTY);
        }

        //通过QQ群查找QQ群的ID标识
        Group toGroup = clientContext.getGroup(group.getNumber());
        if (toGroup != null) {
            group.setGid(toGroup.getGid());
            LogUtils.info(LOGGER, "获取发送消息的QQ群, group.uin={0}, group.account={1}",
                String.valueOf(group.getGid()), String.valueOf(group.getNumber()));
        } else {
            //不是好友群，无法发送群消息
            LogUtils.warn(LOGGER, "不是好友群，无法发送消息到QQ群, group.number={0}",
                String.valueOf(group.getNumber()));
            throw new ActionException(QQClientErrorCode.QQ_GROUP_NUMBER_IS_WRONG);
        }
    }

    /** 
     * @see org.storevm.commons.service.action.AbstractBizAction#doExecute(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    protected Void doExecute(ActionContext context) throws ActionException {
        Group group = getContextAttribute(context, ActionContextKeys.GROUPS_KEY);
        String msg = getContextAttribute(context, ActionContextKeys.MESSAGE_KEY);

        //发送群消息
        sendMessage(SERVICE_URL, group.getGid(), msg);

        return null;
    }

    /** 
     * @see org.okj.im.core.actions.SendMessageAction#makeMessageObject(java.lang.Long, java.lang.String)
     */
    @Override
    protected JSONObject makeMessageObject(Long toUin, String message) {
        JSONObject json = new JSONObject();
        try {
            JSONArray msg = new JSONArray();
            msg.add(message); //发送的消息

            json.put("group_uin", toUin);// 要发送的群标识，并不是群号
            json.put("face", 330);

            JSONArray font = new JSONArray();
            font.add("font");

            JSONObject font1 = new JSONObject();
            font1.put("name", "宋体");
            font1.put("size", "10");

            JSONArray style = new JSONArray();
            style.add(0);
            style.add(0);
            style.add(0);
            font1.put("style", style);
            font1.put("color", "000000");

            font.add(font1);
            msg.add(font);

            json.put("content", "\"" + msg.toString() + "\""); //必须要这样的格式，否则发送消息报错
            json.put("msg_id", new Random().nextInt(10000000));
            json.put("clientid", clientContext.getClientId());
            json.put("psessionid", clientContext.getPSessionId());// 需要这个才能发送
            return json;
        } catch (Exception ex) {
            LogUtils.error(LOGGER, "send message to {0} failure......\n", ex, toUin);
        }
        return null;
    }

}
