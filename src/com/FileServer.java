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

public class FileServer {

	public static boolean uploadFile(String id, String fileName, byte[] cipher, ShareCipher DEScipher,
			Ciphertext condition) {
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
			if (error_no.equals("0")) { // 上传成功

			} else if (error_no.equals("-1")) { // 上传失败
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

	// 获取用户云盘文件列表
	public static List<Map<String, String>> getFileInfoForUser(String id) {
		List<Map<String, String>> fileList = new ArrayList<Map<String, String>>();

		HttpClient httpClient = HttpClientUtil.createDefaultHttpClient();
		PostMethod post = new PostMethod("/ProxyServer/allFilesForUser.htm");

		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		NameValuePair idPair = new NameValuePair("id", id);
		paramList.add(idPair);
		post.setRequestBody(paramList.toArray(new NameValuePair[paramList.size()]));
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
			httpClient.executeMethod(post);
			byte[] responseBody = post.getResponseBody();
			String s = new String(responseBody, "UTF-8");
			JSONObject obj = JSONObject.parseObject(s);

			String error_no = new String(obj.getBytes("error_no"));
			if (error_no.equals("0")) { // 成功
				fileList = (List<Map<String, String>>) CommonFileManager.bytesToObject((obj.getBytes("fileList")));
			} else if (error_no.equals("-1")) { // 失败
				String error_info = new String(obj.getBytes("error_info"));
				JOptionPane.showMessageDialog(null, error_info, "错误", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "服务器错误", JOptionPane.ERROR_MESSAGE);
		} finally {
			post.releaseConnection();
		}
		return fileList;
	}

	// 获取已分享文件列表
	public static List<Map<String, String>> getSharedFileForUser(String id) {
		List<Map<String, String>> fileList = new ArrayList<Map<String, String>>();

		HttpClient httpClient = HttpClientUtil.createDefaultHttpClient();
		PostMethod post = new PostMethod("/ProxyServer/getSharedFile.htm");

		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		NameValuePair idPair = new NameValuePair("id", id);
		paramList.add(idPair);
		post.setRequestBody(paramList.toArray(new NameValuePair[paramList.size()]));
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
			httpClient.executeMethod(post);
			byte[] responseBody = post.getResponseBody();
			String s = new String(responseBody, "UTF-8");
			JSONObject obj = JSONObject.parseObject(s);

			String error_no = new String(obj.getBytes("error_no"));
			if (error_no.equals("0")) { // 成功
				fileList = (List<Map<String, String>>) CommonFileManager.bytesToObject((obj.getBytes("fileList")));
			} else if (error_no.equals("-1")) { // 失败
				String error_info = new String(obj.getBytes("error_info"));
				JOptionPane.showMessageDialog(null, error_info, "错误", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "服务器错误", JOptionPane.ERROR_MESSAGE);
		} finally {
			post.releaseConnection();
		}
		return fileList;
	}
	
	// 获取已收到的分享文件列表
	public static List<Map<String, String>> getReceiveFile(String id) {
		List<Map<String, String>> fileList = new ArrayList<Map<String, String>>();

		HttpClient httpClient = HttpClientUtil.createDefaultHttpClient();
		PostMethod post = new PostMethod("/ProxyServer/getReceiveFile.htm");

		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		NameValuePair idPair = new NameValuePair("id", id);
		paramList.add(idPair);
		post.setRequestBody(paramList.toArray(new NameValuePair[paramList.size()]));
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
			httpClient.executeMethod(post);
			byte[] responseBody = post.getResponseBody();
			String s = new String(responseBody, "UTF-8");
			JSONObject obj = JSONObject.parseObject(s);

			String error_no = new String(obj.getBytes("error_no"));
			if (error_no.equals("0")) { // 成功
				fileList = (List<Map<String, String>>) CommonFileManager.bytesToObject((obj.getBytes("fileList")));
			} else if (error_no.equals("-1")) { // 失败
				String error_info = new String(obj.getBytes("error_info"));
				JOptionPane.showMessageDialog(null, error_info, "错误", JOptionPane.ERROR_MESSAGE);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "服务器错误", JOptionPane.ERROR_MESSAGE);
		} finally {
			post.releaseConnection();
		}
		return fileList;
	}

	// 获取生成重加密密钥需要的grt，和文件条件值
	public static Map<String, byte[]> getParamsForReencryptionKey(String id, String fileName) {
		Map<String, byte[]> resMap = new HashMap<String, byte[]>();

		HttpClient httpClient = HttpClientUtil.createDefaultHttpClient();
		PostMethod post = new PostMethod("/ProxyServer/getParamsForReencryptionKey.htm");

		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		NameValuePair idPair = new NameValuePair("id", id);
		NameValuePair filePair = new NameValuePair("fileName", fileName);
		paramList.add(idPair);
		paramList.add(filePair);
		post.setRequestBody(paramList.toArray(new NameValuePair[paramList.size()]));
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
			httpClient.executeMethod(post);
			byte[] responseBody = post.getResponseBody();
			String s = new String(responseBody, "UTF-8");
			JSONObject obj = JSONObject.parseObject(s);

			String error_no = new String(obj.getBytes("error_no"));
			if (error_no.equals("0")) { // 成功
				byte[] grt = obj.getBytes("grt");
				byte[] condition = obj.getBytes("condition");
				resMap.put("grt", grt);
				resMap.put("condition", condition);
			} else if (error_no.equals("-1")) { // 失败
				String error_info = new String(obj.getBytes("error_info"));
				JOptionPane.showMessageDialog(null, error_info, "错误", JOptionPane.ERROR_MESSAGE);
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "服务器错误", JOptionPane.ERROR_MESSAGE);
			return null;
		} finally {
			post.releaseConnection();
		}
		return resMap;
	}

