/**
 * @(#)AuthToken.java 2013-1-21
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
 * 验证令牌
 * @author Administrator
 * @version $Id: AuthToken.java, v 0.1 2013-1-21 下午3:14:30 Administrator Exp $
 */
public class AuthToken implements Serializable {
    /** UID */
    private static final long serialVersionUID = 7672694560266684780L;
    private int               clientid         = 73937875;
    private String            psessionid;
    private String            ptwebqq;
    private String            vfwebqq;
    private String            skey;
    private String            checkType;
    private String            verifycodeHex;
    private String            verifycode;

    /**
     * 构造函数
     */
    public AuthToken() {
    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "AuthToken [psessionid=" + psessionid + ", ptwebqq=" + ptwebqq + ", vfwebqq="
               + vfwebqq + ", skey=" + skey + "]";
    }

    /**
     * Getter method for property <tt>clientid</tt>.
     * 
     * @return property value of clientid
     */
    public int getClientid() {
        return clientid;
    }

    /**
     * Setter method for property <tt>clientid</tt>.
     * 
     * @param clientid value to be assigned to property clientid
     */
    public void setClientid(int clientid) {
        this.clientid = clientid;
    }

    /**
     * Getter method for property <tt>psessionid</tt>.
     * 
     * @return property value of psessionid
     */
    public String getPsessionid() {
        return psessionid;
    }

    /**
     * Setter method for property <tt>psessionid</tt>.
     * 
     * @param psessionid value to be assigned to property psessionid
     */
    public void setPsessionid(String psessionid) {
        this.psessionid = psessionid;
    }

    /**
     * Getter method for property <tt>ptwebqq</tt>.
     * 
     * @return property value of ptwebqq
     */
    public String getPtwebqq() {
        return ptwebqq;
    }

    /**
     * Setter method for property <tt>ptwebqq</tt>.
     * 
     * @param ptwebqq value to be assigned to property ptwebqq
     */
    public void setPtwebqq(String ptwebqq) {
        this.ptwebqq = ptwebqq;
    }

    /**
     * Getter method for property <tt>vfwebqq</tt>.
     * 
     * @return property value of vfwebqq
     */
    public String getVfwebqq() {
        return vfwebqq;
    }

    /**
     * Setter method for property <tt>vfwebqq</tt>.
     * 
     * @param vfwebqq value to be assigned to property vfwebqq
     */
    public void setVfwebqq(String vfwebqq) {
        this.vfwebqq = vfwebqq;
    }

    /**
     * Getter method for property <tt>skey</tt>.
     * 
     * @return property value of skey
     */
    public String getSkey() {
        return skey;
    }

    /**
     * Setter method for property <tt>skey</tt>.
     * 
     * @param skey value to be assigned to property skey
     */
    public void setSkey(String skey) {
        this.skey = skey;
    }

    /**
     * Getter method for property <tt>checkType</tt>.
     * 
     * @return property value of checkType
     */
    public String getCheckType() {
        return checkType;
    }

    /**
     * Setter method for property <tt>checkType</tt>.
     * 
     * @param checkType value to be assigned to property checkType
     */
    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    /**
     * Getter method for property <tt>verifycodeHex</tt>.
     * 
     * @return property value of verifycodeHex
     */
    public String getVerifycodeHex() {
        return verifycodeHex;
    }

    /**
     * Setter method for property <tt>verifycodeHex</tt>.
     * 
     * @param verifycodeHex value to be assigned to property verifycodeHex
     */
    public void setVerifycodeHex(String verifycodeHex) {
        this.verifycodeHex = verifycodeHex;
    }

    /**
     * Getter method for property <tt>verifycode</tt>.
     * 
     * @return property value of verifycode
     */
    public String getVerifycode() {
        return verifycode;
    }

    /**
     * Setter method for property <tt>verifycode</tt>.
     * 
     * @param verifycode value to be assigned to property verifycode
     */
    public void setVerifycode(String verifycode) {
        this.verifycode = verifycode;
    }
}
