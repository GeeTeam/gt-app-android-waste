package com.geetest.gtapp.slogger.vo;

import com.geetest.gtapp.logger.GtLogger;

/**
 * 手机信息
 * 
 * @author Zheng
 * @time 2014年6月30日 下午5:52:22
 */
public class MobileInfo {

	private String imei = "000000";// IMEI号
	private String imsi = "000";
	private String mtype = "unknowType";// 手机型号
	private String mtyb = "unknow";// 手机品牌
	private String numer = "8600000000000"; // 手机号码，有的可得，有的不可得
	private int netWorkType = 0;// 上网类型

	private String buildVersionSdk = "";// SDK版本
	private String buildVersionRelease = "";// 系统版本

	private int buildVersionSdkInt = 0;// android.os.Build.VERSION.SDK_INT

	

	public int getBuildVersionSdkInt() {
		return buildVersionSdkInt;
	}

	public void setBuildVersionSdkInt(int buildVersionSdkInt) {
		this.buildVersionSdkInt = buildVersionSdkInt;
	}

	public String getBuildVersionSdk() {
		return buildVersionSdk;
	}

	public void setBuildVersionSdk(String buildVersionSdk) {
		this.buildVersionSdk = buildVersionSdk;
	}

	public String getBuildVersionRelease() {
		return buildVersionRelease;
	}

	public void setBuildVersionRelease(String buildVersionRelease) {
		this.buildVersionRelease = buildVersionRelease;
	}

	public int getNetWorkType() {
		return netWorkType;
	}

	public void setNetWorkType(int netWorkType) {
		this.netWorkType = netWorkType;
	}

	public void v() {
		GtLogger.v("手机IMEI号：" + imei + "手机IESI号：" + imsi + "手机型号：" + mtype
				+ "手机品牌：" + mtyb + "手机号码" + numer + " 网络类型 :" + netWorkType);
	}

	public String getMtype() {
		return mtype;
	}

	public void setMtype(String mtype) {
		this.mtype = mtype;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getMtyb() {
		return mtyb;
	}

	public void setMtyb(String mtyb) {
		this.mtyb = mtyb;
	}

	public String getNumer() {
		return numer;
	}

	public void setNumer(String numer) {
		this.numer = numer;
	}
}
