package com.geetest.gtappdemo.model.svo;

/**
 * 图片加载的相关信息
 * 
 * @author Zheng
 * @time 2014年6月30日 下午12:02:23
 */
public class ImageLoadTimeCycle {
	
	private long bg_img_cycle; // 背景图加载时间（完毕时间-开始时间）（ms）
	private long slice_bg_img_cycle;// 切图背景加载时间（ms）
	private long slice_img_cycle;// 切图加载时间（ms）

	

	public long getBg_img_cycle() {
		return bg_img_cycle;
	}

	public void setBg_img_cycle(long bg_img_cycle) {
		this.bg_img_cycle = bg_img_cycle;
	}

	public long getSlice_bg_img_cycle() {
		return slice_bg_img_cycle;
	}

	public void setSlice_bg_img_cycle(long slice_bg_img_cycle) {
		this.slice_bg_img_cycle = slice_bg_img_cycle;
	}



	public long getSlice_img_cycle() {
		return slice_img_cycle;
	}



	public void setSlice_img_cycle(long slice_img_cycle) {
		this.slice_img_cycle = slice_img_cycle;
	}



	public void setSlice_img_cycle(int slice_img_cycle) {
		this.slice_img_cycle = slice_img_cycle;
	}

}
