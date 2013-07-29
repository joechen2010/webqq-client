package org.okj.im.core.task;

import org.okj.commons.service.action.ActionContext;
import org.okj.commons.service.action.ActionsExecutor;
import org.okj.im.core.constants.BizCode;
import org.springframework.beans.factory.annotation.Autowired;

public class ReceiveMessageThead extends Thread {


	@Autowired
	private ActionsExecutor actionsExecutor;

	@Override
	public void run() {
		try {
			while (true) {
				ActionContext context = new ActionContext(
						BizCode.RECEIVE_MESSAGE);
				actionsExecutor.execute(context);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void setActionsExecutor(ActionsExecutor actionsExecutor) {
		this.actionsExecutor = actionsExecutor;
	}

}
