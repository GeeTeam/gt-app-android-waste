package com.geetest.gtappdemo;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Matrix;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.TextureView;
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
import com.geetest.gtapp.utils.data.LoggerTag;
import com.geetest.gtappdemo.model.gconstant.GtApiEnv;
import com.geetest.gtappdemo.model.vo.CaptchaOption;
import com.geetest.gtappdemo.model.vo.greq.AjaxPhp_GreqVo;
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

	private Button btn_refresh;// 用于刷新图片的按钮

	private ImageView igv_slice;// 用于拖动的小切片图
	private ImageView igv_slicebg;// 被切掉后的切图背景
	private ImageView igv_fullbg;// 完整的背景

	private Bitmap bm_slice;
	private Bitmap bm_slicebg;
	private Bitmap bm_fullbg;

	private TextView tv_validateStatus;// 验证码的状态栏

	private float bm_zoom_scale = 1;// 图片缩放比例

	private SeekBar skb_dragCaptcha;// 拖动的seekbar

	private RelativeLayout reLayoutView;// 相框的相对布局

	private RequestQueue mQueue;// 用于Volley的通讯内容

	// 验证通讯数据对象
	private CaptchaOption initCaptchaOption;// 验证码初始化验证数据设置
	private AjaxPhp_GreqVo ajaxPhp_GreqVo;// 上传行为数据的API参数

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

						// TODO 向服务器提交行为数据
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

						// 将屏幕的X坐标进行均分
						float dX = (intScreenX - intWidth) / 100;

						// Log.v("seekbar", ("当前进度：" + progress + "%"));
						// TODO 坐标偏移
						// igv_slice.scrollTo((int) (-dX * progress), (int)
						// (0));// 进行偏移

						igv_slice.scrollTo((int) (-dX * progress),
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

		igv_slice = (ImageView) reLayoutView.findViewById(R.id.imgv_slice);
		igv_slicebg = (ImageView) reLayoutView.findViewById(R.id.imgv_slice_bg);

		skb_dragCaptcha = (SeekBar) findViewById(R.id.seekbar_def); // “系统默认SeekBar”
		btn_refresh = (Button) findViewById(R.id.btn_refresh);
		tv_validateStatus = (TextView) findViewById(R.id.tv_validateStatus);

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

						// 缩放图片数据源

						// bm_zoom_scale = (intScreenX - 80) /
						// response.getWidth();
						igv_slice.setImageBitmap(zoomImage(response,
								bm_zoom_scale));

						// TODO 设置图片控件的y方向位置
						igv_slice.scrollTo(-50, getSliceYposAfterSalced());

						GtLogger.v("initCaptchaOption.getYpos():"
								+ initCaptchaOption.getYpos()
								+ "   igv_slicebg.getTop():"
								+ igv_slicebg.getTop());

						setImageViewScale(igv_slice, 300,
								igv_slicebg.getHeight());

						// igv_slice.setImageBitmap(response);
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
	 * 获取等比例缩放后的切片图的Ypos位置
	 * 
	 * @return
	 */
	private int getSliceYposAfterSalced() {

		int scaledYpos = Math.round(-(initCaptchaOption.getYpos()
				* bm_zoom_scale + igv_slicebg.getTop()));

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
	 * 切掉后的大的背景图
	 */
	private void fullbg_ImageRequest(String relativeUrl) {

		String imgUrl = GtApiEnv.imgServerBase + relativeUrl;

		GtLogger.v(imgUrl);

		ImageRequest bg_imageRequest = new ImageRequest(imgUrl,
				new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap response) {

						// 对图片进行缩放
						bm_zoom_scale = (intScreenX - 80) / response.getWidth();
						GtLogger.v("bm_zoom_scale: " + bm_zoom_scale);
						igv_slicebg.setImageBitmap(zoomImage(response,
								bm_zoom_scale));

						// igv_slicebg.setImageBitmap(response);
					}
				}, 0, 0, Config.RGB_565, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						igv_slice.setImageResource(R.drawable.ic_launcher);
					}
				});

		mQueue.add(bg_imageRequest);
	}

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

						// 请求动态图片
						fullbg_ImageRequest(initCaptchaOption.getImgurl());
						slice_ImageRequest(initCaptchaOption.getSliceurl());

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
		return URLEncodedUtils.format(params, "UTF-8");
	}

	/**
	 * 将对象转成Map
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

	private String genernateApiUrl(String relApiPath, String param) {
		try {

			URI url = URIUtils.createURI(GtApiEnv.httpType,
					GtApiEnv.gtApiBaseUrl, GtApiEnv.gtApiPort, relApiPath,
					param, null);

			return url.toString();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * 上传用户的行为数据
	 */
	public void userBehaviourUpload_StringRequest() {

		// TODO 行为数据的视觉上混淆工作
		int userXpos = 23;

		GtLogger.v("userXpos:  "
				+ GtDataConvert.EnCryptUserResponse("" + userXpos));
		

		ajaxPhp_GreqVo = new AjaxPhp_GreqVo();

		// TODO 使用的假数据
		ajaxPhp_GreqVo.setApi("jordan");
		ajaxPhp_GreqVo.setChallenge(initCaptchaOption.getChallenge());
		ajaxPhp_GreqVo.setUserresponse("22b222dd48");
		ajaxPhp_GreqVo.setPasstime(2702);
		ajaxPhp_GreqVo.setImgload(117);
		// ajaxPhp_GreqVo
		// .setA("s$adcdefg");
		ajaxPhp_GreqVo
				.setA("s$$$o9%27A:?;:J::::::J::::JJ::::J:J:K:J:JI:::J:J9$$$%27!N(N*A*42+73+7060.89-.77!P(G06!U(777JoJ/!E(!r(Kn!Q)nJ!t6");

		GtLogger.v(ajaxPhp_GreqVo.getA());

		// 对象转Map

		// Map编码成List

		String relApiPath = "/ajax.php";
		String param = cdtParams(cdtObjectToMap(ajaxPhp_GreqVo));

		// GtLogger.v(param);

		String optionApiUrl = genernateApiUrl(relApiPath, param);

		GtLogger.v(optionApiUrl);

		StringRequest option_Request = new StringRequest(optionApiUrl,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {
						GtLogger.v("userBehaviourUpload_StringRequest   response:"
								+ response);

						// TODO 先使用假的数据 来做测试接口 2014年5月15日 12:39:42
						AjaxPhp_GresVo ajaxPhp_GresVo = new AjaxPhp_GresVo();

						// String messageString = ajaxPhp_GresVo.getMessage();

						// GtLogger.v(messageString);
						// 对验证结果硬解码
						String resultArray[] = ajaxPhp_GresVo.getMessage()
								.split("\\|");

						// String webJsFunction[] = response.split("=");

						String messageResult = resultArray[0];// 验证结果值
						String actionRank = resultArray[1];// 验证行为排名

						if (ajaxPhp_GresVo.getSuccess().equals("1")) {
							GtLogger.v("验证成功！    " + "TODO秒的速度超过"
									+ (100 - Integer.parseInt(actionRank))
									+ "%的用户");

							tv_validateStatus.setText("验证成功");

							// TODO 如果客户端已经验证成功了，那么再向客户服务器提交请求，进行服务器再查询验证请求
							postCaptchaInfoToCustomServer();

						} else {
							GtLogger.v("验证失败");
							tv_validateStatus.setText("验证失败");
						}

						GtLogger.v(" messageResult: " + messageResult
								+ " actionRank: " + actionRank);

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {

					}
				});

		mQueue.add(option_Request);
	}

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
								GtLogger.v(LoggerString.getFileLineMethod()
										+ e.getMessage());
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
					// TODO 构造的假数据
					gtCustomerSubmit
							.setGeetest_challenge("ad7ba4518b5ed270268bf736c723f935cz");
					gtCustomerSubmit
							.setGeetest_validate("30f58f0de5faa14dc78ffe6c067969fc");
					gtCustomerSubmit
							.setGeetest_seccode("30f58f0de5faa14dc78ffe6c067969fc|jordan");

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
			GtLogger.v(LoggerString.getFileLineMethod() + e.getMessage());
		}

	}

}
