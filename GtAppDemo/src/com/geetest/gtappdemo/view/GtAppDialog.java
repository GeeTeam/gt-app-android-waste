package com.geetest.gtappdemo.view;

import java.util.ArrayList;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.geetest.gtappdemo.R;
import com.geetest.gtappdemo.model.vo.CaptchaOption;
import com.geetest.gtappdemo.model.vo.CaptchaUserAction;
import com.geetest.gtappdemo.model.vo.GtPoint;
import com.geetest.gtappdemo.model.vo.GtShapeSize;
import com.geetest.gtappdemo.model.vo.greq.AjaxPhp_GreqVo;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

/**
 * 弹出的对话框
 * 
 * @author Zheng 2014年5月30日 下午2:22:38
 */
public class GtAppDialog extends Dialog {

	private Context context;// 父窗口
	private int gtAppDlgLayoutResId = 0;

	// 界面元素信息
	private View dlgView;

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

	private RequestQueue mQueue;// 用于Volley的通讯内容

	// 验证通讯数据对象
	private CaptchaOption initCaptchaOption;// 验证码初始化验证数据设置
	private AjaxPhp_GreqVo ajaxPhp_GreqVo;// 上传行为数据的API参数

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
	private final int MSG_SLICE_BG_ALPHA_MISS = 3;// 切片图渐变消失

	// 坐标位置
	private GtPoint sliderStartLeftTopPosition = new GtPoint();// 滑块左上角坐标
	private GtPoint sliderStartPressTouchPosition = new GtPoint();// 按下滑块时的触点位置
	private GtPoint sliderDragMoveTouchPosition = new GtPoint();// 拖动时触点位置
	private GtPoint sliderUpTouchPosition = new GtPoint();// 放开时触点位置

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

	// 图片展示区离屏幕边缘的距--和布局文件的设计有关
	private int leftMargin = 0;// dp
	private int rightMargin = 0;// dp

	// private float seekbar_server_length = 1;
	// 滑条在服务器端的标准长度px--和背景图一样大，在安卓上的显示的长度有1.3倍的差距
	// 80/1.3

	// private int slice_img_width = 126;// 切片图的宽度

	/* 声明相关变量作为存储图片宽高,位置使用 */
	// private int intWidth, intHeight, intDefaultX, intDefaultY;

	private float mX, mY;// 触点的位置

	public GtAppDialog(Context context, int gtAppDlgLayoutResId) {
		super(context);
		this.context = context;
		this.gtAppDlgLayoutResId = gtAppDlgLayoutResId;

	}

	// public void SetDialogResource(int gtAppDlgLayoutResId) {
	//
	// }

	/**
	 * 界面控件元素的绑定工作
	 */
	private void initViews() {
		mQueue = Volley.newRequestQueue(context);// 必须在界面初始化之后才有此声明
		reLayoutView = (RelativeLayout) this.findViewById(R.id.ll_viewArea22);

		imgv_full_bg = (ImageView) reLayoutView.findViewById(R.id.imgv_full_bg);
		imgv_slice = (ImageView) reLayoutView.findViewById(R.id.imgv_slice);
		imgv_slice_bg = (ImageView) reLayoutView
				.findViewById(R.id.imgv_slice_bg);

		skb_dragCaptcha = (SeekBar) findViewById(R.id.seekbar_def); // “系统默认SeekBar”
		btn_refresh = (Button) findViewById(R.id.btn_refresh);
		tv_validateStatus = (TextView) findViewById(R.id.tv_validateStatus);

	}

	/**
	 * 
	 */
	public void setDisplay() {
		setContentView(gtAppDlgLayoutResId);
		initViews();

		// initViewDisplayParameter();
		// initListeners();

		// 刷新图片
		btn_refresh.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				new GtAppDialog(context, R.layout.image_move).setDisplay();
			}
		});

		// setTitle("继承 Dialog类");
		setLocation();
		show();
	}

	/**
	 * 设置位置
	 */
	public void setLocation() {
		Window window = getWindow();
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = 0;// 0为中间
		wl.y = 100;
		wl.alpha = 0.9f;
		wl.gravity = Gravity.TOP;
		window.setAttributes(wl);
	}
}