package com.geetest.gtapp.volley;

import com.geetest.gtapp.logger.GtLogger;
import com.geetest.gtapp.utils.LoggerString;
import com.geetest.gtapp.utils.data.LoggerTag;
import com.geetest.gtapp.volley.data.Volley_CbParaVO;
import com.geetest.gtapp.volley.interfaces.IFT_VolleyCbHandle;
import com.google.gson.Gson;

/**
 * @author zheng
 * @comment DAO层，只是做JSON的原生的还原，不做任何装配动作,这部分最后可以自动生成代码
 * @comment interface的作用是由架构师来做初步的模型--不做多态的时候，可以省去此动作
 * */
public class StaticsDao {

	/**
	 * 提示信息
	 */
	private final String startGsonTipMsg = "开始GSON解析！！！";

	private IFT_VolleyCbHandle ift_VolleyDaoCallbackHandle = new IFT_VolleyCbHandle() {

		public void succeedCbHandle(Volley_CbParaVO daoCbPrarVo) {
			GtLogger.v("回调1" + "Vollery-DAO:succeedCallbackHandle");

			try {
				GtLogger.v(LoggerTag.volleyGetResponse
						+ daoCbPrarVo.getResponse().toString());
				GtLogger.toastDebugMsg(daoCbPrarVo.getContext(), startGsonTipMsg);

				Gson gson = new Gson();
				// 收到消息后开始将JOSN字符串解析成VO对象，然后再传回Service层的回调函数
				Object daoVauleObject = gson.fromJson(daoCbPrarVo.getResponse()
						.toString(), daoCbPrarVo.getDaoVoClassName());

				// 增加进去!!!
				daoCbPrarVo.setDaoValueObject(daoVauleObject);// 4.reponse为什么不用
																// 他们有什么区别
				GtLogger.v("Vollery-DAO:succeedCallbackHandle");
				daoCbPrarVo.getServiceCbHandle().succeedCbHandle(daoCbPrarVo);// ????

			} catch (Exception e) {
				GtLogger.toastExpection(daoCbPrarVo.getContext(),
						LoggerString.getFileLineMethod() + e.getMessage());
			}

		}

		public void errorCbHandle(Volley_CbParaVO cbPrarVo) {
			GtLogger.toastExpection(
					cbPrarVo.getContext(),
					LoggerTag.volleyDaoCbError
							+ LoggerString.getFileLineMethod()
							+ cbPrarVo.getArg0().getMessage());
			// 如果失败了就不用回调到界面了 2013年11月20日 14:36:53
		}
	};

	/**
	 * Get类的都有此方法
	 * 
	 * @param cbParaVo
	 * @time 2013-11-14 下午3:19:08
	 * @author zheng dreamzsm@gmail.com
	 */
	public void getVolleyMethod(Volley_CbParaVO cbParaVo) {
		try {
			cbParaVo.setDaoCbHandle(ift_VolleyDaoCallbackHandle);
			FT_Volley ft_Volley = new FT_Volley(cbParaVo);
			ft_Volley.getJSONByVolley();

		} catch (Exception e) {
			GtLogger.toastExpection(cbParaVo.getContext(),
					LoggerString.getFileLineMethod() + e.getMessage());
		}

	}

	/**
	 * Post类的都用此方法
	 * 
	 * @param cbParaVo
	 * @time 2013-11-14 下午3:19:59
	 * @author zheng dreamzsm@gmail.com
	 */
	public void postVolleyMethod(Volley_CbParaVO cbParaVo) {
		try {
			cbParaVo.setDaoCbHandle(ift_VolleyDaoCallbackHandle);

			FT_Volley ft_Volley = new FT_Volley(cbParaVo);
			ft_Volley.postJSONByVolley();
		} catch (Exception e) {
			GtLogger.toastExpection(cbParaVo.getContext(),
					LoggerString.getFileLineMethod() + e.getMessage());
		}
	}

