package com.geetest.gtapp.volley;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import android.app.ProgressDialog;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.geetest.gtapp.logger.GtLogger;
import com.geetest.gtapp.utils.LoggerString;
import com.geetest.gtapp.utils.data.LoggerTag;
import com.geetest.gtapp.volley.data.Volley_CbParaVO;

/**
 * @author zheng
 * @comment 封装的volley类
 * @time 2013年11月7日 20:21:34
 */
public class FT_Volley {

	private Volley_CbParaVO cbParaVO = new Volley_CbParaVO();

	public FT_Volley(Volley_CbParaVO cbParaVO) {
		this.cbParaVO = cbParaVO;
	}

	/**
	 * 利用Volley获取JSON数据--JSONOjbect
	 */
	public void getJSONByVolley() {

		try {
			RequestQueue requestQueue = Volley.newRequestQueue(cbParaVO
					.getContext());// ？？？？
			// String JSONDataUrl = cbParaVO.getJsonDataUrl();

			GtLogger.v(LoggerTag.volleyUrl + cbParaVO.getJsonDataUrl());

			final ProgressDialog progressDialog = ProgressDialog.show(
					cbParaVO.getContext(), cbParaVO.getProgressDialogTitle(),
					cbParaVO.getProgressDialogProcessMsg());

			JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
					Request.Method.GET, cbParaVO.getJsonDataUrl(), null,
					new Response.Listener<JSONObject>() {
						public void onResponse(JSONObject response) {

							// 进度框结束
							if (progressDialog.isShowing()
									&& progressDialog != null) {
								progressDialog.dismiss();
							}

							cbParaVO.setResponse(response.toString());
							GtLogger.v("mcgvollly", "chenggong");
							// 成功函数回调
							cbParaVO.getDaoCbHandle().succeedCbHandle(cbParaVO);// ？？？？

						}
					}, new Response.ErrorListener() {
						public void onErrorResponse(VolleyError arg0) {

							// 进度框结束
							if (progressDialog.isShowing()
									&& progressDialog != null) {
								progressDialog.dismiss();
							}

							cbParaVO.setArg0(arg0);

							// 错误函数回调
							cbParaVO.getDaoCbHandle().errorCbHandle(cbParaVO);

						}
					});

			// 设置请求超时时间5s：http://blog.csdn.net/xyz_lmn/article/details/12177005
			jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5 * 1000,
					1, 1.0f));

			requestQueue.add(jsonObjectRequest);
		} catch (Exception e) {
			GtLogger.toastExpection(cbParaVO.getContext(),
					LoggerString.getFileLineMethod() + e.getMessage());
		}

	}

	/**
	 * 利用Volley去POST取JSON数据--采用李原提供的方法，使用的是StringRequest,返回的是String型的
	 */
	public void postJSONByVolley() {

		try {

			RequestQueue requestQueue = Volley.newRequestQueue(cbParaVO
					.getContext());

			final ProgressDialog progressDialog = ProgressDialog.show(
					cbParaVO.getContext(), cbParaVO.getProgressDialogTitle(),
					cbParaVO.getProgressDialogProcessMsg());

			// HashMap<String, String> params = new HashMap<String, String>();
			// params.put(cbParaVO.getPostStringKey(),
			// cbParaVO.getPostStringValue());

			GtLogger.v(LoggerTag.volleyUrl + cbParaVO.getJsonDataUrl());

			StringRequest stringRequest = new StringRequest(
					Request.Method.POST, cbParaVO.getJsonDataUrl(),
					new Response.Listener<String>() {

						public void onResponse(String response) {

							try {
								// 进度框结束
								if (progressDialog.isShowing()
										&& progressDialog != null) {
									progressDialog.dismiss();
								}

								// cbParaVO.setResponse(new
								// JSONObject(response));//旧的一直是可行的
								cbParaVO.setResponse(response);// 为了适应JsonArray，返回的值全部弱化成字符串

								// 成功函数回调
								cbParaVO.getDaoCbHandle().succeedCbHandle(
										cbParaVO);// ？？？？接口

							} catch (Exception e) {
								GtLogger.v(LoggerString.getFileLineMethod()
										+ e.getMessage());
							}

						}
					}, new Response.ErrorListener() {

						public void onErrorResponse(VolleyError arg0) {
							// 进度框结束
							if (progressDialog.isShowing()
									&& progressDialog != null) {
								progressDialog.dismiss();
							}

							cbParaVO.setArg0(arg0);
							// 错误函数回调
							cbParaVO.getDaoCbHandle().errorCbHandle(cbParaVO);
						}
					}) {

				@Override
				protected Map<String, String> getParams() {
					Map<String, String> params = new HashMap<String, String>();
					params.put(cbParaVO.getPostStringKey(),
							cbParaVO.getPostStringValue());

					GtLogger.v(LoggerTag.volleyPostRequest
							+ cbParaVO.getPostStringValue());

					return params;
				}

			};

			// 设置请求超时时间5s：http://blog.csdn.net/xyz_lmn/article/details/12177005
			stringRequest.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 1,
					1.0f));
			requestQueue.add(stringRequest);

		} catch (Exception e) {
			GtLogger.toastExpection(cbParaVO.getContext(),
					LoggerString.getFileLineMethod() + e.getMessage());
		}

	}

	/**
	 * 利用Volley去POST取JSON数据--网上找的代码，没有试成功
	 */
	public void postJSONByVolley_Old() {

		try {
			RequestQueue requestQueue = Volley.newRequestQueue(cbParaVO
					.getContext());

			final ProgressDialog progressDialog = ProgressDialog.show(
					cbParaVO.getContext(), cbParaVO.getProgressDialogTitle(),
					cbParaVO.getProgressDialogProcessMsg());

			HashMap<String, String> params = new HashMap<String, String>();
			params.put(cbParaVO.getPostStringKey(),
					cbParaVO.getPostStringValue());

			JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
					cbParaVO.getJsonDataUrl(), new JSONObject(params),
					new Response.Listener<JSONObject>() {
						public void onResponse(JSONObject response) {

							// 进度框结束
							if (progressDialog.isShowing()
									&& progressDialog != null) {
								progressDialog.dismiss();
							}

							cbParaVO.setResponse(response.toString());

							// 成功函数回调
							cbParaVO.getDaoCbHandle().succeedCbHandle(cbParaVO);

						}
					}, new Response.ErrorListener() {
						public void onErrorResponse(VolleyError arg0) {

							// 进度框结束
							if (progressDialog.isShowing()
									&& progressDialog != null) {
								progressDialog.dismiss();
							}

							cbParaVO.setArg0(arg0);

							// 错误函数回调
							cbParaVO.getDaoCbHandle().errorCbHandle(cbParaVO);

						}
					});

			// 设置请求超时时间5s：http://blog.csdn.net/xyz_lmn/article/details/12177005
			jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(5 * 1000,
					1, 1.0f));

			requestQueue.add(jsonObjectRequest);
		} catch (Exception e) {
			GtLogger.toastExpection(cbParaVO.getContext(), e.getMessage());
		}

	}

	// /**
	// * 利用Volley获取JSON数据
	// */
	// public void getJSONByVolley_Net() {
	//
	// RequestQueue requestQueue = Volley.newRequestQueue(this.context);
	// String JSONDataUrl = this.jsonDataUrl;
	//
	// final ProgressDialog progressDialog = ProgressDialog.show(this.context,
	// this.progressDialogTitle, this.progressDialogProcessMsg);
	//
	// JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
	// Request.Method.GET, JSONDataUrl, null,
	// new Response.Listener<JSONObject>() {
	// public void onResponse(JSONObject response) {
	// System.out.println("response=" + response);
	// if (progressDialog.isShowing()
	// && progressDialog != null) {
	// progressDialog.dismiss();
	// }
	// }
	// }, new Response.ErrorListener() {
	// public void onErrorResponse(VolleyError arg0) {
	// System.out.println("sorry,Error");
	// }
	// });
	// requestQueue.add(jsonObjectRequest);
	// }

	// TODO:后面再写一些POST的类。

}
