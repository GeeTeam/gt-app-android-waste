package com.geetest.gtappdemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.geetest.gtapp.logger.GtLogger;
import com.geetest.gtappdemo.model.gconstant.GtApiEnv;
import com.geetest.gtappdemo.model.vo.CaptchaOption;
import com.google.gson.Gson;

/**
 * @author Zheng
 * 
 */
public class ImageMoveActivity extends Activity {

	// 数据区

	// 界面控件区

	private Context context = this;

	private Button btn_refresh;// 用于刷新图片的按钮

	private ImageView igv_slice;// 用于拖动的小切片图
	private ImageView igv_slicebg;// 被切掉后的切图背景
	private ImageView igv_fullbg;// 完整的背景

	
	private SeekBar skb_dragCaptcha;//拖动的seekbar

	private RelativeLayout reLayoutView;// 相框的相对布局

	private RequestQueue mQueue;// 用于Volley的通讯内容

	private CaptchaOption initCaptchaOption;// 验证码初始化验证数据设置

	/* 声明存储屏幕的分辨率变量 */
	private int intScreenX, intScreenY;

	/* 声明相关变量作为存储图片宽高,位置使用 */
	private int intWidth, intHeight, intDefaultX, intDefaultY;

	private float mX, mY;// 触点的位置

	private float actionDown_X, actionDown_Y;// 产生按下事件时的X,Y
	private float actionUp_X, actionUp_Y;// 松开时的X,Y

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_move);
		super.onCreate(savedInstanceState);

		initViews();
		initViewDisplayParameter();
		initListeners();
	}

	private void initViewDisplayParameter() {
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

		captchaInitialOption_StringRequest();

	}

	private void initListeners() {
		igv_slice.setOnTouchListener(new View.OnTouchListener() {

			public boolean onTouch(View arg0, MotionEvent event) {

				float curX, curY;// 鼠标的即时位置

				curX = event.getX();
				curY = event.getY();

				switch (event.getAction()) {

				case MotionEvent.ACTION_DOWN:
					// 获取当前的位置
					mX = event.getX();
					mY = event.getY();

					actionDown_X = event.getX();
					actionDown_Y = event.getY();

					break;
				case MotionEvent.ACTION_MOVE:
					curX = event.getX();
					curY = event.getY();

					// igv_slice.scrollBy((int) (mX - curX), (int) (mY -
					// curY));// 进行偏移
					igv_slice.scrollBy((int) (mX - curX), 0);// 只进行水平方向行偏移
					mX = curX;
					mY = curY;
					break;
				case MotionEvent.ACTION_UP:
					GtLogger.v("Images Change Action_Up");

					actionUp_X = event.getX();
					actionUp_Y = event.getY();

					// if (mX != 0 && mY != 0) {
					// if (mX - actionUp_X > 8) {
					// GtLogger.v("向左滑动");
					// }
					// if (actionUp_X - mX > 8) {
					// GtLogger.v("向右滑动");
					// }
					// }

					// captchaInitialOption_StringRequest();

					curX = event.getX();
					curY = event.getY();
					igv_slice.scrollBy((int) (mX - curX), 0);
					break;
				}

				return true;
			}
		});

		// 刷新图片
		btn_refresh.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				captchaInitialOption_StringRequest();
			}
		});

		skb_dragCaptcha
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
						skb_dragCaptcha.getLocationOnScreen(location);
						int mSeekBarDef_X = location[0];
						int mSeekBarDef_Y = location[1];

						// GtLogger.v("ImageView Width:" +
						// igv_slice.getWidth());
						// // 输出 seekbar的位置：长度
						// GtLogger.v("Seekbar Width:" +
						// mSeekBarDef.getWidth());
						// GtLogger.v("Seekbar X:" + mSeekBarDef_X + "Y:"
						// + mSeekBarDef_Y);

					}

					/**
					 * 拖动条进度改变的时候调用
					 */
					@Override
					public void onProgressChanged(SeekBar seekBar,
							int progress, boolean fromUser) {

						// 将屏幕的X坐标进行均分
						float dX = (intScreenX - intWidth) / 100;

						// Log.v("seekbar", ("当前进度：" + progress + "%"));
						// TODO 坐标偏移
						// igv_slice.scrollTo((int) (-dX * progress), (int)
						// (0));// 进行偏移

						igv_slice.scrollTo((int) (-dX * progress),
								-(initCaptchaOption.getYpos() + igv_slicebg
										.getTop()));// 进行偏移

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

	private void initViews() {
		mQueue = Volley.newRequestQueue(context);// 必须在界面初始化之后才有此声明

		reLayoutView = (RelativeLayout) this.findViewById(R.id.ll_viewArea22);

		// TextView tv = (TextView) ll.findViewById(R.id.contents); // get the
		// child text view

		igv_slice = (ImageView) reLayoutView.findViewById(R.id.img);
		igv_slicebg = (ImageView) reLayoutView.findViewById(R.id.imgv_slip_big);

		skb_dragCaptcha = (SeekBar) findViewById(R.id.seekbar_def); // “系统默认SeekBar”
		btn_refresh = (Button) findViewById(R.id.btn_refresh);
	}

	/**
	 * 小切图
	 */
	private void slice_ImageRequest(String relativeUrl) {

		String imgUrl = GtApiEnv.imgServerBase + relativeUrl;

		GtLogger.v(imgUrl);

		// String url_fullbg =
		// "http://geetest-jordan2.b0.upaiyun.com/pictures/gt/b2cbb350/slice/63328333.png";
		ImageRequest slip_imageRequest = new ImageRequest(imgUrl,
				new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap response) {
						igv_slice.setImageBitmap(response);
					}
				}, 0, 0, Config.RGB_565, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						igv_slice.setImageResource(R.drawable.ic_launcher);
					}
				});

		mQueue.add(slip_imageRequest);
	}

	/**
	 * 切掉后的大的背景图
	 */
	private void fullbg_ImageRequest(String relativeUrl) {

		String imgUrl = GtApiEnv.imgServerBase + relativeUrl;

		GtLogger.v(imgUrl);

		ImageRequest bg_imageRequest = new ImageRequest(imgUrl,
				new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap response) {
						igv_slicebg.setImageBitmap(response);
					}
				}, 0, 0, Config.RGB_565, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						igv_slice.setImageResource(R.drawable.ic_launcher);
					}
				});

		mQueue.add(bg_imageRequest);
	}

	/**
	 * 初始化实验码内容
	 */
	public void captchaInitialOption_StringRequest() {
		// String url = "http://www.baidu.com";
		// 如果出现乱码，应该修改StringRequest的parseNetworkResponse()方法，指定byte[]-->String
		// 编码

		// TODO 1.根据get内容生成URL，请求URL，返回值
		String url = "http://api.geetest.com/get.php?gt=a40fd3b0d712165c5d13e6f747e948d4&product=embed";

		GtLogger.v(url);

		StringRequest option_Request = new StringRequest(url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						// GtLogger.v("response:" + response);

						// 硬解码
						String webJsFunction[] = response.split("=");
						String optionValues[] = webJsFunction[1].split(";");
						String optionValue = optionValues[0];
						// GtLogger.v(optionValue);

						// 解析成对象
						Gson gson = new Gson();
						// 收到消息后开始将JOSN字符串解析成VO对象，然后再传回Service层的回调函数
						initCaptchaOption = gson.fromJson(optionValue,
								CaptchaOption.class);

						GtLogger.v("getFullbg : "
								+ initCaptchaOption.getFullbg());

						// TODO 设置图片控件的y方向位置
						igv_slice.scrollTo(-50,
								(-(initCaptchaOption.getYpos() + igv_slicebg
										.getTop())));

						GtLogger.v("initCaptchaOption.getYpos():"
								+ initCaptchaOption.getYpos()
								+ "   igv_slicebg.getTop():"
								+ igv_slicebg.getTop());

						// 请求动态图片
						fullbg_ImageRequest(initCaptchaOption.getImgurl());
						slice_ImageRequest(initCaptchaOption.getSliceurl());
						
						//重置SeekBar
						skb_dragCaptcha.setProgress(0);

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}
				});

		mQueue.add(option_Request);
	}

	// /**
	// * 利用Volley异步加载图片
	// *
	// * 注意方法参数: getImageListener(ImageView view, int defaultImageResId, int
	// * errorImageResId) 第一个参数:显示图片的ImageView 第二个参数:默认显示的图片资源
	// 第三个参数:加载错误时显示的图片资源
	// */
	// private void loadImageByVolley() {
	// String imageUrl = "http://avatar.csdn.net/6/6/D/1_lfdfhl.jpg";
	// RequestQueue requestQueue = Volley.newRequestQueue(this);
	// final LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(
	// 20);
	// ImageCache imageCache = new ImageCache() {
	// @Override
	// public void putBitmap(String key, Bitmap value) {
	// lruCache.put(key, value);
	// }
	//
	// @Override
	// public Bitmap getBitmap(String key) {
	// return lruCache.get(key);
	// }
	// };
	// ImageLoader imageLoader = new ImageLoader(requestQueue, imageCache);
	// ImageListener listener = ImageLoader.getImageListener(switcherView,
	// R.drawable.ic_launcher, R.drawable.ic_launcher);
	// imageLoader.get(imageUrl, listener);
	// }

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
