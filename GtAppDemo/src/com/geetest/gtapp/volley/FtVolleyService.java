package com.geetest.gtapp.volley;

import com.geetest.gtapp.logger.GtLogger;
import com.geetest.gtapp.utils.LoggerString;
import com.geetest.gtapp.volley.data.Volley_CbParaVO;
import com.geetest.gtapp.volley.interfaces.IFT_VolleyCbHandle;
import com.geetest.gtapp.volley.interfaces.Ift_VolleyService;

/**
 * 所有使用Volley进行通讯的代理
 * 
 * @time 2013-12-10 上午11:03:20
 * @author zheng dreamzsm@gmail.com
 */
public class FtVolleyService implements Ift_VolleyService {

	/**
	 * 被代理的对象
	 */
	private Ift_VolleyService vService;

	public FtVolleyService(Ift_VolleyService vService) {
		this.vService = vService;
	}

	/**
	 * 查询 的回调函数
	 * 
	 * @notes 只有最后一道工序才需要调用 Activity的回调函数
	 */
	private IFT_VolleyCbHandle cb_getDataFromServer = new IFT_VolleyCbHandle() {

		public void succeedCbHandle(Volley_CbParaVO cbParaVo) {

			try {
				setActivityValueObject(cbParaVo);
				cbParaVo.getActivityCbHandle().succeedCbHandle(cbParaVo);
			} catch (Exception e) {
				GtLogger.toastExpection(cbParaVo.getContext(),
						LoggerString.getFileLineMethod() + e.getMessage());
			}

		}

		public void errorCbHandle(Volley_CbParaVO serviceCbPrarVo) {

		}
	};

	/**
	 * 向服务器Get数据
	 * 
	 * @time 2013-12-10 上午11:09:08
	 * @author zheng dreamzsm@gmail.com
	 */
	public void getDataFromServer(Volley_CbParaVO cbParaVo) {

		cbParaVo.setServiceCbHandle(cb_getDataFromServer);
//		this.setServiceCbHandle(cbParaVo);
		this.setJsonDataUrl(cbParaVo);
		this.setDaoVoClassName(cbParaVo);
		// 没有Post的对象
		
		StaticsDao volleyDao = new StaticsDao();
		volleyDao.getVolleyMethod(cbParaVo);
		
	}

	/**
	 * POST提交数据到服务器。实际上不是POST，后面的意义是：使用StringRequst的Volley通讯方式
	 * 
	 * @time 2013-12-10 上午11:09:23
	 * @author zheng dreamzsm@gmail.com
	 */
	public void postDataToServer(Volley_CbParaVO cbParaVo) {

		cbParaVo.setServiceCbHandle(cb_getDataFromServer);
//		this.setServiceCbHandle(cbParaVo);
		this.setJsonDataUrl(cbParaVo);
		this.setDaoVoClassName(cbParaVo);
		// this.setPostStringKey(cbParaVo);//直接全部采用的是默认值
		this.setPostStringValue(cbParaVo);
		
		StaticsDao staticsDao = new StaticsDao();
		staticsDao.postVolleyMethod(cbParaVo);
		
	}

	// 下面都是接口的方法
//	public void setServiceCbHandle(Volley_CbParaVO cbParaVo) {
//		this.vService.setServiceCbHandle(cbParaVo);
//	}

	public void setJsonDataUrl(Volley_CbParaVO cbParaVo) {
		this.vService.setJsonDataUrl(cbParaVo);
	}

	public void setDaoVoClassName(Volley_CbParaVO cbParaVo) {
		this.vService.setDaoVoClassName(cbParaVo);
	}

	public void setActivityValueObject(Volley_CbParaVO cbParaVo) {
		this.vService.setActivityValueObject(cbParaVo);
	}

	public void setPostStringKey(Volley_CbParaVO cbParaVo) {
		this.vService.setPostStringKey(cbParaVo);
	}

	public void setPostStringValue(Volley_CbParaVO cbParaVo) {
		this.vService.setPostStringValue(cbParaVo);

	}

}
