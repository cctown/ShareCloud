package SecretCloudProxy;

import java.io.Serializable;

import it.unisa.dia.gas.jpbc.Element;

public class PublicKey implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1807587019677180570L;
	//公钥 pkA = (gA, g^(s*xA)), 其中gA= H1(IDA)
	public byte[] gA;      
	public byte[] gsxA;
	
	public PublicKey(Element gA, Element gsxA) { 
		this.gA = gA.toBytes(); 
		this.gsxA = gsxA.toBytes(); 
	}
}
