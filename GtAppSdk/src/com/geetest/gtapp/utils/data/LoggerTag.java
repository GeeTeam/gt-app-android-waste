package com.geetest.gtapp.utils.data;

/**
 * 进行Log输出时的一些标记
 * 
 * @time 2013-11-13 上午9:00:38
 * @author zheng dreamzsm@gmail.com
 */
public class LoggerTag {

	// 和Volley通讯相关的输出字符标记
	/**
	 * post请求的数据
	 */
	public static String volleyPostRequest = "Ft_VolleyPostRequest:";
	
	
	public static String volleyUrl = "Ft_VolleyUrl:";

	

	/**
	 * get放url后面的数据
	 */
	public static String volleyGetRequest = "Ft_VolleyGetRequest:";

	/**
	 * get后返回的数据
	 */
	public static String volleyGetResponse = "Ft_VolleyGetResponse:";
	
	
	/**
	 * DAO层通讯失败
	 */
	public static String volleyDaoCbError = "Vollery-DAO：errorCallbackHandle:";

}
