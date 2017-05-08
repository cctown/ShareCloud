package UserDefault;

public class UserInfo {
	public String userName = "";
	public String defaultDESkeyName = "默认DES密钥.dat";
	public String DESkeyPath = "/Users/chencaixia/SecretCloud/Client/key/";
	public String keyPath = "/Users/chencaixia/SecretCloud/Client/key/";
	public String paramsPath = "/Users/chencaixia/SecretCloud/Client/params/";
	public String downloadPath = "/Users/chencaixia/SecretCloud/Client/download/";
	public String encryptPath = "/Users/chencaixia/SecretCloud/Client/encrypt/";
	public String decryptPath = "/Users/chencaixia/SecretCloud/decrypt/Client/";
	
	private UserInfo() {  
    }
	// 将自身的实例对象设置为一个属性,并加上Static和final修饰符
	private static final UserInfo instance = new UserInfo();
	// 静态方法返回该类的实例
	public static UserInfo getInstance() {  
        return instance;  
    }
	
	public void setUserName(String name) {
		this.userName = name;
	}
}
