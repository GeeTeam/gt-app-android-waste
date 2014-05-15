package com.geetest.gtappdemo.model.vo.greq;

/**
 * 内容初始化的请求参数
 * @author Zheng
 *
 */
public class GetPhp_GreqVo {

	public String gt;//验证的“公钥”
	public String product;//验证码产品的类型
	
	
	public String getGt() {
		return gt;
	}
	public void setGt(String gt) {
		this.gt = gt;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	
	
}
