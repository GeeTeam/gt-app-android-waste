package com.geetest.gtapp.slogger;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.geetest.gtapp.slogger.vo.MobileInfo;
import com.geetest.gtappdemo.model.svo.HostInfo;

/**
 * 运行的环境--全局变量，一次读取，终身受用
 * 
 * @author Zheng
 * @time 2014年7月12日 上午12:29:33
 */
public class GtRunEnv {

	private static MobileInfo mobileInfo = new MobileInfo();// 手机的静态固件信息
	private static HostInfo hostInfo = new HostInfo();// 宿主程序的信息

	public static MobileInfo getMobileInfo() {
		return mobileInfo;
	}

	// public static void setMobileInfo(MobileInfo mobileInfo) {
	// GtRunEnv.mobileInfo = mobileInfo;
	// }

	public static HostInfo getHostInfo() {
		return hostInfo;
	}

	public static void setHostInfo(HostInfo hostInfo) {
		GtRunEnv.hostInfo = hostInfo;
	}

	/**
	 * 获取手机信息
	 * 
	 * @time 2014年7月12日 上午12:33:42
	 * @param context
	 */
	public static void setMobileInfo(Context context) {
		@SuppressWarnings("static-access")
		TelephonyManager mTm = (TelephonyManager) context
				.getSystemService(context.TELEPHONY_SERVICE);

		mobileInfo.setImei(mTm.getDeviceId());
		mobileInfo.setImsi(mTm.getSubscriberId());

		mobileInfo.setNumer(mTm.getLine1Number());
		mobileInfo.setNetWorkType(mTm.getNetworkType());

		mobileInfo.setMtyb(android.os.Build.MODEL);
		mobileInfo.setMtyb(android.os.Build.BRAND);
		mobileInfo.setBuildVersionSdk(android.os.Build.VERSION.SDK);
		mobileInfo.setBuildVersionRelease(android.os.Build.VERSION.RELEASE);
		mobileInfo.setBuildVersionSdkInt(android.os.Build.VERSION.SDK_INT);

		// Gson gson = new Gson();
		// String strMsg = gson.toJson(mobileInfo);
		// GtLogger.s_v(context, LogMsgTag.mobileInfo, strMsg);
		// mobileInfo.v();
	}

}
