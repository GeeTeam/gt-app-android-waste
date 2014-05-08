package com.geetest.gtapp.utils;

import android.util.Log;


/**
 * @author Administrator
 * 用于输出内容
 */
public class GtLogger {

	private final static String TAG = "gtApp";
	
	public static void v(String msg)
	{
		Log.v(TAG,msg);
	}
	
}
