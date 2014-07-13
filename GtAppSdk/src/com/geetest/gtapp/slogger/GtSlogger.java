package com.geetest.gtapp.slogger;

import com.geetest.gtapp.logger.GtLogger;
import com.geetest.gtapp.logger.vo.ServerDebugMsg;
import com.geetest.gtapp.slogger.vo.MobileInfo;
import com.geetest.gtappdemo.model.gconstant.GtApiEnv;
import com.geetest.gtappdemo.model.svo.HostInfo;
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
	private MobileInfo mobileInfo = new MobileInfo();// 手机的静态固件信息
	private HostInfo hostInfo = new HostInfo();// 宿主程序的信息

	// private String loggerServerApi = "http://192.168.2.66:80/local_log_msg/";
	private final String loggerServerApi = "http://192.168.1.100:80/local_log_msg/";

	// private final String loggerServerApi =
	// "http://logmsg.duapp.com/debug_msg/";// 公网的服务
	private final String osType = "android";

	public GtSlogger() {
		this.hostInfo = GtRunEnv.getHostInfo();
		this.mobileInfo = GtRunEnv.getMobileInfo();
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
		final String strMsg = gson.toJson(debugMsg);

		new Thread(new Runnable() {
			@Override
			public void run() {

				try {
					HttpInvoker.readContentFromPost(loggerServerApi, strMsg);
				} catch (Exception e) {
					GtLogger.e(e.getMessage());
				}
			}
		}).start();

	}

}
