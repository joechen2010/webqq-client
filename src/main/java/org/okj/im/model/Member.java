/**
 * @(#)User.java 2013-1-23
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
package org.okj.im.model;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.okj.commons.logger.LogUtils;
import org.okj.im.core.utils.ClassLoaderUtils;
import org.okj.im.model.enums.Status;

/**
 * QQ Member Entity
 * @author Administrator
 * @version $Id: User.java, v 0.1 2013-1-23 ����9:12:20 Administrator Exp $
 */
public class Member implements Serializable {
    /** UID */
    private static final long   serialVersionUID = 920861450655906054L;

    /* logger */
    private static final Logger LOGGER           = Logger.getLogger(Member.class);

    /* QQ�û��ı�ʾID */
    private long                uin;

    /* �û�״̬(Ĭ������״̬) */
    private Status              status           = Status.OFFLINE;

    /* QQ�û��˺� */
    private Account             account;

    /* QQ�û���� */
    private Names               name;

    /* QQ�û���ϵ��ʽ */
    private Contract            contract;

    /* ������Ϣ */
    private PersonalInfo        personalInfo;

    /* λ����Ϣ */
    private Location            location;

    /* �Ա� */
    private String              gender;

    /* ��¼ʱ�� */
    private long                loginDate;

    /* �ȼ� */
    private Level               level;

    /* ע��ʱ�� */
    private int                 regTime;

    /* ���� */
    private int                 allow;

    /* �ͻ����� */
    private int                 clientType;

    /* */
    private int                 cip;

    private int                 flag;

    /* ���� */
    private Category            category;

    /* �����б� */
    private List<Member>        friends;

    private Integer index = 0;
    
    /**
     * ���캯��
     */
    public Member() {
    }

    /**
     * ���ؼ��ܵ�QQ��¼����
     * @return
     */
    public String getEncodePassword(AuthToken token) {
        if (StringUtils.isNotBlank(account.getPassword())) {
            InputStream in = null;
            try {
                in = ClassLoaderUtils.getResourceAsStream("encodePass.js", this.getClass());
                Reader inreader = new InputStreamReader(in);
                ScriptEngineManager m = new ScriptEngineManager();
                ScriptEngine se = m.getEngineByName("javascript");
                se.eval(inreader);
                Object t = se.eval("passwordEncoding(\"" + account.getPassword() + "\",\""
                                   + token.getVerifycodeHex() + "\",\""
                                   + StringUtils.upperCase(token.getVerifycode()) + "\");");

                return t.toString();
            } catch (Exception ex) {
                LogUtils.error(LOGGER, "���ؼ��ܵĵ�¼����ʱ�����쳣", ex);
            }
            return account.getPassword();
        }
        return account.getPassword();
    }

    /**
     * ����һ������
     * @param friend
     */
    public void addFriend(Member friend) {
        if (friends == null) {
            friends = new ArrayList<Member>();
        }
        if (friend != null) {
            friends.add(friend);
        }
    }

    public List<Member> getFriends() {
        return friends;
    }

    /**
     * ����ָ��QQ����ĺ��Ѷ���
     * @param account
     * @return
     */
    public Member getFriend(String account) {
        if (friends != null) {
            for (Member friend : friends) {
               // if (StringUtils.equalsIgnoreCase(friend.getAccount().getAccount(), account)) {
            	if (StringUtils.equalsIgnoreCase(String.valueOf(friend.getUin()), account)) {
                    return friend;
                }
            }
        }
        return null;
    }

    /**
     * Getter method for property <tt>uin</tt>.
     * 
     * @return property value of uin
     */
    public long getUin() {
        return uin;
    }

    /**
     * Setter method for property <tt>uin</tt>.
     * 
     * @param uin value to be assigned to property uin
     */
    public void setUin(long uin) {
        this.uin = uin;
    }

    /**
     * Getter method for property <tt>status</tt>.
     * 
     * @return property value of status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * Setter method for property <tt>status</tt>.
     * 
     * @param status value to be assigned to property status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Getter method for property <tt>account</tt>.
     * 
     * @return property value of account
     */
    public Account getAccount() {
        if (account == null) {
            account = new Account();
        }
        return account;
    }

    /**
     * Setter method for property <tt>account</tt>.
     * 
     * @param account value to be assigned to property account
     */
    public void setAccount(Account account) {
        this.account = account;
    }

