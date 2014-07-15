package com.geetest.gtapp.utils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 一些和极验的服务器相关的类
 * 
 * @author Zheng
 * @time 2014年7月15日 下午3:25:41
 */
public class GtAppLib {

	/**
	 * 获取服务器状态值
	 * 
	 * @time 2014年7月15日 下午8:24:25
	 * @return
	 */
	public static int getGtServerStatus() {

		try {
			final String GET_URL = "http://api.geetest.com:80/check_status.php";
			if (readContentFromGet(GET_URL).equals("ok")) {
				System.out.println("is Ok");
				return 1;
			} else {
				return 0;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 读取服务器
	 * 
	 * @author Zheng dreamzsm@gmail.com
	 * @time 2014年7月10日 下午7:11:11
	 * @param getURL
	 * @return
	 * @throws IOException
	 */
	private static String readContentFromGet(String getURL) throws IOException {

		URL getUrl = new URL(getURL);
		HttpURLConnection connection = (HttpURLConnection) getUrl
				.openConnection();

		connection.setConnectTimeout(1000);// 设置连接主机超时（单位：毫秒）
		connection.setReadTimeout(1000);// 设置从主机读取数据超时（单位：毫秒）

		// 建立与服务器的连接，并未发送数据

		connection.connect();
		// 发送数据到服务器并使用Reader读取返回的数据
		StringBuffer sBuffer = new StringBuffer();

		InputStream inStream = null;
		byte[] buf = new byte[1024];
		inStream = connection.getInputStream();
		for (int n; (n = inStream.read(buf)) != -1;) {
			sBuffer.append(new String(buf, 0, n, "UTF-8"));
		}
		inStream.close();
		connection.disconnect();// 断开连接

		return sBuffer.toString();
	}

}
