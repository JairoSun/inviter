package cn.doodlister.api.yzm.sm;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HttpHelper {
	public static String getHtml(String url, String param) {

		String urlNameString = url;
		if (param != null) {
			if (url.indexOf("?") > -1) {
				if (url.lastIndexOf("&") >= 1) {
					urlNameString += param;
				} else {
					urlNameString += "&" + param;
				}
			} else {
				urlNameString += "?" + param;
			}
		}
		return getHtml(urlNameString);
	}

	public static String getHtml(String url) {
		String result = "";
		BufferedReader in = null;

		try {
			URL realUrl = new URL(url);
			URLConnection 	connection = realUrl.openConnection();
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.addRequestProperty("Content-Type", "text/html;charset=UTF-8");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.connect();
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {

				if (in != null) {
					in.close();
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
}
