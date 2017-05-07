package encryption;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
	
	public static Object readObjectFromFile(String path) throws Exception {
		Object temp=null;
	    File file =new File(path);
	    FileInputStream in;
	    in = new FileInputStream(file);
    	ObjectInputStream objIn = new ObjectInputStream(in);
	    temp = objIn.readObject();
	    objIn.close();
	    return temp;
	}
	
	public static byte[] getBytesFromFilepath(String filePath) throws Exception {
		byte[] buffer = null;
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
		byte[] b = new byte[1024];
        int n = fis.read(b);
        while (n != -1) {
        	bos.write(b, 0, n);
        	n = fis.read(b);
        }
        fis.close();
        bos.close();
        buffer = bos.toByteArray();
        System.out.println("从以下路径获取了文件：" + filePath);
		return buffer;
	}
	
	public static void saveBytesToFilepath(byte[] bfile, String filePath) throws Exception {  
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;  
        File file = null;
        try {
            File dir = new File(filePath);  
            if(!dir.exists() && dir.isDirectory()){//判断文件目录是否存在  
                dir.mkdirs();  
            }
            file = new File(filePath);  
            fos = new FileOutputStream(file);  
            bos = new BufferedOutputStream(fos);  
            bos.write(bfile); 
            System.out.println("保存了文件到以下路径：" + filePath);
        }
        finally {  
            if (bos != null) {  
            	bos.close();
            }  
            if (fos != null) {  
            	fos.close();  
            }  
        }  
    }
}