	public static String uploadReencryptionKey(String id, String fileName, String[] receivers, Map<String, byte[]> rkMap) {
		String res = null;
		HttpClient httpClient = HttpClientUtil.createDefaultHttpClient();
		PostMethod post = new PostMethod("/ProxyServer/uploadReencryptionKey.htm");
		Map<String, byte[]> map = new HashMap<String, byte[]>();
		map.put("receivers", CommonFileManager.objectToByteArray(receivers));
		map.put("rkMap", CommonFileManager.objectToByteArray(rkMap));
		String data = JSON.toJSONString(map);

		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		NameValuePair idPair = new NameValuePair("id", id);
		NameValuePair namePair = new NameValuePair("fileName", fileName);
		NameValuePair dataPair = new NameValuePair("data", data);
		paramList.add(idPair);
		paramList.add(namePair);
		paramList.add(dataPair);
		post.setRequestBody(paramList.toArray(new NameValuePair[paramList.size()]));
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
			httpClient.executeMethod(post);
			byte[] responseBody = post.getResponseBody();
			String s = new String(responseBody, "UTF-8");
			JSONObject obj = JSONObject.parseObject(s);
			res = obj.getString("error_info");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "服务器错误", JOptionPane.ERROR_MESSAGE);
		} finally {
			post.releaseConnection();
		}
		return res;
	}
	
	// 下载被分享的密文
	public static Map<String, byte[]> downloadShareFile(String author, String receiver, String fileName) {
		Map<String, byte[]> resMap = new HashMap<String, byte[]>();

		HttpClient httpClient = HttpClientUtil.createDefaultHttpClient();
		PostMethod post = new PostMethod("/ProxyServer/downloadShareFile.htm");
		
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		NameValuePair authorPair = new NameValuePair("author", author);
		NameValuePair receiverPair = new NameValuePair("receiver", receiver);
		NameValuePair filePair = new NameValuePair("fileName", fileName);
		paramList.add(authorPair);
		paramList.add(receiverPair);
		paramList.add(filePair);
		post.setRequestBody(paramList.toArray(new NameValuePair[paramList.size()]));
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
			httpClient.executeMethod(post);
			byte[] responseBody = post.getResponseBody();
			String s = new String(responseBody, "UTF-8");
			JSONObject obj = JSONObject.parseObject(s);

			String error_no = new String(obj.getBytes("error_no"));
			if (error_no.equals("0")) { // 成功
				byte[] cipher = obj.getBytes("cipher");
				byte[] reencryptionCipher = obj.getBytes("reencryptionCipher");
				resMap.put("cipher", cipher);
				resMap.put("reencryptionCipher", reencryptionCipher);
			} else if (error_no.equals("-1")) { // 失败
				String error_info = new String(obj.getBytes("error_info"));
				JOptionPane.showMessageDialog(null, error_info, "错误", JOptionPane.ERROR_MESSAGE);
				return null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "服务器错误", JOptionPane.ERROR_MESSAGE);
			return null;
		} finally {
			post.releaseConnection();
		}
		return resMap;
	}
}
