package org.inkasoft.fileguard.fileguard.cli.cryptor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FileCipher {
    
    public static final int FILE_NOT_CHECKED = 0;
    public static final int FILE_ALREADY_ENCRYPTED = 1;
    public static final int FILE_NOT_ENCRYPTED = 2;
    private static final byte[] ENCRYPTED_FILE_HEADER = "ENCFILEGUARD".getBytes();
    
    private String fileName;
    private int fileState;
    private File file;
    
    public FileCipher (String fileName) {
        this.fileName = fileName;
        fileState = FILE_NOT_CHECKED;
        file = new File(fileName);
    }
    
    public boolean isEncrypted () {
        if (fileState != FILE_NOT_CHECKED) {
            return FILE_ALREADY_ENCRYPTED == fileState ? true : false;
        }
        try {
            FileInputStream fileInputSteam = new FileInputStream(fileName);
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return true;
    }

}
