package SecretCloudProxy;

import java.io.Serializable;

import it.unisa.dia.gas.jpbc.Element;

public class ReencryptionKey implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2816590891189076353L;
	public byte[] gH;      
	public Ciphertext CBx;
	public Ciphertext CBgrt2;
	
	public ReencryptionKey(Element gH, Ciphertext CBx, Ciphertext CBgrt2) { 
		this.gH = gH.toBytes(); 
		this.CBx = CBx; 
		this.CBgrt2 = CBgrt2;
	}
}
