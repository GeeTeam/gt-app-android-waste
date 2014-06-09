package com.geetest.gtappdemo.model.vo.gres;

/**
 * 返回的验证结果数据
 * @author Zheng
 * 2014年5月15日  下午12:42:59
 */
public class AjaxPhp_GresVo {
	
	private String success = "0";//验证结果代码：1表示成功，0表示失败。
	private String message = "d8c78be4cfadde19505b7e1c37edeea1|8";//验证的结果信息
	
	
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
}
