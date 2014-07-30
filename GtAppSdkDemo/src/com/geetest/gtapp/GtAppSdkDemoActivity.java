package com.geetest.gtapp;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.geetest.gtapp.utils.GtAppLib;
import com.geetest.gtapp.utils.itface.GtAppCallback;
import com.geetest.gtappdemo.model.vo.GtAppCbCaptchaResponse;
import com.geetest.gtappdemo.model.vo.GtAppDialogOption;
import com.geetest.gtappdemo.view.GtAppDialog;

public class GtAppSdkDemoActivity extends Activity {

	private Context context = this;
	private Button btn_gtapp_dlg;

	private RequestQueue mQueue;// 通讯相关类

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mQueue = Volley.newRequestQueue(this);// 必须在界面初始化之后才有此声明
		btn_gtapp_dlg = (Button) findViewById(R.id.btn_gtapp_sdk_demo_dlg);
		// openGtAppDialog();
		btn_gtapp_dlg.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				try {
					new GtAppDlgTask().execute();
				} catch (Exception e) {
					Log.e("gtapp", e.getMessage());
				}

			}
		});
	}

	class GtAppDlgTask extends AsyncTask<Void, Integer, Integer> {

		@Override
		protected Integer doInBackground(Void... params) {
			return GtAppLib.getGtServerStatus();
		}

		@Override
		protected void onPostExecute(Integer serverStatusCode) {

			if (serverStatusCode == 1) {
				openGtAppDialog();
			} else {
				// TODO 使用自己的验证码体系来进行判断。
				Toast.makeText(context, "极验验证打开失败-请使用自己的备用验证系统",
						Toast.LENGTH_SHORT).show();
			}
		}
	}

	private void openGtAppDialog() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		String gt_public_key = "b859d74376439f0a80c2c658cbfdfd2a";// TODO 
		// a40fd3b0d712165c5d13e6f747e948d4 // 用户自己在www.geetest.com注册申请公钥

		GtAppCallback gtAppCallback = new GtAppCallback() {
			@Override
			public void gtAppResponse(GtAppCbCaptchaResponse cbResponse) {

				if (cbResponse.getResCode() == 1) {
					// TODO 在此处书写验证成功的回调函数
					String toastMsg = "resCode:" + cbResponse.getResCode()
							+ " resMsg:" + cbResponse.getResMsg();
					Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT)
							.show();
				} else {

				}

			}
		};

		GtAppDialogOption gtOption = new GtAppDialogOption(context,
				gt_public_key, dm, getResources(), gtAppCallback);

		new GtAppDialog(gtOption, mQueue).setDisplay();
	}
}
