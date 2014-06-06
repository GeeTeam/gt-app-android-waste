package com.geetest.gtapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.geetest.gtappdemo.view.GtAppDialog;

public class GtAppSdkDemoActivity extends Activity {

	private Context context = this;	
	private Button btn_gtapp_dlg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn_gtapp_dlg = (Button) findViewById(R.id.btn_gtapp_sdk_demo_dlg);

		// 刷新图片
		btn_gtapp_dlg.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {

				

				Log.v("gtapp", "run here button");

				DisplayMetrics dm = new DisplayMetrics();//获取屏幕信息
				getWindowManager().getDefaultDisplay().getMetrics(dm);
				String gt_public_key = "a40fd3b0d712165c5d13e6f747e948d4";// 公钥

				new GtAppDialog(context, gt_public_key, R.layout.gtapp_main_dlg,
						dm, getResources()).setDisplay();

			}
		});

	}

}
