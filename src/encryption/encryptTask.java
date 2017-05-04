package encryption;

import SecretCloudProxy.Ciphertext;
import SecretCloudProxy.PublicKey;
import it.unisa.dia.gas.jpbc.Element;

public class encryptTask {
	//生成密文(grt, m * e(gA^r, g^s^xAt))
	public static Ciphertext encryptMsg(encryptionModule module, byte[] msg, PublicKey pk, Element t) {
		//随机地选择整数r
		Element r = module.newGTRandomElement().getImmutable();
		Element gr = module.getg().duplicate().powZn(r).getImmutable();
		Element grt = gr.duplicate().powZn(t).getImmutable();
		Element gA = module.newG1ElementFromBytes(pk.gA).getImmutable();
		Element gAr = gA.duplicate().powZn(r).getImmutable();
		Element gsxA = module.newG1ElementFromBytes(pk.gsxA).getImmutable();
		Element gsxAt = gsxA.duplicate().powZn(t).getImmutable();
		Element eg = module.e(gAr, gsxAt).getImmutable();
		Element m = module.newGTElementFromBytes(msg).getImmutable();
		Element me = m.duplicate().mul(eg.duplicate()).getImmutable();
		Ciphertext cipher = new Ciphertext(grt, me.toBytes());
		return cipher;
	}
}
