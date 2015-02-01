package projet;

import com.dropbox.core.*;
import java.io.*;
import java.util.Locale;
import java.util.Scanner;
/**
 *
 * @author Munoz and Porret
 */
public class Dropbox_function {
    
    final static String APP_KEY = "oovohdt1x48i1r7";
    final static String APP_SECRET = "2t654n2hp5beuk4";
    private static DbxRequestConfig config;
    
    public static DbxWebAuthNoRedirect authDropbox(){
        // Get your app key and secret from the Dropbox developers website.
        
        
        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);
        
        config = new DbxRequestConfig(
                "JavaTutorial/1.0", Locale.getDefault().toString());
        DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);
        String authorizeUrl = webAuth.start();
        
        //Ouverture du navigateur web pour effectuer l'authentification
        BrowserControl.displayURL(authorizeUrl);
        return webAuth;
    }
    
    public static DbxClient getTokenDropbox(DbxWebAuthNoRedirect webAuth,String code)throws DbxException  {
        
        
        // This will fail if the user enters an invalid authorization code.
        DbxAuthFinish authFinish = webAuth.finish(code);
        // System.out.println(authFinish.accessToken);
        DbxClient client = new DbxClient(config, authFinish.accessToken);
        // System.out.println("Linked account: " + client.getAccountInfo().displayName);
        //  System.out.println(client);
        return client;
        
    }
    public static String  list(DbxClient jeton) throws DbxException, IOException
    {
        
        DbxClient client = jeton;
        DbxEntry.WithChildren listing = client.getMetadataWithChildren("/");
        
        System.out.println("Voici la liste des fichier sur votre cloud:");
        for (DbxEntry child : listing.children) {
            System.out.println( child.name);
            
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Veuillez choisir un fichier a telecharger :");
        String str = sc.nextLine();
        sc.close();
        return str;
    }
    public static void down(DbxClient jeton,String filename,String path,Interface_principale interface_principale) throws DbxException,IOException
    {
        
        
        DbxClient client = jeton;
        
        
        
        
        FileOutputStream outputStream = new FileOutputStream(path+filename);
        
        try {
            DbxEntry.File downloadedFile = client.getFile("/"+filename, null,
                    outputStream);
            //  System.out.println("Metadata: " + downloadedFile.toString());
            //System.out.println("telecharger");
            //    System.out.println("Metadata: " + downloadedFile.toString());
        } finally {
            outputStream.close();
            
            
            
        }
    }
    public static void Upload(DbxClient jeton,String str,String str1,Fichier fichier,Interface_principale interface_principale) throws DbxException, IOException{
        
        DbxClient client = jeton;
        File inputFile = new File(str);
        File inputFile1 = new File(str1);
        FileInputStream inputStream = new FileInputStream(inputFile);
        FileInputStream inputStream1 = new FileInputStream(inputFile1);
        try {
            String[] s=str.split("/");
            String s1=s[s.length -1 ];
            //System.out.println(s1);
            String[] s2=str1.split("/");
            String s3=s2[s2.length -1 ];
            //System.out.println(s3);
            
            DbxEntry.File uploadedFile = client.uploadFile("/"+s1,
                    DbxWriteMode.add(), inputFile.length(), inputStream);
            //System.out.println("Uploaded: " + uploadedFile.toString());
            
            
            DbxEntry.File uploadedFile1 = client.uploadFile("/"+s3,
                    DbxWriteMode.add(), inputFile1.length(), inputStream1);
            //System.out.println("Uploaded: " + uploadedFile.toString());
            
            
        } finally {
            
            inputStream.close();
            inputStream1.close();
            
        }
    }
    
    public static void delete(DbxClient jeton, String dropboxId)throws DbxException
    {
        jeton.delete("/"+dropboxId);
    }
}
