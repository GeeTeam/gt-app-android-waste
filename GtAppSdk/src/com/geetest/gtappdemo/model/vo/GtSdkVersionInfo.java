package com.geetest.gtappdemo.model.vo;

/**
 * 服务器上的版本信息--和文本文件一样的格式
 * 
 * @author Zheng
 * @time 2014年6月17日 下午4:05:02
 */
public class GtSdkVersionInfo {

	private int verCode = 0;// 版本编号
	private String verName = "1.0";// 版本名称
	private String buildTime = "";// 发布时间

	public int getVerCode() {
		return verCode;
	}

	public void setVerCode(int verCode) {
		this.verCode = verCode;
	}

	public String getVerName() {
		return verName;
	}

	public void setVerName(String verName) {
		this.verName = verName;
	}

	public String getBuildTime() {
		return buildTime;
	}

	public void setBuildTime(String buildTime) {
		this.buildTime = buildTime;
	}

}
