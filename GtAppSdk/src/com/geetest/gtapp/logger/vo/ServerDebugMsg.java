package com.geetest.gtapp.logger.vo;

/**
 * 向服务器端传送的文字格式
 * 
 * @author Zheng
 * @time 2014年6月30日 上午11:22:50
 */
public class ServerDebugMsg {

	private String api = "";// 存储的API的名称
	private String osType = "android";// 操作系统类型 android,ios
	private Object data = "";// 发送的字符数据对象

	public String getOsType() {
		return osType;
	}

	public void setOsType(String osType) {
		this.osType = osType;
	}

	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
