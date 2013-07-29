/**
 * @(#)HeartbeatTask.java 2013-1-21
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
package org.okj.im.core.task;

import org.apache.log4j.Logger;
import org.okj.commons.logger.LogUtils;
import org.okj.commons.service.action.ActionContext;
import org.okj.commons.service.action.ActionException;
import org.okj.commons.service.action.ActionsExecutor;
import org.okj.im.Config;
import org.okj.im.WebQQApp;
import org.okj.im.WebQQClient;
import org.okj.im.core.WebQQClinetContext;
import org.okj.im.core.constants.BizCode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 心跳包任务
 * @author Administrator
 * @version $Id: HeartbeatTask.java, v 0.1 2013-1-21 下午12:40:36 Administrator Exp $
 * 
 * 通过这个接口你可以实时的不间断的获取最新的消息。
 */
public class HeartbeatTask implements Runnable {
    /* logger */
    private static final Logger LOGGER = Logger.getLogger(HeartbeatTask.class);

    /* 任务执行的CRON表达式 */
    private String              cronExpression;

    /* Actions执行器 */
    @Autowired
    private ActionsExecutor     actionsExecutor;
    
    @Autowired
    private WebQQClient           webQQClient;
    
    @Autowired
    private Config config;
    
    @Autowired
    private WebQQApp webqqApp;

    /** 
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        ActionContext context = new ActionContext(BizCode.QQ_CHECK_HEARTBEAT);
        try {
        	if(checkLogin()){
        		actionsExecutor.execute(context);
        		webqqApp.sendHello();
        	}
            
        } catch (ActionException ex) {
            LogUtils.error(LOGGER, "WebQQ心跳检查时发生异常", ex);
        } catch (Exception ex) {
            LogUtils.error(LOGGER, "WebQQ心跳检查时发生系统异常", ex);
        }
    }

    
    private boolean checkLogin(){
    	if(WebQQClinetContext.getInstance().get() != null) return true;
    	return webqqApp.start();
    }
    
    /**
     * Getter method for property <tt>cronExpression</tt>.
     * 
     * @return property value of cronExpression
     */
    public String getCronExpression() {
        return cronExpression;
    }

    /**
     * Setter method for property <tt>cronExpression</tt>.
     * 
     * @param cronExpression value to be assigned to property cronExpression
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    /**
     * Setter method for property <tt>actionsExecutor</tt>.
     * 
     * @param actionsExecutor value to be assigned to property actionsExecutor
     */
    public void setActionsExecutor(ActionsExecutor actionsExecutor) {
        this.actionsExecutor = actionsExecutor;
    }

	public void setWebQQClient(WebQQClient webQQClient) {
		this.webQQClient = webQQClient;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public WebQQApp getWebqqApp() {
		return webqqApp;
	}

	public void setWebqqApp(WebQQApp webqqApp) {
		this.webqqApp = webqqApp;
	}

	public ActionsExecutor getActionsExecutor() {
		return actionsExecutor;
	}

	public WebQQClient getWebQQClient() {
		return webQQClient;
	}

	public Config getConfig() {
		return config;
	}
    
}
