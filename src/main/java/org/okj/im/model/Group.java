/**
 * @(#)Group.java 2013-1-23
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
 * QQ Group Entity
 * @author Administrator
 * @version $Id: Group.java, v 0.1 2013-1-23 上午10:23:39 Administrator Exp $
 */
public class Group implements Serializable {
    /** UID */
    private static final long serialVersionUID = -6903803383624006319L;

    /* 标识 */
    private long              gid;

    /* 群号 */
    private long              number           = -1;

    /* 群名称 */
    private String            name;

    /* 标志位 */
    private long              flag;

    /**/
    private int               mask;

    /* QQ群代码 */
    private long              code;

    /**
     * 构造函数
     */
    public Group() {

    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Group [gid=" + gid + ", number=" + number + ", name=" + name + ", code=" + code
               + "]";
    }

    /**
     * Getter method for property <tt>gid</tt>.
     * 
     * @return property value of gid
     */
    public long getGid() {
        return gid;
    }

    /**
     * Setter method for property <tt>gid</tt>.
     * 
     * @param gid value to be assigned to property gid
     */
    public void setGid(long gid) {
        this.gid = gid;
    }

    /**
     * Getter method for property <tt>number</tt>.
     * 
     * @return property value of number
     */
    public long getNumber() {
        return number;
    }

    /**
     * Setter method for property <tt>number</tt>.
     * 
     * @param number value to be assigned to property number
     */
    public void setNumber(long number) {
        this.number = number;
    }

    /**
     * Getter method for property <tt>name</tt>.
     * 
     * @return property value of name
     */
    public String getName() {
        return name;
    }

    /**
     * Setter method for property <tt>name</tt>.
     * 
     * @param name value to be assigned to property name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter method for property <tt>flag</tt>.
     * 
     * @return property value of flag
     */
    public long getFlag() {
        return flag;
    }

    /**
     * Setter method for property <tt>flag</tt>.
     * 
     * @param flag value to be assigned to property flag
     */
    public void setFlag(long flag) {
        this.flag = flag;
    }

    /**
     * Getter method for property <tt>mask</tt>.
     * 
     * @return property value of mask
     */
    public int getMask() {
        return mask;
    }

    /**
     * Setter method for property <tt>mask</tt>.
     * 
     * @param mask value to be assigned to property mask
     */
    public void setMask(int mask) {
        this.mask = mask;
    }

    /**
     * Getter method for property <tt>code</tt>.
     * 
     * @return property value of code
     */
    public long getCode() {
        return code;
    }

    /**
     * Setter method for property <tt>code</tt>.
     * 
     * @param code value to be assigned to property code
     */
    public void setCode(long code) {
        this.code = code;
    }
}
