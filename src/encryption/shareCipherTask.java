package encryption;

import SecretCloudProxy.Ciphertext;
import SecretCloudProxy.PublicKey;
import SecretCloudProxy.ReencryptionCipher;
import SecretCloudProxy.ShareCipher;
import it.unisa.dia.gas.jpbc.Element;

public class shareCipherTask {
	//生成分享密文
	//c' = C'A(m) = (g^rt，m * e(gA^r，g^s^xAt)) = (g^rt，m * e(pkA.gA^r，pkA.gsxA^t))
	public static ShareCipher encryptShareMsg(encryptionModule module, byte[] msg, PublicKey pk, Element t) {
		Ciphertext cipher = encryptTask.encryptMsg(module, msg, pk, t);
		Element grt = module.newG1ElementFromBytes(cipher.grt).getImmutable();
		ShareCipher shareCipher= new ShareCipher(grt, cipher.me);
		return shareCipher;
	}
	
	//代理重加密密文解密
	//c'' / e(H2(x)，g^rt2)
	public static byte[] decryptShareMsg(encryptionModule module, ReencryptionCipher cipher, Element skB) {
		//解密 CB(x，grt2)得到 x 和 grt2
		Element x = module.newG1ElementFromBytes(decryptTask.decryptMsg(module, cipher.CBx, skB)).getImmutable();
		Element grt2 = module.newG1ElementFromBytes(decryptTask.decryptMsg(module, cipher.CBgrt2, skB)).getImmutable();
		Element H2x = module.H2(x).getImmutable();
		Element eHg = module.e(H2x, grt2).getImmutable();
		Element c = module.newGTElementFromBytes(cipher.reCipher);
		Element m = c.duplicate().div(eHg).duplicate().getImmutable();
		return m.toBytes();
	}
}
