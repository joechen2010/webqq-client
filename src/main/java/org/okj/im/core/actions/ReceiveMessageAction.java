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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.okj.commons.logger.LogUtils;
import org.okj.commons.service.action.ActionContext;
import org.okj.commons.service.action.ActionException;
import org.okj.im.core.constants.QQClientErrorCode;
import org.okj.im.core.service.ExParam;
import org.okj.im.model.Member;

/**
 * 给好友发送QQ消息的action
 * @author Administrator
 * @version $Id: SendMessageToFriendAction.java, v 0.1 2013-1-24 上午9:04:38 Administrator Exp $
 */
public class ReceiveMessageAction extends DefaultBizAction<Void> {
    /* 接收QQ消息的URL */
//    private static final String SERVICE_URL = "http://web2-b.qq.com/channel/poll";
    private static final String SERVICE_URL = "http://d.web2.qq.com/channel/poll?clientid={0}&psessionid={1}&t=";
 
    @Override
    public void before(ActionContext context) throws ActionException {
    	//从管理容器中取出会员对象
        if (clientContext.get() == null) {
            LogUtils.warn(LOGGER, "WebQQ尚未登录，无法执行好友列表查询");
            throw new ActionException(QQClientErrorCode.QQ_NOT_LOGIN);
        }
    }
    
    @Override
    public boolean skip(ActionContext context) {
       return false;
    }

    /** 
     * @see org.okj.im.core.actions.SendMessageAction#doExecute(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    protected Void doExecute(ActionContext context) throws ActionException {
         
    	//调用服务
    	String reqUrl = parseUrl(SERVICE_URL, clientContext.getClientId(),
                clientContext.getPSessionId());
        String contents = getContents(clientContext.get());
        ExParam exParam = new ExParam();
        exParam.setCookie(clientContext.getCookies());
        //exParam.setContents("r=" + contents);
        reqUrl = reqUrl +System.currentTimeMillis();
        String result = invokePostHttpService(reqUrl, exParam);
        LogUtils.info(LOGGER, "接收消息进程, result={0}", result);

        //放入上下文
        context.addAttribute("result", result);

        return null;
    }
    
    @Override
    public void after(ActionContext context) throws ActionException {
        //返回上下文中的参数
        String result = getContextAttribute(context, "result");
        //TODO : SEND
        replyMessage();
    }
    
    private void replyMessage(){
    	
    }
    
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
