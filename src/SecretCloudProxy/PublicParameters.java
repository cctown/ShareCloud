package SecretCloudProxy;

import java.io.Serializable;

import it.unisa.dia.gas.jpbc.Element;

public class PublicParameters implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4385508373128206063L;
	public byte[] g;   // g
	public byte[] gps; // g^s
	
	public PublicParameters(Element g, Element gps) { 
		this.g = g.duplicate().toBytes(); 
		this.gps = gps.duplicate().toBytes();
	}
}

