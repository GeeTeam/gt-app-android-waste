package com.geetest.gtapp.gtlog.vo;

/**
 * 宿主程序信息--一些静态值
 * 
 * @author Zheng
 * @time 2014年7月2日 下午11:44:34
 */
public class SdkInfo {

	// private int verCode = 0;
	// private String verName = "";
	
	private String gt_public_key = "";
	private String gtapp_sdk_version = "";

	// public int getVerCode() {
	// return verCode;
	// }
	//
	// public void setVerCode(int verCode) {
	// this.verCode = verCode;
	// }
	//
	// public String getVerName() {
	// return verName;
	// }
	//
	// public void setVerName(String verName) {
	// this.verName = verName;
	// }

	public String getGt_public_key() {
		return gt_public_key;
	}

	public void setGt_public_key(String gt_public_key) {
		this.gt_public_key = gt_public_key;
	}

	public String getGtapp_sdk_version() {
		return gtapp_sdk_version;
	}

	public void setGtapp_sdk_version(String gtapp_sdk_version) {
		this.gtapp_sdk_version = gtapp_sdk_version;
	}

}
