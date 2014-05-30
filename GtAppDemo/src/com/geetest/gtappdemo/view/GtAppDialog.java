package com.geetest.gtappdemo.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

/**
 * 弹出的对话框
 * 
 * @author Zheng 2014年5月30日 下午2:22:38
 */
public class GtAppDialog extends Dialog {

	public GtAppDialog(Context context) {
		super(context);
	}

	/**
	 * @param dlgLayoutResId
	 *            :资源的编号 R.layout.dialog_layout2
	 */
	public void setDisplay(int dlgLayoutResId) {
		setContentView(dlgLayoutResId);
		// setTitle("继承 Dialog类");
		setLocation();
		show();
	}

	public void setLocation() {
		Window window = getWindow();
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = 0;// 0为中间
		wl.y = 100;
		wl.alpha = 0.6f;
		wl.gravity = Gravity.BOTTOM;
		window.setAttributes(wl);
	}
}