package com.geetest.gtappdemo.model.svo;

import android.content.Context;

/**
 * SDK运行信息
 * 
 * @author Zheng
 * @time 2014年6月30日 下午6:57:11
 */
public class SdkRunInfo {

	private ImageLoadTimeCycle timeCycle = new ImageLoadTimeCycle();
	private MobileInfo mobileInfo = new MobileInfo();

	private Context context;

	public SdkRunInfo() {
		// this.context = context;
	}

	public SdkRunInfo(Context context) {
		this.context = context;
	}

	public ImageLoadTimeCycle getTimeCycle() {
		return timeCycle;
	}

	public void setTimeCycle(ImageLoadTimeCycle timeCycle) {
		this.timeCycle = timeCycle;
	}

	public MobileInfo getMobileInfo() {
		return mobileInfo;
	}

	public void setMobileInfo(MobileInfo mobileInfo) {
		this.mobileInfo = mobileInfo;
	}

	/**
	 * 将信息发送到服务器
	 * 
	 * @time 2014年6月30日 下午7:01:02
	 */
	// public void s_v() {
	//
	// Gson gson = new Gson();
	// String strMsg = gson.toJson(mobileInfo);
	// GtLogger.s_v(context, LogMsgTag.mobileInfo, msgObj);
	//
	// }
}
