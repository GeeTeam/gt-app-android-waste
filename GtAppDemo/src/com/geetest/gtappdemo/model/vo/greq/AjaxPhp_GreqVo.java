package com.geetest.gtappdemo.model.vo.greq;

/**
 * 上传的用户行为数据
 * 
 * @author Zheng
 * 
 */
public class AjaxPhp_GreqVo {

	private String api = "jordan";// 调用的函数名称:
	private String challenge = "0accdbb7cda7c8a11f182cd28f6c2c245v";// 验证行为的一个流水号:
	private String userresponse = "22b222dd48";// 编码后用户答案（仅仅只是在视觉上混淆）:
	private int passtime = 2702;// 客户端交互使用的时间（不是从服务器端传回）:
	private int imgload = 117;// 背景图的加载时间:
	private String a = "s$$$o9%27A:?;:J::::::J::::JJ::::J:J:K:J:JI:::J:J9$$$%27!N(N*A*42+73+7060.89-.77!P(G06!U(777JoJ/!E(!r(Kn!Q)nJ!t6";// 经过编码后的行为数据。:

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

	public float getPasstime() {
		return passtime;
	}

	public int getImgload() {
		return imgload;
	}

	public void setImgload(int imgload) {
		this.imgload = imgload;
	}

	public void setPasstime(int passtime) {
		this.passtime = passtime;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

}
