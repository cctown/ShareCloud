package com;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpMethodParams;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import SecretCloudProxy.Ciphertext;
import SecretCloudProxy.CommonFileManager;
import SecretCloudProxy.ShareCipher;
import UI.GlobalDef;

public class FileServer {

	public static boolean uploadFile(String id, String fileName, byte[] cipher, ShareCipher DEScipher,
			Ciphertext condition) {
		File cipherFile;
		File DEScipherFile;
		File conditionFile;
		try {
			cipherFile = CommonFileManager.saveBytesToFilepath(cipher, GlobalDef.tempPath + "cipher.dat");
			DEScipherFile = CommonFileManager.writeObjectToFile(DEScipher, GlobalDef.tempPath + "DEScipher.dat");
			conditionFile = CommonFileManager.writeObjectToFile(condition, GlobalDef.tempPath + "condition.dat");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return false;
		}

		HttpClient httpClient = HttpClientUtil.createDefaultHttpClient();
		PostMethod post = new PostMethod("/ProxyServer/uploadFile.htm");
		try {
			post.addRequestHeader("id", URLEncoder.encode(id, "UTF-8"));
			post.addRequestHeader("fileName", URLEncoder.encode(fileName, "UTF-8"));
			FilePart cipherFilePart = new CustomFilePart("cipher", cipherFile);
			FilePart DEScipherFilePart = new CustomFilePart("DEScipher", DEScipherFile);
			FilePart conditionFilePart = new CustomFilePart("condition", conditionFile);
			Part[] parts = { cipherFilePart, DEScipherFilePart, conditionFilePart };
			post.setRequestEntity(new MultipartRequestEntity(parts, post.getParams()));
			post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
			httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
			httpClient.getParams().setContentCharset("UTF-8");
			int status = httpClient.executeMethod(post);
			System.out.println(status);
			byte[] responseBody = post.getResponseBody();
			
			String s = new String(responseBody, "UTF-8");
			JSONObject obj = JSONObject.parseObject(s);

			String error_no = obj.getString("error_no");
			if (error_no.equals("0")) { // 上传成功
				
			} else { // 失败
				return false;
			}
		} catch (Exception e) {
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

	public static String uploadReencryptionKey(String id, String fileName, String[] receivers,
			Map<String, byte[]> rkMap) {
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
	public static String downloadReencryptionCipher(String author, String receiver, String fileName) {
		FileOutputStream output = null;
		File reCipherFile;
		HttpClient httpClient = HttpClientUtil.createDefaultHttpClient();
		PostMethod post = new PostMethod("/ProxyServer/downloadReencryptionCipher.htm");
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
			int status = httpClient.executeMethod(post);
			reCipherFile = new File(GlobalDef.tempPath + "ReencryptionCipher.dat");
			output = new FileOutputStream(reCipherFile);
			InputStream in = post.getResponseBodyAsStream();
			byte[] buffer = new byte[4096];
			int readLength = 0;
			while ((readLength = in.read(buffer)) > 0) {
				byte[] bytes = new byte[readLength];
				System.arraycopy(buffer, 0, bytes, 0, readLength);
				output.write(bytes);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "服务器错误", JOptionPane.ERROR_MESSAGE);
			return null;
		} finally {
			try {
				if (output != null) {
					output.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			post.releaseConnection();
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		}
		return reCipherFile.getPath();
	}
	
	public static String downloadCipher(String author, String fileName) {
		FileOutputStream output = null;
		File cipherFile;
		HttpClient httpClient = HttpClientUtil.createDefaultHttpClient();
		PostMethod post = new PostMethod("/ProxyServer/downloadCipher.htm");
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		NameValuePair authorPair = new NameValuePair("author", author);
		NameValuePair filePair = new NameValuePair("fileName", fileName);
		paramList.add(authorPair);
		paramList.add(filePair);
		post.setRequestBody(paramList.toArray(new NameValuePair[paramList.size()]));
		post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "utf-8");
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		try {
			httpClient.executeMethod(post);
			cipherFile = new File(GlobalDef.tempPath + "cipher.dat");
			output = new FileOutputStream(cipherFile);
			InputStream in = post.getResponseBodyAsStream();
			byte[] buffer = new byte[4096];
			int readLength = 0;
			while ((readLength = in.read(buffer)) > 0) {
				byte[] bytes = new byte[readLength];
				System.arraycopy(buffer, 0, bytes, 0, readLength);
				output.write(bytes);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage(), "服务器错误", JOptionPane.ERROR_MESSAGE);
			return null;
		} finally {
			try {
				if (output != null) {
					output.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			post.releaseConnection();
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		}
		return cipherFile.getPath();
	}

	public static void testDownLoad(String remoteFileName, String localFileName) {
		HttpClient httpClient = HttpClientUtil.createDefaultHttpClient();
		httpClient.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, "UTF-8");
		PostMethod get = new PostMethod("/ProxyServer/testDownloadFile.htm");
		FileOutputStream output = null;
		List<NameValuePair> paramList = new ArrayList<NameValuePair>();
		NameValuePair authorPair = new NameValuePair("userName", "测试人员");
		NameValuePair receiverPair = new NameValuePair("fileName", remoteFileName);
		paramList.add(authorPair);
		paramList.add(receiverPair);
		get.setRequestBody(paramList.toArray(new NameValuePair[paramList.size()]));
		try {
			get.setRequestHeader("userName", "测试人员");
			get.setRequestHeader("fileName", remoteFileName);

			int i = httpClient.executeMethod(get);

			if (HttpStatus.SC_OK == i) {
				System.out.println("The response value of token:" + get.getResponseHeader("token"));
				
				File storeFile = new File(localFileName);
				output = new FileOutputStream(storeFile);
				InputStream in = get.getResponseBodyAsStream();
				byte[] buffer = new byte[4096];
				int readLength = 0;
				while ((readLength = in.read(buffer)) > 0) {
					byte[] bytes = new byte[readLength];
					System.arraycopy(buffer, 0, bytes, 0, readLength);
					output.write(bytes);
				}
				// 得到网络资源的字节数组,并写入文件
			} else {
				System.out.println("DownLoad file occurs exception, the error code is :" + i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (output != null) {
					output.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			get.releaseConnection();
			httpClient.getHttpConnectionManager().closeIdleConnections(0);
		}
	}
}
