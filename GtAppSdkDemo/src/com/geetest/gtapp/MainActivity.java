package com.geetest.gtapp;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button btn_gtapp_dlg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btn_gtapp_dlg = (Button) findViewById(R.id.btn_gtapp_dlg);

		// 刷新图片
		btn_gtapp_dlg.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {

				DisplayMetrics dm = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(dm);
				String gt_public_key = "a40fd3b0d712165c5d13e6f747e948d4";// 公钥

				Log.v("gtapp", "run here button");
				
				// new GtAppDialog(context, gt_public_key, R.layout.image_move,
				// dm, getResources()).setDisplay();
			}
		});

	}

}
