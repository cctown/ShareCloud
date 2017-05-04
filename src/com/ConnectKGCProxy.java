package com;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ConnectKGCProxy implements Serializable {
	public Boolean operate;      //false表示请求获取公开参数，true表示请求获取部分私钥
	public String id;			 //请求者用户ID
	
	public ConnectKGCProxy(Boolean operate, String id) { 
		this.operate = operate; 
		this.id = id; 
	}
}
