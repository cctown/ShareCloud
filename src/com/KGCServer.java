package com;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.alibaba.fastjson.JSONObject;

import SecretCloudProxy.CommonDef;
import UserDefault.UserInfo;
import encryption.CommonFileManager;

public class KGCServer {

	public static boolean getParams() {
		boolean res = true;
		HttpClient httpClient = HttpClientUtil.createDefaultKGCHttpClient();
		PostMethod post = new PostMethod("/KGCServer/getParams.htm");
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
			httpClient.executeMethod(post);
			byte[] responseBody = post.getResponseBody();
			String s = new String(responseBody, "UTF-8");
			JSONObject obj = JSONObject.parseObject(s);
			String error_no = new String(obj.getBytes("error_no"));
			String error_info = new String(obj.getBytes("error_info"));
			if (error_no.equals("0")) { // 成功
				JOptionPane.showMessageDialog(null, error_info, "提醒", JOptionPane.DEFAULT_OPTION);
				byte[] params = obj.getBytes("params");
				// 保存公开参数到文件中
				CommonFileManager.writeObjectToFile(params, UserInfo.getInstance().paramsPath + CommonDef.paramsAffix);
			} else {
				res = false;
				JOptionPane.showMessageDialog(null, error_info, "错误", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = false;
			JOptionPane.showMessageDialog(null, e.getMessage(), "KGC错误", JOptionPane.ERROR_MESSAGE);
		} finally {
			post.releaseConnection();
		}
		return res;
	}

	public static boolean getPartKey(String name) {
		boolean res = true;
		HttpClient httpClient = HttpClientUtil.createDefaultKGCHttpClient();
		PostMethod post = new PostMethod("/KGCServer/getPartKey.htm");
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		NameValuePair namePair = new NameValuePair("id", name);
		paramList.add(namePair);
		post.setRequestBody(paramList.toArray(new NameValuePair[paramList.size()]));
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
//		// 设置请求超时为 30 秒
//		post.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 30000);
//		// 使用系统提供的默认的恢复策略
//		post.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
			httpClient.executeMethod(post);
			byte[] responseBody = post.getResponseBody();
			String s = new String(responseBody, "UTF-8");
			JSONObject obj = JSONObject.parseObject(s);
			String error_no = new String(obj.getBytes("error_no"));
			String error_info = new String(obj.getBytes("error_info"));
			if (error_no.equals("0")) { // 成功
				JOptionPane.showMessageDialog(null, error_info, "提醒", JOptionPane.DEFAULT_OPTION);
				byte[] partKey = obj.getBytes("partKey");
				// 保存部分私钥到文件中
				CommonFileManager.saveBytesToFilepath(partKey, UserInfo.getInstance().paramsPath + CommonDef.partKeyAffix(name));
			} else {
				res = false;
				JOptionPane.showMessageDialog(null, error_info, "错误", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			res = false;
			JOptionPane.showMessageDialog(null, e.getMessage(), "KGC错误", JOptionPane.ERROR_MESSAGE);
		} finally {
			post.releaseConnection();
		}
		return res;
	}
}
