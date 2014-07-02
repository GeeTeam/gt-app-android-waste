package com.geetest.gtappdemo.model.svo;

/**
 * 图片的数据收集--时间节点
 * 
 * @author Zheng
 * @time 2014年6月30日 下午12:13:26
 */
/**
 * @author Zheng
 * @time 2014年6月30日 下午2:41:53
 */
public class ImageLoadTimeNode {

	// 软件行为参数收集
	private long dlg_open_time = 0;// 对话框打开的时间
	private long bg_img_start_time = 0;
	private long bg_img_end_time = 0;
	private long slice_bg_img_start_time = 0;
	private long slice_bg_img_end_time = 0;
	private long slice_img_start_time = 0;
	private long slice_img_end_time = 0;

	// private long totalLoadTime = 0;// 所有的图片的加载时间

	
	
	public long getTotalLoadTime() {
		return (slice_img_end_time - bg_img_start_time);
	}

	public long getDlg_open_time() {
		return dlg_open_time;
	}

	public void setDlg_open_time(long dlg_open_time) {
		this.dlg_open_time = dlg_open_time;
	}

	/**
	 * 将时刻节点转换成时间段
	 * 
	 * @time 2014年6月30日 下午2:41:56
	 * @return
	 */
	public ImageLoadTimeCycle getImageLoadTimeCycle() {

		ImageLoadTimeCycle timeCycle = new ImageLoadTimeCycle();
		timeCycle.setBg_img_cycle(bg_img_end_time - bg_img_start_time);
		timeCycle.setSlice_bg_img_cycle(slice_bg_img_end_time
				- slice_bg_img_start_time);
		timeCycle.setSlice_img_cycle(slice_img_end_time - slice_img_start_time);
		timeCycle.setTotal_img_cycle(getTotalLoadTime());

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
