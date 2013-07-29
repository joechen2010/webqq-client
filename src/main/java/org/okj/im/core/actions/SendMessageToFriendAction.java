/**
 * @(#)SendMessageToFriendAction.java 2013-1-24
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

import org.apache.commons.lang.StringUtils;
import org.okj.commons.logger.LogUtils;
import org.okj.commons.service.action.ActionContext;
import org.okj.commons.service.action.ActionException;
import org.okj.im.core.constants.ActionContextKeys;
import org.okj.im.core.constants.QQClientErrorCode;
import org.okj.im.model.Member;

/**
 * 给好友发送QQ消息的action
 * @author Administrator
 * @version $Id: SendMessageToFriendAction.java, v 0.1 2013-1-24 上午9:04:38 Administrator Exp $
 */
public class SendMessageToFriendAction extends SendMessageAction {
    /* 发送QQ消息的URL */
    private static final String SERVICE_URL = "http://d.web2.qq.com/channel/send_buddy_msg2";

    /** 
     * @see org.okj.im.core.actions.SendMessageAction#before(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    public void before(ActionContext context) throws ActionException {
        super.before(context);

        //检查QQ号是否为空
        Member friend = getContextAttribute(context, ActionContextKeys.MEMBER_KEY);
        if (StringUtils.isBlank(friend.getAccount().getAccount())) {
            LogUtils.warn(LOGGER, "发送的QQ为空，无法执行发送消息给好友");
            throw new ActionException(QQClientErrorCode.QQ_NUMBER_IS_EMPTY);
        }

        //根据QQ号码寻找好友列表中的好友对象并将uin字段复制到上下文中的对象中
        Member toFriend = clientContext.get().getFriend(friend.getAccount().getAccount());
        if (toFriend != null) {
            //设置uin
            friend.setUin(toFriend.getUin());
            LogUtils.info(LOGGER, "获取发送消息的QQ好友, friend.uin={0}, friend.account={1}",
                String.valueOf(friend.getUin()), friend.getAccount().getAccount());
        } else {
            //不是好友，无法发送消息
            LogUtils.info(LOGGER, "不是好友，无法发送消息, friend.account=0}", friend.getAccount()
                .getAccount());
            throw new ActionException(QQClientErrorCode.QQ_NUMBER_IS_WRONG);
        }
    }

    /** 
     * @see org.okj.im.core.actions.SendMessageAction#doExecute(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    protected Void doExecute(ActionContext context) throws ActionException {
        Member friend = getContextAttribute(context, ActionContextKeys.MEMBER_KEY);
        String msg = getContextAttribute(context, ActionContextKeys.MESSAGE_KEY);

        //发送消息
        sendMessage(SERVICE_URL, friend.getUin(), msg);

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
            msg.add(message);
            json.put("to", toUin);// 要发送的人
            json.put("face", 0);

            //            JSONArray msg = new JSONArray();
            //            JSONArray face = new JSONArray();
            //            face.add("face");
            //            face.add(0);
            //            msg.add(face);
            //            msg.add(text);
            JSONArray font = new JSONArray();
            font.add("font");

            //设置字体
            JSONObject fontStyle = new JSONObject();
            fontStyle.put("name", "宋体");
            fontStyle.put("size", "10");

            JSONArray style = new JSONArray();
            style.add(0);
            style.add(0);
            style.add(0);
            fontStyle.put("style", style);
            fontStyle.put("color", "000000");

            font.add(fontStyle);
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
