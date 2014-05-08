package com.geetest.gtappdemo;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.geetest.gtapp.utils.GtLogger;

public class ImageMoveActivity extends Activity {

	// “系统默认SeekBar”
	private SeekBar mSeekBarDef;

	/* 声明存储屏幕的分辨率变量 */
	private int intScreenX, intScreenY;

	/* 声明相关变量作为存储图片宽高,位置使用 */
	private int intWidth, intHeight, intDefaultX, intDefaultY;

	private float mX;
	private float mY;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_move);
		super.onCreate(savedInstanceState);

		final ImageView switcherView = (ImageView) this.findViewById(R.id.img);

		// 取得屏幕对象
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		/* 取得屏幕解析像素 */
		intScreenX = dm.widthPixels;
		intScreenY = dm.heightPixels;

		GtLogger.v("Screen   X:" + intScreenX + " Y:" + intScreenY);

		/* 设置图片的宽高 */
		intWidth = 50;
		intHeight = 65;

		switcherView.setOnTouchListener(new View.OnTouchListener() {

			public boolean onTouch(View arg0, MotionEvent event) {

				float curX, curY;// 当前鼠标的位置

				switch (event.getAction()) {

				case MotionEvent.ACTION_DOWN:
					// 获取当前的位置
					mX = event.getX();
					mY = event.getY();
					break;
				case MotionEvent.ACTION_MOVE:
					curX = event.getX();
					curY = event.getY();
					switcherView.scrollBy((int) (mX - curX), (int) (mY - curY));// 进行偏移
					mX = curX;
					mY = curY;
					break;
				case MotionEvent.ACTION_UP:
					curX = event.getX();
					curY = event.getY();
					switcherView.scrollBy((int) (mX - curX), (int) (mY - curY));
					break;
				}

				return true;
			}
		});

		// 做seekbar的事件
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
						
						int[] location = new int[2];
						mSeekBarDef.getLocationOnScreen(location);
						int mSeekBarDef_X = location[0];
						int mSeekBarDef_Y = location[1];

						GtLogger.v("ImageView Width:" + switcherView.getWidth());
						// 输出 seekbar的位置：长度
						GtLogger.v("Seekbar Width:" + mSeekBarDef.getWidth());
						GtLogger.v("Seekbar X:" + mSeekBarDef_X + "Y:" + mSeekBarDef_Y);

					}

					/**
					 * 拖动条进度改变的时候调用
					 */
					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {

						// 将屏幕的X坐标进行均分
						float dX = (intScreenX - intWidth) / 100;

						Log.v("seekbar", ("当前进度：" + progress + "%"));
						switcherView
								.scrollTo((int) (-dX * progress), (int) (0));// 进行偏移

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

						// setLayoutX(switcherView, 2 * progress);

					}
				});

	}

	/* 移动图片的方法 */
	private void picMove(float x, float y) {
		/* 默认微调图片与指针的相对位置 */
		mX = x - (intWidth / 2);
		mY = y - (intHeight / 2);

		/* 防图片超过屏幕的相关处理 */
		/* 防止屏幕向右超过屏幕 */
		if ((mX + intWidth) > intScreenX) {
			mX = intScreenX - intWidth;
		}
		/* 防止屏幕向左超过屏幕 */
		else if (mX < 0) {
			mX = 0;
		}
		/* 防止屏幕向下超过屏幕 */
		else if ((mY + intHeight) > intScreenY) {
			mY = intScreenY - intHeight;
		}
		/* 防止屏幕向上超过屏幕 */
		else if (mY < 0) {
			mY = 0;
		}
		/* 通过log 来查看图片位置 */
		Log.i("jay", Float.toString(mX) + "," + Float.toString(mY));
		/* 以setLayoutParams方法，重新安排Layout上的位置 */
		// mImageView01.setLayoutParams(new
		// AbsoluteLayout.LayoutParams(intWidth,
		// intHeight, (int) mX, (int) mY));
	}

	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.activity_main, menu);
	// return true;
	// }

}
