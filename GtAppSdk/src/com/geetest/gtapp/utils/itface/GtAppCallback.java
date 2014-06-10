package com.geetest.gtapp.utils.itface;

import com.geetest.gtappdemo.model.vo.GtAppCbCaptchaResponse;

/**
 * GtApp的回调函数
 * 
 * @author Zheng
 * 
 */
public interface GtAppCallback {

	/**
	 * 验证的返回值
	 */
	public void gtAppResponse(GtAppCbCaptchaResponse cbResponse);

}
