package org.inkasoft.fileguard.fileguard.cli.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Utils {
    
    public static String getFileExtension (String fileName) {
        int lastIndexOf;
        lastIndexOf = fileName.lastIndexOf('.');

        if (lastIndexOf != -1) {
            return fileName.substring(lastIndexOf);
        }
        
        return "";
    }
    
    public static void copyStream (InputStream in, OutputStream out) {
        byte[] buffer = new byte[1024];
        
        int len;
        try {
            len = in.read(buffer);
            while (len != -1) {
                out.write(buffer, 0, len);
                len = in.read(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
