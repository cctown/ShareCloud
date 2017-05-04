package encryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CommonFileManager {
	
	public static void writeObjectToFile(Object obj, String path) {
		File file =new File(path);
		FileOutputStream out;
	    try {
	    	out = new FileOutputStream(file);
	    	ObjectOutputStream objOut=new ObjectOutputStream(out);
	    	objOut.writeObject(obj);
	    	objOut.flush();
	    	objOut.close();
	    	System.out.println("成功将对象写入路径：" + path);
	    }
	    catch(IOException e) {
	    	System.out.println("写入失败");
	    	e.printStackTrace();
	    }
	}
	
	public static Object readObjectFromFile(String path) {
		Object temp=null;
	    File file =new File(path);
	    FileInputStream in;
	    try {
	    	in = new FileInputStream(file);
	    	ObjectInputStream objIn = new ObjectInputStream(in);
		    temp = objIn.readObject();
		    objIn.close();
		    System.out.println("成功读取该路径的对象：" + path);
	    }
	    catch (IOException e) {
	    	System.out.println("读取失败");
		      e.printStackTrace();
	    }
	    catch (ClassNotFoundException e) {
	    	e.printStackTrace();
	    }
	    return temp;
	}
}
