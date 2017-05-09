package encryption;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CommonFileManager {
	
	public static void writeObjectToFile(Object obj, String path) throws Exception {
		File file =new File(path);
		File superFile = file;
		if(!file.isDirectory()) {   //file是一个文件，则获取它的上级目录
			String superPath = path.substring(0, path.lastIndexOf("/"));
			superFile = new File(superPath);
		}
        if(!superFile.exists()){     //判断文件夹是否存在，不存在就新建文件夹
        	superFile.mkdirs();  
        }
		FileOutputStream out;
		out = new FileOutputStream(file);
    	ObjectOutputStream objOut=new ObjectOutputStream(out);
    	objOut.writeObject(obj);
    	objOut.flush();
    	objOut.close();
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
        try {
        	File file =new File(filePath);
    		File superFile = file;
    		if(!file.isDirectory()) {    //file是一个文件，则获取它的上级目录
    			String superPath = filePath.substring(0, filePath.lastIndexOf("/"));
    			superFile = new File(superPath);
    		}
            if(!superFile.exists()){     //判断文件夹是否存在，不存在就新建文件夹
            	superFile.mkdirs();  
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
	
	public static byte[] objectToByteArray (Object obj) {      
        byte[] bytes = null;      
        ByteArrayOutputStream bos = new ByteArrayOutputStream();      
        try {        
            ObjectOutputStream oos = new ObjectOutputStream(bos);         
            oos.writeObject(obj);        
            oos.flush();         
            bytes = bos.toByteArray ();      
            oos.close();         
            bos.close();        
        } catch (IOException ex) {        
            ex.printStackTrace();   
        }      
        return bytes;    
    }
	
	public static Object bytesToObject (byte[] bytes) {      
        Object obj = null;      
        try {        
            ByteArrayInputStream bis = new ByteArrayInputStream (bytes);        
            ObjectInputStream ois = new ObjectInputStream (bis);        
            obj = ois.readObject();      
            ois.close();   
            bis.close();   
        } catch (IOException ex) {        
            ex.printStackTrace();   
        } catch (ClassNotFoundException ex) {        
            ex.printStackTrace();   
        }      
        return obj;    
    }
}
