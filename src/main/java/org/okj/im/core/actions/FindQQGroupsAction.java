/**
 * Storevm.org Inc.
 * Copyright (c) 2004-2013 All Rights Reserved.
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
import org.okj.im.core.service.ExParam;
import org.okj.im.model.Group;

/**
 * 查找QQ群的action
 * @author Administrator
 * @version $Id: FindQQGroupsAction.java, v 0.1 2013-1-23 下午8:03:51 Administrator Exp $
 */
public class FindQQGroupsAction extends DefaultBizAction<Void> {
    /* 服务URL */
    private static final String SERVICE_URL = "http://s.web2.qq.com/api/get_group_name_list_mask2";

    /** 
     * @see org.okj.im.core.actions.DefaultBizAction#skip(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    public boolean skip(ActionContext context) {
        return false;
    }

    /** 
     * @see org.storevm.commons.service.action.AbstractBizAction#doExecute(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    protected Void doExecute(ActionContext context) throws ActionException {
        List<Group> groups = findGroups(); //查询QQ群列表
        if (groups != null) {
            //写入上下文
            context.addAttribute(ActionContextKeys.GROUPS_KEY, groups);
        }
        return null;
    }

    /**
     * 调用服务
     * @return
     */
    protected List<Group> findGroups() {
        String content = getContent();
        //调用服务
        ExParam exParam = new ExParam();
        exParam.setContents("r=" + content);
        String result = invokePostHttpService(SERVICE_URL, exParam);
        LogUtils.info(LOGGER, "获取查询QQ群列表的响应, result={0}", result);

        return parseResult(result);
    }

    /**
     * 解析结果
     * @param result
     * @return
     */
    protected List<Group> parseResult(String result) {
        try {
            JSONObject retJson = JSONObject.fromObject(result);
            if (retJson.getInt("retcode") == 0) {
                JSONObject json = retJson.getJSONObject("result");
                JSONArray array = json.getJSONArray("gnamelist");
                return makeGroups(array);
            }
        } catch (JSONException ex) {
            LogUtils.error(LOGGER, "解析查询QQ群列表返回结果时发生异常", ex);
        }
        return null;
    }

    /**
     * 生成QQ群集合
     * @param array
     * @return
     */
    protected List<Group> makeGroups(JSONArray array) {
        List<Group> groups = new ArrayList<Group>();
        for (int i = 0, n = array.size(); i < n; i++) {
            Group group = new Group();
            group.setFlag(array.getJSONObject(i).getLong("flag"));
            group.setName(array.getJSONObject(i).getString("name"));
            group.setGid(array.getJSONObject(i).getLong("gid"));
            group.setCode(array.getJSONObject(i).getLong("code"));
            groups.add(group);
        }
        return groups;
    }

    protected String getContent() {
        StringBuffer sb = new StringBuffer();
        sb.append("{\"vfwebqq\":\"").append(clientContext.getVfwebqq()).append("\"}");
        try {
            return URLEncoder.encode(sb.toString(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return sb.toString();
    }
}
