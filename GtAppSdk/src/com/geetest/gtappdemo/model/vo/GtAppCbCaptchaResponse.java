package com.geetest.gtappdemo.model.vo;

/**
 * @author Zheng 回调产生的验证的结果
 * 
 */
public class GtAppCbCaptchaResponse {

	private int resCode = 0;// 验证的结果码
	private String resMsg = "";// 验证结果描述

	public int getResCode() {
		return resCode;
	}

	public void setResCode(int resCode) {
		this.resCode = resCode;
	}

	public String getResMsg() {
		return resMsg;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}

}
