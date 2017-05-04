package encryption;

import SecretCloudProxy.CommonDef;
import SecretCloudProxy.PublicParameters;
import it.unisa.dia.gas.jpbc.Element;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;

public class encryptionModule {
	private static Pairing pairing;
	private static PublicParameters params;
	private static String paramsPath = "/Users/chencaixia/SecretCloud/params/";
	private static Element g;
	private static Element gps;
	
	public encryptionModule() {
		pairing = PairingFactory.getPairing(paramsPath + "a.properties");
		params = (PublicParameters)CommonFileManager.readObjectFromFile(paramsPath + CommonDef.paramsAffix);
		g = newG1ElementFromBytes(params.g).getImmutable();
		gps = newG1ElementFromBytes(params.gps).getImmutable();
	}
	
	//	将byte[] b哈希到G1群
	public Element H1(byte[] b) {
		return pairing.getG1().newRandomElement().setFromHash(b, 0, b.length);
	}
	//	将GT元素哈希到G1群
	public Element H2(Element e) {
		byte[] b = new SHA512().SHAByte(e.toBytes());
		return pairing.getG1().newRandomElement().setFromHash(b, 0, b.length);
	}
	
	//	计算e(a,b)
	public Element e(Element a, Element b) {
		return pairing.pairing(a, b);
	}
	
	public Element newGTElementFromBytes(byte[] b) {
		return pairing.getGT().newElementFromBytes(b);
	}
	
	public Element newG1ElementFromBytes(byte[] b) {
		return pairing.getG1().newElementFromBytes(b);
	}
	
	public Element newG1RandomElement() {
		return pairing.getG1().newRandomElement();
	}
	
	public Element newGTRandomElement() {
		return pairing.getGT().newRandomElement();
	}
	
	public Element getg() {
		return g;
	}
	
	public Element getgps() {
		return gps;
	}
}
