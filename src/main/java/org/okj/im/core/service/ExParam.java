/**
 * @(#)ExParam.java 2013-1-21
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
package org.okj.im.core.service;

import java.io.Serializable;

/**
 *  
 * @author Administrator
 * @version $Id: ExParam.java, v 0.1 2013-1-21 ÏÂÎç4:33:33 Administrator Exp $
 */
public class ExParam implements Serializable {
    /** UID */
    private static final long serialVersionUID = -7052760990713803418L;

    /* cookie */
    private String            cookie           = "";

    /* upload file name */
    private String            filename;

    /* content */
    private String            contents         = "";

    /**
     *  
     */
    public ExParam() {

    }

    /**
     * Getter method for property <tt>cookie</tt>.
     * 
     * @return property value of cookie
     */
    public String getCookie() {
        return cookie;
    }

    /**
     * Setter method for property <tt>cookie</tt>.
     * 
     * @param cookie value to be assigned to property cookie
     */
    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    /**
     * Getter method for property <tt>filename</tt>.
     * 
     * @return property value of filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * Setter method for property <tt>filename</tt>.
     * 
     * @param filename value to be assigned to property filename
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * Getter method for property <tt>contents</tt>.
     * 
     * @return property value of contents
     */
    public String getContents() {
        return contents;
    }

    /**
     * Setter method for property <tt>contents</tt>.
     * 
     * @param contents value to be assigned to property contents
     */
    public void setContents(String contents) {
        this.contents = contents;
    }
}
