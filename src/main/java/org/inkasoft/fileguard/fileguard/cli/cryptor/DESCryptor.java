package org.inkasoft.fileguard.fileguard.cli.cryptor;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
        
        Utils.copyStream(in, cos);
    }
}