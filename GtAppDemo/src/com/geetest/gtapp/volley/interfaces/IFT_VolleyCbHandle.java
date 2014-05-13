package com.geetest.gtapp.volley.interfaces;

import com.geetest.gtapp.volley.data.Volley_CbParaVO;

/**
 * @author zheng
 * @comment 将三层的回调函数全部统一起来
 * @time 2013年11月10日 11:16:11
 */
public interface IFT_VolleyCbHandle {

	/**
	 * @comment 网络通信成功后的回调函数
	 * @param cbPrarVo
	 */
	public void succeedCbHandle(Volley_CbParaVO cbPrarVo);

	/**
	 * @comment 网络通信失败的回调函数
	 */
	public void errorCbHandle(Volley_CbParaVO cbPrarVo);

}
