package com;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.util.EncodingUtil;

public class CustomFilePart extends FilePart {     
    public CustomFilePart(String filename, File file)     
            throws FileNotFoundException {     
        super(filename, file);
        setCharSet("utf-8");
    }     
    
    protected void sendDispositionHeader(OutputStream out) throws IOException {     
        super.sendDispositionHeader(out);     
        String filename = getSource().getFileName();     
        if (filename != null) {     
            out.write(EncodingUtil.getAsciiBytes(FILE_NAME));     
            out.write(QUOTE_BYTES);     
            out.write(EncodingUtil.getBytes(filename, "utf-8"));     
            out.write(QUOTE_BYTES);     
        }     
    }     
}
