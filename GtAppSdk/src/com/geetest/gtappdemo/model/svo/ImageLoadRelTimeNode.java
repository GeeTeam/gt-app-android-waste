package com.geetest.gtappdemo.model.svo;

/**
 * 图片的数据收集--时间节点
 * 
 * @author Zheng
 * @time 2014年6月30日 下午12:13:26
 */
/**
 * 相对时间
 * 
 * @author Zheng
 * @time 2014年6月30日 下午2:41:53
 */
public class ImageLoadRelTimeNode {

	// 软件行为参数收集
	private long dlg_show_time = 0;// 对话框显示的时刻点
	private long bg_img_start_time = 0;
	private long bg_img_end_time = 0;
	private long slice_bg_img_start_time = 0;
	private long slice_bg_img_end_time = 0;
	private long slice_img_start_time = 0;
	private long slice_img_end_time = 0;

	// private long totalLoadTime = 0;// 所有的图片的加载时间

	// 串行的通讯 方式导致的通讯时间
	public long getAbsTotalLoadTime() {
		return (slice_img_end_time - bg_img_start_time);
	}

	public long getDlg_show_time() {
		return dlg_show_time;
	}

	public void setDlg_show_time(long dlg_show_time) {
		this.dlg_show_time = dlg_show_time;
	}

	/**
	 * 将时刻节点转换成时间段
	 * 
	 * @time 2014年6月30日 下午2:41:56
	 * @return
	 */
	public ImageLoadTimeCycle getImageLoadTimeCycle() {

		ImageLoadTimeCycle timeCycle = new ImageLoadTimeCycle();
		// timeCycle.setDlg_show_cycle(dlg_show_time - dlg_open_time);
		// timeCycle.setBg_img_start_cycle(bg_img_start_time - dlg_open_time);
		// timeCycle.setSlice_bg_img_start_cycle(slice_bg_img_start_time
		// - dlg_open_time);
		// timeCycle
		// .setSlice_img_start_cycle(slice_img_start_time - dlg_open_time);

		// 从请求图片开始
		timeCycle.setBg_img_cycle(bg_img_end_time - bg_img_start_time);
		timeCycle.setSlice_bg_img_cycle(slice_bg_img_end_time
				- slice_bg_img_start_time);
		timeCycle.setSlice_img_cycle(slice_img_end_time - slice_img_start_time);
		timeCycle.setTotal_abs_img_cycle(getAbsTotalLoadTime());

		return timeCycle;
	}

	// public void setTotalLoadTime(long totalLoadTime) {
	// this.totalLoadTime = totalLoadTime;
	// }

	public long getBg_img_start_time() {
		return bg_img_start_time;
	}

	public void setBg_img_start_time(long bg_img_start_time) {
		this.bg_img_start_time = bg_img_start_time;
	}

	public long getBg_img_end_time() {
		return bg_img_end_time;
	}

	public void setBg_img_end_time(long bg_img_end_time) {
		this.bg_img_end_time = bg_img_end_time;
	}

	public long getSlice_bg_img_start_time() {
		return slice_bg_img_start_time;
	}

	public void setSlice_bg_img_start_time(long slice_bg_img_start_time) {
		this.slice_bg_img_start_time = slice_bg_img_start_time;
	}

	public long getSlice_bg_img_end_time() {
		return slice_bg_img_end_time;
	}

	public void setSlice_bg_img_end_time(long slice_bg_img_end_time) {
		this.slice_bg_img_end_time = slice_bg_img_end_time;
	}

	public long getSlice_img_start_time() {
		return slice_img_start_time;
	}

	public void setSlice_img_start_time(long slice_img_start_time) {
		this.slice_img_start_time = slice_img_start_time;
	}

	public long getSlice_img_end_time() {
		return slice_img_end_time;
	}

	public void setSlice_img_end_time(long slice_img_end_time) {
		this.slice_img_end_time = slice_img_end_time;
	}

}
