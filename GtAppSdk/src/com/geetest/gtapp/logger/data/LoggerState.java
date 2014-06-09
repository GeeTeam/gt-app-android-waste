package com.geetest.gtapp.logger.data;

/**
 * @author zheng 设置Logger的状态值，然后根据不同的状态值来在不同的地方进行输出。
 */
public class LoggerState {

	/**
	 * 不做任何输出
	 */
	public static final int NO_OUTPUT = 0;

	/**
	 * 将所有的中间过程输出到logCat中，这种在开发模式可以看到。
	 */
	public static final int TO_LOGCAT = 1;

	/**
	 * 输入到文本文件中
	 */
	public static final int TO_TXT_FILE = 2;

}
