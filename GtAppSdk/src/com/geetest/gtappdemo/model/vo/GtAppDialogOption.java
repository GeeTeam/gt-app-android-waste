package com.geetest.gtappdemo.model.vo;

import java.security.PublicKey;

import com.geetest.gtapp.utils.itface.GtAppCallback;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;

/**
 * GtApp 对话框的初始化
 * 
 * @author Zheng
 * @time 2014年6月10日 上午9:46:36
 */
public class GtAppDialogOption {

	private Context context;// 设备上下文###暂无
	private String gt_public_key;// 公钥###暂无
	private int gtAppDlgLayoutResId;// 对话框的布局文件###暂无
	private DisplayMetrics dm;// 屏幕对象###暂无
	private Resources res;// 资源对象###暂无
	private GtAppCallback gtAppCallback;// 回调函数###暂无

	public GtAppDialogOption(Context context, String gt_public_key,
			int gtAppDlgLayoutResId, DisplayMetrics dm, Resources res,
			GtAppCallback gtAppCallback) {

		this.context = context;
		this.gt_public_key = gt_public_key;
		this.gtAppDlgLayoutResId = gtAppDlgLayoutResId;
		this.dm = dm;
		this.res = res;
		this.gtAppCallback = gtAppCallback;

	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public String getGt_public_key() {
		return gt_public_key;
	}

	public void setGt_public_key(String gt_public_key) {
		this.gt_public_key = gt_public_key;
	}

	public int getGtAppDlgLayoutResId() {
		return gtAppDlgLayoutResId;
	}

	public void setGtAppDlgLayoutResId(int gtAppDlgLayoutResId) {
		this.gtAppDlgLayoutResId = gtAppDlgLayoutResId;
	}

	public DisplayMetrics getDm() {
		return dm;
	}

	public void setDm(DisplayMetrics dm) {
		this.dm = dm;
	}

	public Resources getRes() {
		return res;
	}

	public void setRes(Resources res) {
		this.res = res;
	}

	public GtAppCallback getGtAppCallback() {
		return gtAppCallback;
	}

	public void setGtAppCallback(GtAppCallback gtAppCallback) {
		this.gtAppCallback = gtAppCallback;
	}

}
