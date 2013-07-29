/**
 * @(#)Account.java 2013-1-23
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

import java.io.Serializable;

/**
 * QQ Account Entity
 * @author Administrator
 * @version $Id: Account.java, v 0.1 2013-1-23 ÉÏÎç9:19:54 Administrator Exp $
 */
public class Account implements Serializable {
    /** UID */
    private static final long serialVersionUID = -2136779987672597527L;

    /* ÕË»§(QQºÅÂë) */
    private String            account;

    /* µÇÂ¼ÃÜÂë */
    private String            password;

    /**
     * ¹¹Ôìº¯Êý
     */
    public Account() {
    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Account [account=" + account + ", password=" + password + "]";
    }

    /**
     * Getter method for property <tt>account</tt>.
     * 
     * @return property value of account
     */
    public String getAccount() {
        return account;
    }

    /**
     * Setter method for property <tt>account</tt>.
     * 
     * @param account value to be assigned to property account
     */
    public void setAccount(String account) {
        this.account = account;
    }

    /**
     * Getter method for property <tt>password</tt>.
     * 
     * @return property value of password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Setter method for property <tt>password</tt>.
     * 
     * @param password value to be assigned to property password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
