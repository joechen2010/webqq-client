package org.okj.im.core.event;

import org.apache.log4j.Logger;
import org.okj.commons.logger.LogUtils;
import org.okj.commons.robot.ChatRobot;
import org.okj.commons.robot.RobotResponse;
import org.okj.commons.web.jackson.JsonMapper;
import org.okj.commons.web.jackson.JsonMapperFactory;
import org.okj.im.Config;
import org.okj.im.WebQQApp;
import org.okj.im.WebQQClient;
import org.okj.im.core.WebQQClinetContext;
import org.okj.im.core.constants.CommandCode;
import org.okj.im.core.constants.WebQQEventCode;
import org.okj.im.model.Member;
import org.okj.im.model.ReceiveMsg;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 接收QQ消息的监听器实现
 * @author Administrator
 * @version $Id: ReceiveFriendMessageListener.java, v 0.1 2013-1-24 下午3:28:15 Administrator Exp $
 */
public class PollListener implements WebQQEventListener, InitializingBean, DisposableBean {
    /* logger */
    private static final Logger LOGGER = Logger.getLogger(PollListener.class);
    
    private static JsonMapper jsonMapper = JsonMapperFactory.get();

    /* 事件发布器 */
    private WebQQEventPublisher eventPublisher;
    
    @Autowired
    private ChatRobot chatRobot;
    @Autowired
    private WebQQClient           webQQClient;
    @Autowired
    private Config config;
    @Autowired
    private WebQQApp webqqApp;

    /**
     * 构造函数
     */
    public PollListener() {
    }

    /** 
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    @Override
    public void destroy() throws Exception {
        eventPublisher.unregister(this); //注销
    }

    /** 
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        eventPublisher.register(this); //注册监听器
    }

    /** 
     * @see org.storevm.im.core.event.WebQQEventListener#onWebQQEvent(org.storevm.im.core.event.WebQQEvent)
     */
    @Override
    public void onWebQQEvent(WebQQEvent event) {
        LogUtils.info(LOGGER, "接收到QQ事件, event.code={0}, body={1}", event.getEventCode(),
            event.getBody());
        if(WebQQEventCode.FRIEND_MESSAGE_EVENT_CODE.equals(event.getEventCode())){
        	replyFreind(event);
        }else if(WebQQEventCode.FRIEND_STATUS_CHANGE.equals(event.getEventCode())){
        	//TODO
        }else if(WebQQEventCode.GROUP_MESSAGE_EVENT_CODE.equals(event.getEventCode())){
        	// NOTHING TO DO 
        }else if (WebQQEventCode.KICK_QQ.equals(event.getEventCode())){
        	webqqApp.shutdown();
        }
    }

	private void replyFreind(WebQQEvent event) {
		String sendContent = "";
		ReceiveMsg msg = jsonMapper.fromJson(event.getBody().toString(), ReceiveMsg.class);
		try{
			String content = msg.getMsgContent();
			LogUtils.info(LOGGER, msg.getFrom_uin() +" : " + content);
			Member member  = WebQQClinetContext.getInstance().findFriend(msg.getFrom_uin().toString());
			if(CommandCode.SAY_HELLO.equals(content.toUpperCase()) && config.getCommandAccount().equals(msg.getFrom_uin().toString())){
				webqqApp.sendHello();
			}if(CommandCode.RELOGIN.equals(content.toUpperCase()) && config.getCommandAccount().equals(msg.getFrom_uin().toString())){
				webqqApp.startTask();
			}else if (member.getIndex() >= config.getMsgs().size()){
				RobotResponse resp = chatRobot.chat(content);
				sendContent = resp.getMsg();
			}else{
				sendContent = config.getMsgs().get(member.getIndex()+1);
				member.setIndex(member.getIndex()+1);
			}
		}catch(Exception e){
			sendContent = config.getDefautMsg();
			e.printStackTrace();
		}finally{
			webQQClient.sendMessageToFriend(msg.getFrom_uin().toString(), sendContent);
		}
		
	}
	

    /**
     * Setter method for property <tt>eventPublisher</tt>.
     * 
     * @param eventPublisher value to be assigned to property eventPublisher
     */
    public void setEventPublisher(WebQQEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

	public void setChatRobot(ChatRobot chatRobot) {
		this.chatRobot = chatRobot;
	}

	public void setWebQQClient(WebQQClient webQQClient) {
		this.webQQClient = webQQClient;
	}

	public void setConfig(Config config) {
		this.config = config;
	}

	public void setWebqqApp(WebQQApp webqqApp) {
		this.webqqApp = webqqApp;
	}

    

}