package com.geetest.gtappdemo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.geetest.gtappdemo.view.GtAppDialog;

public class MainActivity extends ActionBarActivity {

	private Context context = this;


	private Button btn_gtapp_dlg;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gtappdemo);


		btn_gtapp_dlg = (Button) findViewById(R.id.btn_gtapp_dlg);

		// 刷新图片
		btn_gtapp_dlg.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {

				DisplayMetrics dm = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(dm);
				String gt_public_key = "a40fd3b0d712165c5d13e6f747e948d4";// 公钥

				new GtAppDialog(context, gt_public_key, R.layout.gtapp_main_dlg,
						dm, getResources()).setDisplay();
			}
		});


	}

	/*
	 * 设置控件所在的位置X，并且不改变宽高， X为绝对位置，此时Y可能归0
	 */
	public void setLayoutX(View view, int x) {
		MarginLayoutParams margin = new MarginLayoutParams(
				view.getLayoutParams());
		margin.setMargins(x, margin.topMargin, x + margin.width,
				margin.bottomMargin);
		RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
				margin);
		view.setLayoutParams(layoutParams);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


}
