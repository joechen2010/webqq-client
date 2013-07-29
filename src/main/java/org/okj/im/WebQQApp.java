package org.okj.im;

import java.util.List;

import org.apache.log4j.Logger;
import org.okj.commons.logger.LogUtils;
import org.okj.im.core.WebQQClinetContext;
import org.okj.im.core.task.HeartbeatTaskExecutor;
import org.okj.im.core.task.ReceiveMessageThead;
import org.okj.im.model.Member;
import org.okj.im.model.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;

public class WebQQApp {// implements InitializingBean, DisposableBean{
	
	private static final Logger LOGGER = Logger.getLogger(WebQQApp.class);
	
	@Autowired
	private WebQQClient           webQQClient;
	
	@Autowired
    private ReceiveMessageThead receiveMessageThead;
    
    @Autowired
    private Config config;
    
    @Autowired
    private HeartbeatTaskExecutor heartbeatTaskExecutor;
    
    public void destroy() throws Exception {
    	shutdown();
    }

    /** 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {
    	startTask();
    }
    
    public void startTask(){
    	if (heartbeatTaskExecutor != null) {
        	heartbeatTaskExecutor.startup();
        }
    }
    
    public boolean start(){
    	Member member = new Member();
        member.getAccount().setAccount(config.getAccount());
        member.getAccount().setPassword(config.getPassword());
        member.setStatus(Status.ONLINE);
        //登录WebQQ，登录之后就可以发送消息了
        boolean success = webQQClient.login(member);
        if (success) {
            
            List<Member> olineFriends = webQQClient.findOnlineFriends();
            //List<Group> groups = webQQClient.findGroups();
            
          //如果登录成功，则加载在线好友列表和QQ群列表
        	/*for(Member m : webQQClient.findFriends()){
        		member.addFriend(m);
        	}*/
            
            //receiveMessageThead.start();
            
            LogUtils.info(LOGGER, "启动WebQQ客户端容器完成. 在线好友数=[{0}], QQ群数=[{1}]",   olineFriends.size(),0);
        } else {
            LogUtils.warn(LOGGER, "启动WebQQ客户端失败, account={0}", config.getAccount());
        }
        return success;
    }
    
    public void shutdown(){
    	if (heartbeatTaskExecutor != null) {
            heartbeatTaskExecutor.shutdown(); //关闭心跳检测任务
        }
        if (webQQClient != null) {
            //当Bean销毁时，关闭WebQQ客户端管理容器
            boolean success = webQQClient.logout();
            if (success) {
                LogUtils.warn(LOGGER, "关闭WebQQ客户端完成, account={0}");
            } else {
                LogUtils.warn(LOGGER, "关闭WebQQ客户端失败, account={0}");
            }
        }
    }
    
    public void sendHello(){
    	for(Member m : WebQQClinetContext.getInstance().getOnlineFriends()){
    		if(m.getIndex() == 0){
    			LogUtils.info(LOGGER, "WebQQ发送Hello: " + m.getAccount().getAccount() );
    			webQQClient.sendMessageToFriend(m.getAccount().getAccount(), config.getMsgs().get(0));
    			m.setIndex(m.getIndex()+1);
    		}
    	}
    }

	public WebQQClient getWebQQClient() {
		return webQQClient;
	}

	public void setWebQQClient(WebQQClient webQQClient) {
		this.webQQClient = webQQClient;
	}

	public ReceiveMessageThead getReceiveMessageThead() {
		return receiveMessageThead;
	}

	public void setReceiveMessageThead(ReceiveMessageThead receiveMessageThead) {
		this.receiveMessageThead = receiveMessageThead;
	}

	public Config getConfig() {
		return config;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public HeartbeatTaskExecutor getHeartbeatTaskExecutor() {
		return heartbeatTaskExecutor;
	}

	public void setHeartbeatTaskExecutor(HeartbeatTaskExecutor heartbeatTaskExecutor) {
		this.heartbeatTaskExecutor = heartbeatTaskExecutor;
	}

}
