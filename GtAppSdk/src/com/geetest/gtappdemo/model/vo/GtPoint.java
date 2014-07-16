package com.geetest.gtappdemo.model.vo;

import com.geetest.gtapp.gtlog.GtLogger;

/**
 * 坐标系统中的点对象
 * 
 * @author Zheng 2014年5月28日 上午11:43:15
 */
public class GtPoint {
	private float x = 0;// x坐标
	private float y = 0;// y坐标

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void v(String msg) {
		GtLogger.v(msg + " X: " + x + " Y: " + y);
	}

}
