package com.geetest.gtappdemo.view;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.geetest.gtapp.R;
import com.geetest.gtapp.gtlog.GtLogger;
import com.geetest.gtapp.gtlog.vo.SdkInfo;
import com.geetest.gtapp.utils.GtDataConvert;
import com.geetest.gtapp.utils.LoggerString;
import com.geetest.gtapp.utils.itface.GtAppCallback;
import com.geetest.gtappdemo.model.gconstant.GtApiEnv;
import com.geetest.gtappdemo.model.svo.ImageLoadTimeNode;
import com.geetest.gtappdemo.model.svo.LogMsgTag;
import com.geetest.gtappdemo.model.svo.SdkRunInfo;
import com.geetest.gtappdemo.model.svo.UiElementSize;
import com.geetest.gtappdemo.model.vo.CaptchaOption;
import com.geetest.gtappdemo.model.vo.CaptchaUserAction;
import com.geetest.gtappdemo.model.vo.DecodedChallenge;
import com.geetest.gtappdemo.model.vo.GtAppCbCaptchaResponse;
import com.geetest.gtappdemo.model.vo.GtAppDialogOption;
import com.geetest.gtappdemo.model.vo.GtPoint;
import com.geetest.gtappdemo.model.vo.GtSdkVersionInfo;
import com.geetest.gtappdemo.model.vo.GtShapeSize;
import com.geetest.gtappdemo.model.vo.greq.AjaxPhp_GreqVo;
import com.geetest.gtappdemo.model.vo.greq.GetPhp_GreqVo;
import com.geetest.gtappdemo.model.vo.gres.AjaxPhp_GresVo;
import com.geetest.gtappdemo.model.vo.preq.GtCustomerSubmit;
import com.geetest.gtappdemo.view.GtRefreshableView.PullToRefreshListener;
import com.google.gson.Gson;

/**
 * 弹出的对话框
 * 
 * @author Zheng 2014年5月30日 下午2:22:38
 */
public class GtAppDialog extends Dialog {

	// 传递进来的参数
	private Context context;// 父窗口
	private int gtAppDlgLayoutResId = 0;// 对话框的而已文件
	private DisplayMetrics dm;// 显示屏幕
	private Resources res;
	private GtAppCallback gtAppCallback;// 回调函数

	private String gt_public_key = "";// 公钥

	// 界面元素信息
	// private View dlgView;

	// private Animation animation;

	private RelativeLayout firstReLayoutView;// 最外层布局
	private FrameLayout flView_ImageFrame;// 相框的相对布局
	private FrameLayout flView_self_slider;// 自定义滑块布局
	// private RelativeLayout reLayoutView;// 相框的相对布局
	private LinearLayout fl_slider_string_tip;// 提示滑动布局
	private GtAppNeonLightTip gtAppNeonLightTip;// 滑动霓虹灯

	private LinearLayout beforeGetImageLineraLayout;
	private LinearLayout afterGetImageLineraLayout;

	private ImageView imgv_full_bg;// 完整的背景
	private ImageView imgv_slice;// 用于拖动的小切片图
	private ImageView imgv_slice_bg;// 被切掉后的切图背景
	private ImageView imgv_flashlight;// 闪电图
	private ImageView imgv_self_touch_slice;// 自定义的图片

	private ImageView imgv_skb_anim_tip;// 滑动条操作提示
	private ImageView imgv_change_image;// 换图按钮
	private GtRefreshableView refreshableView;// 下拉刷新

	private ImageView imgv_captcha_status_icon;// 状态锁
	private TextView tv_validateStatus;// 验证码的状态栏
	private TextView tv_validateMsg;// 验证码的消息栏
	private GtAppStatusBar gtStatusBar;// 状态栏

	private Button btn_dlg_close;// 对话框关闭按钮

	private Button btn_refresh;// 用于刷新图片的按钮
	private Button btn_help;// 使用帮助按钮
	private Button btn_about;// "关于"按钮
	private SeekBar skb_dragCaptcha;// 拖动的seekbar
	private TextView tv_slider_tip_msg;// 滑动提示文字

	private RequestQueue mQueue;// 用于Volley的通讯内容
	private Animation anim_skb_finger_tip;// 使用提示动画
	private Animation anim_flashlight;// 闪电图
	// /********************下面是数据区

	// 图片对象
	private Bitmap bm_full_bg;
	private Bitmap bm_slice_bg;
	private Bitmap bm_slice;

	// 验证通讯数据对象
	private CaptchaOption initCaptchaOption;// 验证码初始化验证数据设置
	private AjaxPhp_GreqVo ajaxPhp_GreqVo;// 上传行为数据的API参数

	// 滑块交互用的时间
	private long seekbarStartTime = 0;// 按下时
	private long seekbarEndTime = 0;// 放开时

	// 图片加载的起始时间
	// private long bgImgLoadStartTime = 0;
	// private long bgImgLoadEndTime = 0;

	private String messageResult = "";// Gt服务器返回的验证结果

	// handle里面的消息代号
	private final int MSG_FULL_BG_DISPLAY = 1;// 完整图片显示
	private final int MSG_SLICE_BG_DISPLAY = 2;// 切片图显示
	private final int MSG_SLICE_BG_ALPHA_MISS = 3;// 切片图渐变消失
	private final int MSG_OPTION_DATA = 4;// 初次请求数据
	private final int MSG_BIND_DATA = 5;// 将数据绑定到界面
	private final int MSG_ANIM_FLASH = 6;// 闪动图片
	private final int MSG_ANIM_TIP_MISS = 7;// 提示动画消失

	// 滑块坐标位置
	private GtPoint sliderStartLeftTopPosition = new GtPoint();// 滑块左上角坐标
	private GtPoint sliderStartPressTouchPosition = new GtPoint();// 按下滑块时的触点位置
	private GtPoint sliderDragMoveTouchPosition = new GtPoint();// 拖动时触点位置
	private GtPoint sliderUpTouchPosition = new GtPoint();// 放开时触点位置

	// 下面全部是自定义的内容
	private GtPoint sliderStartLeftTopPosition_self = new GtPoint();// 自定义滑块左上角坐标
	private GtPoint sliderStartPressTouchPosition_self = new GtPoint();// 按下滑块时的触点位置
	private GtPoint sliderDragMoveTouchPosition_self = new GtPoint();// 拖动时触点位置
	private GtPoint sliderUpTouchPosition_self = new GtPoint();// 放开时触点位置

	// 切换图片的滑动条
	private GtPoint changeImageButtonStartPosition = new GtPoint();// 按下时触点位置

	// 形状大小
	private GtShapeSize thumbBmpSize = new GtShapeSize();// 滑块图片的大小
	private GtShapeSize screenSize = new GtShapeSize();// 屏幕分辨率大小

	private float bm_zoom_scale = 1;// 图片缩放比例

	// 图片显示控制
	private int slice_img_alpha = 255;// 切片图的透明度
	private Boolean isrung = true;

	// 用户行为数据
	private int sliderOffsetX = 12;// 滑块x方向偏移量
	private ArrayList<CaptchaUserAction> userActions = new ArrayList<CaptchaUserAction>();// 用户行为数据的数组

	/**
	 * 客户端的验证结果
	 */
	private Boolean clientCaptchaResult = false;
	private Boolean picRequestSucceed = false;// 图片是否加载成功

	// private String logTag = "testImageView";

	// 图片展示区离屏幕边缘的距--和布局文件的设计有关
	private int leftMargin = 0;// dp
	private int rightMargin = 0;// dp

	// 软件行为参数收集
	// private HostInfo hostInfo = new HostInfo();
	private ImageLoadTimeNode imgLoadTimeStamp = new ImageLoadTimeNode();// 中间时间节点记录
	private UiElementSize uiSize = new UiElementSize();// 界面元素的高度收集--方便做一些适配工作
	private ArrayList<int[]> actionArrayList = new ArrayList<int[]>();// 一个数组
	// private GtLogger GtLogger;// 向服务器提交运行日志数据的类
	private Gson gson = new Gson();// 第三方的JSON解析和打包库

	// TODO 下拉刷新加载效果
	// private ScrollView mScrollView;// 可以滑动操作的视图
	// private PullToRefreshScrollView mPullScrollView;// 刷新的加载效果视图
	// private SimpleDateFormat mDateFormat = new
	// SimpleDateFormat("MM-dd HH:mm");

	private float mX, mY;// 触点的位置

