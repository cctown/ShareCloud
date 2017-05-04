package SecretCloudProxy;

import java.io.Serializable;

import it.unisa.dia.gas.jpbc.Element;

public class Ciphertext implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -92333192416040447L;
	public byte[] grt;      
	public byte[] me;
	
	public Ciphertext(Element grt, byte[] me) { 
		this.grt = grt.toBytes(); 
		this.me = me; 
	}
}
