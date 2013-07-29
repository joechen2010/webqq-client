/**
 * Storevm.org Inc.
 * Copyright (c) 2004-2013 All Rights Reserved.
 */
package org.okj.im;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * WebQQ客户端的Servlet监听器，用于在Web容器中启动WebQQ客户端
 * @author Administrator
 * @version $Id: WebQQClinetListener.java, v 0.1 2013-1-23 下午9:33:04 Administrator Exp $
 */
public class WebQQClinetListener implements ServletContextListener {
    /* logger */
    private static final Logger   LOGGER = Logger.getLogger(WebQQClinetListener.class);

    /* QQ客户端 */
    private WebQQApp           webqqApp;

    
    /** 
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {
    	webqqApp.shutdown();
    }

    /** 
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ServletContext context = event.getServletContext();
        
        //获取Spring上下文
        ApplicationContext applicationContext = WebApplicationContextUtils
            .getWebApplicationContext(context);
        if (applicationContext != null) {
        	setWebqqApp(applicationContext.getBean("webqqApp", WebQQApp.class));
        }
        webqqApp.startTask();
    }


	public void setWebqqApp(WebQQApp webqqApp) {
		this.webqqApp = webqqApp;
	}
}
