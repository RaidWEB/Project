/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package projet;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;

/**
 *
 * @author Munoz and Porret
 */
public class Rebuild_Action implements Runnable{
    
    private Interface_principale interface_principale;
    private String pathDownload;
    private JButton rebuildButton;
    private Fichier fichier;
    private ProgressBar_resynchro progressBar_resynchro;
    public Rebuild_Action(Interface_principale interface_principale,String pathDownload,JButton rebuildButton,Fichier fichier)
    {
        this.interface_principale=interface_principale;
        this.pathDownload=pathDownload;
        this.rebuildButton=rebuildButton;
        this.fichier=fichier;
    }
    
    public void rebuid()
    {
        //On désactive le bouton "Reconstruire"
        rebuildButton.setEnabled(false);
        //Création de la barre de progression et affectation du texte à afficher
        progressBar_resynchro=new ProgressBar_resynchro();
        progressBar_resynchro.setjLabeltext("Reconstruction du fichier "+fichier.getName()+" en cours...");
        //Démarrage du Thread de traitement
        new Thread(this).start();
    }
    
    @Override
    public void run() {
        
        //Appel de la fonction de reconstruction du fichier d'origine
        Reconstruction.reconstruire(fichier.getDownloadDropbox_Path1(), fichier.getDownloadDropbox_Path2(), fichier.getDownloadGoogle_Path3(), pathDownload+fichier.getName(),rebuildButton,interface_principale,progressBar_resynchro);
        //Suppression des fichiers qui ont permis la reconsruction
        File file1=new File(fichier.getDownloadDropbox_Path1());
        File file2=new File(fichier.getDownloadDropbox_Path2());
        File file3=new File(fichier.getDownloadGoogle_Path3());
        File file4=new File(fichier.getDownloadGoogle_Path4());
        file1.delete();
        file2.delete();
        file3.delete();
        file4.delete();
        //fermeture de la fenêtre de progression
        progressBar_resynchro.dispose();
        //On remet au premier plan la fenêtre principale
        interface_principale.setEnabled(true);
        interface_principale.setAlwaysOnTop(true);
        interface_principale.setAlwaysOnTop(false);
        
    }
}
