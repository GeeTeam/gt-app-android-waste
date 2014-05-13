//package com.geeksfit.volley.dao;
//
//import com.geeksfit.system.logger.Logger;
//import com.geeksfit.util.data.LoggerTag;
//import com.geeksfit.volley.data.Volley_CbParaVO;
//import com.geeksfit.volley.interfaces.IFT_VolleyCbHandle;
//import com.google.gson.Gson;
//
//public class FT_Volley_BaseDao {
//
////	protected Context context;
////
////	protected Context getContext() {
////		return context;
////	}
////
////	protected void setContext(Context context) {
////		this.context = context;
////	}
//
//	protected IFT_VolleyCbHandle ift_VolleyDaoCallbackHandle = new IFT_VolleyCbHandle() {
//
//		public void succeedCbHandle(Volley_CbParaVO daoCbPrarVo) {
//
//			Logger.v(LoggerTag.volleyGetResponse + daoCbPrarVo.getResponse().toString());
//			Logger.v("Vollery-DAO:succeedCallbackHandle");
//
//			try {
//
//				Logger.toastExpection(daoCbPrarVo.getContext(), "开始GSON解析！！！");
//				Gson gson = new Gson();
//				// 收到消息后开始将JOSN字符串解析成VO对象，然后再传回Service层的回调函数
//				Object daoVauleObject = gson.fromJson(daoCbPrarVo.getResponse().toString(),
//						daoCbPrarVo.getDaoVoClassName());
//				
//				//增加进去!!!
//				daoCbPrarVo.setDaoValueObject(daoVauleObject);
//				
//				daoCbPrarVo.getServiceCbHandle().succeedCbHandle(daoCbPrarVo);
//
//			} catch (Exception e) {
//				Logger.toastExpection(daoCbPrarVo.getContext(), e.getMessage());
//			}
//
//		}
//
//		public void errorCbHandle(Volley_CbParaVO cbPrarVo) {
//			Logger.v("Vollery-DAO：errorCallbackHandle");
//			Logger.toastExpection(cbPrarVo.getContext(), cbPrarVo.getArg0().getMessage());
//
//			// TODO:volleyServiceCallbackHandle.errorCallbackHandle(arg0);
//
//		}
//	};
//
//	
//
//}
