/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package projet;


import javax.swing.JButton;


/**
 *
 * @author Munoz and Porret
 */
public class Decoupage_thread extends Thread{
    
    private Fichier fichier;
    private Choisir_dest_decoupage choisir_dest_decoupage;
    private Interface_principale interface_principale;
    private ProgressBar progressBar;
    private JButton cutButton,sendButton;
    
    public Decoupage_thread(Fichier fichier,Choisir_dest_decoupage choisir_dest_decoupage,Interface_principale interface_principale,ProgressBar progressBar,JButton sendButton,JButton cutButton)
    {
        this.fichier=fichier;
        this.choisir_dest_decoupage=choisir_dest_decoupage;
        this.interface_principale=interface_principale;
        this.progressBar=progressBar;
        this.cutButton=cutButton;
        this.sendButton=sendButton;
        
    }
    
    public void run()
    {
        try {
            
            Decoupage.cut(fichier.getChemin(), fichier.getPath1(), fichier.getPath2(), fichier.getPath3(), fichier.getPath4(),progressBar);
            
           
        } catch(Exception e){
            sendButton.setEnabled(false);
            cutButton.setEnabled(true);
            progressBar.dispose();
            String message="Le fichier n'a pu être découpé car le système ne peut accéder au dossier de destination";
            interface_principale.afficher_Erreur(message);
            this.interrupt();
        }
        
    }
}

