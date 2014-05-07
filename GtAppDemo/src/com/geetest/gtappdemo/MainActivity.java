package com.geetest.gtappdemo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	// 与“系统默认SeekBar”对应的TextView
	private TextView mTvDef;
	// 与“自定义SeekBar”对应的TextView
	private TextView mTvSelf;
	// “系统默认SeekBar”
	private SeekBar mSeekBarDef;
	// “自定义SeekBar”
	private SeekBar mSeekBarSelf;

	// private ImageView image;

	private final String TAG = "Pictrue Test!!!";
	private ImageView image;
	private int height = 0;
	private int width = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_main);
		setContentView(R.layout.gtappdemo);

		image = (ImageView) findViewById(R.id.imgv_slip_small);
		// if (savedInstanceState == null) {
		// getSupportFragmentManager().beginTransaction()
		// .add(R.id.container, new PlaceholderFragment())
		// .commit();
		// }

		// “系统默认SeekBar”
		mSeekBarDef = (SeekBar) findViewById(R.id.seekbar_def);
		mSeekBarDef
				.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
					/**
					 * 拖动条停止拖动的时候调用
					 */
					@Override
					public void onStopTrackingTouch(SeekBar seekBar) {
						Log.v("seekbar", "拖动停止");
					}

					/**
					 * 拖动条开始拖动的时候调用
					 */
					@Override
					public void onStartTrackingTouch(SeekBar seekBar) {
						Log.v("seekbar", "开始拖动");

					}

					/**
					 * 拖动条进度改变的时候调用
					 */
					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {
						Log.v("seekbar", ("当前进度：" + progress + "%"));

						// image.layout(5*progress, image.getPaddingTop(),
						// image.getPaddingRight()+5*progress,
						// image.getPaddingBottom());
						// image.setPadding( image.getPaddingLeft(),
						// image.getPaddingTop()+1*progress,
						// image.getPaddingRight()+0*progress,
						// image.getPaddingBottom());

						// LayoutParams para;
						// para = image.getLayoutParams();
						//
						// Log.d(TAG, "layout height0: " + para.height);
						// Log.d(TAG, "layout width0: " + para.width);
						//
						// para.height = 5*progress;
						// para.width = 5*progress;
						//
						// image.setLayoutParams(para);
						//
						// Log.d(TAG, "layout height: " + para.height);
						// Log.d(TAG, "layout width: " + para.width);
						
					
						setLayoutX(image,2*progress);

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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
