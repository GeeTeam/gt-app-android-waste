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

	// TODO 公钥和私钥 都是需要SDK外部初始化
	public static String gt_public_key = "a40fd3b0d712165c5d13e6f747e948d4";// 公钥
	public static String gt_private_key = "";// 私钥

	public static String gt_product_type = "embed";// 嵌入式的

	public static int slice_img_width = 48;// 切片图宽
	public static int slice_img_height = 64;// 切片图高

	public static int bg_img_width = 260;// 背图宽
	public static int bg_img_height = 90;// 背图高

	// 极验的服务器端API
	public static String httpType = "http";// 通讯方式
	public static String gtApiBaseUrl = "api.geetest.com";// 基本路径--极验的服务器
	// public static String gtApiBaseUrl = "192.168.1.5";// 黄胜蓝的测试服务器
	public static int gtApiPort = 80;// 端口

	// GtApp主要使用的Api的服务器端
	public static final String getOptionApi = "/get.php";
	public static final String ajaxSubmitApi = "/ajax.php";

	// 又拍云 的根路径
	public static String imgServerBase = "http://geetest-jordan2.b0.upaiyun.com/";

	// Gt提交验证的服务器的API名称
	public static String ajaxApiName = "jordan";

	// 验证码的版本类型--在向客户服务器提交时使用
	public static String captChaType = "jordan";

	// // Gt的地址
	// public static String gtApiBase = "http://api.geetest.com/";

	/**
	 * 用于输出调试信息的服务器API
	 */
	public static String debugServerApi = "http://192.168.2.66:80/debug_msg/";;

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
