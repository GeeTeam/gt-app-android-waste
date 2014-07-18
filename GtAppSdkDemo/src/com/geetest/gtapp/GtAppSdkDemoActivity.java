package com.geetest.gtapp;

import java.util.HashMap;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.geetest.gtapp.gtlog.GtLogger;
import com.geetest.gtapp.utils.GtAppLib;
import com.geetest.gtapp.utils.itface.GtAppCallback;
import com.geetest.gtappdemo.model.vo.GtAppCbCaptchaResponse;
import com.geetest.gtappdemo.model.vo.GtAppDialogOption;
import com.geetest.gtappdemo.view.GtAppDialog;

public class GtAppSdkDemoActivity extends Activity {

	private Context context = this;
	private Button btn_gtapp_dlg;

	// 用于调试的信息
	private HashMap<String, Object> oritationMap = new HashMap<>();
	// TODO 实验完毕后需要删除

	private RequestQueue mQueue;// 通讯相关类
	final int originRequestOritation = getRequestedOrientation();// 宿主程序原始的屏幕排列模式

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mQueue = Volley.newRequestQueue(context);// 必须在界面初始化之后才有此声明

		btn_gtapp_dlg = (Button) findViewById(R.id.btn_gtapp_sdk_demo_dlg);
		// openGtAppDialog();
		btn_gtapp_dlg.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				try {
					new GtAppDlgTask().execute();
				} catch (Exception e) {
					Log.e("", e.getMessage());
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
				Log.e("", "极验验证打开失败-请使用自己的备用验证系统");
			}
		}
	}

	private void openGtAppDialog() {

		oritationMap.put("origin", getRequestedOrientation());

		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		String gt_public_key = "a40fd3b0d712165c5d13e6f747e948d4";// 公钥

		GtAppCallback gtAppCallback = new GtAppCallback() {

			@Override
			public void gtAppResponse(GtAppCbCaptchaResponse cbResponse) {

				// TODO 在此处书写回调函数
				Log.v("sdkDemo", "resCode: " + cbResponse.getResCode()
						+ "   resMsg: " + cbResponse.getResMsg());

				oritationMap.put("callback", getRequestedOrientation());
				setRequestedOrientation(originRequestOritation);
				oritationMap.put("afterDialog", getRequestedOrientation());

				GtLogger.s_v("2014715_121747", oritationMap);
			}
		};

		GtAppDialogOption gtOption = new GtAppDialogOption(context,
				gt_public_key, R.layout.gtapp_main_dlg, dm, getResources(),
				gtAppCallback);

		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);//
		// 锁定竖屏
		oritationMap.put("before", getRequestedOrientation());

		// Log.e("", "before");
		new GtAppDialog(gtOption, mQueue).setDisplay();

		// setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);//
		// 传感器相关
	}

}
