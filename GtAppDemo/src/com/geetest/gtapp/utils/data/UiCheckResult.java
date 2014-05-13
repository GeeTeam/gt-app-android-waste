package com.geetest.gtapp.utils.data;

/**
 * 进行界面输入的完整性检查的时候的一些状态
 * @time 2013-11-13 上午10:17:02
 * @author zheng  dreamzsm@gmail.com
 */
public class UiCheckResult {

	/**
	 * 未检测态
	 */
	public static int unCheck = 0;
	
	
	/**
	 * 未通过
	 */
	public static int failed = 0;
	
	/**
	 *检查通过
	 */
	public static int pass = 1;
	
	/**
	 * 输入不完整
	 */
	public static int notComplete = 2;
	
	//TODO 后面可以扩展更多的状态
}
