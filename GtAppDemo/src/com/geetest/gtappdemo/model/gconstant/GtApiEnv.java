package com.geetest.gtappdemo.model.gconstant;

import com.geetest.gtapp.logger.GtLogger;
import com.geetest.gtapp.utils.LoggerString;


/**
 * 极验的API的环境变量
 * @author Zheng
 *
 */
public class GtApiEnv {

	//极验的服务器端API
	public static String httpType = "http://";//通讯方式
	public static String gtServerApiBaseUrl = httpType + "api.geetest.com/";//基本路径
	
	//又拍云 的根路径
	public static String  imgServerBase = "http://geetest-jordan2.b0.upaiyun.com/";
	

	/**
	 * @param apiRelativePath API的相对路径
	 * @return
	 */
	public static String GetGtApiUrl(String apiRelativePath)
	{
		try {
			
			
			
		} catch (Exception e) {
			GtLogger.expection(LoggerString.getFileLineMethod() + e.getMessage());
		}
		
		return null;
	}
	
}
