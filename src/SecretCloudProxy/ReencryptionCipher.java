package SecretCloudProxy;

import java.io.Serializable;

public class ReencryptionCipher implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8807624288160973821L;
	public byte[] reCipher;      
	public Ciphertext CBx;
	public Ciphertext CBgrt2;
	
	public ReencryptionCipher(byte[] reCipher, Ciphertext CBx, Ciphertext CBgrt2) { 
		this.reCipher = reCipher; 
		this.CBx = CBx; 
		this.CBgrt2 = CBgrt2;
	}
}
