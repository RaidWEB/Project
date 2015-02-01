/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projet;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxEntry;
import com.dropbox.core.DbxException;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.ChildList;
import com.google.api.services.drive.model.ChildReference;
import com.google.api.services.drive.model.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.SwingWorker;
import static projet.GoogledropFunction.Upload;
import static projet.GoogledropFunction.downdropgoogle;
import static projet.GoogledropFunction.downdropgoogle1;
import static projet.GoogledropFunction.downdropgoogle2;
import static projet.GoogledropFunction.downdropgoogle3;
import static projet.GoogledropFunction.reform;

/**
 *
 * @author Munoz and Porret
 */
public class Resynchro_back extends SwingWorker<Vector<Fichier>, Fichier>{

    private String folderId;
    private DbxClient jeton;
    private Drive service;
    private ProgressBar_resynchro progressBar_resynchro;
    
    public Resynchro_back(ProgressBar_resynchro progressBar_resynchro,DbxClient jeton,Drive service,String folderId) {
        
        this.folderId=folderId;
        this.jeton=jeton;
        this.progressBar_resynchro=progressBar_resynchro;
        this.service=service;
    }

    
    
   /* protected Vector<Fichier> doInBackground()throws IOException,DbxException{
        //progressBar_resynchro.setVisible(true);
        Vector<Fichier> info=new Vector<Fichier>();
        boolean bool=false;
        boolean bool1=false;
        boolean bool2=false;
        boolean bool3=false;
        String googleUrl_File1="";
        String googleUrl_File2="";
        String googleId_File1="";
        String googleId_File2="";
        
        String testname1="";
        String googleFilename1="";
        String googleFilename2="";
        String testname2="";
        String name="";
        
        String dropboxId2="";
        String dropboxId1="";
        int i=0;
        
        String down1="";
        
        
        Drive.Children.List request = service.children().list(folderId).setQ("trashed = false ");
        
        ChildList children = request.execute();
        java.util.List<ChildReference> a1= children.getItems();
        DbxEntry.WithChildren listing = jeton.getMetadataWithChildren("/");
        java.util.List<DbxEntry> a2=listing.children;
        Iterator<ChildReference> it=a1.iterator() ;
        Iterator<ChildReference> it1=a1.iterator() ;
        Iterator<ChildReference> it2=a1.iterator() ;
      
        
        
        
        while( it.hasNext())
        {
            ChildReference fichier=(ChildReference)it.next();
            
            File file = service.files().get(fichier.getId()).execute();
            
            String a=file.getTitle();
            
            
            String[] b=a.split("\\.");
            int longueur=b.length;
            progressBar_resynchro.setjLabeltext("Vérification de l'intégrite du fichier "+b[0]+"."+b[1]+" ...");
            int j;
            String z="";
            for(j=0;j<longueur -1;j++)
            {
                z+=b[j];
                if(j<longueur -2)
                {
                    z+=".";
                }
                
                name=z;
            }
            
            dropboxId1=name+".1";
            dropboxId2=name+".2";
            googleFilename1=name+".3";
            googleFilename2=name+".4";
            
           
            
            Iterator<DbxEntry> itd=a2.iterator();
            while(itd.hasNext()&& bool==false){
                DbxEntry fichier1=itd.next();
                if(dropboxId1.equals(fichier1.name))
                {
                    bool=true;
                    //System.out.println(bool);
                }
            }
           Iterator<DbxEntry> itd1=a2.iterator();
           while(itd1.hasNext()&& bool1==false){

             DbxEntry fichier1=itd1.next();
                
                if(dropboxId2.equals(fichier1.name))
                {
                    bool1=true;
                    
                }
            }
            
            
            
            
          
            while(it1.hasNext() && bool2==false)
            {
                ChildReference fichier1=(ChildReference)it1.next();
                
                File file1 = service.files().get(fichier1.getId()).execute();
                testname1=file1.getTitle();
                
                // System.out.println(testname1);
                //System.out.println(testname1);
                if(googleFilename1.equals(testname1))
                {
                    bool2=true;
                    googleUrl_File1=file1.getDownloadUrl();
                    googleId_File1=file1.getId();
                    
                }
                
            }
            
           
            
            while(it2.hasNext() && bool3==false)
            {
                ChildReference fichier2=(ChildReference)it2.next();
                
                File file1 = service.files().get(fichier2.getId()).execute();
                testname2=file1.getTitle();
                
                
                if(googleFilename2.equals(testname2))
                {
                    bool3=true;
                    googleUrl_File2=file1.getDownloadUrl();
                    googleId_File2=file1.getId();
                    
                }
                
                
            }
            
            if(bool==false &&  bool1==true && bool2==true && bool3==true)//si il manque le premier fichier dans dropbox
            
                progressBar_resynchro.setjLabeltext("La partie 1 du fichier "+b[0]+"."+b[1]+" est manquante.Téléchargement en cours...");
                //System.out.println("fichier1 manquant");
                Fichier inf=downdropgoogle3(jeton,dropboxId2,service,googleUrl_File1,googleUrl_File2,);//on utilise la methode downdropgoogle3 qui me permet de download les 3 fichiers presents et de retourner un objet fichier avec les 4 full path
                progressBar_resynchro.setjLabeltext("Reconstruction de la partie manquante...");
                reform(inf.getDownloadDropbox_Path1(),inf.getDownloadDropbox_Path2(),inf.getDownloadGoogle_Path3(),inf.getDownloadGoogle_Path4());//On reconstruit le fichier manquant grace au "XOR" des trois autres
                progressBar_resynchro.setjLabeltext("Envoi  de la partie manquante dans le cloud...");
                Upload(jeton, inf.getDownloadGoogle_Path4());//on renvoie le fichier reconstruit sur le cloud appropri�e
                Fichier listinfo=new Fichier(name,dropboxId1,dropboxId2,googleUrl_File1,googleUrl_File2,googleId_File1,googleId_File2,googleFilename1,googleFilename2);//on cree un objet fichier avec toutes les info necessaire pour download les fichier sur le cloud
                info.add(listinfo);//on ajoute cet objet au vecteur info
            
            if(bool==true && bool1==false && bool2==true && bool3==true)//si il manque le deuxieme fichier dans dropbox
            {
                System.out.println("fichier2 manquant");
                progressBar_resynchro.setjLabeltext("La partie 2 du fichier "+b[0]+"."+b[1]+" est manquante.Téléchargement en cours ...");
                Fichier inf=downdropgoogle2(jeton,dropboxId1,service,googleUrl_File1,googleUrl_File2);
                progressBar_resynchro.setjLabeltext("Reconstruction de la partie manquante...");
                reform(inf.getDownloadDropbox_Path1(),inf.getDownloadDropbox_Path2(),inf.getDownloadGoogle_Path3(),inf.getDownloadGoogle_Path4());
                progressBar_resynchro.setjLabeltext("Envoi  de la partie manquante dans le cloud...");
                Upload(jeton,inf.getDownloadGoogle_Path4() );
                Fichier listinfo=new Fichier(name,dropboxId1,dropboxId2,googleUrl_File1,googleUrl_File2,googleId_File1,googleId_File2,googleFilename1,googleFilename2);
                info.add(listinfo);
            }
            if(bool==true && bool1==true && bool2==false && bool3==true)//si il manque le premier fichier dans googledrive
            {
                System.out.println("fichier3 manquant");
                progressBar_resynchro.setjLabeltext("La partie 3 du fichier "+b[0]+"."+b[1]+" est manquante.Téléchargement en cours...");
                Fichier inf=downdropgoogle1(jeton,dropboxId1,dropboxId2,service,googleUrl_File2);
                progressBar_resynchro.setjLabeltext("Reconstruction de la partie manquante...");
                reform(inf.getDownloadDropbox_Path1(),inf.getDownloadDropbox_Path2(),inf.getDownloadGoogle_Path3(),inf.getDownloadGoogle_Path4());
                progressBar_resynchro.setjLabeltext("Envoi  de la partie manquante dans le cloud...");
                GoogledropFunction.insertFile1(service,folderId,inf.getDownloadGoogle_Path4());
                
                Fichier listinfo=new Fichier(name,dropboxId1,dropboxId2,googleUrl_File1,googleUrl_File2,googleId_File1,googleId_File2,googleFilename1,googleFilename2);
                info.add(listinfo);
            }
            if(bool==true && bool1==true && bool2==true && bool3==false)//si il manque le deuxieme fichier dans googledrive
            {
                System.out.println("fichier4 manquant");
                progressBar_resynchro.setjLabeltext("La partie 4 du fichier "+b[0]+"."+b[1]+" est manquante.Téléchargement en cours...");
                Fichier inf=downdropgoogle(jeton,dropboxId1,dropboxId2,service,googleUrl_File1);
                progressBar_resynchro.setjLabeltext("Reconstruction de la partie manquante...");
                reform(inf.getDownloadDropbox_Path1(),inf.getDownloadDropbox_Path2(),inf.getDownloadGoogle_Path3(),inf.getDownloadGoogle_Path4());
                progressBar_resynchro.setjLabeltext("Envoi  de la partie manquante dans le cloud...");
                GoogledropFunction.insertFile1(service,folderId,inf.getDownloadGoogle_Path4());
                Fichier listinfo=new Fichier(name,dropboxId1,dropboxId2,googleUrl_File1,googleUrl_File2,googleId_File1,googleId_File2,googleFilename1,googleFilename2);
                info.add(listinfo);
            }
            
            
            
            
            if(bool==true && bool1==true && bool2==true && bool3==true)//si tous les fichier sont present..
            {
                progressBar_resynchro.setjLabeltext("Le fichier "+b[0]+"."+b[1]+" est complet...");
                Fichier listinfo=new Fichier(name,dropboxId1,dropboxId2,googleUrl_File1,googleUrl_File2,googleId_File1,googleId_File2,googleFilename1,googleFilename2);//on cree un objet fichier avec toutes les info necessaire pour download les fichier sur le cloud
                
                
                info.add(listinfo);//on ajoute cet objet au vecteur info
                it.next();
            }
            
            
            bool=false;//on remet les valeur les booleans a false sinon on ne repassera pas dans le boucles While
            bool1=false;
            bool2=false;
            bool3=false;
            googleUrl_File1="";
            googleUrl_File2="";
        }
        progressBar_resynchro.setjLabeltext("Vérification terminée...");
        for(i=0;i<info.size()-1;i++)
        {
            if((info.get(i).getName().equals(info.get(i+1).getName()))){
                info.remove(i);//on supprime les doublons du au listing un par un des fichier(comme on a deux fichier avec le meme corps jjjio
            }
        }
        
        for(i=0;i<info.size();i++)
        {
            System.out.println(info.get(i).getName());
            
        }
       progressBar_resynchro.dispose();
        return info;
        
        
        
    }
    */
     protected Vector<Fichier> doList()throws IOException,DbxException{
         Vector<Fichier> info=new Vector<Fichier>();
      Drive.Children.List request = service.children().list(folderId).setQ("trashed = false ");
        String name="";
        String googleId1="";
        String googleId2 ="";
        String dropboxId1="";
        String dropboxId2="";
        ChildList children = request.execute();
        java.util.List<ChildReference> a1= children.getItems();
        Iterator<ChildReference> it=a1.iterator() ;
        Iterator<ChildReference> it1=a1.iterator() ;
        int i;
       
          
        
        while( it.hasNext())
        {
             
            ChildReference fichier=(ChildReference)it.next();
           
            
            File file = service.files().get(fichier.getId()).execute();
          
            
            String a=file.getTitle();
            googleId1=file.getId();
            ChildReference fichier1=(ChildReference)it.next();
            File file1 = service.files().get(fichier1.getId()).execute();
            googleId2=file1.getId();
            String[] b=a.split("\\.");
            int longueur=b.length;
            
            int j;
            String z="";
            for(j=0;j<longueur -1;j++)
            {
                z+=b[j];
                if(j<longueur -2)
                {
                    z+=".";
                }
                
                name=z;
                
            }
            dropboxId1=name+".1";
            dropboxId2=name+".2";
            Fichier fich=new Fichier(name,googleId1,googleId2,dropboxId1,dropboxId2);
            info.add(fich);
        }
//          for(i=0;i<info.size()-1;i++)
//        {
//            if((info.get(i).getName().equals(info.get(i+1).getName()))){
//                info.remove(i);//on supprime les doublons du au listing un par un des fichier(comme on a deux fichier avec le meme corps jjjio
//            }
      //  }
        return info;
     }
      protected Vector<Fichier> doList1()throws IOException,DbxException{
         Vector<Fichier> info=new Vector<Fichier>();
    DbxEntry.WithChildren listing = jeton.getMetadataWithChildren("/");
        java.util.List<DbxEntry> a2=listing.children;
         Iterator<DbxEntry> itd=a2.iterator();
                
        String name="";
      
      
       
        int i;
        while( itd.hasNext())
        {
           DbxEntry fichier1=itd.next();
                
           String a =fichier1.name;
                
                 
            String[] b=a.split("\\.");
            int longueur=b.length;
            
            int j;
            String z="";
            for(j=0;j<longueur -1;j++)
            {
                z+=b[j];
                if(j<longueur -2)
                {
                    z+=".";
                }
                
                name=z;
            }
            Fichier fich=new Fichier(name);
            info.add(fich);
        }
          for(i=0;i<info.size()-1;i++)
        {
            if((info.get(i).getName().equals(info.get(i+1).getName()))){
                info.remove(i);//on supprime les doublons du au listing un par un des fichier(comme on a deux fichier avec le meme corps jjjio
            }
        }
        return info;
     }

    @Override
    protected Vector<Fichier> doInBackground() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
