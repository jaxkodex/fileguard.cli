package org.inkasoft.fileguard.fileguard.cli;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.NoSuchPaddingException;

import org.apache.log4j.Logger;
import org.inkasoft.fileguard.fileguard.cli.cryptor.DESCryptor;
import org.inkasoft.fileguard.fileguard.cli.util.Utils;

public class App {
    private static final Logger LOGGER = Logger.getLogger(App.class);
    private static final String ENCRYPT = "E";
    private static final String DECRYPT = "D";
    
    
    private static void printHowToUse () {
        System.out.println("Uso: <archivo fuente> <metodo>");
        System.out.println(" Archivo fuente Direccion absoluta del archivo.");
        System.out.println(" Método         Valores E/D (E=Encrypt, D=Descrypt)");
        System.out.println(" Password       Password de al menos 9 caracteres");
    }
    
    /**
     * Realiza encriptación de contenido haciendo uso del
     * algoritmo DES
     * @param args Los argumentos tienen que ser tres, 
     *          el primero debe tener el archivo
     *          el segundo debe tener la accion (ENC/DENC)
     * @throws IOException 
     * @throws FileNotFoundException 
     * @throws NoSuchPaddingException 
     * @throws InvalidKeySpecException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     */
    public static void main( String[] args ) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, FileNotFoundException, IOException {
        System.out.println(args);
        if (args.length != 3) {
            LOGGER.debug("No hay suficientes argumentos");
            printHowToUse();
            System.exit(-1);
        }
        String filePath, password, metodo, fileName, extension;
        
        filePath = args[0];
        metodo = args[1];
        password = args[2];
        
        if (password.length() < 9) {
            LOGGER.debug("password no cumple");
            printHowToUse();
            System.exit(-1);
        }
        
        if (metodo == null || (!ENCRYPT.equals(metodo) && !DECRYPT.equals(metodo))) {
            LOGGER.debug("metodo no cumple");
            printHowToUse();
            System.exit(-1);
        }
        
        File file = new File(filePath);
        
        if (!file.exists() || !file.isFile()) {
            LOGGER.debug("Archivo no existe");
            LOGGER.debug(file.exists());
            LOGGER.debug(file.isFile());
            LOGGER.debug(filePath);
            System.out.println("El archivo seleccionado no es un archivo que se pueda encriptar");
            printHowToUse();
            System.exit(-1);
        }
        
        fileName = file.getName();
        extension = Utils.getFileExtension(fileName);
        LOGGER.debug(extension);
        LOGGER.debug(file.getParent());
        Path tmpFile = Files.createTempFile("data_", ".dt");
        tmpFile.toFile().deleteOnExit();
        LOGGER.debug(tmpFile.getParent());
        
        DESCryptor cryptor = new DESCryptor(password);
        
        if (ENCRYPT.equals(metodo)) {
            cryptor.encryptToFile(tmpFile.toFile().getPath(), new FileInputStream(filePath));
        } else if (DECRYPT.equals(metodo)) {
            cryptor.decryptToFile(tmpFile.toFile().getPath(), new FileInputStream(filePath));
        } else {
            printHowToUse();
            System.exit(-1);
        }
        Utils.copyStream(new FileInputStream(tmpFile.toFile().getPath()), new FileOutputStream(filePath));
        System.exit(0);
    }
}
