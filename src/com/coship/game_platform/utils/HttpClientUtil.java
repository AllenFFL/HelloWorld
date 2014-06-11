package com.coship.game_platform.utils;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.coship.game_platform.domain.PlatformConstants;

public class HttpClientUtil {
	private HttpClient client;

//	private HttpRequest request;
	private HttpPost post;
//	private HttpGet get;

	private HttpResponse response;

	private static Header[] headers;

	static {
		headers = new Header[10];

		headers[0] = new BasicHeader("Appkey", "12343");
		headers[1] = new BasicHeader("Udid", "");// 手机串号
		headers[2] = new BasicHeader("Os", "android");//
		headers[3] = new BasicHeader("Osversion", "");//
		headers[4] = new BasicHeader("Appversion", "");// 1.0
		headers[5] = new BasicHeader("Sourceid", "");//
		headers[6] = new BasicHeader("Ver", "");

		headers[7] = new BasicHeader("Userid", "");
		headers[8] = new BasicHeader("Usersession", "");

		headers[9] = new BasicHeader("Unique", "");
	}

	public HttpClientUtil() {
		client = new DefaultHttpClient();
		// 设置wap的代理信息
		if (StringUtils.isNotBlank(PlatformConstants.PROXY_IP)) {
			HttpHost host = new HttpHost(PlatformConstants.PROXY_IP, PlatformConstants.PROXY_PORT);
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, host);
		}
	}

	/**
	 * 发送xml文件到服务器端
	 * 
	 * @param url
	 * @param xml
	 */
	public InputStream sendXml(String url, String xml) {
		post = new HttpPost(url);

		// 设置连接参数
		// HttpParams httpParams = new BasicHttpParams();//
		// httpParams = new BasicHttpParams();
		// HttpConnectionParams.setConnectionTimeout(httpParams, 8000);
		// HttpConnectionParams.setSoTimeout(httpParams, 8000);
		// post.setParams(httpParams);

		// 设置传输内容
		try {
			StringEntity entity = new StringEntity(xml, PlatformConstants.CHARSET);
			post.setEntity(entity);

			response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				return response.getEntity().getContent();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 发送带参数post请求
	 * 
	 * @param url
	 * @param params
	 * @return
	 */
	public String sendPost(String url, Map<String, String> params) {
		post = new HttpPost(url);
		// 设置公共头信息
		post.setHeaders(headers);

		// 设置参数
		if (params != null && params.size() > 0) {
			List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
			for (Map.Entry<String, String> item : params.entrySet()) {
				BasicNameValuePair pair = new BasicNameValuePair(item.getKey(), item.getValue());
				parameters.add(pair);

			}
			try {
				UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(parameters, PlatformConstants.CHARSET);
				post.setEntity(encodedFormEntity);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				// response.getEntity().getContent()
				// 需要将回复内容（getEntity()）转换成字符串信息
				return EntityUtils.toString(response.getEntity(), PlatformConstants.CHARSET);
				// return response.getEntity().getContent();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

}
