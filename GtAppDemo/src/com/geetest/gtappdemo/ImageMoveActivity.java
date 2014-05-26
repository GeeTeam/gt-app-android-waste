package com.geetest.gtappdemo;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.geetest.gtapp.logger.GtLogger;
import com.geetest.gtapp.utils.GtDataConvert;
import com.geetest.gtapp.utils.LoggerString;
import com.geetest.gtappdemo.model.gconstant.GtApiEnv;
import com.geetest.gtappdemo.model.vo.CaptchaOption;
import com.geetest.gtappdemo.model.vo.CaptchaUserAction;
import com.geetest.gtappdemo.model.vo.DecodedChallenge;
import com.geetest.gtappdemo.model.vo.greq.AjaxPhp_GreqVo;
import com.geetest.gtappdemo.model.vo.greq.GetPhp_GreqVo;
import com.geetest.gtappdemo.model.vo.gres.AjaxPhp_GresVo;
import com.geetest.gtappdemo.model.vo.preq.GtCustomerSubmit;
import com.google.gson.Gson;

/**
 * @author Zheng
 * 
 */
/**
 * @author Zheng
 * 
 */
public class ImageMoveActivity extends Activity {

	// 数据区

	// 界面控件区

	private Context context = this;

	private RelativeLayout reLayoutView;// 相框的相对布局

	private ImageView imgv_full_bg;// 完整的背景
	private ImageView imgv_slice;// 用于拖动的小切片图
	private ImageView imgv_slice_bg;// 被切掉后的切图背景

	// 图片对象
	// private Bitmap bm_slice;
	// private Bitmap bm_slicebg;
	// private Bitmap bm_fullbg;

	private TextView tv_validateStatus;// 验证码的状态栏

	private Button btn_refresh;// 用于刷新图片的按钮
	private SeekBar skb_dragCaptcha;// 拖动的seekbar

	private float bm_zoom_scale = 1;// 图片缩放比例

	private RequestQueue mQueue;// 用于Volley的通讯内容

	// 验证通讯数据对象
	private CaptchaOption initCaptchaOption;// 验证码初始化验证数据设置
	private AjaxPhp_GreqVo ajaxPhp_GreqVo;// 上传行为数据的API参数

	// 滑块的初始化位置
	private int seekbarX = 0;
	private int seekbarY = 0;

	// 滑块交互用的时间
	private long seekbarStartTime = 0;// 按下时
	private long seekbarEndTime = 0;// 放开时

	// 图片加载的起始时间
	private long bgImgLoadStartTime = 0;
	private long bgImgLoadEndTime = 0;

	private String messageResult = "";// Gt服务器返回的验证结果

	// handle里面的消息代号
	private final int MSG_FULL_BG_DISPLAY = 1;// 完整图片显示
	private final int MSG_SLICE_BG_DISPLAY = 2;// 切片图显示

	/**
	 * 当前的用户行为
	 */
	// private CaptchaUserAction curUserAction = new CaptchaUserAction();

	// 用户行为数据
	private int sliderOffsetX = 12;// 滑块x方向偏移量
	private ArrayList<CaptchaUserAction> userActions = new ArrayList<CaptchaUserAction>();// 用户行为数据的数组

	/* 声明存储屏幕的分辨率变量 */
	private int intScreenX, intScreenY;

	// 图片展示区离屏幕边缘的距--和布局文件的设计有关
	private int leftMargin = 20;// dp
	private int rightMargin = 20;// dp

	private int seekbar_server_length = 80;// 滑条在服务器端的标准长度px--和背景图一样大

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

	/**
	 * 按下滑块时，设置背景图片的原始图片的显示
	 */
	private void setImageViewDisplayWhenDragSlider() {

		// GtLogger.v("bg,bg_slice,slice:gone,visible,visible");

		imgv_full_bg.setVisibility(View.INVISIBLE);
		imgv_slice_bg.setVisibility(View.VISIBLE);
		imgv_slice.setVisibility(View.VISIBLE);

		// TODO 在第一次加载的时候会导致切图找不到
		// GtLogger.v("imgv_slice_bg### " + "xPos: " + imgv_slice_bg.getLeft()
		// + " yPos: " + imgv_slice_bg.getTop());

	}

