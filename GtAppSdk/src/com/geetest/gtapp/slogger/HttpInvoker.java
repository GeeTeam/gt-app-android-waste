package com.geetest.gtapp.slogger;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;

import org.apache.http.NameValuePair;

public class HttpInvoker {
	// public static final String GET_URL = "http://localhost:8080/welcome1";

	// public static final String POST_URL = "http://localhost:8080/welcome1";

	// private static final String POST_URL =
	// "http://192.168.1.100:80/local_log_msg/";

	// 还没有修改的一个网上COPY过来的代码
	public static void readContentFromGet(String GET_URL) throws IOException {
		// 拼凑get请求的URL字串，使用URLEncoder.encode对特殊和不可见字符进行编码
		String getURL = GET_URL + "?username="
				+ URLEncoder.encode("fat man", "utf-8");
		URL getUrl = new URL(getURL);
		// 根据拼凑的URL，打开连接，URL.openConnection函数会根据URL的类型，
		// 返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
		HttpURLConnection connection = (HttpURLConnection) getUrl
				.openConnection();
		// 进行连接，但是实际上get request要在下一句的connection.getInputStream()函数中才会真正发到
		// 服务器
		connection.connect();
		// 取得输入流，并使用Reader读取
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));
		String lines;
		while ((lines = reader.readLine()) != null) {
			System.out.println(lines);
		}
		reader.close();
		// 断开连接
		connection.disconnect();
	}

	/**
	 * 将key/value对转换成post的表单格式
	 * 
	 * @time 2014年7月11日 下午11:48:42
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static String getQuery(List<NameValuePair> params)
			throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		boolean first = true;

		for (NameValuePair pair : params) {
			if (first)
				first = false;
			else
				result.append("&");

			result.append(URLEncoder.encode(pair.getName(), "UTF-8"));
			result.append("=");
			result.append(URLEncoder.encode(pair.getValue(), "UTF-8"));
		}

		return result.toString();
	}

	public static void readContentFromPost(String POST_URL,
			String postStringData) throws IOException {
		URL postUrl = new URL(POST_URL);
		HttpURLConnection connection = (HttpURLConnection) postUrl
				.openConnection();
		connection.setConnectTimeout(1000);// 设置连接主机超时（单位：毫秒）
		connection.setReadTimeout(2000);// 设置从主机读取数据超时（单位：毫秒）
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		connection.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		connection.connect();
		DataOutputStream out = new DataOutputStream(
				connection.getOutputStream());

		// List<NameValuePair> params = new ArrayList<NameValuePair>();
		// params.add(new BasicNameValuePair("debug_msg", "AAAAbbb"));
		// params.add(new BasicNameValuePair("secondParam", "AAAA"));
		// params.add(new BasicNameValuePair("thirdParam", "AAAA"));
		// out.writeBytes(getQuery(params));
		String content = "debug_msg="
				+ URLEncoder.encode(postStringData, "utf-8");
		out.writeBytes(content);

		out.flush();
		out.close(); // flush and close
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream()));

		StringBuffer sBuffer = new StringBuffer();
		InputStream in = null;
		byte[] buf = new byte[1024];
		in = connection.getInputStream();
		for (int n; (n = in.read(buf)) != -1;) {
			sBuffer.append(new String(buf, 0, n, "UTF-8"));
		}

		System.out.println(sBuffer.toString());

		reader.close();
		connection.disconnect();
	}

}