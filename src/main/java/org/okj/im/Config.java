package org.okj.im;

import java.util.List;

import com.google.common.collect.Lists;

public class Config {
	
	private String commandAccount;
	
	private String account;
	
	private String password;
	
	private String defautMsg;
	
	private List<String> msgs = Lists.newArrayList();

	public List<String> getMsgs() {
		return msgs;
	}

	public void setMsgs(List<String> msgs) {
		this.msgs = msgs;
	}

	public String getDefautMsg() {
		return defautMsg;
	}

	public void setDefautMsg(String defautMsg) {
		this.defautMsg = defautMsg;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCommandAccount() {
		return commandAccount;
	}

	public void setCommandAccount(String commandAccount) {
		this.commandAccount = commandAccount;
	}
	
	

}
