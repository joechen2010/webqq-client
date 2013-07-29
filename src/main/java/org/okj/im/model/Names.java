/**
 * @(#)Names.java 2013-1-23
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
 * QQ Name Entity
 * @author Administrator
 * @version $Id: Names.java, v 0.1 2013-1-23 上午9:24:27 Administrator Exp $
 */
public class Names implements Serializable {
    /**  */
    private static final long serialVersionUID = -4319221756011799059L;

    /* 昵称 */
    private String            nickname;

    /* 备注姓名 */
    private String            markname;

    /* 个性化签名 */
    private String            lnick;

    /**
     * 构造函数
     */
    public Names() {
    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Names [nickname=" + nickname + ", markname=" + markname + ", lnick=" + lnick + "]";
    }

    /**
     * Getter method for property <tt>nickname</tt>.
     * 
     * @return property value of nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Setter method for property <tt>nickname</tt>.
     * 
     * @param nickname value to be assigned to property nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Getter method for property <tt>markname</tt>.
     * 
     * @return property value of markname
     */
    public String getMarkname() {
        return markname;
    }

    /**
     * Setter method for property <tt>markname</tt>.
     * 
     * @param markname value to be assigned to property markname
     */
    public void setMarkname(String markname) {
        this.markname = markname;
    }

    /**
     * Getter method for property <tt>lnick</tt>.
     * 
     * @return property value of lnick
     */
    public String getLnick() {
        return lnick;
    }

    /**
     * Setter method for property <tt>lnick</tt>.
     * 
     * @param lnick value to be assigned to property lnick
     */
    public void setLnick(String lnick) {
        this.lnick = lnick;
    }
}
