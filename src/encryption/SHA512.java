package encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA512 {
	
	public static byte[] SHAByte(final byte[] b) {  
		return SHAByte(b, "SHA-512");
	}
	
	public static byte[] SHAStringToString(final String strText) {  
		return SHAToByte(strText, "SHA-512");
	}
	
	public static String SHAStringToByte(final String strText) {  
		return SHAToString(strText, "SHA-512");
	}
	
	private static byte[] SHAByte(final byte[] b, final String strType) {
		//返回值
		byte byteBuffer[] = null;
	    // 是否是有效字符串
	    if (b != null) {
	    	try {
	    		// SHA 加密开始  
		        // 创建加密对象 并傳入加密類型  
		        MessageDigest messageDigest = MessageDigest.getInstance(strType);  
		        // 传入要加密的字符串  
		        messageDigest.update(b);
		        // 得到 byte 類型结果  
		        byteBuffer = messageDigest.digest();
	    	}
	    	catch (NoSuchAlgorithmException e) {
	    		e.printStackTrace();
	    	}
	    }
	    return byteBuffer;
	}
	
	private static byte[] SHAToByte(final String strText, final String strType) {
		//返回值
		byte byteBuffer[] = null;
		// 是否是有效字符串
		if (strText != null && strText.length() > 0) {
			try {
				// SHA 加密开始
				// 创建加密对象 并傳入加密類型
				MessageDigest messageDigest = MessageDigest.getInstance(strType);
				// 传入要加密的字符串 
				messageDigest.update(strText.getBytes());
				// 得到 byte 類型结果
				byteBuffer = messageDigest.digest();
			} 
			catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
		return byteBuffer;
	}
	
	private static String SHAToString(final String strText, final String strType) {
		// 返回值
		String strResult = null;
		// 是否是有效字符串
		if (strText != null && strText.length() > 0) {
			// 得到 byte 類型结果
			byte byteBuffer[] = SHAToByte(strText, "SHA-512");
			// 將 byte 轉換爲 string
			StringBuffer strHexString = new StringBuffer();
			// 遍歷 byte buffer
			for (int i = 0; i < byteBuffer.length; i++) {
				String hex = Integer.toHexString(0xff & byteBuffer[i]);
				if (hex.length() == 1) {
					strHexString.append('0');
				}
				strHexString.append(hex);
			}
			// 得到返回結果
			strResult = strHexString.toString();
		}
		return strResult;
	}
}
