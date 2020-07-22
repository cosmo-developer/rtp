package org.cosmo.remotetransport.security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;

/**
 * In try face
 */
public class DigitalSignature {
    private byte[] publicKey;
    public DigitalSignature(){
        try {
            String plaintext="follicacid.txt";
            String encrypted="follicacide.txt";
            Cipher cipher=Cipher.getInstance("RSA");
            KeyPairGenerator kpg=KeyPairGenerator.getInstance("RSA");
            SecureRandom sr=new SecureRandom();
            kpg.initialize(2048,new SecureRandom());
            KeyPair kp=kpg.genKeyPair();
            cipher.init(Cipher.ENCRYPT_MODE,kp.getPrivate());
            byte[] plain=new byte[245];
            FileInputStream fis=new FileInputStream(plaintext);
            FileOutputStream fos=new FileOutputStream(encrypted);
            while(fis.read(plain)!=-1){
                fos.write(cipher.doFinal(plain));
            }
            fos.flush();
            fis.close();
            cipher.init(Cipher.DECRYPT_MODE,kp.getPublic());
            fis=new FileInputStream(encrypted);
            byte[] cipherText=new byte[256];
            fos=new FileOutputStream("follicacidd.txt");
            while(fis.read(cipherText)!=-1){
                byte[] d=cipher.doFinal(cipherText);
                System.out.print(new String(d));
                fos.write(d);
            }
            fos.flush();
            fis.close();
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        DigitalSignature n= new DigitalSignature();
    }
}
