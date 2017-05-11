package SecretCloudProxy;

public class CommonDef {
	
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
	
	public static final String pkPath = "/Users/chencaixia/SecretCloud/PublicPath/pk/";
	public static final String paramsPath = "/Users/chencaixia/SecretCloud/PublicPath/params.dat";
	public static final String propertiesPath = "/Users/chencaixia/SecretCloud/PublicPath/a.properties";
}
