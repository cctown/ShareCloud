package com;

import org.apache.commons.httpclient.HttpClient;

public class HttpClientUtil {
	
	public static HttpClient createDefaultHttpClient() {
		HttpClient httpClient = HttpClientUtil.createHttpClient("www.miyun.com", 8080);
		return httpClient;
	}
	
	public static HttpClient createHttpClient(String appServer, int port) {
		HttpClient httpClient = new HttpClient();
		httpClient.getHostConfiguration().setHost(appServer, port, "http");
		return httpClient;
	}
}
