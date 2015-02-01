package projet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Munoz and 
 * * Cette oeuvre, création, site ou texte est sous licence Creative Commons Attribution -
* Pas d’Utilisation Commerciale 4.0 International.
* Pour accéder à une copie de cette licence, merci de vous rendre à l'adresse suivante http://creativecommons.org/licenses/by-nc/4.0/ ou envoyez un courrier à Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.
 */
public class AESFile {
    
    private static final int IV_LENGTH=32;
    //méthode permettant le chiffrement du mot de passe de l'utilisateur
    public static String encryptPass(String password)
    {
        try
        {
           
            String password1=Salt.randomString(25)+":"+password;
            System.out.println(password1);
            byte[] bytesOfMessage = password1.getBytes("UTF-8");
        
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] thedigest = md.digest(bytesOfMessage);
            Key clef = new SecretKeySpec(key.getBytes("ISO-8859-1"),"Blowfish");
            Cipher cipher=Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE,clef);
            String newpass=new String(cipher.doFinal(thedigest));
            newpass=newpass.substring(2,18);
            return newpass;
        }
        catch (Exception e)
        {
            return null;
        }
    }
    
    //Méthode permettant le chiffrement des données
    public static void encrypt(InputStream in, OutputStream out, String password) throws Exception{
        byte[] keypass= new byte[IV_LENGTH];
        byte[] tempKey =password.getBytes();
        
            //Récupération des 16 premiers octets pour former la clé secrète
            for (int i = 0; i < 16; i++) 
            {
               keypass[i]=tempKey[i];
            }
        
        SecureRandom r = new SecureRandom();
        byte[] iv = new byte[IV_LENGTH];
        r.nextBytes(iv);
        //write IV as a prefix
        out.write(iv); 
        out.flush();
        
        //"DES/ECB/PKCS5Padding";"AES/CBC/PKCS5Padding"
        Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding"); 
       
        SecretKeySpec keySpec = new SecretKeySpec(keypass, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
        
        out = new CipherOutputStream(out, cipher);
        byte[] buf = new byte[1024];
        int numRead = 0;
        while ((numRead = in.read(buf)) >= 0) {
            out.write(buf, 0, numRead);
        }
        out.close();
    }
    
    //Méthode permettant de déchiffrer les données
    public static void decrypt(InputStream in, OutputStream out, String password) throws Exception{
        
        byte[] keypass= new byte[IV_LENGTH];
        byte[] tempKey =password.getBytes();
            //Récupération des 16 premiers octets pour former la clé secrète
            for (int i = 0; i < 16; i++) 
            {
               keypass[i]=tempKey[i];
            }
        
        byte[] iv = new byte[IV_LENGTH];
        in.read(iv);
        
        //"DES/ECB/PKCS5Padding";"AES/CBC/PKCS5Padding"
        Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding"); 
       
        SecretKeySpec keySpec = new SecretKeySpec(keypass, "AES");
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
        
        in = new CipherInputStream(in, cipher);
        byte[] buf = new byte[1024];
        int numRead = 0;
        while ((numRead = in.read(buf)) >= 0) {
            out.write(buf, 0, numRead);
        }
        in.close();
        out.close();
        
    }
    
    
    public static void copy(int mode, String inputFile, String outputFile, String password) throws Exception {
        
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(inputFile));
        BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(outputFile));
        if(mode==Cipher.ENCRYPT_MODE){
            encrypt(is, os, password);
        }
        else if(mode==Cipher.DECRYPT_MODE){
            decrypt(is, os, password);
        }
        else throw new Exception("unknown mode");
        is.close();
        os.close();
    }
    public static void encryptfinal( String inputFile, String outputFile, String password) throws Exception {
        
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(inputFile));
        BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(outputFile));
        encrypt(is, os, password);
        is.close();
        os.close();
        copyFile(outputFile, inputFile);
    }
    public static void decryptfinal( String inputFile, String outputFile, String password) throws Exception {
        
        BufferedInputStream is = new BufferedInputStream(new FileInputStream(inputFile));
        BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(outputFile));
        
        decrypt(is, os, password);
        is.close();
        os.close();
        copyFile(outputFile, inputFile);
    }
   //Méthode permettant de copier le chiffrement et le déchiffrement dans les fichiers d'origine
    public static void copyFile (String src, String dest) throws IOException {
        InputStream in = new BufferedInputStream(new FileInputStream(src));
        OutputStream out = new BufferedOutputStream(new FileOutputStream(dest));
        byte[] buf = new byte[4096];
        int n;
        while ((n=in.read(buf, 0, buf.length)) > 0)
            out.write(buf, 0, n);
        
        in.close();
        out.close();
        File file=new File(src);
        file.delete();
        
    }
   
    
}

