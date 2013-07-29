/**
 * @(#)PersonalInfo.java 2013-1-23
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
import java.util.Date;

/**
 * QQ User Personal Information
 * @author Administrator
 * @version $Id: PersonalInfo.java, v 0.1 2013-1-23 上午9:31:16 Administrator Exp $
 */
public class PersonalInfo implements Serializable {
    /** UID */
    private static final long serialVersionUID = 2091950525077554627L;

    /* 出生日期 */
    private Date              birthday;

    /* 毕业院校 */
    private String            college;

    /* 星座 */
    private int               constel;

    /* 血型 */
    private int               blood;

    /* 个人主页 */
    private String            homepage;

    /* 统计 */
    private int               stat;

    /* VIP信息 */
    private int               vipInfo;

    /* 个人说明 */
    private String            personal;

    /* 职业 */
    private String            occupation;

    /* 生肖 */
    private int               chineseZodiac;

    /**
     * 构造函数
     */
    public PersonalInfo() {
    }

    /** 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "PersonalInfo [birthday=" + birthday + ", college=" + college + ", constel="
               + constel + ", blood=" + blood + ", homepage=" + homepage + ", stat=" + stat
               + ", vipInfo=" + vipInfo + ", personal=" + personal + ", occupation=" + occupation
               + ", chineseZodiac=" + chineseZodiac + "]";
    }

    /**
     * Getter method for property <tt>birthday</tt>.
     * 
     * @return property value of birthday
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * Setter method for property <tt>birthday</tt>.
     * 
     * @param birthday value to be assigned to property birthday
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    /**
     * Getter method for property <tt>college</tt>.
     * 
     * @return property value of college
     */
    public String getCollege() {
        return college;
    }

    /**
     * Setter method for property <tt>college</tt>.
     * 
     * @param college value to be assigned to property college
     */
    public void setCollege(String college) {
        this.college = college;
    }

    /**
     * Getter method for property <tt>constel</tt>.
     * 
     * @return property value of constel
     */
    public int getConstel() {
        return constel;
    }

    /**
     * Setter method for property <tt>constel</tt>.
     * 
     * @param constel value to be assigned to property constel
     */
    public void setConstel(int constel) {
        this.constel = constel;
    }

    /**
     * Getter method for property <tt>blood</tt>.
     * 
     * @return property value of blood
     */
    public int getBlood() {
        return blood;
    }

    /**
     * Setter method for property <tt>blood</tt>.
     * 
     * @param blood value to be assigned to property blood
     */
    public void setBlood(int blood) {
        this.blood = blood;
    }

    /**
     * Getter method for property <tt>homepage</tt>.
     * 
     * @return property value of homepage
     */
    public String getHomepage() {
        return homepage;
    }

    /**
     * Setter method for property <tt>homepage</tt>.
     * 
     * @param homepage value to be assigned to property homepage
     */
    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    /**
     * Getter method for property <tt>stat</tt>.
     * 
     * @return property value of stat
     */
    public int getStat() {
        return stat;
    }

    /**
     * Setter method for property <tt>stat</tt>.
     * 
     * @param stat value to be assigned to property stat
     */
    public void setStat(int stat) {
        this.stat = stat;
    }

    /**
     * Getter method for property <tt>vipInfo</tt>.
     * 
     * @return property value of vipInfo
     */
    public int getVipInfo() {
        return vipInfo;
    }

    /**
     * Setter method for property <tt>vipInfo</tt>.
     * 
     * @param vipInfo value to be assigned to property vipInfo
     */
    public void setVipInfo(int vipInfo) {
        this.vipInfo = vipInfo;
    }

    /**
     * Getter method for property <tt>personal</tt>.
     * 
     * @return property value of personal
     */
    public String getPersonal() {
        return personal;
    }

    /**
     * Setter method for property <tt>personal</tt>.
     * 
     * @param personal value to be assigned to property personal
     */
    public void setPersonal(String personal) {
        this.personal = personal;
    }

    /**
     * Getter method for property <tt>occupation</tt>.
     * 
     * @return property value of occupation
     */
    public String getOccupation() {
        return occupation;
    }

    /**
     * Setter method for property <tt>occupation</tt>.
     * 
     * @param occupation value to be assigned to property occupation
     */
    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    /**
     * Getter method for property <tt>chineseZodiac</tt>.
     * 
     * @return property value of chineseZodiac
     */
    public int getChineseZodiac() {
        return chineseZodiac;
    }

    /**
     * Setter method for property <tt>chineseZodiac</tt>.
     * 
     * @param chineseZodiac value to be assigned to property chineseZodiac
     */
    public void setChineseZodiac(int chineseZodiac) {
        this.chineseZodiac = chineseZodiac;
    }
}
