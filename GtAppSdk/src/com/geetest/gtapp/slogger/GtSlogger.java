package com.geetest.gtapp.slogger;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.telephony.TelephonyManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.geetest.gtapp.logger.GtLogger;
import com.geetest.gtapp.logger.vo.ServerDebugMsg;
import com.geetest.gtapp.utils.LoggerString;
import com.geetest.gtappdemo.model.gconstant.GtApiEnv;
import com.geetest.gtappdemo.model.svo.HostInfo;
import com.geetest.gtappdemo.model.svo.MobileInfo;
import com.google.gson.Gson;

/**
 * 向服务器提交日志的一个框架体系
 * 
 * @author Zheng
 * @time 2014年7月1日 下午12:57:29
 */
public class GtSlogger {

	/**
	 * 要向服务器提交
	 */
	private Context context;
	private MobileInfo mobileInfo = new MobileInfo();// 手机的静态固件信息
	private HostInfo hostInfo = new HostInfo();// 宿主程序的信息

	// private String loggerServerApi = "http://192.168.2.66:80/debug_msg/";
	private final String loggerServerApi = "http://192.168.1.102:80/debug_msg/";
	private final String osType = "android";

	public GtSlogger() {

	}

	public GtSlogger(Context context, HostInfo hostInfo) {
		this.context = context;
		this.hostInfo = hostInfo;
		setMobileInfo();
	}

	/**
	 * 获取IMEI号，IESI号，手机型号
	 */
	private void setMobileInfo() {
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

	public void v(Object msgObj) {
		if (GtApiEnv.DEBUG_STATE) {
			s_v("verbose", msgObj);
		}
	}

	public void i(Object msgObj) {
		s_v("info", msgObj);
	}

	public void e(Object msgObj) {
		s_v("error", msgObj);
	}

	/**
	 * 报告异常信息
	 * 
	 * @time 2014年7月1日 下午8:50:05
	 */
	public void ex(Object msgObj) {
		s_v("exception", msgObj);
		GtLogger.exception(msgObj.toString());
	}

	/**
	 * 在发布的 时候，不输出这些中间信息
	 * 
	 * @time 2014年7月2日 上午10:25:09
	 * @param msgObj
	 */
	public void d(Object msgObj) {

		if (GtApiEnv.DEBUG_STATE) {
			s_v("debug", msgObj);
		}
	}

	public void w(Object msgObj) {
		s_v("warn", msgObj);
	}

	/**
	 * 给不同的信息类型加上不同的标签
	 * 
	 * @time 2014年6月30日 下午3:32:02
	 * @param context
	 * @param msgTag
	 * @param msgObj
	 */
	public void s_v(String msgTag, Object msgObj) {

		ServerDebugMsg debugMsg = new ServerDebugMsg();
		debugMsg.setOsType(osType);
		debugMsg.setOsDetail(mobileInfo);
		debugMsg.setHostInfo(hostInfo);
		debugMsg.setMsgTag(msgTag);
		debugMsg.setLogMsg(msgObj);

		Gson gson = new Gson();
		String strMsg = gson.toJson(debugMsg);
		postMsgToServer(context, strMsg);
	}

	/**
	 * 向服务器提交数据--2014年5月28日 10:31:46
	 */
	private void postMsgToServer(Context ctx, final String jsonMsg) {

		try {

			final RequestQueue mQueue;// 用于Volley的通讯内容
			mQueue = Volley.newRequestQueue(ctx);// 必须在界面初始化之后才有此声明

			String customServerGtApiUrl = loggerServerApi;

			StringRequest stringRequest = new StringRequest(
					Request.Method.POST, customServerGtApiUrl,
					new Response.Listener<String>() {

						public void onResponse(String response) {

							try {
								// 安卓客户端接收到消息后进行相应的处理
								GtLogger.v("gtLoggerResponse: " + response);
							} catch (Exception e) {
								GtLogger.exception(LoggerString
										.getFileLineMethod() + e.getMessage());
							}

						}
					}, new Response.ErrorListener() {

						public void onErrorResponse(VolleyError arg0) {

							GtLogger.v(LoggerString.getFileLineMethod()
									+ arg0.getMessage());
						}
					}) {

				@Override
				protected Map<String, String> getParams() {
					Map<String, String> params = new HashMap<String, String>();

					// 将客户端的信息编码成一个Json串，然后上传到客户服务器
					params.put("debug_msg", jsonMsg);

					return params;
				}

			};

			// 设置请求超时时间5s：http://blog.csdn.net/xyz_lmn/article/details/12177005
			stringRequest.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 1,
					1.0f));
			mQueue.add(stringRequest);

		} catch (Exception e) {
			GtLogger.exception(LoggerString.getFileLineMethod()
					+ e.getMessage());
		}
	}

}
