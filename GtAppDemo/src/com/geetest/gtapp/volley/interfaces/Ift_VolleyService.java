package com.geetest.gtapp.volley.interfaces;

import com.geetest.gtapp.volley.data.Volley_CbParaVO;


/**
 * 所有使用Volley通讯的Serice类都需要实现如下接口
 * @time 2013-12-9 下午4:55:01
 * @author zheng  dreamzsm@gmail.com
 */
public interface Ift_VolleyService {

	
	
//	/**
//	 * Service层的回调函数
//	 * @param callbackHandle
//	 * @time 2013-12-9 下午5:05:24
//	 * @author zheng  dreamzsm@gmail.com
//	 */
//	void setServiceCbHandle(Volley_CbParaVO cbParaVo);
	
	/**
	 * 设置http的URL
	 * @param jsonDataUrl
	 * @time 2013-12-9 下午5:07:33
	 * @author zheng  dreamzsm@gmail.com
	 */
	void setJsonDataUrl(Volley_CbParaVO cbParaVo);
	
	
	/**
	 * 设置DAO层返回的JSON对象对应的JAVA类的名称--方便GSON来做解析
	 * @param className
	 * @time 2013-12-9 下午5:07:46
	 * @author zheng  dreamzsm@gmail.com
	 */
	void setDaoVoClassName(Volley_CbParaVO cbParaVo);
	
	 /**
	  * 设置返回给Acitivty层的数值对象--
	 * @param activityValueObject
	 * @time 2013-12-9 下午5:04:03
	 * @author zheng  dreamzsm@gmail.com
	 */
	void setActivityValueObject(Volley_CbParaVO cbParaVo);
	 
	 

	/**
	 * POST的内容的key值-非Post类不需要
	 * @param cbParaVo
	 * @time 2013-12-10 上午11:29:05
	 * @author zheng  dreamzsm@gmail.com
	 */
	void setPostStringKey(Volley_CbParaVO cbParaVo);
	
	
	/**
	 * POST字符串的内容-非Post类不需要
	 * @param cbParaVo
	 * @time 2013-12-10 下午12:07:53
	 * @author zheng  dreamzsm@gmail.com
	 */
	void setPostStringValue(Volley_CbParaVO cbParaVo);
	
	
	
}
