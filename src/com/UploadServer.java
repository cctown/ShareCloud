package com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import Event.EventDef;
import Event.observeEvent;
import SecretCloudProxy.Ciphertext;
import SecretCloudProxy.ShareCipher;
import encryption.CommonFileManager;

public class UploadServer {

	public static boolean uploadFile(String id, String fileName, byte[] cipher, ShareCipher DEScipher, Ciphertext condition) {
		HttpClient httpClient = HttpClientUtil.createDefaultHttpClient();
		PostMethod post = new PostMethod("/ProxyServer/uploadFile.htm");
		Map<String, byte[]> map = new HashMap<String, byte[]>();
		map.put("cipher", CommonFileManager.objectToByteArray(cipher));
		map.put("DEScipher", CommonFileManager.objectToByteArray(DEScipher));
		map.put("condition", CommonFileManager.objectToByteArray(condition));
		String file = JSON.toJSONString(map);
		
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		NameValuePair idPair = new NameValuePair("id", id);
		NameValuePair namePair = new NameValuePair("fileName", fileName);
		NameValuePair filePair = new NameValuePair("file", file);
		paramList.add(idPair);
		paramList.add(namePair);
		paramList.add(filePair);
		post.setRequestBody(paramList.toArray(new NameValuePair[paramList.size()]));
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
			httpClient.executeMethod(post);
			byte[] responseBody = post.getResponseBody();
			String s = new String(responseBody, "UTF-8");
			JSONObject obj = JSONObject.parseObject(s);
			String error_no = obj.getString("error_no");
			if (error_no.equals("0")) {     // 上传成功
				 
			} else if(error_no.equals("-1")){   // 上传失败
				return false;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "服务器错误", JOptionPane.ERROR_MESSAGE);
			return false;
		} finally {
			post.releaseConnection();
		}
		return true;
	}
}
