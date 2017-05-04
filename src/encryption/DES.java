package encryption;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DES {
	private static final String PASSWORD_CRYPT_KEY = "kEHrDooxWHCWtfeSxvDvgqZq";
	private static final String path = "/Users/chencaixia/SecretCloud/download/msg.txt";
	private static final String encryptPath = "/Users/chencaixia/SecretCloud/encrypt/加密结果.txt";
	private static final String decryptPath = "/Users/chencaixia/SecretCloud/decrypt/解密结果.txt";
	
	public static void main (String args[]) throws Exception {
		//原始密钥串
		byte[] key = PASSWORD_CRYPT_KEY.getBytes();
		String msg = "测试数据";
		System.out.println("原文为：" + msg);
		byte[] a = DES.encrypt(msg.getBytes(), key);
		String str = new String(a);
		System.out.println("加密得到密文" + str);
		a = DES.decrypt(a, PASSWORD_CRYPT_KEY.getBytes());
		String re = new String(a);
		System.out.println("解密得到" + re);
		
		//获取指定路径的明文
		byte[] file = CommonFileManager.getBytesFromFilepath(path);
		//将明文加密得到密文比特
		byte[] encryptFile = DES.encrypt(file, key);
		//将密文比特转换成文件存起来
		CommonFileManager.saveBytesToFilepath(encryptFile, encryptPath);
		//获取指定路径的密文
		byte[] cipher = CommonFileManager.getBytesFromFilepath(encryptPath);
		//解密密文
		byte[] decryptFile = DES.decrypt(cipher, key);
		//将解密得到结果转换成文件存起来
		CommonFileManager.saveBytesToFilepath(decryptFile, decryptPath);
	} 
	
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		// 从原始密匙数据创建DESKeySpec对象
		DESKeySpec dks = new DESKeySpec(key);
		// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey securekey = keyFactory.generateSecret(dks);
		// Cipher对象实际完成加密操作
		Cipher cipher = Cipher.getInstance("DES");
		// 用密匙初始化Cipher对象
		cipher.init(Cipher.ENCRYPT_MODE, securekey, sr);
		return cipher.doFinal(data);
	}
	
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		SecureRandom sr = new SecureRandom();
		DESKeySpec dks = new DESKeySpec(key);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, securekey, sr);
		return cipher.doFinal(data);
	}
	
	
}