	public GtAppDialog(GtAppDialogOption option, RequestQueue mQueue) {
		super(option.getContext());

		imgLoadTimeStamp.setDlg_open_time(getCurTimeTag());

		this.context = option.getContext();
		this.mQueue = mQueue;// 必须多activity中传递进来
		// mQueue = Volley.newRequestQueue(context);// 必须在界面初始化之后才有此声明

		this.gt_public_key = option.getGt_public_key();
		this.gtAppDlgLayoutResId = option.getGtAppDlgLayoutResId();
		this.dm = option.getDm();
		this.res = option.getRes();
		this.gtAppCallback = option.getGtAppCallback();
		// 收集一些测试信息--用于打印中间字段--一个app中只需要设置一次就OK了
		GtLogger.setContext(context);
		GtLogger.setSdkInfo(getSdkInfo());

		requestSdkVersionFromServer();
	}

	/**
	 * 在初始化时 获取 宿主APP的信息
	 * 
	 * @time 2014年7月2日 下午11:57:20
	 * @return
	 */
	private SdkInfo getSdkInfo() {
		SdkInfo sdkInfo = new SdkInfo();
		sdkInfo.setGt_public_key(gt_public_key);
		sdkInfo.setGtapp_sdk_version(GtApiEnv.getSdkVersionInfo());
		return sdkInfo;
	}

	/**
	 * 设置显示方式
	 */
	public void setDisplay() {

		try {

			requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉对话框的标题
			setCanceledOnTouchOutside(false);// 在外面点击不会消失

			setContentView(gtAppDlgLayoutResId);// 设置资源内容
			initViews();
			initViewDisplayParameter();
			initListeners();

			// setTitle("GtDialog");

			setLocation();
			show();
			imgLoadTimeStamp.setDlg_show_time(System.currentTimeMillis());

			sendMsgToUpdateUI(MSG_OPTION_DATA);

		} catch (Exception e) {
			GtLogger.ex(LoggerString.getFileLineMethod() + e.getMessage());
		}

	}

	/**
	 * 获取当前时间tag
	 * 
	 * @time 2014年7月9日 上午10:58:45
	 * @return
	 */
	private long getCurTimeTag() {
		return System.currentTimeMillis();
	}

	/**
	 * 检查SDK的版本
	 * 
	 * @time 2014年6月17日 下午3:17:36
	 */
	public void requestSdkVersionFromServer() {
		final long start_time = getCurTimeTag();
		// mQueue = Volley.newRequestQueue(context);// 必须在界面初始化之后才有此声明

		int randomNum = new Random().nextInt(1000) + 1;// 访问静态资源时需要随机数,避免缓存

		String url = GtApiEnv.sdkNewestVersionInfoLink + "?randomNum="
				+ randomNum;

		StringRequest sdkVersion_Request = new StringRequest(url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {

						try {
							// 解码抽取出JSON格式
							GtLogger.s_v("201479_142023", getCurTimeTag()
									- start_time);
							GtSdkVersionInfo serverSdkVersionInfo = new GtSdkVersionInfo();
							// 解析成对象
							serverSdkVersionInfo = gson.fromJson(response,
									GtSdkVersionInfo.class);
							// 判断当前的版本问题
							if (serverSdkVersionInfo.getVerCode() > GtApiEnv.sdkVersionCode) {
								// 做一次服务器比对
								GtLogger.e("当前SDK版本比较落后,请到极验官网服务器下载并集成GtApp最新版本");
							}
						} catch (Exception e) {
							GtLogger.ex(LoggerString.getFileLineMethod()
									+ e.getMessage());
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						GtLogger.ex(LoggerString.getFileLineMethod()
								+ error.getMessage());
					}
				});

		mQueue.add(sdkVersion_Request);

	}

	/**
	 * 向Gt服务器请求初始化数据
	 */
	private void requestOptionDataFromGtServer() {
		captchaInitialOption_StringRequest();
	}

	/**
	 * 将服务器请求的数据绑定到本地的界面中
	 */
	private void bindOptionDataToLocalViews() {

		imgv_full_bg.setImageBitmap(bm_full_bg);
		imgv_slice_bg.setImageBitmap(bm_slice_bg);
		imgv_slice.setImageBitmap(bm_slice);

		// 设置图片控件的y方向位置
		// imgv_slice.scrollTo(-50, getSliceYposAfterSalced());

		setImageViewDisplayWhenRefresh();

		beforeGetImageLineraLayout.setVisibility(View.GONE);
		afterGetImageLineraLayout.setVisibility(View.VISIBLE);

		// 重置SeekBar
		skb_dragCaptcha.setProgress(0);
		tv_slider_tip_msg.setVisibility(View.VISIBLE);// 拖动的提示文字消失
		imgv_skb_anim_tip.setVisibility(View.VISIBLE);// 拖动手指显示
		imgv_flashlight.setVisibility(View.INVISIBLE);// 闪电图在开始时不可见
		imgv_self_touch_slice.scrollTo(0, 0);// 重置到最左边，滑块

		clientCaptchaResult = false;// 最开始状态是为不通过的。
		skb_dragCaptcha.setEnabled(true);

		gtStatusBar.setToWaitStatus();

		GtLogger.v("initCaptchaOption.getYpos():" + initCaptchaOption.getYpos()
				+ "   igv_slicebg.getTop():" + imgv_slice_bg.getTop());

		setImageViewScale(imgv_slice, bm_full_bg.getWidth(),
				bm_full_bg.getHeight());

		setImageViewScale(imgv_flashlight, bm_full_bg.getWidth(),
				bm_full_bg.getHeight());// 设置闪电图

		// igv_slice.setImageBitmap(response);

		// 在图片全部加载完毕后，获取一些基本的参数值
		setThumbBmpSize();
		setSliderStartLeftTopPosition();
		setSelf_touch_StartLeftTopPosition();

		sendMsgToUpdateUI(MSG_FULL_BG_DISPLAY);

		imgv_skb_anim_tip.startAnimation(anim_skb_finger_tip);

		picRequestSucceed = true;

		// 将通讯相关的性能参数值上传
		GtLogger.s_v(LogMsgTag.imageLoadCycle,
				imgLoadTimeStamp.getImageLoadTimeCycle());

		postSdkRunInfo();
	}

	private void initViewDisplayParameter() {
		getScreenSize();

		// 设置图片点位 默认长宽
		setImageViewDefaultDisplay();
	}

	/**
	 * 将设置的图片放大到指定的大小，然后占位
	 */
	private void setImageViewDefaultDisplay() {

		try {
			// 做一个宽高比例为29*6的背景图片来占位
			// 获取图片资源的宽和高

			// BitmapDrawable bm_full_bg_occupy = (BitmapDrawable) res
			// .getDrawable(R.drawable.full_bg);
			//
			// int bm_full_bg_occupy_width = bm_full_bg_occupy.getBitmap()
			// .getWidth();
			//
			// GtLogger.v("bm_full_bg_occupy_width: " + bm_full_bg_occupy_width
			// + "  height: " + bm_full_bg_occupy.getBitmap().getHeight());
			//
			// // 获得占位图片的缩放比例
			//
			// // 重新设置imageView的大小
			// imgv_full_bg.setImageBitmap(zoomImage(
			// bm_full_bg_occupy.getBitmap(), skb_dragCaptcha.getWidth()
			// / bm_full_bg_occupy_width));

			// LayoutParams para;
			// para = imgv_full_bg.getLayoutParams();
			//
			// para.height = skb_dragCaptcha.getWidth() * 26 / 9;
			// para.width = skb_dragCaptcha.getWidth();
			// imgv_full_bg.setLayoutParams(para);

			// 设置默认的初始的画框大小
			// int seekbar_width = skb_dragCaptcha.getWidth();
			// setImageViewScale(imgv_full_bg, seekbar_width, seekbar_width * 9
			// /
			// 26);

		} catch (Exception e) {
			GtLogger.ex(LoggerString.getFileLineMethod() + e.getMessage());
		}

	}

	/**
	 * 获取获取屏幕的大小分辨率值
	 */
	private void getScreenSize() {
		// // 取得屏幕对象
		// DisplayMetrics dm = new DisplayMetrics();
		// getWindowManager().getDefaultDisplay().getMetrics(dm);

		/* 取得屏幕解析像素 */

		screenSize.setWidth(dm.widthPixels);
		screenSize.setHeight(dm.heightPixels);

	}

