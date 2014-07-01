package com.geetest.gtappdemo.model.svo;

/**
 * 界面元素尺寸信息
 * 
 * @author Zheng
 * @time 2014年7月1日 下午9:03:57
 */
public class UiElementSize {

	// private GtShapeSize spSkb_dragCaptcha;// 滑块的高度信息

	private String skb_dragCaptcha_height;// 滑块高度
	private String skb_dragCaptcha_position;// 滑块相对安卓坐标系统的位置
	private String sliderStartLeftTopPosition;// 滑块左上角位置

	public String getSliderStartLeftTopPosition() {
		return sliderStartLeftTopPosition;
	}

	public void setSliderStartLeftTopPosition(String sliderStartLeftTopPosition) {
		this.sliderStartLeftTopPosition = sliderStartLeftTopPosition;
	}

	public String getSkb_dragCaptcha_height() {
		return skb_dragCaptcha_height;
	}

	public void setSkb_dragCaptcha_height(String skb_dragCaptcha_height) {
		this.skb_dragCaptcha_height = skb_dragCaptcha_height;
	}

	public String getSkb_dragCaptcha_position() {
		return skb_dragCaptcha_position;
	}

	public void setSkb_dragCaptcha_position(String skb_dragCaptcha_position) {
		this.skb_dragCaptcha_position = skb_dragCaptcha_position;
	}

	// public GtShapeSize getSpSkb_dragCaptcha() {
	// return spSkb_dragCaptcha;
	// }
	//
	// public void setSpSkb_dragCaptcha(GtShapeSize spSkb_dragCaptcha) {
	// this.spSkb_dragCaptcha = spSkb_dragCaptcha;
	// }

}