	// /**
	// * 获取所有的不良记录数据
	// *
	// * @param queryVo
	// * @param cbPrarVo
	// * @time 2013-11-11 下午7:58:25
	// * @author zheng dreamzsm@gmail.com
	// */
	// public void getIQCDefectiveMaterialClassifyStatisticsByIQCBatchId(
	// Volley_CbParaVO cbParaVo) {
	//
	// //TODO 某个参数类可以删除了
	// IFT_VolleyCbHandle ift_VolleyDaoCallbackHandle =
	// super.ift_VolleyDaoCallbackHandle;// 直接从父类继承同样的代码
	// cbParaVo.setDaoCbHandle(ift_VolleyDaoCallbackHandle);
	// FT_Volley ft_Volley = new FT_Volley(cbParaVo);
	// ft_Volley.getJSONByVolley();
	// }
	//
	// /**
	// * 添加一条记录
	// *
	// * @param deleteVo
	// * @param cbPrarVo
	// * @time 2013-11-11 下午8:09:45
	// * @author zheng dreamzsm@gmail.com
	// */
	// public void addIQCDefectiveMaterialClassifyStatistics(
	// Volley_CbParaVO cbParaVo) {
	//
	// try {
	//
	// IFT_VolleyCbHandle ift_VolleyDaoCallbackHandle =
	// super.ift_VolleyDaoCallbackHandle;// 直接从父类继承同样的代码
	// cbParaVo.setDaoCbHandle(ift_VolleyDaoCallbackHandle);
	// FT_Volley ft_Volley = new FT_Volley(cbParaVo);
	// ft_Volley.postJSONByVolley();
	//
	// } catch (Exception e) {
	// Logger.toastExpection(cbParaVo.getContext(),
	// LoggerString.getFileLineMethod() + e.getMessage());
	// }
	//
	// }
	//
	//
	//
	// public void updateIQCDefectiveMaterialClassifyStatistics(
	// Volley_CbParaVO cbParaVo) {
	// try {
	// IFT_VolleyCbHandle ift_VolleyDaoCallbackHandle =
	// super.ift_VolleyDaoCallbackHandle;// 直接从父类继承同样的代码
	// cbParaVo.setDaoCbHandle(ift_VolleyDaoCallbackHandle);
	//
	// FT_Volley ft_Volley = new FT_Volley(cbParaVo);
	// ft_Volley.postJSONByVolley();
	// } catch (Exception e) {
	// Logger.toastExpection(cbParaVo.getContext(),
	// LoggerString.getFileLineMethod() + e.getMessage());
	// }
	//
	// }
	//
	// /**
	// * 删除指定的SID的不良记录
	// *
	// * @param deleteVo
	// * 干脆传递整个VO
	// * @time 2013-11-11 下午8:01:50
	// * @author zheng dreamzsm@gmail.com
	// */
	// public void deleteIQCDefectiveMaterialClassifyStatistics(
	// Volley_CbParaVO cbPrarVo) {
	//
	//
	// IFT_VolleyCbHandle ift_VolleyDaoCallbackHandle =
	// super.ift_VolleyDaoCallbackHandle;// 直接从父类继承同样的代码
	// // 本地构建的参数
	// cbPrarVo.setDaoCbHandle(ift_VolleyDaoCallbackHandle);
	//
	// FT_Volley ft_Volley = new FT_Volley(cbPrarVo);
	// ft_Volley.getJSONByVolley();
	//
	// }
	//
	//
	//
	// public void getDefectiveMaterialClassify(Volley_CbParaVO cbParaVo) {
	//
	// IFT_VolleyCbHandle ift_VolleyDaoCallbackHandle =
	// super.ift_VolleyDaoCallbackHandle;// 直接从父类继承同样的代码
	// // 本地构建的参数
	// cbParaVo.setDaoCbHandle(ift_VolleyDaoCallbackHandle);
	// FT_Volley ft_Volley = new FT_Volley(cbParaVo);
	// ft_Volley.getJSONByVolley();
	//
	// }

}