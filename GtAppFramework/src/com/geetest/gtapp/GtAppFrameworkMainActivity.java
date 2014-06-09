package com.geetest.gtapp;

import android.app.Activity;
import android.os.Bundle;

public class GtAppFrameworkMainActivity extends Activity {

	// private Context context = this;
	//
	// private Button btn_gtapp_dlg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gtappdemo);

//		btn_gtapp_dlg = (Button) findViewById(R.id.btn_gtapp_dlg);
//
//		// 刷新图片
//		btn_gtapp_dlg.setOnClickListener(new Button.OnClickListener() {
//			public void onClick(View v) {
//
//				DisplayMetrics dm = new DisplayMetrics();
//				getWindowManager().getDefaultDisplay().getMetrics(dm);
//				String gt_public_key = "a40fd3b0d712165c5d13e6f747e948d4";// 公钥
//
//				new GtAppDialog(context, gt_public_key,
//						R.layout.gtapp_main_dlg, dm, getResources())
//						.setDisplay();
//			}
//		});

	}

}
