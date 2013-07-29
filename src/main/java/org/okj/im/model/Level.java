/**
 * @(#)Level.java 2013-1-23
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
 * QQ等级
 * @author Administrator
 * @version $Id: Level.java, v 0.1 2013-1-23 下午5:46:14 Administrator Exp $
 */
public class Level implements Serializable {
    /** UID */
    private static final long serialVersionUID = 9190525161525988970L;

    /* 当前等级 */
    private int               level;

    /* 活跃天数 */
    private int               activeDays;

    /* 升级剩余天数 */
    private int               remainDays;

    /**
     * 构造函数
     */
    public Level() {
    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Level [level=" + level + ", activeDays=" + activeDays + ", remainDays="
               + remainDays + "]";
    }

    /**
     * Getter method for property <tt>level</tt>.
     * 
     * @return property value of level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Setter method for property <tt>level</tt>.
     * 
     * @param level value to be assigned to property level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Getter method for property <tt>activeDays</tt>.
     * 
     * @return property value of activeDays
     */
    public int getActiveDays() {
        return activeDays;
    }

    /**
     * Setter method for property <tt>activeDays</tt>.
     * 
     * @param activeDays value to be assigned to property activeDays
     */
    public void setActiveDays(int activeDays) {
        this.activeDays = activeDays;
    }

    /**
     * Getter method for property <tt>remainDays</tt>.
     * 
     * @return property value of remainDays
     */
    public int getRemainDays() {
        return remainDays;
    }

    /**
     * Setter method for property <tt>remainDays</tt>.
     * 
     * @param remainDays value to be assigned to property remainDays
     */
    public void setRemainDays(int remainDays) {
        this.remainDays = remainDays;
    }
}