    /**
     * Getter method for property <tt>name</tt>.
     * 
     * @return property value of name
     */
    public Names getName() {
        if (name == null) {
            name = new Names();
        }
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     * 
     * @param name value to be assigned to property name
     */
    public void setName(Names name) {
        this.name = name;
    }

    /**
     * Getter method for property <tt>contract</tt>.
     * 
     * @return property value of contract
     */
    public Contract getContract() {
        if (contract == null) {
            contract = new Contract();
        }
        return contract;
    }

    /**
     * Setter method for property <tt>contract</tt>.
     * 
     * @param contract value to be assigned to property contract
     */
    public void setContract(Contract contract) {
        this.contract = contract;
    }

    /**
     * Getter method for property <tt>personalInfo</tt>.
     * 
     * @return property value of personalInfo
     */
    public PersonalInfo getPersonalInfo() {
        if (personalInfo == null) {
            personalInfo = new PersonalInfo();
        }
        return personalInfo;
    }

    /**
     * Setter method for property <tt>personalInfo</tt>.
     * 
     * @param personalInfo value to be assigned to property personalInfo
     */
    public void setPersonalInfo(PersonalInfo personalInfo) {
        this.personalInfo = personalInfo;
    }

    /**
     * Getter method for property <tt>location</tt>.
     * 
     * @return property value of location
     */
    public Location getLocation() {
        if (location == null) {
            location = new Location();
        }
        return location;
    }

    /**
     * Setter method for property <tt>location</tt>.
     * 
     * @param location value to be assigned to property location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Getter method for property <tt>gender</tt>.
     * 
     * @return property value of gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Setter method for property <tt>gender</tt>.
     * 
     * @param gender value to be assigned to property gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Getter method for property <tt>loginDate</tt>.
     * 
     * @return property value of loginDate
     */
    public long getLoginDate() {
        return loginDate;
    }

    /**
     * Setter method for property <tt>loginDate</tt>.
     * 
     * @param loginDate value to be assigned to property loginDate
     */
    public void setLoginDate(long loginDate) {
        this.loginDate = loginDate;
    }

    /**
     * Getter method for property <tt>level</tt>.
     * 
     * @return property value of level
     */
    public Level getLevel() {
        if (level == null) {
            level = new Level();
        }
        return level;
    }

    /**
     * Setter method for property <tt>level</tt>.
     * 
     * @param level value to be assigned to property level
     */
    public void setLevel(Level level) {
        this.level = level;
    }

    /**
     * Getter method for property <tt>regTime</tt>.
     * 
     * @return property value of regTime
     */
    public int getRegTime() {
        return regTime;
    }

    /**
     * Setter method for property <tt>regTime</tt>.
     * 
     * @param regTime value to be assigned to property regTime
     */
    public void setRegTime(int regTime) {
        this.regTime = regTime;
    }

    /**
     * Getter method for property <tt>allow</tt>.
     * 
     * @return property value of allow
     */
    public int getAllow() {
        return allow;
    }

    /**
     * Setter method for property <tt>allow</tt>.
     * 
     * @param allow value to be assigned to property allow
     */
    public void setAllow(int allow) {
        this.allow = allow;
    }

    /**
     * Getter method for property <tt>clientType</tt>.
     * 
     * @return property value of clientType
     */
    public int getClientType() {
        return clientType;
    }

    /**
     * Setter method for property <tt>clientType</tt>.
     * 
     * @param clientType value to be assigned to property clientType
     */
    public void setClientType(int clientType) {
        this.clientType = clientType;
    }

    /**
     * Getter method for property <tt>category</tt>.
     * 
     * @return property value of category
     */
    public Category getCategory() {
        if (category == null) {
            category = new Category();
        }
        return category;
    }

    /**
     * Setter method for property <tt>category</tt>.
     * 
     * @param category value to be assigned to property category
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * Setter method for property <tt>friends</tt>.
     * 
     * @param friends value to be assigned to property friends
     */
    public void setFriends(List<Member> friends) {
        this.friends = friends;
    }

    /**
     * Getter method for property <tt>cip</tt>.
     * 
     * @return property value of cip
     */
    public int getCip() {
        return cip;
    }

    /**
     * Setter method for property <tt>cip</tt>.
     * 
     * @param cip value to be assigned to property cip
     */
    public void setCip(int cip) {
        this.cip = cip;
    }

    /**
     * Getter method for property <tt>flag</tt>.
     * 
     * @return property value of flag
     */
    public int getFlag() {
        return flag;
    }

    /**
     * Setter method for property <tt>flag</tt>.
     * 
     * @param flag value to be assigned to property flag
     */
    public void setFlag(int flag) {
        this.flag = flag;
    }

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
}