	/**
	 * 按初始化或者点击刷新时:设置背景图片的原始图片的显示
	 */
	private void setImageViewDisplayWhenRefresh() {

		// GtLogger.v("bg,bg_slice,slice:visible,gone,gone");

		imgv_full_bg.setVisibility(View.VISIBLE);
		imgv_slice_bg.setVisibility(View.INVISIBLE);
		imgv_slice.setVisibility(View.INVISIBLE);
	}

	private void initListeners() {
		imgv_slice.setOnTouchListener(new View.OnTouchListener() {

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
					curX = event.getX();// 当前x
					curY = event.getY();// 当前y

					// igv_slice.scrollBy((int) (mX - curX), (int) (mY -
					// curY));// 进行偏移
					imgv_slice.scrollBy((int) (mX - curX), 0);// 只进行水平方向行偏移
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
					imgv_slice.scrollBy((int) (mX - curX), 0);
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

		// 拖动条的touch事件
		skb_dragCaptcha.setOnTouchListener(new View.OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float curX, curY;// 鼠标的即时位置

				curX = event.getX();
				curY = event.getY();

				switch (event.getAction()) {

				case MotionEvent.ACTION_DOWN:
					// 获取当前的位置
					mX = event.getX();
					mY = event.getY();

					setImageViewDisplayWhenDragSlider();
					// TODO 通知界面进行控制显示
					// Message msg = mHandler.obtainMessage();
					// msg.what = MSG_SLICE_BG_DISPLAY;
					// msg.sendToTarget();

					seekbarStartTime = System.currentTimeMillis();

					actionDown_X = event.getX();
					actionDown_Y = event.getY();

					GtLogger.v("按下拖动条");
					// 如果seekbar状态是按下，则开始记录第一组行为数据
					// long mouseDownTimeTag = System.currentTimeMillis();//
					// 当前时间标记
					userActions = new ArrayList<CaptchaUserAction>();// 用户行为数据的数组--重新清空初始化一次
					CaptchaUserAction firstAction = new CaptchaUserAction();
					firstAction.bindMemData((int) (seekbarX - mX),
							(int) (seekbarY - mY), 0);
					firstAction.v();
					userActions.add(firstAction);

					break;
				case MotionEvent.ACTION_MOVE:
					curX = event.getX();// 当前x
					curY = event.getY();// 当前y

					mX = curX;
					mY = curY;

					// TODO 这个数据类型需要后面修复 2014年5月20日 16:55:52
					long curTimeTag = System.currentTimeMillis();// 当前时间标记
					CaptchaUserAction curUserAction = new CaptchaUserAction();
					curUserAction.bindMemData((int) curX, (int) curY,
							(int) (curTimeTag - seekbarStartTime));
					// curUserAction.v();
					userActions.add(curUserAction);

					break;
				case MotionEvent.ACTION_UP:

					GtLogger.v("Images Change Action_Up");

					seekbarEndTime = System.currentTimeMillis();
					CaptchaUserAction lastAction = new CaptchaUserAction();
					lastAction.bindMemData((int) curX, (int) curY,
							(int) (seekbarEndTime - seekbarStartTime));
					lastAction.v();
					userActions.add(lastAction);

					actionUp_X = event.getX();
					actionUp_Y = event.getY();

					curX = event.getX();
					curY = event.getY();

					break;
				}
				return false;
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

						// 向服务器提交行为数据
						userBehaviourUpload_StringRequest();
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

						// GtLogger.v("当前进度：" + progress + "%");
						// Log.v("seekbar", ("当前进度：" + progress + "%"));
						// 坐标偏移
						// igv_slice.scrollTo((int) (-dX * progress), (int)
						// (0));// 进行偏移

						// 将屏幕的X坐标进行均分
						float dX = (intScreenX - intWidth) / 100.0f;

						sliderOffsetX = (int) (seekbar_server_length * progress / 100);// 以服务器上的绝对值为准

						imgv_slice.scrollTo((int) (-dX * progress),
								getSliceYposAfterSalced());// 进行偏移

						// igv_slice.scrollTo(-50,
						// Math.round(-(initCaptchaOption
						// .getYpos() * bm_zoom_scale + igv_slicebg
						// .getTop())));

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

		imgv_full_bg = (ImageView) reLayoutView.findViewById(R.id.imgv_full_bg);
		imgv_slice = (ImageView) reLayoutView.findViewById(R.id.imgv_slice);
		imgv_slice_bg = (ImageView) reLayoutView
				.findViewById(R.id.imgv_slice_bg);

		skb_dragCaptcha = (SeekBar) findViewById(R.id.seekbar_def); // “系统默认SeekBar”
		btn_refresh = (Button) findViewById(R.id.btn_refresh);
		tv_validateStatus = (TextView) findViewById(R.id.tv_validateStatus);

	}

	/**
	 * 向服务器请求完整的图片
	 * 
	 * @param relativeUrl
	 */
	private void fullbg_ImageRequest(String relativeUrl) {

		String imgUrl = GtApiEnv.imgServerBase + relativeUrl;

		GtLogger.v(imgUrl);

		ImageRequest bg_imageRequest = new ImageRequest(imgUrl,
				new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap response) {

						// TODO 对图片进行缩放--以充斥全屏
						bm_zoom_scale = getImageZoomScale(response.getWidth());

						seekbar_server_length = response.getWidth();

						imgv_full_bg.setImageBitmap(zoomImage(response,
								bm_zoom_scale));

						slice_bg_ImageRequest(initCaptchaOption.getImgurl());// 再请求切图背景
						// igv_slicebg.setImageBitmap(response);
					}
				}, 0, 0, Config.RGB_565, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						imgv_full_bg.setImageResource(R.drawable.ic_launcher);
					}
				});

