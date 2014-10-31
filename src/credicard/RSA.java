/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package credicard;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAKeyGenParameterSpec;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class RSA {
    
    static Cipher cipher;
    static PublicKey publicKey;
    static PrivateKey privateKey;
    
    public static PublicKey getPublicKey(File file) throws FileNotFoundException, IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream (new FileInputStream (file));  
        PublicKey ret = (PublicKey) ois.readObject();  
        ois.close();  
        return ret; 
    }
    
    public static PrivateKey getPrivateKey(File file) throws FileNotFoundException, IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream (new FileInputStream (file));  
        PrivateKey ret = (PrivateKey) ois.readObject();  
        ois.close();  
        return ret;
    }
    
    public static void genKeyPair() throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, FileNotFoundException, IOException{
        KeyPairGenerator kpg = KeyPairGenerator.getInstance ("RSA");  
        kpg.initialize (new RSAKeyGenParameterSpec(1024, RSAKeyGenParameterSpec.F4));  
        KeyPair kpr = kpg.generateKeyPair ();  
        PrivateKey priv = kpr.getPrivate();          
        PublicKey pub = kpr.getPublic();  
          
        //-- Gravando a chave pública em formato serializado  
        ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream ("Key"+new SimpleDateFormat("YYYY-MM-dd").format(Calendar.getInstance().getTime())+".public"));  
        oos.writeObject (pub);  
        oos.close();  
          
        //-- Gravando a chave privada em formato serializado  
        //-- Não é a melhor forma (deveria ser guardada em um keystore, e protegida por senha),   
        //-- mas isto é só um exemplo  
        oos = new ObjectOutputStream (new FileOutputStream ("Key"+new SimpleDateFormat("YYYY-MM-dd").format(Calendar.getInstance().getTime())+".private"));  
        oos.writeObject (priv);  
        oos.close();
    }
    
    public static String getEncrypt(String input, File filePublicKey){
        try {
            publicKey = RSA.getPublicKey(filePublicKey);
            
            byte[] inData = input.getBytes();
            byte[] outData = encryptData(inData);
            return new String(new BASE64Encoder().encode(outData));
        } catch (Exception ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static String getDecrypt(String input, File filePrivateKey){
        try {
            privateKey = RSA.getPrivateKey(filePrivateKey);
        
            byte[] inData = new BASE64Decoder().decodeBuffer(input);
            byte[] outData = decryptData(inData);
            return new String(outData);
        } catch (Exception ex) {
            Logger.getLogger(RSA.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static byte[] encryptData(byte[] inData) throws Exception {
        cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(inData);
    }

    public static byte[] decryptData(byte[] inData) throws Exception {
        cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(inData);
    }
    
}
