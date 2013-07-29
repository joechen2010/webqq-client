/**
 * @(#)Category.java 2013-1-21
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
 * 
 * @author Administrator
 * @version $Id: Category.java, v 0.1 2013-1-21 下午3:07:00 Administrator Exp $
 */
public class Category implements Serializable {
    /** UID */
    private static final long serialVersionUID = -2200743280311903954L;
    private int               index;
    private int               sort;
    private String            name;
    private int               onlineCount      = 0;

    /**
     * 构造函数
     */
    public Category() {
    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Category [index=" + index + ", sort=" + sort + ", name=" + name + ", onlineCount="
               + onlineCount + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || this != obj) {
            return false;
        }
        if (obj instanceof Category) {
            Category c = (Category) obj;
            if (c.index == this.index) {
                return true;
            }
        }
        return super.equals(obj);
    }

    /**
     * Getter method for property <tt>index</tt>.
     * 
     * @return property value of index
     */
    public int getIndex() {
        return index;
    }

    /**
     * Setter method for property <tt>index</tt>.
     * 
     * @param index value to be assigned to property index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Getter method for property <tt>sort</tt>.
     * 
     * @return property value of sort
     */
    public int getSort() {
        return sort;
    }

    /**
     * Setter method for property <tt>sort</tt>.
     * 
     * @param sort value to be assigned to property sort
     */
    public void setSort(int sort) {
        this.sort = sort;
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
     * Getter method for property <tt>onlineCount</tt>.
     * 
     * @return property value of onlineCount
     */
    public int getOnlineCount() {
        return onlineCount;
    }

    /**
     * Setter method for property <tt>onlineCount</tt>.
     * 
     * @param onlineCount value to be assigned to property onlineCount
     */
    public void setOnlineCount(int onlineCount) {
        this.onlineCount = onlineCount;
    }
}
