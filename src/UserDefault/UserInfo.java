package UserDefault;

public class UserInfo {
	private String userName = "";
	private String password = "";
	private String defaultDESkeyName = "默认DES密钥.dat";
	private String defaultPath = "/Users/chencaixia/SecretCloud/Client/";
	
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
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUserName() {
		return this.userName;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getDefaultDESkeyName() {
		return this.defaultDESkeyName;
	}
	
	public String getDESkeyPath() {
		return defaultPath + this.userName + "/DESkey/";
	}
	
	public String getParamsPath() {
		return defaultPath + this.userName + "/params/";
	}
	
	public String getSecretKeyPath() {
		return defaultPath + this.userName + "/sk/";
	}
	
	public String getDownloadPath() {
		return defaultPath + this.userName + "/download/";
	}
	
	public String getEncryptPath() {
		return defaultPath + this.userName + "/encrypt/";
	}
	
	public String getDecryptPath() {
		return defaultPath + this.userName + "/decrypt/";
	}
}
