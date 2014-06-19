package com.geetest.gtappdemo;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.geetest.gtapp.R;
import com.geetest.gtapp.utils.itface.GtAppCallback;
import com.geetest.gtappdemo.model.vo.GtAppCbCaptchaResponse;
import com.geetest.gtappdemo.model.vo.GtAppDialogOption;
import com.geetest.gtappdemo.view.GtAppDialog;

public class MainActivity extends Activity {

	private Context context = this;

	private Button btn_gtapp_dlg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gtappdemo);

		openGtAppDialog();

		btn_gtapp_dlg = (Button) findViewById(R.id.btn_gtapp_dlg);

		// 刷新图片
		btn_gtapp_dlg.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				openGtAppDialog();
			}
		});

	}

	private void openGtAppDialog() {

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 锁定竖屏

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		String gt_public_key = "a40fd3b0d712165c5d13e6f747e948d4";// 公钥
		GtAppCallback gtAppCallback = new GtAppCallback() {

			@Override
			public void gtAppResponse(GtAppCbCaptchaResponse cbResponse) {
				// TODO 在此处书写回调函数
				Log.v("sdkDemo", "resCode: " + cbResponse.getResCode()
						+ "   resMsg: " + cbResponse.getResMsg());

				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);// 传感器相关
			}
		};

		GtAppDialogOption gtOption = new GtAppDialogOption(context,
				gt_public_key, R.layout.gtapp_main_dlg, dm, getResources(),
				gtAppCallback);

		new GtAppDialog(gtOption).setDisplay();
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);// 传感器相关
	}

}
