package org.okj.im.model;

import net.sf.json.JSONArray;

public class ReceiveMsg {
	
	private static final String SPLIT  = "],";
	
	private Integer msg_id;
	private Long from_uin;
	private Long to_uin;
	private Integer msg_id2;
	private Integer msg_type;
	private Long reply_ip;
	private Long time;
	private JSONArray content;
	
	
	public Integer getMsg_id() {
		return msg_id;
	}
	public void setMsg_id(Integer msg_id) {
		this.msg_id = msg_id;
	}
	public Long getFrom_uin() {
		return from_uin;
	}
	public void setFrom_uin(Long from_uin) {
		this.from_uin = from_uin;
	}
	public Long getTo_uin() {
		return to_uin;
	}
	public void setTo_uin(Long to_uin) {
		this.to_uin = to_uin;
	}
	public Integer getMsg_id2() {
		return msg_id2;
	}
	public void setMsg_id2(Integer msg_id2) {
		this.msg_id2 = msg_id2;
	}
	public Integer getMsg_type() {
		return msg_type;
	}
	public void setMsg_type(Integer msg_type) {
		this.msg_type = msg_type;
	}
	public Long getReply_ip() {
		return reply_ip;
	}
	public void setReply_ip(Long reply_ip) {
		this.reply_ip = reply_ip;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public JSONArray getContent() {
		return content;
	}
	public void setContent(JSONArray content) {
		this.content = content;
	}
	
	public String getMsgContent(){
		String[] msgContent = content.toString().split(SPLIT);
		String msg =  msgContent[msgContent.length -1];
		return msg.replace("\"", "").replace("]", "");
	}

	
	public static void main(String[] args){
		String json = "[[\"font\",{\"size\":12,\"color\":\"ff0000\",\"style\":[1,0,0],\"name\":\"楷体\"}],[\"cface\",{\"name\":\"{F986E4FB-AE00-45BD-987F-AFAD14BC2E16}.jpg\",\"file_id\":420333115,\"key\":\"RhHFipQmQTgzQ5Sb\",\"server\":\"171.240.39.125:0\"}],\" fdsfsafas\"]";
		String[] msgContent = json.toString().split(SPLIT);
		String msg =  msgContent[msgContent.length -1];
		System.out.println(msg.replace("\"", "").replace("]", "")) ;
	}
	
}
