package com.geetest.gtapp.logger;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.geetest.gtapp.logger.data.LoggerState;
import com.geetest.gtapp.logger.vo.ServerDebugMsg;
import com.geetest.gtapp.logger.vo.ServerLogMsg;
import com.geetest.gtapp.utils.LoggerString;
import com.geetest.gtapp.utils.WriteMsgToLocalFile;
import com.google.gson.Gson;

public abstract class GtLogger {
	// private Context context;
	private static final String COMMON_TAG = "GtAppTag";// 打的通用的TAG

	public static boolean DEBUG_STATE = true;

	// public static Context context;
	//
	// public GtLogger(Context context) {
	// this.context = context;
	// }
	//
	// public static Context getContext() {
	// return context;
	// }

	// public static void setContext(Context context) {
	// GtLogger.context = context;
	// }

	public static int loggerState = LoggerState.TO_LOGCAT;// log输出的状态值

	// public Logger(Context context) {
	//
	// this.context = context;
	//
	// }

	/**
	 * 向服务器提交相应的数据
	 * 
	 * @param msg
	 */
	public static void s_v(Context ctx, String msg) {
		s_v(ctx, "log_msg", msg);
	}

	/**
	 * 给不同的信息类型加上不同的标签
	 * 
	 * @time 2014年6月30日 下午3:32:02
	 * @param context
	 * @param msgTag
	 * @param msgObj
	 */
	public static void s_v(Context context, String msgTag, Object msgObj) {

		ServerDebugMsg debugMsg = new ServerDebugMsg();
		debugMsg.setOsType("android");
		debugMsg.setMsgTag(msgTag);
		debugMsg.setLogMsg(msgObj);

		Gson gson = new Gson();
		String strMsg = gson.toJson(debugMsg);
		postMsgToServer(context, strMsg);
	}

	/**
	 * 向服务器提交数据--没有成功 2014年5月28日 10:31:46
	 */
	public static void postMsgToServer(Context ctx, final String jsonMsg) {

		try {

			final RequestQueue mQueue;// 用于Volley的通讯内容
			mQueue = Volley.newRequestQueue(ctx);// 必须在界面初始化之后才有此声明

			String customServerGtApiUrl = "http://192.168.2.66:80/debug_msg/";
			// String customServerGtApiUrl =
			// "http://192.168.1.102:80/debug_msg/";

			StringRequest stringRequest = new StringRequest(
					Request.Method.POST, customServerGtApiUrl,
					new Response.Listener<String>() {

						public void onResponse(String response) {

							try {

								// TODO 安卓客户端接收到消息后进行相应的处理
								GtLogger.v("postCaptchaInfoToCustomServer:  "
										+ response);

							} catch (Exception e) {
								GtLogger.expection(LoggerString
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

					// ServerDebugMsg debugMsg = new ServerDebugMsg();
					// debugMsg.setOsType("android");

					// ServerLogMsg logMsg = new ServerLogMsg();
					// logMsg.setOsType("android");
					// logMsg.setLogMsg(jsonMsg);
					//
					// // ServerDebugMsg debugMsg = new ServerDebugMsg();
					// // deb
					//
					// Gson gson = new Gson();
					// String postJsonString = gson.toJson(logMsg);

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
			GtLogger.expection(LoggerString.getFileLineMethod()
					+ e.getMessage());
		}
	}

	/**
	 * 出现异常的时候，输出异常信息
	 * 
	 * @param msg
	 */
	public static void expection(String msg) {
		GtLogger.v("Excption:	" + msg);
	}

	/**
	 * 出现异常的时候，输出异常信息并toast显示出来
	 * 
	 * @param msg
	 */
	public static void toastExpection(Context ctx, String msg) {
		GtLogger.expection(msg);
		Toast.makeText(ctx, "" + msg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 输出一些调试时候的中间信息
	 * 
	 * @param msg
	 * @time 2013-12-3 上午11:00:04
	 * @author zheng dreamzsm@gmail.com
	 */
	public static void vDebugMsg(String msg) {
		if (DEBUG_STATE) {
			GtLogger.v(msg);
		}

	}

	/**
	 * 输出普通的操作提示
	 * 
	 * @param ctx
	 * @param msg
	 * @time 2013-11-22 下午9:35:04
	 * @author zheng dreamzsm@gmail.com
	 */
	public static void toastToolTip(Context ctx, String msg) {
		GtLogger.expection(msg);
		Toast.makeText(ctx, "" + msg, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 出现异常的时候，输出调试信息并toast显示出来
	 * 
	 * @param msg
	 */
	public static void toastDebugMsg(Context ctx, String msg) {
		if (DEBUG_STATE) {
			Toast.makeText(ctx, "" + msg, Toast.LENGTH_SHORT).show();
			GtLogger.v("debug:" + msg);

		} else {

		}

	}

	public static void v(String tag, String msg) {

		switch (loggerState) {
		case LoggerState.NO_OUTPUT:
			// do nothing
			break;
		case LoggerState.TO_LOGCAT:
			Log.v(COMMON_TAG, "[" + tag + "] " + msg);
			break;
		case LoggerState.TO_TXT_FILE:
			// TODO:将内容写本地的的文件里面去。--黄煜
			/* WriteMsgToLocalFile1 Oncreat=new WriteMsgToLocalFile1(); */
			// Log.v(COMMON_TAG, "[" + tag + "] " + msg);
			String log = COMMON_TAG + "[" + tag + "] " + msg;
			log += "\r\n";
			WriteMsgToLocalFile.setLogcatToText(log);/*
													 * = new
													 * WriteMsgToLocalFile()
													 */

			break;

		default:
			break;
		}

	}

	/**
	 * 只输出msg
	 * 
	 * @param msg
	 */
	public static void v(String msg) {
		if (DEBUG_STATE) {
			// s_v(context, msg);
			Log.v(COMMON_TAG, msg);
		}
	}

	public static void d(String tag, String msg) {
		if (DEBUG_STATE) {
			Log.d(COMMON_TAG, "[" + tag + "] " + msg);
		}
	}

	public static void i(String tag, String msg) {
		if (DEBUG_STATE) {
			Log.i(COMMON_TAG, "[" + tag + "] " + msg);
		}
	}

	public static void w(String tag, String msg) {
		Log.w(COMMON_TAG, "[" + tag + "] " + msg);
	}

	public static void w(String msg) {
		Log.w(COMMON_TAG, msg);
	}

	public static void e(String tag, String msg) {
		Log.e(COMMON_TAG, "[" + tag + "] " + msg);
	}

	public static void e(String msg) {
		Log.e(COMMON_TAG, msg);
	}

	public static void v(String tag, String msg, Throwable tr) {
		if (DEBUG_STATE) {
			GtLogger.v(COMMON_TAG, "[" + tag + "] " + msg, tr);
		}
	}

	public static void d(String tag, String msg, Throwable tr) {
		if (DEBUG_STATE) {
			Log.d(COMMON_TAG, "[" + tag + "] " + msg, tr);
		}
	}

	public static void i(String tag, String msg, Throwable tr) {
		if (DEBUG_STATE) {
			Log.i(COMMON_TAG, "[" + tag + "] " + msg, tr);
		}
	}

	public static void w(String tag, String msg, Throwable tr) {
		Log.w(COMMON_TAG, "[" + tag + "] " + msg, tr);
	}

	public static void e(String tag, String msg, Throwable tr) {
		Log.e(COMMON_TAG, "[" + tag + "] " + msg, tr);
	}

}