		bgImgLoadStartTime = System.currentTimeMillis();// 图片开始请求时记录一个时间节点。

		mQueue.add(bg_imageRequest);
	}

	/**
	 * 获取绽放的比例值
	 * 
	 * @param orginImageWidth
	 * @return
	 */
	private float getImageZoomScale(int orginImageWidth) {

		float zoom_scale = ((intScreenX - leftMargin - rightMargin) * 1000)
				/ (orginImageWidth * 1000.0f);
		GtLogger.v("zoom_scale: " + zoom_scale);

		return zoom_scale;
	}

	/**
	 * 切掉后的大的背景图
	 */
	private void slice_bg_ImageRequest(String relativeUrl) {

		String imgUrl = GtApiEnv.imgServerBase + relativeUrl;

		GtLogger.v(imgUrl);

		ImageRequest bg_imageRequest = new ImageRequest(imgUrl,
				new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap response) {
						// 对图片进行缩放
						imgv_slice_bg.setImageBitmap(zoomImage(response,
								bm_zoom_scale));

						slice_ImageRequest(initCaptchaOption.getSliceurl());// 再请求小切图
						// igv_slicebg.setImageBitmap(response);
					}
				}, 0, 0, Config.RGB_565, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						imgv_slice.setImageResource(R.drawable.ic_launcher);
					}
				});

		mQueue.add(bg_imageRequest);
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

						bgImgLoadEndTime = System.currentTimeMillis();// 背景图加载截止时间

						// 缩放图片数据源

						// bm_zoom_scale = (intScreenX - 80) /
						// response.getWidth();
						imgv_slice.setImageBitmap(zoomImage(response,
								bm_zoom_scale));

						// 设置图片控件的y方向位置
						imgv_slice.scrollTo(-50, getSliceYposAfterSalced());

						GtLogger.v("initCaptchaOption.getYpos():"
								+ initCaptchaOption.getYpos()
								+ "   igv_slicebg.getTop():"
								+ imgv_slice_bg.getTop());

						setImageViewScale(imgv_slice, 300,
								imgv_slice_bg.getHeight());

						// igv_slice.setImageBitmap(response);
					}
				}, 0, 0, Config.RGB_565, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						imgv_slice.setImageResource(R.drawable.ic_launcher);
					}
				});

		mQueue.add(slip_imageRequest);
	}

	/**
	 * 获取等比例缩放后的切片图的Ypos位置
	 * 
	 * @return
	 */
	private int getSliceYposAfterSalced() {

		int scaledYpos = Math.round(-(initCaptchaOption.getYpos()
				* bm_zoom_scale + imgv_slice_bg.getTop()));

		return scaledYpos;

	}

	/**
	 * 设置ImageView的大小（防止图片拖动的时候超过显示范围了）
	 * 
	 * @param imgv
	 * @param imgv_width
	 * @param imgv_height
	 */
	private void setImageViewScale(ImageView imgv, int imgv_width,
			int imgv_height) {
		// 设置图片控件image view的大小
		LayoutParams para;
		para = imgv.getLayoutParams();
		para.width = imgv_width;
		para.height = imgv_height;
		// para.height = igv_slicebg.getHeight();
		imgv.setLayoutParams(para);
	}

	/**
	 * 对位图进行缩放得到新的位图
	 * 
	 * @param bm
	 * @param scale
	 * @return
	 */
	private Bitmap zoomImage(Bitmap bm, float scale) {
		// 获得图片的宽高
		int width = bm.getWidth();
		int height = bm.getHeight();
		// 设置想要的大小
		float newWidth = width * scale;
		float newHeight = height * scale;
		// int newWidth = 320;
		// int newHeight = 480;
		// 计算缩放比例
		float scaleWidth = ((float) newWidth) / width;
		float scaleHeight = ((float) newHeight) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(scaleWidth, scaleHeight);
		// 得到新的图片
		Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
				true);

		return newbm;
	}

	/**
	 * 初始化实验码内容
	 */
	public void captchaInitialOption_StringRequest() {
		// String url = "http://www.baidu.com";
		// 如果出现乱码，应该修改StringRequest的parseNetworkResponse()方法，指定byte[]-->String
		// 编码

		// 这里是一切数据的源头：
		// 1.根据get内容生成URL，请求URL，返回值

		GetPhp_GreqVo getPhp_GreqVo = new GetPhp_GreqVo();
		getPhp_GreqVo.setGt(GtApiEnv.gt_public_key);
		getPhp_GreqVo.setProduct(GtApiEnv.gt_product_type);

		String relApiPath = GtApiEnv.getOptionApi;
		String param = cdtParams(cdtObjectToMap(getPhp_GreqVo));
		String url = genernateApiUrl(relApiPath, param);

		// String url =
		// "http://api.geetest.com/get.php?gt=a40fd3b0d712165c5d13e6f747e948d4&product=embed";

		setImageViewDisplayWhenRefresh();

		StringRequest option_Request = new StringRequest(url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						// GtLogger.v("response:" + response);

						// 硬解码抽取出JSON格式
						String webJsFunction[] = response.split("=");
						String optionValues[] = webJsFunction[1].split(";");
						String optionValue = optionValues[0];
						// GtLogger.v(optionValue);

						// 解析成对象
						Gson gson = new Gson();
						initCaptchaOption = gson.fromJson(optionValue,
								CaptchaOption.class);

						GtLogger.v("getFullbg : "
								+ initCaptchaOption.getFullbg());

						// 请求动态图片
						fullbg_ImageRequest(initCaptchaOption.getFullbg());

						// 重置SeekBar
						skb_dragCaptcha.setProgress(0);
						tv_validateStatus.setText("等待验证");

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}
				});

		mQueue.add(option_Request);
	}

	/**
	 * Map转url的参数
	 * 
	 * @param map
	 * @return
	 */
	private String cdtParams(Map<String, Object> map) {
		// 处理参数
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		Set<String> keys = map.keySet();
		for (String key : keys) {
			params.add(new BasicNameValuePair(key, map.get(key).toString()));
		}

		// 将参数转换
		String paramString = URLEncodedUtils.format(params, "UTF-8");

		GtLogger.v(paramString);

		return paramString;
	}

	/**
	 * 将对象转成Map,以实现对url参数的编码
	 * 
	 * @return
	 */
	private Map<String, Object> cdtObjectToMap(Object getParaObj) {

		Map<String, Object> mapPara = new HashMap<String, Object>();

		try {
			Class<?> cla = getParaObj.getClass();

			Field[] f = cla.getDeclaredFields();
			for (Field field : f) {
				field.setAccessible(true);
				mapPara.put(field.getName(), field.get(getParaObj));

				// GtLogger.v(field.getName() + " : " + field.get(getParaObj));

			}
			// System.out.println("属性="+field.toString());
			// System.out.println("数据类型＝"+field.getType());
			// System.out.println("属性名＝"+field.getName());
			// int mod=field.getModifiers();
			// System.out.println("属性修饰符＝"+Modifier.toString(mod));
			//
			// for (int i = 0; i < m.length; i++) {
			// if (m[i].getName().indexOf("get")==0) {
			// //System.out.println("方法名："+m[i].getName());
			// // System.out.println("值："+ m[i].invoke(obj, new Object[0]));
			// hashMap.put(m[i].getName(), m[i].invoke(obj, new Object[0]));
			// }
			// }
		} catch (Throwable e) {
			System.err.println(e);
		}

		return mapPara;

	}

	/**
	 * 生成带参数的完整的API的URL
	 * 
	 * @param relApiPath
	 * @param param
	 * @return
	 */
	private String genernateApiUrl(String relApiPath, String param) {
		try {

			URI url = URIUtils.createURI(GtApiEnv.httpType,
					GtApiEnv.gtApiBaseUrl, GtApiEnv.gtApiPort, relApiPath,
					param, null);

			GtLogger.v("ApiFullUrl: " + url.toString());

			return url.toString();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成带参数的完整的API的URL
	 * 
	 * @param relApiPath
	 * @param param
	 * @return
	 */
	private String genernateTestApiUrl(String relApiPath, String param) {
		try {

			String localBaseUrl = "192.168.2.66";
			URI url = URIUtils.createURI(GtApiEnv.httpType, localBaseUrl,
					GtApiEnv.gtApiPort, relApiPath, param, null);

			GtLogger.v("ApiFullUrl: " + url.toString());

			return url.toString();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 生成的固定的用于测试的数据--在正常发布时没有什么用。
	 */
	private void generateUserActionsBehaviour() {
		// // 第一组数据，比较特殊，时间起点必须为0
		CaptchaUserAction fistAction = new CaptchaUserAction(-30, -20, 0);
		userActions.add(fistAction);

		// 制造随机假数据： 用户行为数据
		for (int i = 1; i < 11; i++) {
			CaptchaUserAction userAction = new CaptchaUserAction();

			userAction.setxPos(2 * i);

			// Random random = new Random(2);
			// userAction.setyPos(random.nextInt());// 产生随机数

			userAction.setyPos(1);

			userAction.setTimeIncrement(10 * (i + 1));

			GtLogger.v(userAction.getxPos() + "," + userAction.getyPos() + ","
					+ userAction.getTimeIncrement());

			userActions.add(userAction);
		}
	}

	/**
	 * 获取鼠标放开时，滑块的偏移量
	 * 
	 * @return
	 */
	private int getSliderBarOffset() {

		// sliderOffsetX = 12;
		// TODO 使用的假数据：需要获取鼠标放开时，滑块相对产生的对初始位置的偏移量,可能需要做一些修改工作
		GtLogger.v("sliderOffsetX: " + sliderOffsetX);
		return sliderOffsetX;

	}

	/**
	 * 上传用户的行为数据
	 */
	public void userBehaviourUpload_StringRequest() {

		// 行为数据的视觉上混淆工作
		// String challenge = "0accdbb7cda7c8a11f182cd28f6c2c245v";
		// String challenge = initCaptchaOption.getChallenge();

		// 用户交互的x坐标答案
		DecodedChallenge decodedChallenge = new DecodedChallenge(
				initCaptchaOption.getChallenge());// 解码原始的challenge

		int userXpos = getSliderBarOffset();

		String encodeUserResponse = GtDataConvert.EnCryptUserResponse(userXpos,
				decodedChallenge);

		String encodeUserActions = GtDataConvert.EncryptUserAction(userActions);

		GtLogger.v("userResponse:  " + encodeUserResponse);
		GtLogger.v("a:  " + encodeUserActions);

		ajaxPhp_GreqVo = new AjaxPhp_GreqVo();
		ajaxPhp_GreqVo.setApi(GtApiEnv.ajaxApiName);
		ajaxPhp_GreqVo.setChallenge(initCaptchaOption.getChallenge());
		ajaxPhp_GreqVo.setUserresponse(encodeUserResponse);
		ajaxPhp_GreqVo.setPasstime((int) (seekbarEndTime - seekbarStartTime));
		ajaxPhp_GreqVo
				.setImgload((int) (bgImgLoadEndTime - bgImgLoadStartTime));
		ajaxPhp_GreqVo.setA(encodeUserActions);

		GtLogger.v(ajaxPhp_GreqVo.getA());

		// 对象转Map,Map编码成List
		String relApiPath = GtApiEnv.ajaxSubmitApi;
		String param = cdtParams(cdtObjectToMap(ajaxPhp_GreqVo));
		String optionApiUrl = genernateApiUrl(relApiPath, param);

		// TODO 使用的是内部的测试数据
		optionApiUrl = genernateTestApiUrl("/gtapp_ajax", param);

		StringRequest option_Request = new StringRequest(optionApiUrl,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {

						try {
							GtLogger.v("userBehaviourUpload_StringRequest   response:"
									+ response);

							// 对response 硬解码成Json
							// var GeeTest_tempData={success: 0,message:"fail"}
							String resultAry[] = response.split("=");

							AjaxPhp_GresVo ajaxPhp_GresVo = new AjaxPhp_GresVo();

							Gson gson = new Gson();
							ajaxPhp_GresVo = gson.fromJson(resultAry[1],
									AjaxPhp_GresVo.class);

							// 如果返回的是成功的验证结果
							if (ajaxPhp_GresVo.getSuccess().equals("1")) {

								// 对验证结果硬解码
								String resultArray[] = ajaxPhp_GresVo
										.getMessage().split("\\|");

								messageResult = resultArray[0];// 验证结果值
								String actionRank = resultArray[1];// 验证行为排名占比

								double orginActionTime = (seekbarEndTime - seekbarStartTime) / 1000.0;
								// 四舍五入保留一位小数
								float convertActionTime = (float) (Math
										.round(orginActionTime * 10)) / 10;// (这里的100就是2位小数点,如果要其它位,如4位,这里两个100改成10000)

								GtLogger.v("验证成功！    " + convertActionTime
										+ "秒的速度超过"
										+ (100 - Integer.parseInt(actionRank))
										+ "%的用户");

								GtLogger.v(" messageResult: " + messageResult
										+ " actionRank: " + actionRank);

								tv_validateStatus.setText("验证成功");
								// 如果客户端已经验证成功了，那么再向客户服务器提交请求，进行服务器再查询验证请求
								postCaptchaInfoToCustomServer();

							} else {
								// 验证失败后，就不需要向客户机发起请求二次验证了
								GtLogger.v("验证错误");
								tv_validateStatus.setText("验证失败：拖动滑块使悬浮图像正确拼合");

								// TODO 在界面上交替闪烁--后面采用线程的方式进行
								SetImgStatusAfterFailed he = new SetImgStatusAfterFailed();
								Thread demo = new Thread(he, "Action");
								demo.start();

							}
						} catch (Exception e) {
							GtLogger.expection(LoggerString.getFileLineMethod()
									+ e.getMessage());
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}
				});

		mQueue.add(option_Request);
	}

	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			switch (msg.what) {
			case MSG_FULL_BG_DISPLAY:
				GtLogger.v("背景");
				setImageViewDisplayWhenRefresh();
				break;

			case MSG_SLICE_BG_DISPLAY:
				GtLogger.v("切图");
				setImageViewDisplayWhenDragSlider();
				break;

			default:
				break;
			}

			super.handleMessage(msg);
		}
	};

	/**
	 * 设置 验证失败后的图片显示设置的线程
	 * 
	 * @author Zheng 2014年5月22日 上午9:34:39
	 */
	class SetImgStatusAfterFailed implements Runnable {
		public void run() {
			try {

				int gapTime = 100;

				// 交替闪烁
				Message msg = mHandler.obtainMessage();
				msg.what = MSG_FULL_BG_DISPLAY;
				msg.sendToTarget();
				Thread.sleep(gapTime);

				msg = mHandler.obtainMessage();
				msg.what = MSG_SLICE_BG_DISPLAY;
				msg.sendToTarget();
				Thread.sleep(gapTime);

				msg = mHandler.obtainMessage();
				msg.what = MSG_FULL_BG_DISPLAY;
				msg.sendToTarget();
				Thread.sleep(gapTime);

				msg = mHandler.obtainMessage();
				msg.what = MSG_SLICE_BG_DISPLAY;
				msg.sendToTarget();
				Thread.sleep(gapTime);

				GtLogger.v("验证失败后的图片线程");
				System.out.println(Thread.currentThread().getName());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 向客户机的服务器发起 二重验证提醒
	 */
	public void postCaptchaInfoToCustomServer() {

		try {

			String customServerGtApiUrl = "http://192.168.2.66:8000/gtapp_submit/";

			StringRequest stringRequest = new StringRequest(
					Request.Method.POST, customServerGtApiUrl,
					new Response.Listener<String>() {

						public void onResponse(String response) {

							try {

								// TODO 安卓客户端接收到消息后进行相应的处理
								GtLogger.v("postCaptchaInfoToCustomServer:  "
										+ response);

							} catch (Exception e) {
								GtLogger.expection(LoggerString
										.getFileLineMethod() + e.getMessage());
							}

						}
					}, new Response.ErrorListener() {

						public void onErrorResponse(VolleyError arg0) {

							GtLogger.v(LoggerString.getFileLineMethod()
									+ arg0.getMessage());
						}
					}) {

				@Override
				protected Map<String, String> getParams() {
					Map<String, String> params = new HashMap<String, String>();

					GtCustomerSubmit gtCustomerSubmit = new GtCustomerSubmit();
					// 构造的假数据
					// gtCustomerSubmit
					// .setGeetest_challenge("ad7ba4518b5ed270268bf736c723f935cz");
					// gtCustomerSubmit
					// .setGeetest_validate("30f58f0de5faa14dc78ffe6c067969fc");
					// gtCustomerSubmit
					// .setGeetest_seccode("30f58f0de5faa14dc78ffe6c067969fc|jordan");

					gtCustomerSubmit.setGeetest_challenge(ajaxPhp_GreqVo
							.getChallenge());
					gtCustomerSubmit.setGeetest_validate(messageResult);
					gtCustomerSubmit.setGeetest_seccode(messageResult + "\\|"
							+ GtApiEnv.captChaType);

					Gson gson = new Gson();
					String postJsonString = gson.toJson(gtCustomerSubmit);

					// 将客户端的信息编码成一个Json串，然后上传到客户服务器
					params.put("captcha_info", postJsonString);

					GtLogger.v("postJsonString: " + postJsonString);

					return params;
				}

			};

			// 设置请求超时时间5s：http://blog.csdn.net/xyz_lmn/article/details/12177005
			stringRequest.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 1,
					1.0f));
			mQueue.add(stringRequest);

		} catch (Exception e) {
			GtLogger.expection(LoggerString.getFileLineMethod()
					+ e.getMessage());
		}

	}

}
