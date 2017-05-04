package com;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import SecretCloudProxy.PublicParameters;

public class Client {
	Socket clientSocket;
	
	public void waitForServer() throws UnknownHostException, IOException, ClassNotFoundException {
		while(true) {
			clientSocket = new Socket("10.14.108.140",6789);
			InputStream inputStream = clientSocket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(inputStream);  
			PublicParameters param = (PublicParameters)ois.readObject();
			System.out.println("FROM SERVER: "+ param);
			clientSocket.close();
			if(param != null) {
				break;
			}
		}
	}
	
	public void GetPartKey() throws IOException {
		clientSocket = new Socket("10.14.108.140",6789);
		OutputStream outputStream = clientSocket.getOutputStream();
		ObjectOutputStream objectOutputStream=new ObjectOutputStream(outputStream);
		ConnectKGCProxy parameters = new ConnectKGCProxy(false, "小花");
		objectOutputStream.writeObject(parameters);
		clientSocket.close();
	}
}
