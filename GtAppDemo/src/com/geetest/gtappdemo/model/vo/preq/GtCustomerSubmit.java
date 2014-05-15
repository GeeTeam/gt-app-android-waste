package com.geetest.gtappdemo.model.vo.preq;

/**
 * 向客户服务器发起 的提交请求
 * 
 * @author Zheng 2014年5月15日 下午7:24:24
 */
public class GtCustomerSubmit {

	private String geetest_challenge = "ad7ba4518b5ed270268bf736c723f935cz";// 验证行为的一个流水号,之前下载过来的。
	private String geetest_validate = "30f58f0de5faa14dc78ffe6c067969fc";// Success之后的那个成功码
	private String geetest_seccode = "30f58f0de5faa14dc78ffe6c067969fc|jordan";// geetest_validate成功码
																				// |
																				// 两套验证码标识符号（和origon做区分）

	public String getGeetest_challenge() {
		return geetest_challenge;
	}

	public void setGeetest_challenge(String geetest_challenge) {
		this.geetest_challenge = geetest_challenge;
	}

	public String getGeetest_validate() {
		return geetest_validate;
	}

	public void setGeetest_validate(String geetest_validate) {
		this.geetest_validate = geetest_validate;
	}

	public String getGeetest_seccode() {
		return geetest_seccode;
	}

	public void setGeetest_seccode(String geetest_seccode) {
		this.geetest_seccode = geetest_seccode;
	}

}
