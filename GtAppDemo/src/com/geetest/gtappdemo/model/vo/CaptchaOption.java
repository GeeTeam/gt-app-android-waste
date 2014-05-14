package com.geetest.gtappdemo.model.vo;

/**
 * 验证码初始化的Option对象
 * 
 * @author Zheng
 * 
 */
public class CaptchaOption {

	String product;// 产品类型: "embed",
	String fullbg;// 完整的图片地址: "pictures/gt/d57238b1/d57238b1.jpg",
	String staticserver;// 静态的图片服务器: "http://geetest-jordan2.b0.upaiyun.com/",
	String challenge;// 验证行为的流水号ID: "9ca39487a81073e1e50c23ebe7a0208c96",
	int ypos;// ？: 16,
	String apiserver;// GT的服务器路径: "http://api.geetest.com/",
	String sliceurl;// 切出图: "pictures/gt/d57238b1/slice/0d5fa7a5.png",
	String theme;// 主题: "default",
	String version;// 版本: "2.5.12",
	String link;// ？: "",
	String imgserver;// 图片服务器: "http://geetest-jordan2.b0.upaiyun.com/",
	String https;// ？: false,
	String geetestid;// ？: "a40fd3b0d712165c5d13e6f747e948d4",
	String id;// ？: "a9ca39487a81073e1e50c23ebe7a0208c",
	String imgurl;// ？: "pictures/gt/d57238b1/bg/0d5fa7a5.jpg"

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getFullbg() {
		return fullbg;
	}

	public void setFullbg(String fullbg) {
		this.fullbg = fullbg;
	}

	public String getStaticserver() {
		return staticserver;
	}

	public void setStaticserver(String staticserver) {
		this.staticserver = staticserver;
	}

	public String getChallenge() {
		return challenge;
	}

	public void setChallenge(String challenge) {
		this.challenge = challenge;
	}

	public int getYpos() {
		return ypos;
	}

	public void setYpos(int ypos) {
		this.ypos = ypos;
	}

	public String getApiserver() {
		return apiserver;
	}

	public void setApiserver(String apiserver) {
		this.apiserver = apiserver;
	}

	public String getSliceurl() {
		return sliceurl;
	}

	public void setSliceurl(String sliceurl) {
		this.sliceurl = sliceurl;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getImgserver() {
		return imgserver;
	}

	public void setImgserver(String imgserver) {
		this.imgserver = imgserver;
	}

	public String getHttps() {
		return https;
	}

	public void setHttps(String https) {
		this.https = https;
	}

	public String getGeetestid() {
		return geetestid;
	}

	public void setGeetestid(String geetestid) {
		this.geetestid = geetestid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getImgurl() {
		return imgurl;
	}

	public void setImgurl(String imgurl) {
		this.imgurl = imgurl;
	}

}
