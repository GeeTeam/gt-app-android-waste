package com.geetest.gtappdemo.model.svo;

/**
 * 图片的数据收集--时间节点--绝对时间
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
	private long dlg_show_time = 0;// 对话框显示的时刻点
	// 初始化参数通讯
	private long option_start_time = 0;
	private long option_end_time = 0;

	// 大背景图通讯
	private long bg_img_start_time = 0;
	private long bg_img_end_time = 0;
	// 切图背景通讯
	private long slice_bg_img_start_time = 0;
	private long slice_bg_img_end_time = 0;
	// 切图通讯
	private long slice_img_start_time = 0;
	private long slice_img_end_time = 0;

	// 上传行为数据
	private long post_action_start_time = 0;
	private long post_action_end_time = 0;

	// private long totalLoadTime = 0;// 所有的图片的加载时间

	/**
	 * 从绝对时刻点，转换成相对的时刻点
	 * 
	 * @time 2014年7月7日 下午11:45:34
	 * @return
	 */
	public ImageLoadRelTimeNode getRelativeTimeNode() {

		ImageLoadRelTimeNode relativeTimeNode = new ImageLoadRelTimeNode();
		relativeTimeNode.setDlg_show_time(dlg_show_time - dlg_open_time);

		relativeTimeNode
				.setOption_start_time(option_start_time - dlg_open_time);
		relativeTimeNode.setOption_end_time(option_end_time - dlg_open_time);
		relativeTimeNode.setPost_action_start_time(post_action_start_time
				- dlg_open_time);
		relativeTimeNode.setPost_action_end_time(post_action_end_time
				- dlg_open_time);

		relativeTimeNode
				.setBg_img_start_time(bg_img_start_time - dlg_open_time);
		relativeTimeNode.setBg_img_end_time(bg_img_end_time - dlg_open_time);
		relativeTimeNode.setSlice_bg_img_start_time(slice_bg_img_start_time
				- dlg_open_time);
		relativeTimeNode.setSlice_bg_img_end_time(slice_bg_img_end_time
				- dlg_open_time);
		relativeTimeNode.setSlice_img_start_time(slice_img_start_time
				- dlg_open_time);
		relativeTimeNode.setSlice_img_end_time(slice_img_end_time
				- dlg_open_time);

		return relativeTimeNode;

	}

	public long getOption_start_time() {
		return option_start_time;
	}

	public void setOption_start_time(long option_start_time) {
		this.option_start_time = option_start_time;
	}

	public long getOption_end_time() {
		return option_end_time;
	}

	public void setOption_end_time(long option_end_time) {
		this.option_end_time = option_end_time;
	}

	public long getPost_action_start_time() {
		return post_action_start_time;
	}

	public void setPost_action_start_time(long post_action_start_time) {
		this.post_action_start_time = post_action_start_time;
	}

	public long getPost_action_end_time() {
		return post_action_end_time;
	}

	public void setPost_action_end_time(long post_action_end_time) {
		this.post_action_end_time = post_action_end_time;
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
		timeCycle.setOption_cycle(option_start_time - option_end_time);
		timeCycle.setPost_action_cycle(post_action_end_time
				- post_action_start_time);
		timeCycle.setBg_img_cycle(bg_img_end_time - bg_img_start_time);
		timeCycle.setSlice_bg_img_cycle(slice_bg_img_end_time
				- slice_bg_img_start_time);
		timeCycle.setSlice_img_cycle(slice_img_end_time - slice_img_start_time);
		timeCycle.setTotal_abs_img_cycle(getAbsTotalLoadTime());

		return timeCycle;

	}

	// public long getTotalLoadTime() {
	// return (slice_img_end_time - bg_img_start_time);
	// }

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

	public long getDlg_open_time() {
		return dlg_open_time;
	}

	public void setDlg_open_time(long dlg_open_time) {
		this.dlg_open_time = dlg_open_time;
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
