package com;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.alibaba.fastjson.JSONObject;

import Event.EventDef;
import Event.observeEvent;
import encryption.SHA512;

public class LoginServer {

	public static void signup(String name, char[] password) {
		String passwordString = SHA512.SHAStringToString(String.valueOf(password));
		HttpClient httpClient = HttpClientUtil.createDefaultHttpClient();
		PostMethod post = new PostMethod("/ProxyServer/registry.htm");
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		NameValuePair namePair = new NameValuePair("id", name);
		NameValuePair passwordPair = new NameValuePair("password", passwordString);
		paramList.add(namePair);
		paramList.add(passwordPair);
		post.setRequestBody(paramList.toArray(new NameValuePair[paramList.size()]));
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
			httpClient.executeMethod(post);
			byte[] responseBody = post.getResponseBody();
			String s = new String(responseBody, "UTF-8");
			JSONObject obj = JSONObject.parseObject(s);
			String error_no = obj.getString("error_no");
			if (error_no.equals("0")) { // 注册成功
				observeEvent.getInstance().setEventTag(EventDef.signupSuccess);
			} else {
				String error_info = obj.getString("error_info");
				JOptionPane.showMessageDialog(null, error_info, "提醒", JOptionPane.DEFAULT_OPTION);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "服务器错误", JOptionPane.ERROR_MESSAGE);
		} finally {
			post.releaseConnection();
		}
	}

	public static void login(String name, char[] password) {
		String passwordString = SHA512.SHAStringToString(String.valueOf(password));
		HttpClient httpClient = HttpClientUtil.createDefaultHttpClient();
		PostMethod post = new PostMethod("/ProxyServer/loginCheck.htm");
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		NameValuePair namePair = new NameValuePair("id", name);
		NameValuePair passwordPair = new NameValuePair("password", passwordString);
		paramList.add(namePair);
		paramList.add(passwordPair);
		post.setRequestBody(paramList.toArray(new NameValuePair[paramList.size()]));
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
			httpClient.executeMethod(post);
			byte[] responseBody = post.getResponseBody();
			String s = new String(responseBody, "UTF-8");
			JSONObject obj = JSONObject.parseObject(s);
			String error_no = obj.getString("error_no");
			if (error_no.equals("0")) { // 登录成功
				observeEvent.getInstance().setEventTag(EventDef.loginSuccess);
			} else {
				String error_info = obj.getString("error_info");
				JOptionPane.showMessageDialog(null, error_info, "提醒", JOptionPane.DEFAULT_OPTION);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "服务器错误", JOptionPane.ERROR_MESSAGE);
		} finally {
			post.releaseConnection();
		}
	}
}
