package org.inkasoft.fileguard.fileguard.cli.cryptor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
 


import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.log4j.Logger;
import org.inkasoft.fileguard.fileguard.cli.util.Utils;

public class DESCryptor {
    private static final Logger LOGGER = Logger.getLogger(DESCryptor.class);
    private static final String DES = "DES";
    
    private String password;
    
    public DESCryptor (String password) {
        this.password = password;
        LOGGER.debug(this.password);
    }
    
    public void encryptToFile (String fileName, InputStream in) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IOException {
        FileOutputStream out;
        
        out = new FileOutputStream(fileName);
        
        DESKeySpec dks = new DESKeySpec(password.getBytes());
        SecretKeyFactory skf = SecretKeyFactory.getInstance(DES);
        SecretKey desKey = skf.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(DES);
        
        cipher.init(Cipher.ENCRYPT_MODE, desKey);
        CipherInputStream cis = new CipherInputStream(in, cipher);
        
//        copy(cis, out);
        Utils.copyStream(cis, out);
    }
    
    public void decryptToFile (String fileName, InputStream in) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, IOException {
        FileOutputStream out;
        
        out = new FileOutputStream (fileName);
 
        DESKeySpec dks = new DESKeySpec(password.getBytes());
        SecretKeyFactory skf = SecretKeyFactory.getInstance(DES);
        SecretKey desKey = skf.generateSecret(dks);
        Cipher cipher = Cipher.getInstance(DES);
        
        cipher.init(Cipher.DECRYPT_MODE, desKey);
        CipherOutputStream cos = new CipherOutputStream(out, cipher);
        
//        copy(in, cos);
        Utils.copyStream(in, cos);
    }
    
//    private static void copy (InputStream in, OutputStream out) throws IOException {
//        byte[] buffer = new byte[1024];
//        
//        int len = in.read(buffer);
//        while (len != -1) {
//            out.write(buffer, 0, len);
//            len = in.read(buffer);
//        }
//        
//        in.close();
//        out.close();
//        out.flush();
//    }
    
    public static void main (String args[]) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, FileNotFoundException, IOException {
        for (String s : args) {
            System.out.println(s);
        }
        
//        String password = "<password>";
//        
//        DESCryptor crypto = new DESCryptor(password);
//        crypto.encryptToFile("C:\\Users\\laptopcert2\\Koala2.jpg", new FileInputStream("C:\\Users\\laptopcert2\\Koala.jpg"));
//        crypto.decryptToFile("C:\\Users\\laptopcert2\\Koala2_ok.jpg", new FileInputStream("C:\\Users\\laptopcert2\\Koala2.jpg"));
        
        
//        ByteArrayOutputStream out = encript(password, new FileInputStream("C:\\Users\\laptopcert2\\Koala.jpg"));
//        toFile("C:\\Users\\laptopcert2\\Koala2.jpg", out);
//        
//        ByteArrayOutputStream out2 = decript(password, new FileInputStream("C:\\Users\\laptopcert2\\Koala2.jpg"));
//        toFile("C:\\Users\\laptopcert2\\Koala2_ok.jpg", out2);
    }
 
}