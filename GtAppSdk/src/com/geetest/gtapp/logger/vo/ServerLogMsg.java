package com.geetest.gtapp.logger.vo;

/**
 * 向服务器端提交的信息
 * 
 * @author Zheng
 * @time 2014年6月29日 下午3:04:43
 */
public class ServerLogMsg {

	private String msgTag = "log_msg";//消息的标签

	private String osType = "android";// 操作系统类型 android,ios
	private String logMsg = "";// #消息名称

	
	

	public String getMsgTag() {
		return msgTag;
	}

	public void setMsgTag(String msgTag) {
		this.msgTag = msgTag;
	}

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public String getLogMsg() {
		return logMsg;
	}

	public void setLogMsg(String logMsg) {
		this.logMsg = logMsg;
	}

}
