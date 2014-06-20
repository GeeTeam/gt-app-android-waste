package com.geetest.gtappdemo.view;

import android.widget.ImageView;
import android.widget.TextView;

import com.geetest.gtapp.R;

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
	 * 
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
	 * 
	 * @time 2014年6月11日 下午12:39:33
	 */
	public void setToSucceedStatus(String succeedTip) {
		// TODO 设置不同的状态值
		imgv_captcha_status_icon
				.setImageResource(R.drawable.gtapp_status_succeed);
		tv_validateStatus.setText("验证完成：");
		tv_validateStatus.setTextColor(0xff87C35D);// android.graphics.Color.GREEN
		tv_validateMsg.setText(succeedTip);
	}

	/**
	 * 尝试过多
	 * 
	 * @time 2014年6月11日 下午5:50:33
	 */
	public void setToTryTooMuchStatus() {

		imgv_captcha_status_icon
				.setImageResource(R.drawable.gtapp_status_too_much);
		tv_validateStatus.setText("尝试过多：");
		tv_validateStatus.setTextColor(0xffFE9866);// android.graphics.Color.GREEN
		tv_validateMsg.setText("系统正在自动更新刷新验证图片……");
	}

	/**
	 * 验证错误
	 * 
	 * @time 2014年6月11日 下午5:57:10
	 */
	public void setToFailedStatus() {

		imgv_captcha_status_icon
				.setImageResource(R.drawable.gtapp_status_failed);
		tv_validateStatus.setText("验证错误：");
		tv_validateStatus.setTextColor(0xffE54C01);// android.graphics.Color.GREEN
		tv_validateMsg.setText("拖动滑块使悬浮图像正确拼合");
	}

	/**
	 * 等待验证TODO
	 * 
	 * @time 2014年6月11日 下午5:57:01
	 */
	public void setToWaitStatus() {
		imgv_captcha_status_icon.setImageResource(R.drawable.gtapp_status_wait);
		tv_validateStatus.setText("等待验证：");
		tv_validateStatus.setTextColor(0xffF2BD5F);
		tv_validateMsg.setText("拖动滑块使悬浮图像正确拼合Xaceddgqwer");
	}

	/**
	 * 行为异常
	 * 
	 * @time 2014年6月11日 下午5:56:52
	 */
	public void setToExceptionStatus() {

		imgv_captcha_status_icon
				.setImageResource(R.drawable.gtapp_status_exception);
		tv_validateStatus.setText("行为异常：");
		tv_validateStatus.setTextColor(0xff04E7BA);
		tv_validateMsg.setText("你是来自星星的都敏俊吧？");
	}

}
