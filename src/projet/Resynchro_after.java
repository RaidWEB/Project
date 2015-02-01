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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Munoz and Porret
 */
public class Resynchro_after implements Runnable{
    
    private ProgressBar_resynchro progressBar_resynchro; 
    private final Drive serviceGoogle;
    private final DbxClient jeton;
    private final String googleDirectory;
    private Interface_principale interface_principale;
    private Vector<Fichier> list;
    
    public Resynchro_after(ProgressBar_resynchro progressBar_resynchro,Drive serviceGoogle,DbxClient jeton,String googleDirectory,Interface_principale interface_principale)
    {
      this.progressBar_resynchro=progressBar_resynchro;
      this.jeton=jeton;
      this.serviceGoogle=serviceGoogle;
      this.googleDirectory=googleDirectory;
      this.interface_principale=interface_principale;
    }
    
    public void resynchro()         
    {
      
       new Thread(this).start();
        
    }
    
    @Override
    public void run() {
        try {
            list=GoogledropFunction.printFilesInFolder(serviceGoogle, jeton, googleDirectory, progressBar_resynchro);
        } catch (IOException ex) {
            Logger.getLogger(Resynchro_after.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DbxException ex) {
            Logger.getLogger(Resynchro_after.class.getName()).log(Level.SEVERE, null, ex);
        }
        interface_principale.setListeFichier(list);
        interface_principale.traitement_vecteur();
    }
    
}
