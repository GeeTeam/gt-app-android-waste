package com.geetest.gtapp.volley.data;

import org.json.JSONObject;

import android.content.Context;

import com.android.volley.VolleyError;
import com.geetest.gtapp.volley.interfaces.IFT_VolleyCbHandle;

/**
 * @author zheng
 * @comment 回调机制下的参数-CallbackParameterValueObject
 * @comment 为了方便，采用一个大的VO,把所有的都包含在里面
 * @time 2013年11月10日 10:53:57
 */
public class Volley_CbParaVO {

	private Context context;// 设备上下文

	private Class<?> activityVoClassName;// activity层告诉service层：我需要这样的一个VO类，最终需要拼接成的和界面相关的VO的类名称
	private Object activityValueObject;// 在回调的时候，service层会提供一个这样的值回来
	private Class<?> daoVoClassName;// DAO层提供的和JSON相对应的VO类。
	private Object daoValueObject;// DAO层提供的和JSON相对应的VO值

	private String jsonDataUrl;// 服务器的API链接路径
	private int httpMethod;// 是get/POST
	private String postStringKey = "jsonStr";// 使用POST的方式发送时的key值:默认是此值
	private String postStringValue = "";

	private JSONObject jsonRequest;// POST里面的内容

	private String progressDialogTitle = "获取服务器数据";// 进度框标题-有默认值
	private String progressDialogProcessMsg = "...加载中...";// 加载信息-有默认值

	private IFT_VolleyCbHandle activityCbHandle;// activity层的回调函数
	private IFT_VolleyCbHandle serviceCbHandle;// service层的回调函数
	private IFT_VolleyCbHandle daoCbHandle;// dao层的回调函数

	private String response;
	private VolleyError arg0;

	public String getPostStringKey() {
		return postStringKey;
	}

	public void setPostStringKey(String postStringKey) {
		this.postStringKey = postStringKey;
	}

	public String getPostStringValue() {
		return postStringValue;
	}

	public void setPostStringValue(String postStringValue) {
		this.postStringValue = postStringValue;
	}

	public String getJsonDataUrl() {
		return jsonDataUrl;
	}

	public void setJsonDataUrl(String jsonDataUrl) {
		this.jsonDataUrl = jsonDataUrl;
	}

	public int getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(int httpMethod) {
		this.httpMethod = httpMethod;
	}

	public JSONObject getJsonRequest() {
		return jsonRequest;
	}

	public void setJsonRequest(JSONObject jsonRequest) {
		this.jsonRequest = jsonRequest;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public void setActivityCbHandle(IFT_VolleyCbHandle activityCbHandle) {
		this.activityCbHandle = activityCbHandle;
	}

	public void setServiceCbHandle(IFT_VolleyCbHandle serviceCbHandle) {
		this.serviceCbHandle = serviceCbHandle;
	}

	public void setDaoCbHandle(IFT_VolleyCbHandle daoCbHandle) {
		this.daoCbHandle = daoCbHandle;
	}

	public Class<?> getActivityVoClassName() {
		return activityVoClassName;
	}

	public void setActivityVoClassName(Class<?> activityVoClassName) {
		this.activityVoClassName = activityVoClassName;
	}

	public Object getActivityValueObject() {
		return activityValueObject;
	}

	public void setActivityValueObject(Object activityValueObject) {
		this.activityValueObject = activityValueObject;
	}

	public Class<?> getDaoVoClassName() {
		return daoVoClassName;
	}

	public void setDaoVoClassName(Class<?> daoVoClassName) {
		this.daoVoClassName = daoVoClassName;
	}

	public Object getDaoValueObject() {
		return daoValueObject;
	}

	public void setDaoValueObject(Object daoValueObject) {
		this.daoValueObject = daoValueObject;
	}

	public IFT_VolleyCbHandle getActivityCbHandle() {
		return activityCbHandle;
	}

	public IFT_VolleyCbHandle getServiceCbHandle() {
		return serviceCbHandle;
	}

	public IFT_VolleyCbHandle getDaoCbHandle() {
		return daoCbHandle;
	}

	public String getProgressDialogTitle() {
		return progressDialogTitle;
	}

	public void setProgressDialogTitle(String progressDialogTitle) {
		this.progressDialogTitle = progressDialogTitle;
	}

	public String getProgressDialogProcessMsg() {
		return progressDialogProcessMsg;
	}

	public void setProgressDialogProcessMsg(String progressDialogProcessMsg) {
		this.progressDialogProcessMsg = progressDialogProcessMsg;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public VolleyError getArg0() {
		return arg0;
	}

	public void setArg0(VolleyError arg0) {
		this.arg0 = arg0;
	}
}
