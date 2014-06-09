package com.geetest.gtappdemo.model.vo;

/**
 * 验证码的答案
 * @author Zheng
 * 2014年5月19日  上午10:54:22
 */
public class CaptchaAnswer {
	
	private int disX = 4;//滑块的X方向偏移量
	private String randBase = "5V";//一次验证会话产生的的随机码
	
	
	public int getDisX() {
		return disX;
	}
	public void setDisX(int disX) {
		this.disX = disX;
	}
	public String getRandBase() {
		return randBase;
	}
	public void setRandBase(String randBase) {
		this.randBase = randBase;
	}
	

	
	
}
