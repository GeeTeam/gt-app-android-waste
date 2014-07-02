package com.geetest.gtapp.logger.vo;

/**
 * 向服务器端传送的文字格式
 * 
 * @author Zheng
 * @time 2014年6月30日 上午11:22:50
 */
public class ServerDebugMsg {

	private String msgTag = "log_msg";// 消息的标签
	private String osType = "android";// 操作系统类型 android,ios,WP8
	private Object osDetail = "osDetail";// 操作系统的详细信息
	private Object hostInfo = "";// 宿主程序信息
	private Object logMsg = "";// 发送的字符数据对象

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public Object getOsDetail() {
		return osDetail;
	}

	public void setOsDetail(Object osDetail) {
		this.osDetail = osDetail;
	}

	public Object getHostInfo() {
		return hostInfo;
	}

	public void setHostInfo(Object hostInfo) {
		this.hostInfo = hostInfo;
	}

	public String getMsgTag() {
		return msgTag;
	}

	public void setMsgTag(String msgTag) {
		this.msgTag = msgTag;
	}

	public Object getLogMsg() {
		return logMsg;
	}

	public void setLogMsg(Object logMsg) {
		this.logMsg = logMsg;
	}

}
