package encryption;

import SecretCloudProxy.Ciphertext;
import SecretCloudProxy.CommonDef;
import SecretCloudProxy.PublicKey;
import SecretCloudProxy.ReencryptionKey;
import UserDefault.UserInfo;
import it.unisa.dia.gas.jpbc.Element;

public class KeyGen {
	//生成公私钥对
	public static void skpkGen(encryptionModule module, String ID, Element dA) throws Exception {
		//随机选择一个整数xA ∈ Z∗p
		Element xA = module.newGTRandomElement().getImmutable();
		//私钥 skA = gA^s^xA
		Element sk = dA.duplicate().powZn(xA).getImmutable();
		CommonFileManager.writeObjectToFile(sk.toBytes(), UserInfo.getInstance().keyPath + CommonDef.secretKeyAffix(ID));
		 
		//公钥 pkA = (gA, g^s^xA)), 其中gA= H1(IDA)
		Element gA = module.H1(ID.getBytes()).getImmutable();
		Element gsxA = module.getgps().duplicate().powZn(xA).getImmutable();
		PublicKey pk = new PublicKey(gA, gsxA);
		CommonFileManager.writeObjectToFile(pk, UserInfo.getInstance().keyPath + CommonDef.publicKeyAffix(ID));
	}
	
	//生成重加密密钥
	//rkA→B = (gA^(-s·xA) * H2(x)^t，CB(x), CB(grt2)) = skA^(-1) * H2(x)^t, CB(x), CB(grt2))
	public static ReencryptionKey rkGen(encryptionModule module, Element skA, PublicKey pkB, Element grt, Element t) {
		//随机的选择x ∈ GT
		Element x = module.newGTRandomElement().getImmutable();
		Element skA1 = skA.duplicate().invert().getImmutable();
		Element H2x = module.H2(x).getImmutable();
		Element H2xt = H2x.duplicate().powZn(t).getImmutable();
		Element skH2 = skA1.duplicate().mul(H2xt).getImmutable();
		 
		Ciphertext CBx = encryptTask.encryptMsg(module, x.toBytes(), pkB, t);
		Element grt2 = grt.duplicate().powZn(t).getImmutable();
		Ciphertext CBgrt2 = encryptTask.encryptMsg(module, grt2.toBytes(), pkB, t);
		ReencryptionKey key= new ReencryptionKey(skH2, CBx, CBgrt2);
		
		return key;
	}
}
