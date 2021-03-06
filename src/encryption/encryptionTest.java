//package encryption;
//
//import SecretCloudProxy.CommonDef;
//import SecretCloudProxy.CommonFileManager;
//import SecretCloudProxy.PublicKey;
//import SecretCloudProxy.ReencryptionCipher;
//import SecretCloudProxy.ReencryptionKey;
//import SecretCloudProxy.ShareCipher;
//import UserDefault.UserInfo;
//import it.unisa.dia.gas.jpbc.Element;
//
//public class encryptionTest {
//	private Element dA;
//	private Element dB;
//	private Element t;
//	private String IDA = "lala";
//	private String IDB = "小树";
//	private String testS = "测试数据:安全已经被认为是最重要的";
//	
//	private static String paramsPath = "/Users/chencaixia/SecretCloud/Client/params/";
//	private Element skA;
//	private Element skB;
//	private PublicKey pkA;
//	private PublicKey pkB;
//	private static encryptionModule module;
//	
//	@SuppressWarnings("unused")
//	public static void main (String args[]) throws Exception {
//		encryptionModule module = new encryptionModule();
//		encryptionTest gen = new encryptionTest(module);
//	}
//	
//	public encryptionTest(encryptionModule mod) throws Exception {
//		module = mod;
////		getPartKey();
////		KeyGen.skpkGen(module, IDA, dA);
////		KeyGen.skpkGen(module, IDB, dB);
////		getskpk();
//		
//		t = module.newGTRandomElement().getImmutable();
//		byte[] sm = testS.getBytes();
//		byte[] skAbyte = (byte[])CommonFileManager.readObjectFromFile("/Users/chencaixia/SecretCloud/Client/lala/sk/lala_secretKey.dat");
//		skA = module.newG1ElementFromBytes(skAbyte).getImmutable();
//		pkA = (PublicKey)CommonFileManager.readObjectFromFile(CommonDef.pkPath + CommonDef.publicKeyAffix(IDA));
////		System.out.println("原文为:" + testS);
////		System.out.println("加密之前的字节长度为：" + sm.length);
////		Ciphertext cipher = encryptTask.encryptMsg(module, testS.getBytes(), pkB, t);
////		System.out.println("加密之后的字节长度为：" + cipher.me.length);
////		byte[] m = decryptTask.decryptMsg(module, cipher, skB);
////		System.out.println("解密之后的字节长度为：" + m.length);
////		String msg = new String(m);
////		System.out.println("解密出原文为：" + msg);
//		
//		ShareCipher shareCipher = shareCipherTask.encryptShareMsg(module, sm, pkA, t);
//		byte[] m1 = decryptTask.decryptShareCipher(module, shareCipher, skA);
//		
//		Element grt = module.newG1ElementFromBytes(shareCipher.grt).getImmutable();
//		ReencryptionKey reKey = KeyGen.rkGen(module, skA, pkB, grt, t);
//		ReencryptionCipher reCipher = reencryptMsg(module, shareCipher, reKey);
//		byte[] m2 = shareCipherTask.decryptShareMsg(module, reCipher, skB);
//		String msg2 = new String(m2);
//		byte[] b = msg2.getBytes();
//		System.out.println("重加密解密出原文为：" + msg2);
//	}
//	
////	private void getPartKey() throws Exception {
////		byte[] partKeyA = (byte[])CommonFileManager.readObjectFromFile(paramsPath + CommonDef.partKeyAffix(IDA));
////		dA = module.newG1ElementFromBytes(partKeyA).getImmutable();
////		byte[] partKeyB = (byte[])CommonFileManager.readObjectFromFile(paramsPath + CommonDef.partKeyAffix(IDB));
////		dB = module.newG1ElementFromBytes(partKeyB).getImmutable();
////	}
////	
////	private void getskpk() throws Exception {
////		byte[] skAbyte = (byte[])CommonFileManager.readObjectFromFile();
////		byte[] skBbyte = (byte[])CommonFileManager.readObjectFromFile();
////		skA = module.newG1ElementFromBytes(skAbyte).getImmutable();
////		skB = module.newG1ElementFromBytes(skBbyte).getImmutable();
////		
////		pkA = (PublicKey)CommonFileManager.readObjectFromFile(CommonDef.pkPath + CommonDef.publicKeyAffix(IDA));
////		pkB = (PublicKey)CommonFileManager.readObjectFromFile(CommonDef.pkPath + CommonDef.publicKeyAffix(IDB));
////	}
//	
//	//代理重加密
//	//c'' = m * e(gA^r，g^s·xAt) * e(gA^-s·xA · H2^t (x)，g^rt) = shareCipher.cipher * e(rk.gH, shareCipher.grt)
//	public static ReencryptionCipher reencryptMsg(encryptionModule module, ShareCipher shareCipher, ReencryptionKey rk) {
//		Element m = module.newGTElementFromBytes(shareCipher.cipher).getImmutable();
//		Element gH = module.newG1ElementFromBytes(rk.gH).getImmutable();
//		Element grt = module.newG1ElementFromBytes(shareCipher.grt).getImmutable();
//		Element egg = module.e(gH, grt).getImmutable();
//		Element me = m.duplicate().mul(egg).duplicate().getImmutable();
//		ReencryptionCipher cipher = new ReencryptionCipher(me.toBytes(), rk.CBx, rk.CBgrt2);
//		return cipher;
//	}
//}
