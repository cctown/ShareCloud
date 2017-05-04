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
	
	public static byte[] getBytesFromFilepath(String filePath) {
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			if (file.exists()) {
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
		    }
		    else {
		    	System.out.println(filePath + "该文件不存在");
		   }
		}
		catch (FileNotFoundException e) {  
			e.printStackTrace();  
		}
		catch (IOException e) {  
			e.printStackTrace();  
		}
		return buffer;
	}
	
	public static void saveBytesToFilepath(byte[] bfile, String filePath) {  
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
        catch (Exception e) {  
            e.printStackTrace();  
        }
        finally {  
            if (bos != null) {  
                try {  
                    bos.close();  
                }
                catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
            if (fos != null) {  
                try {  
                    fos.close();  
                }
                catch (IOException e1) {  
                    e1.printStackTrace();  
                }  
            }  
        }  
    }
}
