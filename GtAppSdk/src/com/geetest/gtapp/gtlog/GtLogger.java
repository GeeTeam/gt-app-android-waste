package com.geetest.gtapp.gtlog;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.geetest.gtapp.gtlog.vo.SdkInfo;

public abstract class GtLogger {
	private static final String COMMON_TAG = "GtAppTag";// 打的通用的TAG

	// TODO 发布时修改
	private static boolean DEBUG_STATE = false;

	// private static Context context;// 用于获取程序信息的上下文
	// private static SdkInfo sdkInfo;// SDK的版本信息

	// public static int loggerState = LoggerState.TO_LOGCAT;// log输出的状态值

	public static void setContext(Context context) {
		// TODO 发布时可以删除
		// FtSlog.setMobileInfo(context);
	}

	public static void setSdkInfo(SdkInfo sdkInfo) {
		// TODO 发布时可以删除
		// FtSlog.setNoteMsg(sdkInfo);
	}

	public static void s_v(String tag, Object msg) {

		// Log.v(tag, msg);// 本地调试状态

		// TODO 发布时可以删除
		// FtSlog.s_v(tag, msg);

	}

	/**
	 * 出现异常的时候，输出异常信息
	 * 
	 * @param msg
	 */
	public static void ex(String msg) {
		s_v("exception", msg);
	}

	public static void i(String msg) {
		if (DEBUG_STATE) {
			Log.i(COMMON_TAG, msg);
		}
	}

	/**
	 * 出现异常的时候，输出异常信息并toast显示出来
	 * 
	 * @param msg
	 */
	public static void toastExpection(Context ctx, String msg) {
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

		Log.v(tag, msg);// 本地调试状态

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
