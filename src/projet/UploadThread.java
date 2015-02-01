/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package projet;

import com.dropbox.core.DbxClient;
import com.dropbox.core.DbxException;
import com.google.api.services.drive.Drive;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author Munoz and Porret
 */
public class UploadThread extends Thread{
    
    private DbxClient jeton;
    private String newpath1,newpath2,newpath3,newpath4;
    private Interface_principale interface_principale;
    private Drive serviceGoogle;
    private Fichier fichier;
    private JButton sendButton;
    private String googleDirectory;
    private ProgressBar_resynchro progressBar_resynchro;
    private boolean resultat;
    
    public UploadThread(DbxClient jeton,String newpath1, String newpath2,Fichier fichier,JButton sendButton,Interface_principale interface_principale,Drive serviceGoogle,String newpath3,String newpath4,String googleDirectory,ProgressBar_resynchro progressBar_resynchro,boolean resultat)
    {
        this.jeton=jeton;
        this.fichier=fichier;
        this.newpath1=newpath1;
        this.newpath2=newpath2;
        this.newpath3=newpath3;
        this.newpath4=newpath4;
        this.sendButton=sendButton;
        this.serviceGoogle=serviceGoogle;
        this.interface_principale=interface_principale;
        this.googleDirectory=googleDirectory;
        this.progressBar_resynchro=progressBar_resynchro;
        this.resultat=resultat;
    }
    
    public void run()
    {
        try {
            interface_principale.setEnabled(false);
            progressBar_resynchro.setjLabeltext("Envoi de la partie 1 et 2 du fichier "+fichier.getName()+" en cours...");
            Dropbox_function.Upload(jeton, newpath1, newpath2, fichier,interface_principale);
            //Création des deux attributs permettant le téléchargement des fichiers sur dropbox
            String filename=fichier.getName();
            String dropboxId1=filename+".1";
            String dropboxId2=filename+".2";
            fichier.setDropboxId1(dropboxId1);
            fichier.setDropboxId2(dropboxId2);
            String[] googleList=new String[4];
            progressBar_resynchro.setjLabeltext("Envoi de la partie 3 et 4 du fichier "+fichier.getName()+" en cours...");
            googleList = GoogleFunction.upload(serviceGoogle, newpath3, newpath4,googleDirectory);
            
            //Création des attributs premmettant le téléchargement des fichiers sur GoogleDrive
            fichier.setGoogleId_File1(googleList[0]);
            fichier.setGoogleUrl_File1(googleList[1]);
            fichier.setGoogleId_File2(googleList[2]);
            fichier.setGoogleUrl_File2(googleList[3]);
            fichier.setGoogleFilename1(fichier.getName()+".3");
            fichier.setGoogleFilename2(fichier.getName()+".4");
            resultat=true;
            interface_principale.setResultat(resultat);
            interface_principale.setEnabled(true);
        } catch (DbxException ex) {
            progressBar_resynchro.dispose();
            String message="L'envoi du fichier n' a pu s'effectuer sur Dropbox.";
            ExceptionDialog exceptionDialog=new ExceptionDialog(interface_principale, true, message);
            exceptionDialog.setVisible(true);
            sendButton.setEnabled(true);
            resultat=false;
            GoogleFunction.deleteFile(serviceGoogle,fichier.getGoogleId_File1());
            GoogleFunction.deleteFile(serviceGoogle, fichier.getGoogleId_File2());
            
            try {
                Dropbox_function.delete(jeton, fichier.getDropboxId1());
                Dropbox_function.delete(jeton, fichier.getDropboxId2());
            } catch (DbxException ex1) {
                Logger.getLogger(UploadThread.class.getName()).log(Level.SEVERE, null, ex1);
            }
            
            interface_principale.setResultat(resultat);
            interface_principale.setEnabled(true);
            this.interrupt();
        } catch (IOException ex) {
            progressBar_resynchro.dispose();
            String message="L'envoi du fichier n' a pu s'effectuer sur Google.";
            ExceptionDialog exceptionDialog=new ExceptionDialog(interface_principale, true, message);
            exceptionDialog.setVisible(true);
            sendButton.setEnabled(true);
            resultat=false;
            interface_principale.setResultat(resultat);
            GoogleFunction.deleteFile(serviceGoogle,fichier.getGoogleId_File1());
            GoogleFunction.deleteFile(serviceGoogle, fichier.getGoogleId_File2());
            try {
                Dropbox_function.delete(jeton, fichier.getDropboxId1());
                Dropbox_function.delete(jeton, fichier.getDropboxId2());
            } catch (DbxException ex1) {
                Logger.getLogger(UploadThread.class.getName()).log(Level.SEVERE, null, ex1);
            }
            interface_principale.setEnabled(true);
            this.interrupt();
        }
        
        
    }
}