	/**
	 * 收集参数运行信息
	 * 
	 * @time 2014年6月30日 下午7:17:27
	 */
	private void postSdkRunInfo() {
		SdkRunInfo sdkRunInfo = new SdkRunInfo();

		sdkRunInfo.setTimeCycle(imgLoadTimeStamp.getImageLoadTimeCycle());
		sdkRunInfo.setUiSize(uiSize);

		// 如果是上传成字符串到字段中，则使用下面的语句
		// String strMsg = gson.toJson(sdkRunInfo);
		GtLogger.s_v(LogMsgTag.sdkRunInfo, sdkRunInfo);

		// 这是第一次加载时的元素信息
		GtLogger.s_v(LogMsgTag.elementFirstLoadTime,
				imgLoadTimeStamp.getRelativeTimeNode());

	}

	/**
	 * 获取滑块左上角的坐标点--并转化成skb的坐标体系
	 */
	private void setSliderStartLeftTopPosition() {

		GtPoint skbPositon = new GtPoint();// 滑条的左边中点的坐标值
		skbPositon.setX(skb_dragCaptcha.getLeft());
		skbPositon
				.setY((skb_dragCaptcha.getBottom() + skb_dragCaptcha.getTop()) / 2);

		// 将滑块的坐标体系从 屏幕坐标体系 转成 seekbar坐标体系
		sliderStartLeftTopPosition.setX(skbPositon.getX());
		sliderStartLeftTopPosition.setY(skbPositon.getY()
				- thumbBmpSize.getHeight() / 2 - skb_dragCaptcha.getTop());

		// Debug: 收集界面运行的信息
		uiSize.setSkb_dragCaptcha_height("滑块高度Hieght: "
				+ (skb_dragCaptcha.getBottom() - skb_dragCaptcha.getTop()));
		uiSize.setSkb_dragCaptcha_position("滑动条Bar坐标--X: " + skbPositon.getX()
				+ " Y: " + skbPositon.getY());
		uiSize.setSliderStartLeftTopPosition("滑动块sliderStartLeftTopPosition--X: "
				+ sliderStartLeftTopPosition.getX()
				+ " Y: "
				+ sliderStartLeftTopPosition.getY());

	}

	/**
	 * 设置自定义滑块的左上角坐标点--直接使用屏幕坐标体系，不用转换
	 * 
	 * @time 2014年7月16日 下午4:58:12
	 */
	private void setSelf_touch_StartLeftTopPosition() {
		sliderStartLeftTopPosition_self.setX(imgv_self_touch_slice.getLeft());
		sliderStartLeftTopPosition_self.setY(imgv_self_touch_slice.getTop());

		sliderStartLeftTopPosition_self.v("2014716_171639");

	}

	/**
	 * 按下滑块时，设置背景图片的原始图片的显示
	 */
	private void setImageViewDisplayWhenDragSlider() {

		// GtLogger.v("bg,bg_slice,slice:gone,visible,visible");

		// imgv_full_bg.setVisibility(View.GONE);
		// imgv_slice_bg.setVisibility(View.GONE);
		// imgv_slice.setVisibility(View.VISIBLE);

		// 设置透明度--完全不透明
		imgv_full_bg.setAlpha(0);
		imgv_slice_bg.setAlpha(255);
		imgv_slice.setAlpha(255);

		// imgv_slice.getVisibility();
		// imgv_slice.getBottom();

		// imgv_full_bg.postInvalidate();
		// imgv_slice_bg.postInvalidate();
		// imgv_slice.postInvalidate();

		// 在第一次加载的时候会导致切图找不到
		// GtLogger.v("imgv_slice_bg### " + "xPos: " + imgv_slice_bg.getLeft()
		// + " yPos: " + imgv_slice_bg.getTop());

		// 设置透明控制的标志位
		isrung = true;
		slice_img_alpha = 255;

	}

	/**
	 * 按初始化或者点击刷新时:设置背景图片的原始图片的显示
	 */
	private void setImageViewDisplayWhenRefresh() {

		// GtLogger.v("bg,bg_slice,slice:visible,gone,gone");

		// imgv_full_bg.setVisibility(View.VISIBLE);
		// imgv_slice_bg.setVisibility(View.INVISIBLE);
		// imgv_slice.setVisibility(View.INVISIBLE);

		// 设置透明度
		imgv_full_bg.setAlpha(255);// 不透明
		imgv_slice_bg.setAlpha(0);
		imgv_slice.setAlpha(0);
	}

	/**
	 * 按初始化或者点击刷新时:设置背景图片的原始图片的显示
	 */
	private void setImageViewDisplayWhenCaptchaSucceed() {

		// GtLogger.v("bg,bg_slice,slice:visible,gone,gone");

		imgv_full_bg.setVisibility(View.VISIBLE);
		imgv_slice_bg.setVisibility(View.INVISIBLE);
		// imgv_slice.setVisibility(View.INVISIBLE);

		// // 设置透明度
		imgv_full_bg.setAlpha(255);// 不透明
		imgv_slice_bg.setAlpha(255);
		// imgv_slice.setAlpha(255);
	}

	private void initListeners() {

		// imgv_change_image.setOnTouchListener(new View.OnTouchListener() {
		//
		// public boolean onTouch(View arg0, MotionEvent event) {
		//
		// float curX, curY;// 鼠标的即时位置
		//
		// curX = event.getX();
		// curY = event.getY();
		//
		// switch (event.getAction()) {
		//
		// case MotionEvent.ACTION_DOWN:
		// // 获取当前的位置
		// mX = event.getX();
		// mY = event.getY();
		//
		// changeImageButtonStartPosition.setX(imgv_change_image
		// .getTop());
		// changeImageButtonStartPosition.setX(imgv_change_image
		// .getLeft());// 获取起始的位置
		// // actionDown_X = event.getX();
		// // actionDown_Y = event.getY();
		//
		// break;
		// case MotionEvent.ACTION_MOVE:
		// curX = event.getX();// 当前x
		// curY = event.getY();// 当前y
		//
		// // igv_slice.scrollBy((int) (mX - curX), (int) (mY -
		// // curY));// 进行偏移
		// // imgv_change_image.scrollBy((int) (mX - curX), 0);//
		// // 只进行水平方向行偏移
		// imgv_change_image.scrollBy(0, (int) (mY - curY));
		// mX = curX;
		// mY = curY;
		// break;
		// case MotionEvent.ACTION_UP:
		// GtLogger.v("Images Change Action_Up");
		//
		// curX = event.getX();
		// curY = event.getY();
		//
		// // imgv_change_image.scrollBy((int) (mX - curX), 0);
		// imgv_change_image.scrollTo(0,
		// (int) (changeImageButtonStartPosition.getY()));// 归位
		//
		// if ((curY - changeImageButtonStartPosition.getY()) > 100) {
		// // 开始刷新图片
		// captchaInitialOption_StringRequest();
		// }
		//
		// break;
		// }
		//
		// return true;
		// }
		// });

		refreshableView.setOnRefreshListener(new PullToRefreshListener() {
			@Override
			public void onRefresh() {
				try {
					// TODO 开始刷新图片
					picRequestSucceed = false;// 在进行加载前，先设置标志位

					int waitCnt = 0;
					captchaInitialOption_StringRequest();

					// 因为都是异步的，所以需要设置标志位，来进行等待执行完毕
					while (!picRequestSucceed) {
						if (waitCnt > 100) {
							break;
						}
						Thread.sleep(100);// 模拟一个耗时为2s的事件
					}

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				refreshableView.finishRefreshing();
			}
		}, 0);

		btn_dlg_close.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {

				GtShapeSize pt_bGtSp = new GtShapeSize();
				pt_bGtSp.setWidth((btn_dlg_close.getWidth()));
				pt_bGtSp.setHeight((btn_dlg_close.getHeight()));
				pt_bGtSp.v();
				dismiss();// 当前对话框关闭
			}
		});

