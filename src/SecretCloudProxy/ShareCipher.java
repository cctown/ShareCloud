package SecretCloudProxy;

import java.io.Serializable;

import it.unisa.dia.gas.jpbc.Element;

public class ShareCipher implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7056830660208180303L;
	public byte[] grt; 
	public byte[] cipher;
	
	public ShareCipher(Element grt, byte[] cipher) { 
		this.grt = grt.toBytes();
		this.cipher = cipher;
	}
}
