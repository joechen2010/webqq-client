/**
 * Storevm.org Inc.
 * Copyright (c) 2004-2013 All Rights Reserved.
 */
package org.okj.im.core.actions;

import java.util.List;

import org.okj.commons.service.action.ActionContext;
import org.okj.commons.service.action.ActionException;
import org.okj.im.core.constants.ActionContextKeys;
import org.okj.im.model.Group;
import org.springframework.beans.BeanUtils;

/**
 * 
 * @author Administrator
 * @version $Id: CompletedFindQQGroupsAction.java, v 0.1 2013-1-23 下午8:30:21 Administrator Exp $
 */
public class CompletedFindQQGroupsAction extends DefaultBizAction<Void> {

    /** 
     * @see org.okj.im.core.actions.DefaultBizAction#skip(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    public boolean skip(ActionContext context) {
        List<Group> groups = getContextAttribute(context, ActionContextKeys.GROUPS_KEY);
        if (groups == null) {
            return true;
        }
        return false;
    }

    /** 
     * @see org.storevm.commons.service.action.AbstractBizAction#doExecute(org.storevm.commons.service.action.ActionContext)
     */
    @Override
    protected Void doExecute(ActionContext context) throws ActionException {
        List<Group> groups = getContextAttribute(context, ActionContextKeys.GROUPS_KEY);
        for (Group group : groups) {
            //将QQ群放入容器中管理
            Group nGroup = new Group();
            BeanUtils.copyProperties(group, nGroup); //复制对象并放入容器中进行全局管理
            clientContext.addGroup(nGroup); //添加QQ群列表
        }
        return null;
    }

}
