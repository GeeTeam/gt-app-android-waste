package com.geetest.gtappdemo.model.vo.greq;

/**
 * 上传的用户行为数据
 * 
 * @author Zheng
 * 
 */
public class AjaxPhp_GreqVo {
	
	private String api;// 调用的函数名称:
	private String challenge;// 验证行为的一个流水号:
	private String userresponse;// 编码后用户答案（仅仅只是在视觉上混淆）:
	private String passtime;// 客户端交互使用的时间（不是从服务器端传回）:
	private String imgload;// 背景图的加载时间:
	private String a;// 经过编码后的行为数据。:
	
	
	public String getApi() {
		return api;
	}
	public void setApi(String api) {
		this.api = api;
	}
	public String getChallenge() {
		return challenge;
	}
	public void setChallenge(String challenge) {
		this.challenge = challenge;
	}
	public String getUserresponse() {
		return userresponse;
	}
	public void setUserresponse(String userresponse) {
		this.userresponse = userresponse;
	}
	public String getPasstime() {
		return passtime;
	}
	public void setPasstime(String passtime) {
		this.passtime = passtime;
	}
	public String getImgload() {
		return imgload;
	}
	public void setImgload(String imgload) {
		this.imgload = imgload;
	}
	public String getA() {
		return a;
	}
	public void setA(String a) {
		this.a = a;
	}
	
	
	
}
