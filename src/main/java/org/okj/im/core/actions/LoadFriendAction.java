/**
 * @(#)LoadMemberAction.java 2013-1-23
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

import java.text.ParseException;
import java.util.Date;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import org.apache.commons.lang.time.DateUtils;
import org.okj.commons.logger.LogUtils;
import org.okj.commons.service.action.ActionContext;
import org.okj.commons.service.action.ActionException;
import org.okj.im.core.constants.ActionContextKeys;
import org.okj.im.core.service.ExParam;
import org.okj.im.model.Member;

/**
 * 加载QQ会员详情
 * @author Administrator
 * @version $Id: LoadMemberAction.java, v 0.1 2013-1-23 下午2:53:08 Administrator Exp $
 */
public class LoadFriendAction extends AbstractLoadFriendAction {
    /* 服务URL */
    private static final String SERVICE_URL = "http://s.web2.qq.com/api/get_friend_info2?tuin={0}&verifysession=&code=&vfwebqq={1}&t={2}";

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

        String reqUrl = parseUrl(SERVICE_URL, String.valueOf(friend.getUin()),
            clientContext.getVfwebqq(), String.valueOf(System.currentTimeMillis()));
        LogUtils.info(LOGGER, "查询好友详情的请求URL, url={0}", reqUrl);

        //如果找到，则调用服务，加载该QQ好友的详细信息
        ExParam exParam = new ExParam();
        String result = invokeGetHttpService(reqUrl, exParam);
        LogUtils.info(LOGGER, "获取查询好友详情的响应, result={0}", result);

        //解析响应结果
        parseResult(result, friend);

        //放入上下文中
        context.addAttribute(ActionContextKeys.FRIEND_KEY, friend);
        return null;
    }

    /**
     * 解析JSON
     * @param result
     * @return
     */
    protected void parseResult(String result, Member friend) {
        try {
            JSONObject retJson = JSONObject.fromObject(result);
            if (retJson.getInt("retcode") == 0) {
                JSONObject json = retJson.getJSONObject("result");
                //设置好友属性
                friend.getPersonalInfo().setBirthday(parseDate(json.getJSONObject("birthday"))); //出生日期
                friend.getPersonalInfo().setOccupation(json.getString("occupation")); //职业
                friend.getPersonalInfo().setCollege(json.getString("college")); //毕业院校
                friend.getPersonalInfo().setConstel(json.getInt("constel")); //星座
                friend.getPersonalInfo().setBlood(json.getInt("blood")); //血型
                friend.getPersonalInfo().setHomepage(json.getString("homepage")); //个人主页
                friend.getPersonalInfo().setStat(json.getInt("stat")); //统计
                friend.getPersonalInfo().setVipInfo(json.getInt("vip_info")); //VIP信息
                friend.getPersonalInfo().setPersonal(json.getString("personal")); //个人信息
                friend.getPersonalInfo().setChineseZodiac(json.getInt("shengxiao")); //生肖
                friend.getLocation().setCountry(json.getString("country")); //国家
                friend.getLocation().setCity(json.getString("city")); //城市
                friend.getLocation().setProvince(json.getString("province")); //省
                friend.getContract().setPhone(json.getString("phone")); //电话
                friend.getContract().setEmail(json.getString("email")); //邮件
                friend.getContract().setMobile(json.getString("mobile")); //手机
                friend.getName().setNickname(json.getString("nick")); //昵称
                friend.setAllow(json.getInt("allow")); //允许
                if (json.has("reg_time")) {
                    friend.setRegTime(json.getInt("reg_time"));
                }
                friend.setUin(json.getLong("uin"));
                friend.setGender(json.getString("gender"));
                if (json.has("client_type")) {
                    friend.setClientType(json.getInt("client_type"));
                }
            }
        } catch (JSONException ex) {
            LogUtils.error(LOGGER, "解析查询好友详情返回字符串时发生异常", ex);
        }
    }

    /**
     * 解析注册日期
     * @param jsonobj
     * @return
     */
    protected Date parseDate(JSONObject jsonobj) {
        String dat = jsonobj.getInt("year") + "-" + jsonobj.getInt("month") + "-"
                     + jsonobj.getInt("day");
        try {
            return DateUtils.parseDate(dat, new String[] { "yyyy-MM-dd" });
        } catch (ParseException ex) {
            LogUtils.error(LOGGER, "解析日期发生异常", ex);
        }
        return null;
    }
}
