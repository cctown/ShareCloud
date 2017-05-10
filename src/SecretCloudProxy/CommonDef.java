package SecretCloudProxy;

public class CommonDef {
	public static final String paramsAffix = "params.dat";
	
	public static String partKeyAffix(String ID) {
		return ID + "_partKey.dat";
	}
	
	public static String secretKeyAffix(String ID) {
		return ID + "_secretKey.dat";
	}
	
	public static String publicKeyAffix(String ID) {
		return ID + "_publicKey.dat";
	}
	
	public static String reencryptionKeyAffix(String ownerID, String receiverID, String fileName) {
		fileName = fileName.replace(".", "");
		return ownerID + "-" +receiverID + "-" + fileName + "_rk.dat";
	}
	
	public final String pkPath = "/Users/chencaixia/SecretCloud/Client/key/";
}
