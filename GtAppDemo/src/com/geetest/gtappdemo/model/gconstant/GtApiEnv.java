package com.geetest.gtappdemo.model.gconstant;

import com.geetest.gtapp.logger.GtLogger;
import com.geetest.gtapp.utils.LoggerString;

/**
 * 极验的API的环境变量
 * 
 * @author Zheng
 * 
 */
public class GtApiEnv {

	public static int slice_img_width = 48;// 切片图宽
	public static int slice_img_height = 64;// 切片图高

	public static int bg_img_width = 260;// 背图宽
	public static int bg_img_height = 90;// 背图高

	// 极验的服务器端API
	public static String httpType = "http";// 通讯方式
	public static String gtApiBaseUrl = "api.geetest.com";// 基本路径
	public static int gtApiPort = 80;// 端口

	// 又拍云 的根路径
	public static String imgServerBase = "http://geetest-jordan2.b0.upaiyun.com/";

	
	//提交验证的服务器的API名称
	public static String ajaxApiName = "jordan";
	
	
	//验证码的版本类型
	public static String captChaType = "jordan";
	
	
	// // Gt的地址
	// public static String gtApiBase = "http://api.geetest.com/";

	/**
	 * @param apiRelativePath
	 *            API的相对路径
	 * @return
	 */
	public static String GetGtApiUrl(String apiRelativePath) {
		try {

		} catch (Exception e) {
			GtLogger.expection(LoggerString.getFileLineMethod()
					+ e.getMessage());
		}

		return null;
	}

}
