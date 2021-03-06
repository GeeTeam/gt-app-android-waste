package com.geetest.gtappdemo.model.vo;

import com.geetest.gtapp.gtlog.GtLogger;

/**
 * 用户在使用验证码时的交互行为
 * 
 * @author Zheng 2014年5月16日 下午2:50:15
 */
public class CaptchaUserAction {

	private int xPos = 6;// 鼠标X坐标值
	private int yPos = 4;// 鼠标Y坐标值

	// 后续过会继续扩展--比如，在GtApp上会有更多的传感器信息等等

	private int timeIncrement = 20;// 时间增量（s）

	public CaptchaUserAction() {

	}

	/**
	 * 有输入参数的构造函数
	 * 
	 * @param xPos
	 * @param yPos
	 * @param timeIncrement
	 */
	public CaptchaUserAction(int xPos, int yPos, int timeIncrement) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.timeIncrement = timeIncrement;

	}

	/**
	 * 设置成员数据
	 * @param xPos
	 * @param yPos
	 * @param timeIncrement
	 */
	public void bindMemData(int xPos, int yPos, int timeIncrement) {
		this.xPos = xPos;
		this.yPos = yPos;
		this.timeIncrement = timeIncrement;

	}

	/**
	 * 调试函数进行参数的LogCat输出
	 */
	public void v() {
		GtLogger.v("" + this.getxPos() + " " + this.getyPos() + " "
				+ this.getTimeIncrement());
	}

	public int getxPos() {
		return xPos;
	}

	public void setxPos(int xPos) {
		this.xPos = xPos;
	}

	public int getyPos() {
		return yPos;
	}

	public void setyPos(int yPos) {
		this.yPos = yPos;
	}

	public int getTimeIncrement() {
		return timeIncrement;
	}

	public void setTimeIncrement(int timeIncrement) {
		this.timeIncrement = timeIncrement;
	}

}
