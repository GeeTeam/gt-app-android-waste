package com.geetest.gtappdemo.model.vo;


/**
 * 服务器上的版本信息--和文本文件一样的格式
 * 
 * @author Zheng
 * @time 2014年6月17日 下午4:05:02
 */
public class GtSdkVersionInfo {

	private int version_code = 0;// 版本编号
	private String version_name = "1.0";// 版本名称
	private String build_time = "";// 发布时间

	public int getVersion_code() {
		return version_code;
	}

	public void setVersion_code(int version_code) {
		this.version_code = version_code;
	}

	public String getVersion_name() {
		return version_name;
	}

	public void setVersion_name(String version_name) {
		this.version_name = version_name;
	}

	public String getBuild_time() {
		return build_time;
	}

	public void setBuild_time(String build_time) {
		this.build_time = build_time;
	}

}
