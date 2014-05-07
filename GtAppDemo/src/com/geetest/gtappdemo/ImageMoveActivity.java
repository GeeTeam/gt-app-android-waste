package com.geetest.gtappdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class ImageMoveActivity extends Activity {

	private float mx;
	private float my;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_move);
		super.onCreate(savedInstanceState);

		final ImageView switcherView = (ImageView) this.findViewById(R.id.img);

		switcherView.setOnTouchListener(new View.OnTouchListener() {

			public boolean onTouch(View arg0, MotionEvent event) {

				float curX, curY;

				switch (event.getAction()) {

				case MotionEvent.ACTION_DOWN:
					mx = event.getX();
					my = event.getY();
					break;
				case MotionEvent.ACTION_MOVE:
					curX = event.getX();
					curY = event.getY();
					switcherView.scrollBy((int) (mx - curX), (int) (my - curY));
					mx = curX;
					my = curY;
					break;
				case MotionEvent.ACTION_UP:
					curX = event.getX();
					curY = event.getY();
					switcherView.scrollBy((int) (mx - curX), (int) (my - curY));
					break;
				}

				return true;
			}
		});

	}

//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.activity_main, menu);
//		return true;
//	}

}
