package com.geetest.gtappdemo.model.vo;

import com.geetest.gtapp.gtlog.GtLogger;

/**
 * 形状尺寸
 * 
 * @author Zheng 2014年5月28日 上午11:47:53
 */
/**
 * @author Zheng 2014年5月28日 下午5:54:46
 */
public class GtShapeSize {

	private int width = 0;
	private int height = 0;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void v() {
		this.v("");
	}

	/**
	 * 带描述性的输出
	 * 
	 * @param pointTag
	 */
	public void v(String pointTag) {
		GtLogger.v(pointTag + " Width: " + this.width + " Height: "
				+ this.height);
	}

}
