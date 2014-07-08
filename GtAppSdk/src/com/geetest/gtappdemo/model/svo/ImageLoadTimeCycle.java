package com.geetest.gtappdemo.model.svo;

/**
 * 图片加载的相关信息
 * 
 * @author Zheng
 * @time 2014年6月30日 下午12:02:23
 */
public class ImageLoadTimeCycle {

	// // 以打开对话框时作为零点时间
	// private long dlg_show_cycle;// 对话框显示的时刻点
	// private long bg_img_start_cycle;
	// private long slice_img_start_cycle;
	// private long slice_bg_img_start_cycle;

	// 下面是从图片加载的时候开始算的时间

	private long option_cycle;// 初始化请求的周期
	private long post_action_cycle;// 上传行为数据的周期
	private long bg_img_cycle; // 背景图加载时间（完毕时间-开始时间）（ms）
	private long slice_bg_img_cycle;// 切图背景加载时间（ms）
	private long slice_img_cycle;// 切图加载时间（ms）
	private long total_abs_img_cycle;// 三张图片载的总时间

	// public long getBg_img_start_cycle() {
	// return bg_img_start_cycle;
	// }
	//
	// public void setBg_img_start_cycle(long bg_img_start_cycle) {
	// this.bg_img_start_cycle = bg_img_start_cycle;
	// }
	//
	// public long getSlice_img_start_cycle() {
	// return slice_img_start_cycle;
	// }
	//
	// public void setSlice_img_start_cycle(long slice_img_start_cycle) {
	// this.slice_img_start_cycle = slice_img_start_cycle;
	// }
	//
	// public long getSlice_bg_img_start_cycle() {
	// return slice_bg_img_start_cycle;
	// }
	//
	// public void setSlice_bg_img_start_cycle(long slice_bg_img_start_cycle) {
	// this.slice_bg_img_start_cycle = slice_bg_img_start_cycle;
	// }
	//
	// public long getDlg_show_cycle() {
	// return dlg_show_cycle;
	// }
	//
	// public void setDlg_show_cycle(long dlg_show_cycle) {
	// this.dlg_show_cycle = dlg_show_cycle;
	// }

	// public long getTotal_img_cycle() {
	// return total_img_cycle;
	// }
	//
	// public void setTotal_img_cycle(long total_img_cycle) {
	// this.total_img_cycle = total_img_cycle;
	// }

	public long getTotal_abs_img_cycle() {
		return total_abs_img_cycle;
	}

	public long getOption_cycle() {
		return option_cycle;
	}

	public void setOption_cycle(long option_cycle) {
		this.option_cycle = option_cycle;
	}

	public long getPost_action_cycle() {
		return post_action_cycle;
	}

	public void setPost_action_cycle(long post_action_cycle) {
		this.post_action_cycle = post_action_cycle;
	}

	public void setTotal_abs_img_cycle(long total_abs_img_cycle) {
		this.total_abs_img_cycle = total_abs_img_cycle;
	}

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
