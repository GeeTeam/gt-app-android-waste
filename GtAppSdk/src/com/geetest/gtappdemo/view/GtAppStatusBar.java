package com.geetest.gtappdemo.view;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * GtApp的状态栏
 * 
 * @author Zheng
 * @time 2014年6月11日 下午12:34:59
 */
public class GtAppStatusBar {

	private ImageView imgv_captcha_status_icon;// 状态锁
	private TextView tv_validateStatus;// 验证码的状态栏
	private TextView tv_validateMsg;// 验证码的消息栏

	public GtAppStatusBar() {

	}

	/**
	 * 构造函数
	 * @param imgv_captcha_status_icon
	 * @param tv_validateStatus
	 * @param tv_validateMsg
	 */
	public GtAppStatusBar(ImageView imgv_captcha_status_icon,// 状态锁
			TextView tv_validateStatus,// 验证码的状态栏
			TextView tv_validateMsg// 验证码的消息栏
	) {
		this.imgv_captcha_status_icon = imgv_captcha_status_icon;
		this.tv_validateStatus = tv_validateStatus;
		this.tv_validateMsg = tv_validateMsg;
	}
	
	
	
	/**
	 * 验证成功的状态
	 * @time 2014年6月11日  下午12:39:33
	 */
	public void SetToSucceedStatus()
	{
		//TODO 设置不同的状态值
	}
	
	
	public void SetToTryTooMuchStatus()
	{
		
	}
	
	
	public void SetToFailedStatus()
	{
		
	}
	
	public void SetToWaitStatus()
	{
		
	}
	
	public void SetToExceptionStatus()
	{
		
	}
	

}
