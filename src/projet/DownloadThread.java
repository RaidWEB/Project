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
import java.util.List;
import javax.swing.JButton;
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
public class DownloadThread extends Thread {
    
    
    private DbxClient jeton;
    private Drive serviceGoogle;
    private Fichier fichier;
    private Interface_principale interface_principale;
    private String path;
    private ProgressBar_resynchro progressBar_resynchro;
    private JButton downloadButton,rebuildButton;
    private String folderid,password;
    
    
    public  DownloadThread(DbxClient jeton,Drive serviceGoogle,Fichier fichier,Interface_principale interface_principale,String path,ProgressBar_resynchro progressBar_resynchro,JButton downloadButton,JButton rebuilButton,String folderid,String password)
    {
        this.fichier=fichier;
        this. interface_principale=interface_principale;
        this.jeton=jeton;
        this.path=path;
        this.serviceGoogle=serviceGoogle;
        this.downloadButton=downloadButton;
        this.rebuildButton=rebuilButton;
        this.progressBar_resynchro=progressBar_resynchro;
        this.folderid=folderid;
        this.password=password;
        
    }
    
    public void run()
    {
        ;
        boolean bool=false;
        boolean bool1=false;
        boolean bool2=false;
        boolean bool3=false;
        String googleUrl_File1="";
        String googleUrl_File2="";
        String testname1="";
        String googleFilename1=fichier.getName()+".3";
        String googleFilename2=fichier.getName()+".4";
        String testname2="";

        
        String dropboxId2=fichier.getName()+".2";
        String dropboxId1=fichier.getName()+".1";
        
        int i=0;
        
        
        try {
            //Récupération de la liste puis traitement de celle-ci
            Drive.Children.List request = serviceGoogle.children().list(folderid).setQ("trashed=false " + "and title='" + googleFilename1 + "'");
            Drive.Children.List request1 = serviceGoogle.children().list(folderid).setQ("trashed=false " + "and title='" + googleFilename2 + "'");
            ChildList children = request.execute();
            ChildList children1 = request1.execute();
            DbxEntry listing = jeton.getMetadata("/"+dropboxId1);
            DbxEntry listing1 = jeton.getMetadata("/"+dropboxId2);
            
            
            List<ChildReference> google1= children.getItems();
            List<ChildReference> google2= children1.getItems();
            
            Iterator<ChildReference> it=google1.iterator() ;
            Iterator<ChildReference> it1=google2.iterator() ;
            
            if(listing!=null)
            {
                
                bool=true;
            }
            
            if(listing1!=null)
            {
                bool1=true;
                
            }
            
            while(it.hasNext())
            {
                ChildReference fichier1=(ChildReference)it.next();
                
                File file1 = serviceGoogle.files().get(fichier1.getId()).execute();
                testname1=file1.getTitle();
                
                if(googleFilename1.equals(testname1))
                {
                    bool2=true;
                    googleUrl_File1=file1.getDownloadUrl();
                   
                    
                }
                
            }
               
            while(it1.hasNext())
            {
                ChildReference fichier2=(ChildReference)it1.next();
                
                File file1 = serviceGoogle.files().get(fichier2.getId()).execute();
                testname2=file1.getTitle();
                
                
                
                if(googleFilename2.equals(testname2))
                {
                    bool3=true;
                    googleUrl_File2=file1.getDownloadUrl();
                 
                    
                }
                
                
            }
            if(bool==true && bool1==true && bool2==true && bool3==true){
                progressBar_resynchro.setjLabeltext("Téléchargement de la partie 1 du fichier "+fichier.getName()+" en cours...");
                Dropbox_function.down(jeton,dropboxId1, path,interface_principale);
                progressBar_resynchro.setjLabeltext("Téléchargement de la partie 2 du fichier "+fichier.getName()+" en cours...");
                Dropbox_function.down(jeton,dropboxId2, path,interface_principale);
                progressBar_resynchro.setjLabeltext("Téléchargement de la partie 3 du fichier "+fichier.getName()+" en cours...");
                GoogleFunction.download(serviceGoogle, googleUrl_File1, path, googleFilename1, interface_principale);
                progressBar_resynchro.setjLabeltext("Téléchargement de la partie 4 du fichier "+fichier.getName()+" en cours...");
                GoogleFunction.download(serviceGoogle,googleUrl_File2, path,googleFilename2, interface_principale);
                progressBar_resynchro.setjLabeltext("Déchiffrement des parties du fichier "+fichier.getName()+" en cours...");
                AESFile.decryptfinal(path+dropboxId1, path+dropboxId1+".dec",password);
                AESFile.decryptfinal(path+dropboxId2, path+dropboxId2+".dec",password);
                AESFile.decryptfinal(path+googleFilename1,path+googleFilename1+".dec",password);
                AESFile.decryptfinal(path+googleFilename2,path+googleFilename2+".dec",password);
//
                
                
                
            }
            else if(bool==false &&  bool1==true && bool2==true && bool3==true)//si il manque le premier fichier dans dropbox
            {
                
                progressBar_resynchro.setjLabeltext("La partie 1 du fichier "+fichier.getName()+" est manquante.Téléchargement en cours...");
                //on utilise la methode downdropgoogle3 qui me permet de download
                //les 3 fichiers presents et de retourner un objet fichier avec les 4 full path
                Fichier inf=downdropgoogle3(jeton,dropboxId2,serviceGoogle,googleUrl_File1,googleUrl_File2,path);
                progressBar_resynchro.setjLabeltext("Reconstruction de la partie manquante...");
                AESFile.decryptfinal(inf.getDownloadDropbox_Path1(), inf.getDownloadDropbox_Path1()+".dec",password);
                AESFile.decryptfinal(inf.getDownloadDropbox_Path2(), inf.getDownloadDropbox_Path2()+".dec",password);
                AESFile.decryptfinal(inf.getDownloadGoogle_Path3(), inf.getDownloadGoogle_Path3()+".dec",password);
                //On reconstruit le fichier manquant grace au "XOR" des trois autres
                reform(inf.getDownloadDropbox_Path1(),inf.getDownloadDropbox_Path2(),inf.getDownloadGoogle_Path3(),inf.getDownloadGoogle_Path4());
                
                
                progressBar_resynchro.setjLabeltext("Envoi  de la partie manquante dans le cloud...");
                AESFile.encryptfinal(inf.getDownloadGoogle_Path4(),inf.getDownloadGoogle_Path4()+".enc",password);
                //on renvoie le fichier reconstruit sur le cloud appropriée
                Upload(jeton,inf.getDownloadGoogle_Path4());
                
                AESFile.decryptfinal(inf.getDownloadGoogle_Path4(), inf.getDownloadGoogle_Path4()+".dec",password);
            }
            //si il manque le deuxieme fichier dans dropbox
            else if(bool==true && bool1==false && bool2==true && bool3==true)
            {
                
                progressBar_resynchro.setjLabeltext("La partie 2 du fichier "+fichier.getName()+" est manquante.Téléchargement en cours ...");
                Fichier inf=downdropgoogle2(jeton,dropboxId1,serviceGoogle,googleUrl_File1,googleUrl_File2,path);
                progressBar_resynchro.setjLabeltext("Reconstruction de la partie manquante...");
                AESFile.decryptfinal(inf.getDownloadDropbox_Path1(), inf.getDownloadDropbox_Path1()+".dec",password);
                AESFile.decryptfinal(inf.getDownloadDropbox_Path2(), inf.getDownloadDropbox_Path2()+".dec",password);
                AESFile.decryptfinal(inf.getDownloadGoogle_Path3(), inf.getDownloadGoogle_Path3()+".dec",password);
                reform(inf.getDownloadDropbox_Path1(),inf.getDownloadDropbox_Path2(),inf.getDownloadGoogle_Path3(),inf.getDownloadGoogle_Path4());                
                progressBar_resynchro.setjLabeltext("Envoi  de la partie manquante dans le cloud...");
                AESFile.encryptfinal(inf.getDownloadGoogle_Path4(),inf.getDownloadGoogle_Path4()+".enc",password);
                
                Upload(jeton,inf.getDownloadGoogle_Path4());
                AESFile.decryptfinal(inf.getDownloadGoogle_Path4(), inf.getDownloadGoogle_Path4()+".dec",password);
                
            }
            //si il manque le premier fichier dans googledrive
            else if(bool==true && bool1==true && bool2==false && bool3==true)
            {
                
                progressBar_resynchro.setjLabeltext("La partie 3 du fichier "+fichier.getName()+" est manquante.Téléchargement en cours...");
                Fichier inf=downdropgoogle1(jeton,dropboxId1,dropboxId2,serviceGoogle,googleUrl_File2,path);
                progressBar_resynchro.setjLabeltext("Reconstruction de la partie manquante...");
                AESFile.decryptfinal(inf.getDownloadDropbox_Path1(), inf.getDownloadDropbox_Path1()+".dec",password);
                AESFile.decryptfinal(inf.getDownloadDropbox_Path2(), inf.getDownloadDropbox_Path2()+".dec",password);
                AESFile.decryptfinal(inf.getDownloadGoogle_Path3(), inf.getDownloadGoogle_Path3()+".dec",password);
                reform(inf.getDownloadDropbox_Path1(),inf.getDownloadDropbox_Path2(),inf.getDownloadGoogle_Path3(),inf.getDownloadGoogle_Path4());
                
                
                
                
                progressBar_resynchro.setjLabeltext("Envoi  de la partie manquante dans le cloud...");
                AESFile.encryptfinal(inf.getDownloadGoogle_Path4(),inf.getDownloadGoogle_Path4()+".enc",password);
                GoogledropFunction.insertFile1(serviceGoogle,folderid,inf.getDownloadGoogle_Path4());
                
                AESFile.decryptfinal(inf.getDownloadGoogle_Path4(), inf.getDownloadGoogle_Path4()+".dec",password);
            }
            //si il manque le deuxieme fichier dans googledrive
            else if(bool==true && bool1==true && bool2==true && bool3==false)
            {
                
                progressBar_resynchro.setjLabeltext("La partie 4 du fichier "+fichier.getName()+" est manquante.Téléchargement en cours...");
                Fichier inf=downdropgoogle(jeton,dropboxId1,dropboxId2,serviceGoogle,googleUrl_File1,path);
                progressBar_resynchro.setjLabeltext("Reconstruction de la partie manquante...");
                
                
                AESFile.decryptfinal(inf.getDownloadDropbox_Path1(), inf.getDownloadDropbox_Path1()+".dec",password);
                AESFile.decryptfinal(inf.getDownloadDropbox_Path2(), inf.getDownloadDropbox_Path2()+".dec",password);
                AESFile.decryptfinal(inf.getDownloadGoogle_Path3(), inf.getDownloadGoogle_Path3()+".dec",password);
                
                reform(inf.getDownloadDropbox_Path1(),inf.getDownloadDropbox_Path2(),inf.getDownloadGoogle_Path3(),inf.getDownloadGoogle_Path4());
                
                AESFile.encryptfinal(inf.getDownloadGoogle_Path4(),inf.getDownloadGoogle_Path4()+".enc",password);
                progressBar_resynchro.setjLabeltext("Envoi  de la partie manquante dans le cloud...");
                GoogledropFunction.insertFile1(serviceGoogle,folderid,inf.getDownloadGoogle_Path4());
                AESFile.decryptfinal(inf.getDownloadGoogle_Path4(), inf.getDownloadGoogle_Path4()+".dec",password);
                
            }
            
            
            else{
                progressBar_resynchro.dispose();
                String message="L'intégrité du fichier a été affecté, nous ne pouvons reconstruire le fichier original.";
                ExceptionDialog exceptionDialog= new ExceptionDialog(interface_principale, true, message);
                exceptionDialog.setVisible(true);
                rebuildButton.setEnabled(false);
                this.interrupt();
            }
            
           progressBar_resynchro.dispose();
            
        } catch (IOException ex) {
            rebuildButton.setEnabled(false);
            downloadButton.setEnabled(true);
            progressBar_resynchro.dispose();
            String message="L'application n'a pas de droit en écriture sur la destination sélectionnée ou le serveur Google n'est pas joignable";
            interface_principale.afficher_Erreur(message);
            this.interrupt();
        } catch (DbxException ex) {
            rebuildButton.setEnabled(false);
            downloadButton.setEnabled(true);
            progressBar_resynchro.dispose();
            String message="Le serveur Dropbox n'est pas joignable. Veuillez réessayer ultérieurement.";
            interface_principale.afficher_Erreur(message);
            this.interrupt();
        }catch (Exception ex) {
            rebuildButton.setEnabled(false);
            downloadButton.setEnabled(true);
            progressBar_resynchro.dispose();
            String message="Le déchiffrement des données a rencontré un problème.";
            interface_principale.afficher_Erreur(message);
            this.interrupt();        }
    }
    
}
