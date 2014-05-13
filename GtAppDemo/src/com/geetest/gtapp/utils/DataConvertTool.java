package com.geetest.gtapp.utils;

/**
 * @author zheng 2 013年11月1日 15:19:26 数据格式转换
 */
public class DataConvertTool {

	private static final String bTrueViewName = "是";// true的界面显示名称
	private static final String bFalseViewName = "否";
	private static final String bUnkonwViewName = "未知";

	/**
	 * 将bool的String：true,false转换成可显示的中文:是、否、未知
	 * 
	 * @param strBoolValue
	 * @return 显示的中文
	 */
	public static String convertBoolStringToViewName(String strBoolValue) {
		String strViewName = bUnkonwViewName;

		if (strBoolValue == "true") {
			strViewName = bTrueViewName;
		} else if (strBoolValue == "false") {
			strViewName = bFalseViewName;
		} else {
			strViewName = bUnkonwViewName;
		}

		return strViewName;
	}
	
	
	
	/**
	 * 主要防止出现时间null的时候，在界面也显示成null，所以需要做一次转换:2013年11月1日 15:39:29
	 * @param timeValueString
	 * @return
	 */
	public static String convertTimeStringToViewName(String timeValueString)
	{
		String strViewName = bUnkonwViewName;

		if (timeValueString != "null") {
			strViewName = timeValueString;
		} else if (timeValueString == "null") {
			strViewName = bUnkonwViewName;
		} else {
			strViewName = bUnkonwViewName;
		}

		return strViewName;
	}
	
}
