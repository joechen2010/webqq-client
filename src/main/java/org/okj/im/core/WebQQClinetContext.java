/**
 * @(#)WebQQClinetContext.java 2013-1-23
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
package org.okj.im.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;
import org.okj.im.model.AuthToken;
import org.okj.im.model.Group;
import org.okj.im.model.Member;

/**
 * WebQQ客户端的上下文
 * @author Administrator
 * @version $Id: WebQQClinetContext.java, v 0.1 2013-1-23 上午10:43:51 Administrator Exp $
 */
public class WebQQClinetContext implements Serializable {
    /** UID */
    private static final long         serialVersionUID = 5262709099737501091L;

    /* 单例 */
    private static WebQQClinetContext instance         = new WebQQClinetContext();

    /* 登录的QQ会员 */
    private Member                    self;

    /* cookies */
    private StringBuffer              cookies          = new StringBuffer();

    /* 验证令牌 */
    private AuthToken                 token            = new AuthToken();

    /* 在线好友 */
    private Map<String, Member>       onlineFriends    = new ConcurrentHashMap<String, Member>();

    /* QQ群列表 */
    private Map<Long, Group>          groups           = new ConcurrentHashMap<Long, Group>();
    
    private boolean run = false;

    /**
     * 保护的构造函数
     */
    protected WebQQClinetContext() {
    }

    /**
     * 返回单利的对象
     * @return
     */
    public static WebQQClinetContext getInstance() {
        return instance;
    }

    /**
     * 设置受管的登录会员对象
     * @param member
     */
    public void put(Member member) {
        this.self = member;
    }

    /**
     * 返回受管的登录会员对象
     * @return
     */
    public Member get() {
        return this.self;
    }

    /**
     * 添加cookie
     * @param cookie
     */
    public void addCookie(String cookie) {
        cookies.append(cookie);
    }

    /**
     * 返回cookies
     * @return
     */
    public String getCookies() {
        return cookies.toString();
    }

    /**
     * 删除cookies
     */
    public void removeCookies() {
        cookies.delete(0, cookies.length());
    }

    /**
     * 重置验证令牌
     * @param checkType 检查类型
     * @param verifycode 验证码 
     * @param verifycodeHex 验证码的16进制格式
     */
    public void resetToken(String checkType, String verifycode, String verifycodeHex) {
        token.setCheckType(checkType);
        token.setVerifycode(verifycode);
        token.setVerifycodeHex(verifycodeHex);
    }

    /**
     * 返回验证码
     * @return
     */
    public String getVerifyCode() {
        if (StringUtils.isNotBlank(token.getVerifycode())) {
            return StringUtils.upperCase(token.getVerifycode());
        }
        return StringUtils.EMPTY;
    }

    /**
     * 返回clientId
     * @return
     */
    public String getClientId() {
        return String.valueOf(token.getClientid());
    }

    /**
     * 返回session的ID
     * @return
     */
    public String getPSessionId() {
        return token.getPsessionid();
    }

    /**
     * 返回getVfwebqq
     * @return
     */
    public String getVfwebqq() {
        return token.getVfwebqq();
    }

    /**
     * 返回验证令牌
     * @return
     */
    public AuthToken getToken() {
        return token;
    }

    /**
     * 是否已经加载了好友
     * @return
     */
    public boolean isLoadedFriends() {
        if (self == null) {
            return false;
        }

        if (self.getFriends() != null && !self.getFriends().isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 根据QQ号码查找出指定的好友对象
     * @param account
     * @return
     */
    public Member findFriend(String account) {
        for (Member friend : this.self.getFriends()) {
            if (StringUtils.equals(account, friend.getAccount().getAccount())) {
                return friend;
            }
        }
        return null;
    }

    /**
     * 根据内部识别号查找(不推荐使用)
     * @param uin
     * @return
     */
    public Member findFriend(long uin) {
        for (Member friend : this.self.getFriends()) {
            if (friend.getUin() == uin) {
                return friend;
            }
        }
        return null;
    }

    /**
     * 加入在线好友
     * @param friend
     */
    public void addOnlineFriend(Member friend) {
        onlineFriends.put(String.valueOf(friend.getUin()), friend);
    }

    /**
     * 返回指定QQ号码的在线好友
     * @param account
     * @return
     */
    public Member getOnlineFriend(String account) {
        return onlineFriends.get(account);
    }

    /**
     * 删除一个在线好友
     * @param account
     */
    public void removeOnlineFriend(String account) {
        onlineFriends.remove(account);
    }

    /**
     * 清空在线好友列表
     */
    public void removeOnlineFriends() {
        onlineFriends.clear();
    }

    /**
     * 返回在线好友列表
     * @return
     */
    public List<Member> getOnlineFriends() {
        return new ArrayList<Member>(onlineFriends.values());
    }

    /**
     * 加入QQ群列表
     * @param friend
     */
    public void addGroup(Group group) {
        if (group != null) {
            groups.put(group.getNumber(), group);
        }
    }

    /**
     * 返回指定QQ群号码的群
     * @param account
     * @return
     */
    public Group getGroup(long number) {
        return groups.get(number);
    }

    /**
     * 删除一个QQ群
     * @param account
     */
    public void removeGroup(long number) {
        groups.remove(number);
    }

    /**
     * 清空QQ群列表
     */
    public void removeGroups() {
        groups.clear();
    }

    /**
     * 返回QQ群集合
     * @return
     */
    public List<Group> getGroups() {
        return new ArrayList<Group>(groups.values());
    }

    /**
     * 清除
     */
    public void clear() {
        this.self = null;
        this.cookies = null;
        this.token = null;
        onlineFriends.clear();
        groups.clear();
    }

	public Member getSelf() {
		return self;
	}

	public void setSelf(Member self) {
		this.self = self;
	}

	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}
    
    
}
