package org.joechen.tools;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class Start {
	
	public static void main(String[] args) throws Exception{  
        //ApplicationContext context = new ClassPathXmlApplicationContext("webqqApplicationContext.xml");  
		Server server = new Server(8080);
        server.setHandler(new WebAppContext("src/main/webapp", "/"));
        server.setStopAtShutdown(true);
        server.start();
    }  

}
