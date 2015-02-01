package projet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;
import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.dropbox.core.DbxWriteMode;
import com.google.api.services.drive.Drive.Files.List;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.ParentReference;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

/**
 *
 * @author Munoz and Porret
 */

public class GoogledropFunction {
    private static final String CLIENT_ID = "613762133068-ts4b4j45upg0b6i4prck8ctrfrabc1si.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "oLZg-9VnQmMSfv3gBs8-NXo4";
    final static String APP_KEY = "oovohdt1x48i1r7";
    final static String APP_SECRET = "2t654n2hp5beuk4";
    
    private static final String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
    
    
    
    public static void Upload(DbxClient jeton,String str) throws DbxException, IOException{//jeton d'acc�s au cloud + fullpath du fichier a envoyer
        
        java.io.File inputFile = new java.io.File(str);
        
        
        FileInputStream inputStream = new FileInputStream(inputFile);//cr�ation d'un flux d'entr�e pour le fichier str
        
        try {
            String[] s=str.split("\\\\");
            //recuperation du nom du fichier a envoyer
            String s1=s[s.length -1 ];
            
            
            
            DbxEntry.File uploadedFile = jeton.uploadFile("/"+s1,DbxWriteMode.add(), inputFile.length(), inputStream);
            //targetPath - The path to the file on Dropbox (see DbxPath). If a file at that path already exists on Dropbox, then the writeMode parameter will determine what happens.
            //writeMode - Determines what to do if there's already a file at the given targetPath.
            //numBytes - The number of bytes in the given stream. Use -1 if you don't know.
            //contents - The source of file contents. This stream will be automatically closed (whether or not the upload succeeds).
            //envoie du contenu de l'inputstream(du fichier str) avec comme nom s1
            
            
        } finally {
            //on ferme le flux inputsream
            inputStream.close();
            
            
        }
    }
    
    
    public static void reform (String src, String src1, String src2, String dst) throws IOException
    {
        final int MAX =3;
        
        
        //FileInputStream(ouvre unfichier en lecture),BufferedInputStream(permet de bufferiser l'acces au fichier,flux d'entree)
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
        BufferedInputStream bis1 = new BufferedInputStream(new FileInputStream(src1));
        BufferedInputStream bis2 = new BufferedInputStream(new FileInputStream(src2));
        //FileOutputStream(ouvre unfichier en ecriture),BufferedOutputStream(permet de bufferiser l'acces au fichier,flux de sortit)
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dst));
        //creation d'un tableau d'octet
        byte buffer [] = new byte[MAX];
        byte buffer1 [] = new byte[MAX];//idem
        byte buffer2 [] = new byte[MAX];//idem
        boolean fini = false;
        while(!fini)
        {
            //remplit un tableau d'octet et retourne le nombre d'octet lus
            int nbeLus = bis.read(buffer, 0,buffer.length);
            int nbeLus1 = bis1.read(buffer1, 0,buffer1.length);
            int nbeLus2 = bis2.read(buffer2, 0,buffer2.length);
           //tant qu'il y a des données a lire
            if(nbeLus != -1)
            {
                for (int j = 0; j < MAX; j++)
                {
                    //correspond  aux octet 1 à 3 du fichier src
                    byte a=buffer[j];
                    //correspond  aux octet 1 à 3 du fichier src1
                    byte aa=buffer1[j];
                    //correspond aux octets 1 à 3  du fichier src2
                    byte aaa=buffer2[j];
                    
                    //reconstruit les trois premier  octet du fichier
                    byte an=(byte) (a^aa^aaa);
                    //on ecrit dans le bos les octets reconstruit grace au 'XOR' des trois autre octet
                    bos.write(an);
                    
                    // et ainsi de suite
                }
            }
            else{
                //si il n'y a plus rien a lire
                fini=true;
            }
            
        }
        bis.close();
        bis1.close();
        bis2.close();
        //on ferme le flux de sortit
        bos.close();
    }
    
    
    
    public static  String createFolder(Drive d)throws IOException {
        Vector<File> result = new Vector<File>();
        boolean trouve=false;
        String id="";
        List request = d.files().list().setQ("mimeType='application/vnd.google-apps.folder' and trashed=false");
        FileList files = request.execute();
        
        result.addAll(files.getItems());
        
        Iterator<File> it=result.iterator() ;
        
        while(trouve==false && it.hasNext())
        {
            File fichier=(File)it.next();
            System.out.println(fichier.getTitle());
            if(fichier.getTitle().equals("Raidweb"))
            {
                trouve=true;
                id=fichier.getId();
                
            }
            System.out.println(trouve);
        }
        
        
        
        
        if(trouve==false){
            
            
            File body = new File();
            
            
            
            
            
            body.setTitle("Raidweb");
            
            body.setMimeType("application/vnd.google-apps.folder");
            
            
            File file = d.files().insert(body).execute();
            
            return file.getId();
        }
        else{
            
            
            return id;
            
        }
        
    }
    public static String[] insertFile(Drive service,  String parentId, String filepath,String filepath1) throws IOException {
        String[] w=new String[4];
        
        String[] str=filepath.split("/");
        String str1=str[str.length -1 ];
        
        
        String[] str2=filepath1.split("/");
        String str3=str2[str2.length -1];
        
        
        File body = new File();
        File body1 = new File();
        body.setTitle(str1);
        body1.setTitle(str3);
        body.setMimeType("text/plain");
        body1.setMimeType("text/plain");
        // Set the parent folder.
        if (parentId != null && parentId.length() > 0) {
            body.setParents(
                    Arrays.asList(new ParentReference().setId(parentId)));
            body1.setParents(
                    Arrays.asList(new ParentReference().setId(parentId)));
        }
        
        // File's content.
        java.io.File fileContent = new java.io.File(filepath);
        java.io.File fileContent1 = new java.io.File(filepath1);
        FileContent mediaContent = new FileContent("text/plain", fileContent);
        FileContent mediaContent1 = new FileContent("text/plain", fileContent1);
        
        File file = service.files().insert(body, mediaContent).execute();
        File file1 = service.files().insert(body1, mediaContent1).execute();
        String z=file.getId();
        String q=file1.getId();
        String u=file.getDownloadUrl();
        String v=file1.getDownloadUrl();
        
        w[0]=u;
        w[1]=v;
        w[2]=q;
        w[3]=z;
        
        return w;
        
        
        
    }
    public static String[] insertFile1(Drive service,  String parentId, String filepath) throws IOException {
        String[] w=new String[2];
        
        String[] str=filepath.split("\\\\");
        String str1=str[str.length -1 ];
        
        
        
        
        File body = new File();
        
        body.setTitle(str1);
        
        body.setMimeType("text/plain");
        
        // Set the parent folder.
        if (parentId != null && parentId.length() > 0) {
            body.setParents(
                    Arrays.asList(new ParentReference().setId(parentId)));
            
        }
        
        // File's content.
        java.io.File fileContent = new java.io.File(filepath);
        
        FileContent mediaContent = new FileContent("text/plain", fileContent);
        
        
        File file = service.files().insert(body, mediaContent).execute();
        
        String z=file.getId();
        
        String u=file.getDownloadUrl();
        
        
        w[0]=u;
        w[1]=z;
        
        return w;
        
        
        
    }
    
    public static HttpResponse downloadFile(Drive service, File file) {
        if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0) {
            try {
                
                
                HttpResponse resp =
                        service.getRequestFactory().buildGetRequest(new GenericUrl(file.getDownloadUrl()))
                                .execute();
                
                
                return resp;
                
                
            } catch (IOException e) {
                // An error occurred.
                e.printStackTrace();
                return null;
            }
        } else {
            // The file doesn't have any content stored on Drive.
            return null;
        }
    }
    public static void Down(Drive d,String downloadUrl) throws IOException {
        
        
        File f= new File();
        f.setTitle("imageTE.txt");
        f.setDownloadUrl(downloadUrl);
        HttpResponse resp= downloadFile(d,f);
        java.io.File ff = new java.io.File("P:\\"+f.getTitle());
        FileOutputStream fout = new FileOutputStream(ff);
        
        resp.download(fout);
        
        fout.close();
        
        //fout1.close();
    }
    public static void deleteFile(Drive service, String fileId) {
        try {
            service.files().delete(fileId).execute();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
    }
    
    
    
    public static Fichier   downdropgoogle(DbxClient jeton,String str,String str1,Drive d,String downloadUrl,String pathtotal) throws DbxException, IOException
    {
        String path="";
        String path1="";
        String path2="";
        String path3="";
        String title="";
        String title1="";
        String[] b=str.split("\\.");
        int longueur=b.length;
        
        int j;
        String z="";
        for(j=0;j<longueur -1;j++)
        {
            z+=b[j];
            if(j<longueur -2){
                z+=".";
            }
            
            title=z;
        }
        title1=z+".3";
        path3=pathtotal+z+".4";
        
        DbxClient client = jeton;
        
        FileOutputStream outputStream = new FileOutputStream(pathtotal+str);
        FileOutputStream outputStream1 = new FileOutputStream(pathtotal+str1);
        
        try {
            DbxEntry.File downloadedFile = client.getFile("/"+str, null,outputStream);//R�cup�re le contenu d'un fichier et �crit dans le OutputStream donn�.
            path=pathtotal+str;
            DbxEntry.File downloadedFile1 = client.getFile("/"+str1, null,outputStream1);//R�cup�re le contenu d'un fichier et �crit dans le OutputStream donn�.
            path1=pathtotal+str1;
            
        } finally {
            outputStream.close();
            outputStream1.close();
        }
        File f= new File();
        f.setTitle(title1);
        f.setDownloadUrl(downloadUrl);
        HttpResponse resp= downloadFile(d,f);
        java.io.File ff = new java.io.File(pathtotal+f.getTitle());
        path2=pathtotal+f.getTitle();
        FileOutputStream fout = new FileOutputStream(ff);
        
        resp.download(fout);
        Fichier info=new Fichier(path,path1,path2,path3);
        fout.close();
        return info;
        
    }
    public static Fichier   downdropgoogle1(DbxClient jeton,String str,String str1,Drive d,String downloadUrl,String pathtotal) throws DbxException, IOException
    {
        String path="";
        String path1="";
        String path2="";
        String path3="";
        String title="";
        String title1="";
        String[] b=str.split("\\.");
        int longueur=b.length;
        
        int j;
        String z="";
        for(j=0;j<longueur -1;j++)
        {
            z+=b[j];
            if(j<longueur -2){
                z+=".";
            }
            
            title=z;
        }
        title1=z+".4";
        path3=pathtotal+z+".3";
        
        DbxClient client = jeton;
        
        FileOutputStream outputStream = new FileOutputStream(pathtotal+str);
        FileOutputStream outputStream1 = new FileOutputStream(pathtotal+str1);
        
        try {
            DbxEntry.File downloadedFile = client.getFile("/"+str, null,
                    outputStream);
            path=pathtotal+str;
            DbxEntry.File downloadedFile1 = client.getFile("/"+str1, null,
                    outputStream1);
            path1=pathtotal+str1;
            
        } finally {
            outputStream.close();
            outputStream1.close();
        }
        File f= new File();
        f.setTitle(title1);
        f.setDownloadUrl(downloadUrl);
        HttpResponse resp= downloadFile(d,f);
        java.io.File ff = new java.io.File(pathtotal+f.getTitle());
        path2=pathtotal+f.getTitle();
        FileOutputStream fout = new FileOutputStream(ff);
        
        resp.download(fout);
        Fichier info=new Fichier(path,path1,path2,path3);
        fout.close();
        return info;
        
    }
    public static Fichier  downdropgoogle2(DbxClient jeton,String str,Drive d,String downloadUrl,String downloadUrl1,String pathtotal) throws DbxException, IOException
    {
        String path="";
        String path1="";
        String path2="";
        String path3="";
        String title1="";
        String title2="";
        
        String[] b=str.split("\\.");
        int longueur=b.length;
        
        int j;
        String z="";
        for(j=0;j<longueur -1;j++)
        {
            z+=b[j];
            if(j<longueur -2){
                z+=".";
            }
        }
        
        title1=z+".3";
        title2=z+".4";
        
        
        FileOutputStream outputStream = new FileOutputStream(pathtotal+str);
        
        
        
        DbxEntry.File downloadedFile = jeton.getFile("/"+str, null,outputStream);
        path=pathtotal+str;
        path3=pathtotal+z+".2";
        
        
        
        
        
        File f= new File();
        f.setTitle(title1);
        f.setDownloadUrl(downloadUrl);
        HttpResponse resp= downloadFile(d,f);
        java.io.File ff = new java.io.File(pathtotal+f.getTitle());
        path1=pathtotal+f.getTitle();
        FileOutputStream fout = new FileOutputStream(ff);
        
        resp.download(fout);
        resp.disconnect();
        File f1= new File();
        f1.setTitle(title2);
        f1.setDownloadUrl(downloadUrl1);
        HttpResponse resp1= downloadFile(d,f1);
        java.io.File ff1 = new java.io.File(pathtotal+f1.getTitle());
        path2=pathtotal+f1.getTitle();
        FileOutputStream fout1 = new FileOutputStream(ff1);
        
        resp1.download(fout1);
        resp1.disconnect();
        Fichier info=new Fichier(path,path1,path2,path3);
        fout.close();
        fout1.close();
        outputStream.close();
        return info;
        
    }
    public static Fichier  downdropgoogle3(DbxClient jeton,String str,Drive d,String downloadUrl,String downloadUrl1,String pathtotal) throws DbxException, IOException
    {
        String path="";
        String path1="";
        String path2="";
        String path3="";
        String title1="";
        String title2="";
        
        String[] b=str.split("\\.");
        int longueur=b.length;
        
        int j;
        String z="";
        for(j=0;j<longueur -1;j++)
        {
            z+=b[j];
            if(j<longueur -2){
                z+=".";
            }
        }
        
        title1=z+".3";
        title2=z+".4";
        
        
        FileOutputStream outputStream = new FileOutputStream(pathtotal+str);
        
        
        
        DbxEntry.File downloadedFile = jeton.getFile("/"+str, null,outputStream);
        path=pathtotal+str;
        path3=pathtotal+z+".1";
        
        
        
        
        
        File f= new File();
        f.setTitle(title1);
        f.setDownloadUrl(downloadUrl);
        HttpResponse resp= downloadFile(d,f);
        java.io.File ff = new java.io.File(pathtotal+f.getTitle());
        path1=pathtotal+f.getTitle();
        FileOutputStream fout = new FileOutputStream(ff);
        
        resp.download(fout);
        resp.disconnect();
        File f1= new File();
        f1.setTitle(title2);
        f1.setDownloadUrl(downloadUrl1);
        HttpResponse resp1= downloadFile(d,f1);
        java.io.File ff1 = new java.io.File(pathtotal+f1.getTitle());
        path2=pathtotal+f1.getTitle();
        FileOutputStream fout1 = new FileOutputStream(ff1);
        
        resp1.download(fout1);
        resp1.disconnect();
        Fichier info=new Fichier(path,path1,path2,path3);
        fout.close();
        fout1.close();
        outputStream.close();
        return info;
        
    }
    public static void downdrop(DbxClient jeton,String str) throws DbxException, IOException//jeton d'acc�s au cloud client + nom de fichier a download
    {
        
        
        FileOutputStream outputStream = new FileOutputStream("P:\\"+str);// 	Cr�e un flux pour �crire  dans le fichier source "P:\\"+str
        
        try {
            DbxEntry.File downloadedFile = jeton.getFile("/"+str, null,outputStream);//�crit le contenut du fichier str dans le fileOutputStram
            
        } finally {
            outputStream.close();
        }
    }
    public static Vector<Fichier> printFilesInFolder(Drive service,DbxClient jeton, String folderId,ProgressBar_resynchro progressBar_resynchro)
            throws IOException, DbxException {
        
        
        
        
        progressBar_resynchro.setjLabeltext("Récupération de la liste des fichiers présents sur le cloud");
        
        
        Resynchro_back resynchro_back=new Resynchro_back(progressBar_resynchro, jeton, service, folderId);
        Vector<Fichier> info=resynchro_back.doList1();
        progressBar_resynchro.dispose();
        return info;
        
        
    }
    
    
    
    
    
}