		// 刷新图片
		btn_refresh.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				captchaInitialOption_StringRequest();
			}
		});

		btn_help.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// 转向使用帮助的页面
				Uri uri = Uri.parse(GtApiEnv.sdkUserHelpLink);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				context.startActivity(intent);
			}
		});

		btn_about.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {

				String toastMsg = "GtAppSdk_Vc" + GtApiEnv.sdkVersionCode
						+ "_Vn" + GtApiEnv.sdkVersionName;
				GtLogger.v(toastMsg);

				Toast.makeText(context, toastMsg, Toast.LENGTH_SHORT).show();

				// 转向使用帮助的页面--后面会做一个Web端的页面，详细记录当前的版本的号码，做一个API的URL
				// 2014年6月9日 16:46:58
				// Uri uri = Uri.parse(GtApiEnv.sdkUserHelpLink);
				// Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				// context.startActivity(intent);

			}
		});

		// 自定义的图片--采集行为数据
		imgv_self_touch_slice.setOnTouchListener(new View.OnTouchListener() {

			public boolean onTouch(View arg0, MotionEvent event) {

				float curX, curY;// 鼠标的即时位置

				curX = event.getX();
				curY = event.getY();

				switch (event.getAction()) {

				case MotionEvent.ACTION_DOWN:
					sendMsgToUpdateUI(MSG_ANIM_TIP_MISS);// 提示动画消失
					sendMsgToUpdateUI(MSG_SLICE_BG_DISPLAY);// 通知界面进行控制显示

					seekbarStartTime = System.currentTimeMillis();// 记录起始时间
					// 获取当前的位置
					mX = event.getX();
					mY = event.getY();
					setSliderStartPressTouchPosition(mX, mY);

					userActions = new ArrayList<CaptchaUserAction>();// 用户行为数据的数组--重新清空初始化一次
					// 滑块的左上角值
					CaptchaUserAction firstActionTag = new CaptchaUserAction();
					firstActionTag.bindMemData(
							(int) (sliderStartLeftTopPosition_self.getX()),
							(int) (sliderStartLeftTopPosition_self.getY()), 0);
					userActions.add(firstActionTag);

					CaptchaUserAction firstAction = new CaptchaUserAction();

					// TODO 可能需要做一些转变，坐标全部使用浮点的，最后的时候再转成整形
					firstAction.bindMemData(
							(int) (sliderStartPressTouchPosition.getX()),
							(int) (sliderStartPressTouchPosition.getY()), 0);

					firstAction.v();
					userActions.add(firstAction);

					break;
				case MotionEvent.ACTION_MOVE:
					curX = event.getX();// 当前x
					curY = event.getY();// 当前y

					int slice_img_X = (int) (curX - bm_slice.getWidth() / 2);
					int touch_X = (int) (curX - thumbBmpSize.getWidth() / 2);

					GtLogger.s_v("2014716_192724", bm_slice.getWidth() + " , "
							+ thumbBmpSize.getWidth());

					imgv_self_touch_slice.scrollTo((int) (-touch_X), 0);// 进行偏移
					imgv_slice.scrollTo((int) (-slice_img_X),
							getSliceYposAfterSalced());//

					// imgv_self_touch_slice.scrollTo((int) (-curX), 0);// 进行偏移
					// imgv_slice.scrollTo((int) (-curX),
					// getSliceYposAfterSalced());//

					mX = curX;
					mY = curY;

					// TODO 这个数据类型需要后面修复 2014年5月20日 16:55:52
					long curTimeTag = System.currentTimeMillis();// 当前时间标记
					CaptchaUserAction curUserAction = new CaptchaUserAction();

					curUserAction.bindMemData((int) curX, (int) curY,
							(int) (curTimeTag - seekbarStartTime));

					userActions.add(curUserAction);

					break;
				case MotionEvent.ACTION_UP:

					GtLogger.v("Images Change Action_Up");

					curX = event.getX();
					curY = event.getY();

					setSliderUpTouchPosition(curX, curY);

					seekbarEndTime = System.currentTimeMillis();
					CaptchaUserAction lastAction = new CaptchaUserAction();
					lastAction.bindMemData((int) curX, (int) curY,
							(int) (seekbarEndTime - seekbarStartTime));
					lastAction.v();
					userActions.add(lastAction);
					Log.v("seekbar", "拖动停止");

					// sliderOffsetX = (int) curX;// 获取偏移量
					sliderOffsetX = (int) curX - bm_slice.getWidth() / 2;// 获取偏移量

					// 向服务器提交行为数据
					userBehaviourUpload_StringRequest();
					break;
				}

				return true;
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
					sendMsgToUpdateUI(MSG_ANIM_TIP_MISS);// 提示动画消失
					sendMsgToUpdateUI(MSG_SLICE_BG_DISPLAY);// 通知界面进行控制显示

					seekbarStartTime = System.currentTimeMillis();// 记录起始时间
					// 获取当前的位置
					mX = event.getX();
					mY = event.getY();
					setSliderStartPressTouchPosition(mX, mY);

					GtLogger.v("skb_dragCaptcha.getLeft(): "
							+ skb_dragCaptcha.getLeft()
							+ "   skb_dragCaptcha.getRight(): "
							+ skb_dragCaptcha.getRight());

					userActions = new ArrayList<CaptchaUserAction>();// 用户行为数据的数组--重新清空初始化一次

					// 滑块的左上角值
					CaptchaUserAction firstActionTag = new CaptchaUserAction();
					firstActionTag.bindMemData(
							(int) (sliderStartLeftTopPosition.getX()),
							(int) (sliderStartLeftTopPosition.getY()), 0);
					userActions.add(firstActionTag);

					CaptchaUserAction firstAction = new CaptchaUserAction();

					// TODO 可能需要做一些转变，坐标全部使用浮点的，最后的时候再转成整形
					firstAction.bindMemData(
							(int) (sliderStartPressTouchPosition.getX()),
							(int) (sliderStartPressTouchPosition.getY()), 0);

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

					userActions.add(curUserAction);

					break;
				case MotionEvent.ACTION_UP:

					GtLogger.v("Images Change Action_Up");

					curX = event.getX();
					curY = event.getY();

					setSliderUpTouchPosition(curX, curY);

					seekbarEndTime = System.currentTimeMillis();
					CaptchaUserAction lastAction = new CaptchaUserAction();
					lastAction.bindMemData((int) curX, (int) curY,
							(int) (seekbarEndTime - seekbarStartTime));
					lastAction.v();
					userActions.add(lastAction);

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
						// int mSeekBarDef_X = location[0];
						// int mSeekBarDef_Y = location[1];

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

						// GtLogger.s_v(context, "Hellow On Progress");

						// GtLogger.v("当前进度：" + progress + "%");
						// 坐标偏移
						// igv_slice.scrollTo((int) (-dX * progress), (int)
						// (0));// 进行偏移

						// 目前只能采用硬编码的方式来处理图片的大小问题了
						// Resources res = getResources();
						// BitmapDrawable skb_thumb = (BitmapDrawable) res
						// .getDrawable(R.drawable.gtapp_skb_drag_normal);

						// GtLogger.v("skb_thumb"+skb_thumb.);

						// GtLogger.v("thumb bitmap width: "
						// + skb_thumb.getBitmap().getWidth());
						// GtLogger.v("thumb bitmap height: "
						// + skb_thumb.getBitmap().getHeight());

						// thumb.
						// Drawable thumb = res
						// .getDrawable(R.drawable.seekbar_thumb);
						// thumb.getCurrent().get
						// GtLogger.v("thumb width: "
						// + (thumb.getBounds().right -
						// thumb.getBounds().left));
						// GtLogger.v("thumb height: "
						// + (thumb.getBounds().bottom -
						// thumb.getBounds().top));

						// 将屏幕的X坐标进行均分
						int seekbar_width = seekBar.getWidth();
						// intWidth = skb_thumb.getBitmap().getWidth();
						// GtLogger.v("seekbar_width: " + seekbar_width
						// + "   thumbBitmapWidth: " + intWidth);// 720,171
						// 需要生成其移动空间
						// float dX = (intScreenX - intWidth) /
						// 100.0f;//slice_img_width
						float scrollXRange = seekbar_width - leftMargin
								- rightMargin - thumbBmpSize.getWidth();// 减去左右的

						float dX = scrollXRange / 100.0f;
						// GtLogger.v("滑块的移动范围： " + (scrollXRange));
						// 边缘值距离值。,减去图片的宽度的一半值。

						// sliderOffsetX = (int) (seekbar_server_length *
						// progress / 100);// 以服务器上的绝对值为准

						sliderOffsetX = (int) (dX * progress);

						// GtLogger.v("sliderOffsetX new : " + dX * progress);

						// GtLogger.v("图片移动到： " + (-dX * progress));
						imgv_slice.scrollTo((int) ((-dX * progress)),// 在前面加一个10即可实现完全一样的同步。
								getSliceYposAfterSalced());// 进行偏移
					}
				});

		// 渐变过程监听
		anim_skb_finger_tip.setAnimationListener(new AnimationListener() {

			/**
			 * 动画开始时
			 */
			@Override
			public void onAnimationStart(Animation animation) {
				System.out.println("动画开始...");
				imgv_skb_anim_tip.setVisibility(View.VISIBLE);
			}

			/**
			 * 重复动画时
			 */
			@Override
			public void onAnimationRepeat(Animation animation) {
				// System.out.println("动画重复...");
			}

			/**
			 * 动画结束时
			 */
			@Override
			public void onAnimationEnd(Animation animation) {
				System.out.println("动画结束...");
				imgv_skb_anim_tip.setVisibility(View.INVISIBLE);
			}
		});

		// 渐变过程监听
		anim_flashlight.setAnimationListener(new AnimationListener() {

			/**
			 * 动画开始时
			 */
			@Override
			public void onAnimationStart(Animation animation) {
				System.out.println("动画开始...");
				imgv_flashlight.setVisibility(View.VISIBLE);
			}

			/**
			 * 重复动画时
			 */
			@Override
			public void onAnimationRepeat(Animation animation) {
				// System.out.println("动画重复...");
			}

			/**
			 * 动画结束时
			 */
			@Override
			public void onAnimationEnd(Animation animation) {
				// 设置一个动画的事件，在动画结束后停止 关闭对话框
				imgv_flashlight.setVisibility(View.INVISIBLE);
				imgv_skb_anim_tip.setVisibility(View.INVISIBLE);

				// 对话框需要在动画之后关闭，必须要放在线程里面，否则会报异常
				// 做一个回调的函数。
				GtAppCbCaptchaResponse cbResponse = new GtAppCbCaptchaResponse();
				cbResponse.setResCode(1);
				cbResponse.setResMsg("succeed");
				gtAppCallback.gtAppResponse(cbResponse);

				Handler handler = new Handler();// 必须重新new一个，否则会出现问题
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						dismiss();
					}
				}, 500);

				// dismiss();// 当前对话框关闭

				// mHandler.postDelayed(new Runnable() {
				// @Override
				// public void run() {
				//
				// }
				// }, 500);// 500ms后关闭对话框

			}
		});

	}

	/**
	 * 设置动画提示消失的功能
	 * 
	 * @time 2014年7月16日 下午2:23:50
	 */
	private void setAnimTipMissView() {
		tv_slider_tip_msg.setVisibility(View.INVISIBLE);// 拖动的提示文字消失
		imgv_skb_anim_tip.clearAnimation();// 清除动画
		imgv_skb_anim_tip.setVisibility(View.INVISIBLE);// 动画图片消失
	}

	/**
	 * 发送消息给 handle去更新界面
	 * 
	 * @param msgId
	 */
	private void sendMsgToUpdateUI(int msgId) {
		Message msg = mHandler.obtainMessage();
		msg.what = msgId;
		msg.sendToTarget();
	}

	/**
	 * 设置初始点--左上角为原点
	 * 
	 * @param mX
	 *            当前触点绝对坐标
	 * @param mY
	 */
	private void setSliderStartPressTouchPosition(float mX, float mY) {
		// sliderStartPressTouchPosition.setX(sliderStartLeftTopPosition.getX()
		// - mX);
		// sliderStartPressTouchPosition.setY(sliderStartLeftTopPosition.getY()
		// - mY);

		// 安卓的坐标体系
		sliderStartPressTouchPosition.setX(mX);
		sliderStartPressTouchPosition.setY(mY);

		GtLogger.v("起始↓↓↓触点：- X: " + sliderStartPressTouchPosition.getX()
				+ " Y: " + sliderStartPressTouchPosition.getY());
	}

	/**
	 * 求出移动过程中的相对坐标值
	 * 
	 * @param mX
	 * @param mY
	 */
	private void setSliderDragMoveTouchPosition(float mX, float mY) {
		// sliderDragMoveTouchPosition.setX(mX
		// - sliderStartPressTouchPosition.getX());
		// sliderDragMoveTouchPosition.setY(mY
		// - sliderStartPressTouchPosition.getY());

		sliderDragMoveTouchPosition.setX(mX);
		sliderDragMoveTouchPosition.setY(mY);

		GtLogger.v("拖动→→→时触点：- X: " + sliderDragMoveTouchPosition.getX()
				+ " Y: " + sliderDragMoveTouchPosition.getY());
	}

	/**
	 * 滑块放开时的坐标值
	 * 
	 * @param mX
	 * @param mY
	 */
	private void setSliderUpTouchPosition(float mX, float mY) {
		// sliderStartPressTouchPosition.setX(sliderStartLeftTopPosition.getX()
		// - mX);
		// sliderStartPressTouchPosition.setY(sliderStartLeftTopPosition.getY()
		// - mY);

		// 安卓的坐标体系
		sliderUpTouchPosition.setX(mX);
		sliderUpTouchPosition.setY(mY);

		GtLogger.v("起↑↑↑↑触点：- X: " + sliderUpTouchPosition.getX() + " Y: "
				+ sliderUpTouchPosition.getY());
	}

	/**
	 * 获取按钮图片的大小尺寸
	 */
	private void setThumbBmpSize() {

		try {
			// Resources res = getResources();
			BitmapDrawable skb_thumb = (BitmapDrawable) res
					.getDrawable(R.drawable.gtapp_skb_drag_normal);

			thumbBmpSize.setWidth(skb_thumb.getBitmap().getWidth());
			thumbBmpSize.setHeight(skb_thumb.getBitmap().getHeight());

			// GradientDrawable skb_thumb = (GradientDrawable) res
			// .getDrawable(R.drawable.gtapp_skb_thumb_normal_shape);

			// thumbBmpSize.setWidth(skb_thumb.getBounds().width());
			// thumbBmpSize.setHeight(skb_thumb.getBounds().height());

			// 先使用硬编码--2014年6月10日 16:38:19
			// thumbBmpSize.setWidth(70);
			// thumbBmpSize.setHeight(50);

			GtLogger.v("thumbBmpSize.getWidth(): " + thumbBmpSize.getWidth()
					+ "  thumbBmpSize.getHeight()： " + thumbBmpSize.getHeight());

		} catch (Exception e) {
			GtLogger.ex(LoggerString.getFileLineMethod() + e.getMessage());
		}

	}

	/**
	 * 界面控件元素的绑定工作
	 */
	private void initViews() {

		try {
			// LayoutInflater flater = LayoutInflater.from(context);

			// View paraView = flater.inflate(R.layout.image_move, null);
			// LayoutInflater paraView = (View)
			// findViewById(R.layout.image_move);

			firstReLayoutView = (RelativeLayout) findViewById(R.id.rl_viewRoot);// 根布局
			// 加载前后
			beforeGetImageLineraLayout = (LinearLayout) firstReLayoutView
					.findViewById(R.id.ll_before_load);
			afterGetImageLineraLayout = (LinearLayout) firstReLayoutView
					.findViewById(R.id.ll_view_after_image_load);

			// 状态栏
			imgv_captcha_status_icon = (ImageView) findViewById(R.id.imgv_captcha_status_icon);
			tv_validateStatus = (TextView) findViewById(R.id.tv_validateStatus);
			tv_validateMsg = (TextView) findViewById(R.id.tv_validateMsg);

			gtStatusBar = new GtAppStatusBar(res, imgv_captcha_status_icon,
					tv_validateStatus, tv_validateMsg);// 状态条

			// 图片框布局--好像相对布局必须要这样弄
			flView_ImageFrame = (FrameLayout) firstReLayoutView
					.findViewById(R.id.rl_view_image_frame);
			imgv_full_bg = (ImageView) flView_ImageFrame
					.findViewById(R.id.imgv_full_bg);
			imgv_slice = (ImageView) flView_ImageFrame
					.findViewById(R.id.imgv_slice);
			imgv_slice_bg = (ImageView) flView_ImageFrame
					.findViewById(R.id.imgv_slice_bg);
			imgv_flashlight = (ImageView) flView_ImageFrame
					.findViewById(R.id.imgv_flashlight);

			// 自定义的滑块布局
			flView_self_slider = (FrameLayout) firstReLayoutView
					.findViewById(R.id.fl_self_seekbar);
			imgv_self_touch_slice = (ImageView) flView_self_slider
					.findViewById(R.id.imgv_self_touch_slice);// 自定义滑动条
			tv_slider_tip_msg = (TextView) flView_self_slider
					.findViewById(R.id.tv_slider_tip_msg);// 提示文字

			imgv_skb_anim_tip = (ImageView) flView_self_slider
					.findViewById(R.id.imgv_skb_anim_tip);
			imgv_change_image = (ImageView) findViewById(R.id.imgv_change_image);

			refreshableView = (GtRefreshableView) findViewById(R.id.refreshable_view);// 自定义的下拉组件

			skb_dragCaptcha = (SeekBar) findViewById(R.id.seekbar_def); // “系统默认SeekBar”

			btn_dlg_close = (Button) findViewById(R.id.btn_dlg_close);

			// 滑动提示文字布局
			fl_slider_string_tip = (LinearLayout) findViewById(R.id.fl_slider_string_tip);
			// gtAppNeonLightTip = new GtAppNeonLightTip(fl_slider_string_tip);

			btn_refresh = (Button) findViewById(R.id.btn_refresh);
			btn_help = (Button) findViewById(R.id.btn_help);
			btn_about = (Button) findViewById(R.id.btn_about_info);

			anim_skb_finger_tip = AnimationUtils.loadAnimation(context,
					R.anim.gtapp_anim_skb_tip);// 手拖动动画。

			anim_flashlight = AnimationUtils.loadAnimation(context,
					R.anim.gtapp_anim_flashlight);// 闪电图。

		} catch (Exception e) {
			GtLogger.ex(LoggerString.getFileLineMethod() + e.getMessage());
		}

	}

	/**
	 * 向服务器请求完整的图片
	 * 
	 * @param relativeUrl
	 */
	private void fullbg_ImageRequest(String relativeUrl) {

		String imgUrl = getFullUrl(relativeUrl);

		ImageRequest bg_imageRequest = new ImageRequest(imgUrl,
				new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap response) {

						try {
							imgLoadTimeStamp.setBg_img_end_time(System
									.currentTimeMillis());

							// 对图片进行缩放--以充斥全屏
							bm_zoom_scale = getImageZoomScale(response
									.getWidth());

							// seekbar_server_length = response.getWidth();
							// 保存放大后的图片资源到本地
							bm_full_bg = zoomImage(response, bm_zoom_scale);
							slice_bg_ImageRequest(initCaptchaOption.getImgurl());// 再请求切图背景
							// igv_slicebg.setImageBitmap(response);
						} catch (Exception e) {
							GtLogger.ex(LoggerString.getFileLineMethod()
									+ e.getMessage());
						}

					}
				}, 0, 0, Config.RGB_565, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						GtLogger.ex(LoggerString.getFileLineMethod()
								+ error.getMessage());
					}
				});

		imgLoadTimeStamp.setBg_img_start_time(System.currentTimeMillis());
		// bgImgLoadStartTime = ;// 图片开始请求时记录一个时间节点。

		mQueue.add(bg_imageRequest);
	}

	/**
	 * 获取绽放的比例值
	 * 
	 * @param orginImageWidth
	 * @return
	 */
	private float getImageZoomScale(int orginImageWidth) {

		int gtapp_dlg_width = skb_dragCaptcha.getRight()
				- skb_dragCaptcha.getLeft();
		GtLogger.v("seekbar_width: " + gtapp_dlg_width);

		if (0 == gtapp_dlg_width) {
			gtapp_dlg_width = firstReLayoutView.getRight()
					- firstReLayoutView.getLeft();
			GtLogger.v("root_gtapp_dlg_width: " + gtapp_dlg_width);
		}

		// int seekbar_width = skb_dragCaptcha.getRight()
		// - skb_dragCaptcha.getLeft();

		// int relWidth = reLayoutView.getRight() - reLayoutView.getLeft();

		int relWidth = firstReLayoutView.getRight()
				- firstReLayoutView.getLeft();

		// GtLogger.v("seekbar_width: " + seekbar_width);

		float zoom_scale = ((gtapp_dlg_width - leftMargin - rightMargin) * 1000)
				/ (orginImageWidth * 1000.0f);

		// float zoom_scale = ((screenSize.getWidth() - leftMargin -
		// rightMargin) * 1000)
		// / (orginImageWidth * 1000.0f);

		GtLogger.v("orginImageWidth : " + orginImageWidth + "  zoom_scale: "
				+ zoom_scale);

		return zoom_scale;
	}

	/**
	 * 根据相对路径获取完整路径
	 * 
	 * @time 2014年7月8日 下午7:19:13
	 * @param relativeUrl
	 * @return
	 */
	private String getFullUrl(String relativeUrl) {
		return GtApiEnv.imgServerBase + relativeUrl;
	}

	/**
	 * 切掉后的大的背景图
	 */
	private void slice_bg_ImageRequest(String relativeUrl) {

		String imgUrl = getFullUrl(relativeUrl);

		ImageRequest bg_imageRequest = new ImageRequest(imgUrl,
				new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap response) {
						imgLoadTimeStamp.setSlice_bg_img_end_time(System
								.currentTimeMillis());
						// 对图片进行缩放
						bm_slice_bg = zoomImage(response, bm_zoom_scale);
						slice_ImageRequest(initCaptchaOption.getSliceurl());// 再请求小切图
						// igv_slicebg.setImageBitmap(response);
					}
				}, 0, 0, Config.RGB_565, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						GtLogger.ex(LoggerString.getFileLineMethod()
								+ error.getMessage());
						// imgv_slice.setImageResource(R.drawable.ic_launcher);
					}
				});

		imgLoadTimeStamp.setSlice_bg_img_start_time(System.currentTimeMillis());

		mQueue.add(bg_imageRequest);
	}

	/**
	 * 小切图
	 */
	private void slice_ImageRequest(String relativeUrl) {

		String imgUrl = getFullUrl(relativeUrl);

		// String url_fullbg =
		// "http://geetest-jordan2.b0.upaiyun.com/pictures/gt/b2cbb350/slice/63328333.png";
		ImageRequest slip_imageRequest = new ImageRequest(imgUrl,
				new Response.Listener<Bitmap>() {
					@Override
					public void onResponse(Bitmap response) {

						imgLoadTimeStamp.setSlice_img_end_time(System
								.currentTimeMillis());

						GtLogger.v("切片图 Width: " + response.getWidth());
						GtLogger.v("切片图 放大 后Width: " + response.getWidth()
								* bm_zoom_scale);
						bm_slice = zoomImage(response, bm_zoom_scale);

						sendMsgToUpdateUI(MSG_BIND_DATA);
						// bindOptionDataToLocalViews();

					}
				}, 0, 0, Config.RGB_565, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						GtLogger.ex(LoggerString.getFileLineMethod()
								+ error.getMessage());
					}
				});

		imgLoadTimeStamp.setSlice_img_start_time(System.currentTimeMillis());
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

		GtLogger.v("imgv_width: " + imgv_width + "  imgv_height: "
				+ imgv_height);

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

	public String getOption() {

		StringBuffer sBuffer = new StringBuffer();
		long start_time = System.currentTimeMillis();
		imgLoadTimeStamp.setOption_start_time(System.currentTimeMillis());
		try {
			URL u = new URL(getOptionUrl());
			InputStream in = null;
			HttpURLConnection conn = (HttpURLConnection) u.openConnection();
			conn.setDoInput(true);
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type",
					"application/json; charset=utf-8");
			if (conn.getResponseCode() == 400) {
				InputStream erris = conn.getErrorStream();
				// MyFileOut.writeOutputStrem(erris);
			} else if (conn.getResponseCode() == 200) {
				byte[] buf = new byte[1024];
				in = conn.getInputStream();
				for (int n; (n = in.read(buf)) != -1;) {
					sBuffer.append(new String(buf, 0, n, "UTF-8"));
				}
			}
			in.close();
			conn.disconnect();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		long userTime = System.currentTimeMillis() - start_time;
		imgLoadTimeStamp.setOption_end_time(System.currentTimeMillis());
		GtLogger.v("userTime:" + userTime);
		GtLogger.v("sBuffer.toString():" + sBuffer.toString());

		return sBuffer.toString();
	}

	/**
	 * 获取option的url信息
	 * 
	 * @time 2014年7月9日 上午10:21:07
	 * @return
	 */
	private String getOptionUrl() {
		GetPhp_GreqVo getPhp_GreqVo = new GetPhp_GreqVo();
		getPhp_GreqVo.setGt(gt_public_key);
		getPhp_GreqVo.setProduct(GtApiEnv.gt_product_type);

		String relApiPath = GtApiEnv.getOptionApi;
		String param = cdtParams(cdtObjectToMap(getPhp_GreqVo));
		String urlStr = genernateApiUrl(relApiPath, param);

		return urlStr;
	}

	/**
	 * 解决首次请求时时间太长的问题
	 * 
	 * @time 2014年7月8日 下午9:43:46
	 */
	private void firstRequestOption() {

		String resultData = "";

		long start_time = System.currentTimeMillis();

		try {

			URL url = new URL(getOptionUrl());
			HttpURLConnection urlConn = (HttpURLConnection) url
					.openConnection();

			// 得到读取的内容(流)
			InputStreamReader in = new InputStreamReader(
					urlConn.getInputStream());
			// 为输出创建BufferedReader
			BufferedReader buffer = new BufferedReader(in);
			String inputLine = null;
			// 使用循环来读取获得的数据
			while (((inputLine = buffer.readLine()) != null)) {
				// 我们在每一行后面加上一个"\n"来换行
				resultData += inputLine + "\n";
			}
			// 关闭InputStreamReader
			in.close();
			// 关闭http连接
			urlConn.disconnect();

			long userTime = System.currentTimeMillis() - start_time;

			GtLogger.v("userTime:" + userTime);
			GtLogger.v("resultData:" + resultData);
			// TODO

		} catch (Exception e) {
			GtLogger.ex(LoggerString.getFileLineMethod() + e.getMessage());
		}

	}

	/**
	 * 解码option，然后得到一个josn串
	 * 
	 * @time 2014年7月8日 下午11:18:33
	 * @param response
	 * @return
	 */
	private String decodeOptionValue(String response) {
		// 硬解码抽取出JSON格式
		String webJsFunction[] = response.split("=");
		String optionValues[] = webJsFunction[1].split(";");
		String optionValue = optionValues[0];
		return optionValue;
	}

	/**
	 * 从GT服务器上获取验证码控件初始化的内容元素
	 */
	private void captchaInitialOption_StringRequest() {
		// String url = "http://www.baidu.com";
		// 如果出现乱码，应该修改StringRequest的parseNetworkResponse()方法，指定byte[]-->String
		// 编码

		// 这里是一切数据的源头：
		// 1.根据get内容生成URL，请求URL，返回值

		GetPhp_GreqVo getPhp_GreqVo = new GetPhp_GreqVo();
		getPhp_GreqVo.setGt(gt_public_key);
		getPhp_GreqVo.setProduct(GtApiEnv.gt_product_type);

		String relApiPath = GtApiEnv.getOptionApi;
		String param = cdtParams(cdtObjectToMap(getPhp_GreqVo));
		String url = genernateApiUrl(relApiPath, param);

		StringRequest option_Request = new StringRequest(url,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {

						imgLoadTimeStamp.setOption_end_time(System
								.currentTimeMillis());

						// 硬解码抽取出JSON格式
						String webJsFunction[] = response.split("=");
						String optionValues[] = webJsFunction[1].split(";");
						String optionValue = optionValues[0];

						// 解析成对象
						initCaptchaOption = gson.fromJson(optionValue,
								CaptchaOption.class);

						GtLogger.s_v("2014713_233715",
								initCaptchaOption.getFullbg());

						// 开始连锁的串行向服务器请求图片
						fullbg_ImageRequest(initCaptchaOption.getFullbg());
						// GtLogger.s_v(
						// "debug_optionTime_thread",
						// imgLoadTimeStamp.getOption_end_time()
						// - imgLoadTimeStamp
						// .getOption_start_time());

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						GtLogger.ex(LoggerString.getFileLineMethod()
								+ error.getMessage());
					}
				});

		mQueue.add(option_Request);
		imgLoadTimeStamp.setOption_start_time(System.currentTimeMillis());
	}

	/**
	 * Map转url的参数--进行了中文编码
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
	 * 将map转换成url --没有进行unicode编码
	 * 
	 * @param map
	 * @return
	 */
	private String getUrlParamsByMap(Map<String, Object> map) {
		if (map == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (Map.Entry<String, Object> entry : map.entrySet()) {
			sb.append(entry.getKey() + "=" + entry.getValue());
			sb.append("&");
		}
		String s = sb.toString();
		// if (s.endsWith("&")) {
		// s = StringUtils.substringBeforeLast(s, "&");
		// }
		return s;
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

			return url.toString();
		} catch (URISyntaxException e) {
			GtLogger.ex(LoggerString.getFileLineMethod() + e.getMessage());
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

			return url.toString();
		} catch (URISyntaxException e) {
			GtLogger.ex(LoggerString.getFileLineMethod() + e.getMessage());
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

			GtLogger.s_v("2014713_233824",
					userAction.getxPos() + "," + userAction.getyPos() + ","
							+ userAction.getTimeIncrement());

			userActions.add(userAction);
		}
	}

	/**
	 * 获取鼠标放开时，滑块的偏移量--除去一个缩放比例
	 * 
	 * @return
	 */
	private int getSliderBarOffset() {

		// sliderOffsetX = 12;
		// 使用的假数据：需要获取鼠标放开时，滑块相对产生的对初始位置的偏移量

		// GtLogger.v("testOffsetX: " + testOffsetX / bm_zoom_scale);

		sliderOffsetX = (int) (sliderOffsetX / bm_zoom_scale);
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

		// GtLogger.v("进行一次坐标系统转换");

		GtLogger.v("进行缩放，比例：bm_zoom_scale: " + bm_zoom_scale);

		// 用户行为数据的x,y值都除掉一个放大系数
		for (int i = 0; i < userActions.size(); i++) {

			CaptchaUserAction temp = userActions.get(i);

			// 进行一次坐标系统转换--原点转换
			temp.setxPos((int) (temp.getxPos() - sliderStartPressTouchPosition
					.getX()));
			temp.setyPos((int) (temp.getyPos() - sliderStartPressTouchPosition
					.getY()));

			// 坐标刻度缩放
			temp.setxPos((int) (temp.getxPos() / bm_zoom_scale));
			temp.setyPos((int) (temp.getyPos() / bm_zoom_scale));
			// 时间增量是不是缩放的
		}

		String encodeUserActions = GtDataConvert.EncryptUserAction(userActions);

		GtLogger.v("userResponse:  " + encodeUserResponse);
		GtLogger.v("a:  " + encodeUserActions);

		ajaxPhp_GreqVo = new AjaxPhp_GreqVo();
		ajaxPhp_GreqVo.setApi(GtApiEnv.ajaxApiName);
		ajaxPhp_GreqVo.setChallenge(initCaptchaOption.getChallenge());
		ajaxPhp_GreqVo.setUserresponse(encodeUserResponse);
		ajaxPhp_GreqVo.setPasstime((int) (seekbarEndTime - seekbarStartTime));
		ajaxPhp_GreqVo
				.setImgload((int) (imgLoadTimeStamp.getAbsTotalLoadTime()));
		ajaxPhp_GreqVo.setA(encodeUserActions);

		// GtLogger.v(ajaxPhp_GreqVo.getA());
		GtLogger.s_v("2014712_010609", ajaxPhp_GreqVo.getA());

		// 对象转Map,Map编码成List
		String relApiPath = GtApiEnv.ajaxSubmitApi;
		// String param = cdtParams(cdtObjectToMap(ajaxPhp_GreqVo));
		String param = getUrlParamsByMap((cdtObjectToMap(ajaxPhp_GreqVo)));
		String optionApiUrl = genernateApiUrl(relApiPath, param);

		// 使用的是内部的测试数据服务器
		// optionApiUrl = genernateTestApiUrl("/gtapp_ajax", param);
		imgLoadTimeStamp.setPost_action_start_time(System.currentTimeMillis());
		StringRequest option_Request = new StringRequest(optionApiUrl,
				new Response.Listener<String>() {

					@Override
					public void onResponse(String response) {

						try {
							imgLoadTimeStamp.setPost_action_end_time(System
									.currentTimeMillis());
							GtLogger.v("userBehaviourUpload_StringRequest   response:   "
									+ response);

							// 对response 硬解码成Json
							// var GeeTest_tempData={success: 0,message:"fail"}
							String resultAry[] = response.split("=");

							AjaxPhp_GresVo ajaxPhp_GresVo = new AjaxPhp_GresVo();

							// Gson gson = new Gson();
							ajaxPhp_GresVo = gson.fromJson(resultAry[1],
									AjaxPhp_GresVo.class);

							GtLogger.s_v("2014715_110148", ajaxPhp_GresVo);

							// 如果返回的是成功的验证结果
							if (ajaxPhp_GresVo.getSuccess().equals("1")) {

								clientCaptchaResult = true;// 验证成功
								skb_dragCaptcha.setEnabled(false);// 禁用

								setImgStatusAfterCaptchaSucceed();
								// 设置状态栏
								gtStatusBar
										.setToSucceedStatus(getSucceedToolTip(ajaxPhp_GresVo
												.getMessage()));

							} else if (ajaxPhp_GresVo.getSuccess().equals("0")) {

								clientCaptchaResult = false;
								skb_dragCaptcha.setEnabled(true);

								// 验证失败后，就不需要向客户机发起请求二次验证了
								GtLogger.v("验证错误");

								// 所有的验证失败的情况--状态栏会给予不同的显示
								if (ajaxPhp_GresVo.getMessage().equals("fail")) {
									gtStatusBar.setToFailedStatus();
								} else if (ajaxPhp_GresVo.getMessage().equals(
										"forbidden")) {
									gtStatusBar.setToExceptionStatus();
								} else if (ajaxPhp_GresVo.getMessage().equals(
										"abuse")) {
									gtStatusBar.setToTryTooMuchStatus();
									// 本次验证次数过多，然后会自动刷新图片
									captchaInitialOption_StringRequest();

								} else {
									GtLogger.v("出现未知失败结果");
								}
								setImgStatusAfterCaptchaFailed();

							} else {
								GtLogger.v("出现 未知结果");

							}
						} catch (Exception e) {
							setImgStatusAfterCaptchaFailed();
							GtLogger.ex(LoggerString.getFileLineMethod()
									+ e.getMessage());
						}

					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						GtLogger.ex(LoggerString.getFileLineMethod()
								+ error.getMessage());
					}
				});

		mQueue.add(option_Request);
	}

	/**
	 * 根据编码信息来生成 成功提示
	 * 
	 * @time 2014年6月12日 下午5:08:00
	 * @param encodedSucceedMsg
	 * @return
	 */
	private String getSucceedToolTip(String encodedSucceedMsg) {
		// 对验证结果硬解码
		String resultArray[] = encodedSucceedMsg.split("\\|");

		messageResult = resultArray[0];// 验证结果值
		String actionRank = resultArray[1];// 验证行为排名占比

		double orginActionTime = (seekbarEndTime - seekbarStartTime) / 1000.0;
		// 四舍五入保留一位小数
		float convertActionTime = (float) (Math.round(orginActionTime * 10)) / 10;// (这里的100就是2位小数点,如果要其它位,如4位,这里两个100改成10000)

		String succeedTip = convertActionTime + "秒的速度超过"
				+ (100 - Integer.parseInt(actionRank)) + "%的用户";

		GtLogger.v(succeedTip);
		GtLogger.v(" messageResult: " + messageResult + " actionRank: "
				+ actionRank);

		return succeedTip;
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
			case MSG_ANIM_TIP_MISS:
				setAnimTipMissView();
				break;

			case MSG_SLICE_BG_ALPHA_MISS:
				// GtLogger.v("渐变消失");
				setImageViewDisplayWhenCaptchaSucceed();
				imgv_slice.setAlpha(slice_img_alpha);
				// 设置textview显示当前的Alpha值
				// tv_validateStatus.setText("现在的alpha值是:"
				// + Integer.toString(slice_img_alpha));
				// 刷新视图
				imgv_slice.invalidate();
				break;
			case MSG_ANIM_FLASH:
				imgv_flashlight.startAnimation(anim_flashlight);
				break;

			case MSG_OPTION_DATA:
				// 向服务器请求初始化信息
				requestOptionDataFromGtServer();

				// new Thread(new Runnable() {
				// @Override
				// public void run() {
				//
				// new Thread(new Runnable() {
				// @Override
				// public void run() {
				// requestOptionDataFromGtServer();
				//
				// }
				// }).start();
				// }
				// }).start();

				// firstRequestOption();
				// getOption();

				break;
			case MSG_BIND_DATA:
				bindOptionDataToLocalViews();
				break;

			default:
				break;
			}

			super.handleMessage(msg);
		}
	};

	/**
	 * 在界面上交替闪烁--后面采用线程的方式进行
	 */
	private void setImgStatusAfterCaptchaSucceed() {

		// 在界面上交替闪烁--后面采用线程的方式进行
		SetImgStatusAfterSucceed he = new SetImgStatusAfterSucceed();
		Thread demo = new Thread(he, "Action");
		demo.start();

	}

	/**
	 * 验证成功后的线程
	 * 
	 * @author Zheng 2014年5月22日 上午9:34:39
	 */
	class SetImgStatusAfterSucceed implements Runnable {
		public void run() {
			try {

				while (isrung) {
					try {
						Thread.sleep(20);
						// 更新Alpha值
						updateAlpha();
					} catch (InterruptedException e) {
						GtLogger.ex(LoggerString.getFileLineMethod()
								+ e.getMessage());
						e.printStackTrace();
					}
				}

				sendMsgToUpdateUI(MSG_ANIM_FLASH);// 设计单独的动画
				// imgv_flashlight.startAnimation(anim_flashlight);

			} catch (Exception e) {
				GtLogger.ex(LoggerString.getFileLineMethod() + e.getMessage());
			}
		}
	}

	// 更新Alpha
	public void updateAlpha() {
		if (slice_img_alpha - 7 >= 0) {
			slice_img_alpha -= 7;
		} else {
			slice_img_alpha = 0;
			isrung = false;

		}

		// GtLogger.v("alpha: " + slice_img_alpha);
		// 发送需要更新imageview视图的消息-->这里是发给主线程
		sendMsgToUpdateUI(MSG_SLICE_BG_ALPHA_MISS);
	}

	/**
	 * 在界面上交替闪烁--后面采用线程的方式进行
	 */
	private void setImgStatusAfterCaptchaFailed() {

		// 在界面上交替闪烁--后面采用线程的方式进行
		SetImgStatusAfterFailed he = new SetImgStatusAfterFailed();
		Thread demo = new Thread(he, "Action");
		demo.start();

	}

	/**
	 * 设置 验证失败后的图片显示设置的线程
	 * 
	 * @author Zheng 2014年5月22日 上午9:34:39
	 */
	class SetImgStatusAfterFailed implements Runnable {
		public void run() {
			try {

				int gapTime = 100;// 闪烁交替时间

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
				GtLogger.ex(LoggerString.getFileLineMethod() + e.getMessage());
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

								// 安卓客户端接收到消息后进行相应的处理
								GtLogger.v("postCaptchaInfoToCustomServer:  "
										+ response);

							} catch (Exception e) {
								GtLogger.ex(LoggerString.getFileLineMethod()
										+ e.getMessage());
							}

						}
					}, new Response.ErrorListener() {

						public void onErrorResponse(VolleyError error) {

							GtLogger.ex(LoggerString.getFileLineMethod()
									+ error.getMessage());
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

					// Gson gson = new Gson();
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
			GtLogger.ex(LoggerString.getFileLineMethod() + e.getMessage());
		}

	}

	// // 用于调试的函数区
	//
	// /**
	// *
	// */
	// public void postDebugMsgToServer(final String jsonDebugMsg) {
	//
	// try {
	//
	// String customServerGtApiUrl = GtApiEnv.debugServerApi;
	//
	// StringRequest stringRequest = new StringRequest(
	// Request.Method.POST, customServerGtApiUrl,
	// new Response.Listener<String>() {
	//
	// public void onResponse(String response) {
	//
	// try {
	//
	// // 安卓客户端接收到消息后进行相应的处理
	// GtLogger.v("postCaptchaInfoToCustomServer:  "
	// + response);
	//
	// } catch (Exception e) {
	// GtLogger.ex(LoggerString.getFileLineMethod()
	// + e.getMessage());
	// }
	//
	// }
	// }, new Response.ErrorListener() {
	//
	// public void onErrorResponse(VolleyError arg0) {
	//
	// GtLogger.v(LoggerString.getFileLineMethod()
	// + arg0.getMessage());
	// }
	// }) {
	//
	// @Override
	// protected Map<String, String> getParams() {
	// Map<String, String> params = new HashMap<String, String>();
	//
	// // 将客户端的信息编码成一个Json串，然后上传到客户服务器
	// params.put("debug_msg", jsonDebugMsg);
	// return params;
	// }
	//
	// };
	//
	// // 设置请求超时时间5s：http://blog.csdn.net/xyz_lmn/article/details/12177005
	// stringRequest.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 1,
	// 1.0f));
	// mQueue.add(stringRequest);
	//
	// } catch (Exception e) {
	// GtLogger.ex(LoggerString.getFileLineMethod() + e.getMessage());
	// }
	//
	// }

	/**
	 * 设置位置
	 */
	public void setLocation() {
		Window window = getWindow();

		window.setWindowAnimations(R.style.toast_anim);

		WindowManager.LayoutParams wl = window.getAttributes();
		wl.width = dm.widthPixels;// 旁边会有一些小距离，可能是
		GtLogger.v("wl.width: " + wl.width + "   wl.height: " + wl.height);

		wl.x = 0;// 0为中间
		wl.y = dm.heightPixels / 5;// 放在屏幕的1/5处
		wl.alpha = 0.9f;
		wl.gravity = (Gravity.LEFT | Gravity.TOP);
		window.setAttributes(wl);
	}
}