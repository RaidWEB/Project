/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package projet;




import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 *
 * @author Munoz and Porret
 */
//Classe permettant de choisir la destination des fichiers issus du découpage
public class Choisir_dest_decoupage extends javax.swing.JFrame {
    
    
    private Interface_principale interface_accueil;
    private Fichier fichier_choisir;
    private JButton cutButton;
    private JButton sendButton;
    
    public Choisir_dest_decoupage(Interface_principale interface_accueil,Fichier fichier,JButton cutButton,JButton sendButton) {
        this.interface_accueil=interface_accueil;
        this.fichier_choisir=fichier;
        this.cutButton=cutButton;
        this.sendButton=sendButton;
        initComponents();
        this.setIconImage(new ImageIcon(getClass().getResource("/images/download-cloud3.png")).getImage());
        
    }
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("RAID Web - Choix du dossier de destination");
        setAlwaysOnTop(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
        });

        jFileChooser1.setApproveButtonText("Découper");
        jFileChooser1.setApproveButtonToolTipText("Lance le découpage");
        jFileChooser1.setCurrentDirectory(new java.io.File("C:\\"));
            jFileChooser1.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
            jFileChooser1.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jFileChooser1ActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jFileChooser1, javax.swing.GroupLayout.DEFAULT_SIZE, 711, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents
    
    private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser1ActionPerformed
        // Cas où l'utilisateur clique sur le bouton "Découper"
        if(evt.getActionCommand() == "ApproveSelection")
        {
            //On désactive le bouton "Découper" de l'interface principale
            cutButton.setEnabled(false);
            //Activation du bouton "Envoyer" de l'interface principale
            sendButton.setEnabled(true);
            //On ferme la fenêtre de séléction de la destination
            dispose();
            //On remet en premier plan la fenêtre principale
            interface_accueil.setEnabled(true);
            interface_accueil.setAlwaysOnTop(true);
            interface_accueil.setAlwaysOnTop(false);
            //On récupère le chemin absolu du dossier de destination
            String path=jFileChooser1.getSelectedFile().getAbsolutePath()+"\\";
            //On affecte les attributs de la classe Fichier correspondant au chemin absolu des 4 fichiers issus du découpage
            fichier_choisir.setCutfilePath(path+fichier_choisir.getName()+".1", path+fichier_choisir.getName()+".2", path+fichier_choisir.getName()+".3", path+fichier_choisir.getName()+".4");
            System.out.println(jFileChooser1.getSelectedFile().getAbsolutePath());
            //Initialistaion de la barre de progression pour le découpage
            ProgressBar progressBar=new ProgressBar(this, true,fichier_choisir,sendButton,interface_accueil,jFileChooser1.getSelectedFile().getAbsolutePath());
            //Création du Thread pour le découpage
            Decoupage_thread decoupage_thread=new Decoupage_thread(fichier_choisir, this, interface_accueil,progressBar,sendButton,cutButton);
            //Lancement du Thread
            decoupage_thread.start();
            //Affichage de la barre progression
            progressBar.setVisible(true);
            
            
            
            
        }
        else if(evt.getActionCommand() == "CancelSelection")
        {
            //On ferme la fenêtre de séléction de la destination
            dispose();
            //On remet en premier plan la fenêtre principale
            interface_accueil.setEnabled(true);
            interface_accueil.setAlwaysOnTop(true);
            interface_accueil.setAlwaysOnTop(false);
            
        }
        
        
        
    }//GEN-LAST:event_jFileChooser1ActionPerformed
    
    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        interface_accueil.setEnabled(true);
        interface_accueil.setAlwaysOnTop(true);
        interface_accueil.setAlwaysOnTop(false);
    }//GEN-LAST:event_formWindowClosed
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser jFileChooser1;
    // End of variables declaration//GEN-END:variables
}
