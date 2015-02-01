/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package projet;





import com.dropbox.core.DbxClient;

import com.google.api.services.drive.Drive;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.Iterator;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;





/**
 *
 * @author Munoz and Porret
 * Cette oeuvre, création, site ou texte est sous licence Creative Commons Attribution -
* Pas d’Utilisation Commerciale 4.0 International.
* Pour accéder à une copie de cette licence, merci de vous rendre à l'adresse suivante http://creativecommons.org/licenses/by-nc/4.0/ ou envoyez un courrier à Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.
 */
public class Interface_principale extends javax.swing.JFrame implements Runnable{
    
    
    
    final private DbxClient jeton;
    final private Drive serviceGoogle;
    private final  String googleDirectory;
    private Vector<Fichier> listeFichier;
    private String newpath1,newpath2,newpath3,newpath4,path1,path2,path3,path4,password,pathDownload;
    private JButton send,cut,remove;
    private Fichier fichier_traitement;
    private JPanel jpanel_traitement;
    private ProgressBar_resynchro progressBar_resynchro;
    private boolean resultat;
    
    /**
     * Creates new form interface_accueil
     */
    public Interface_principale(DbxClient jeton,Drive serviceGoogle,String googleDirectory,Vector<Fichier> listeFichier,String password)
    {
        this.serviceGoogle=serviceGoogle;
        this.jeton=jeton;
        this.googleDirectory=googleDirectory;
        this.password=AESFile.encryptPass(password);
        
        initComponents();
        this.listeFichier=listeFichier;
        jMenuItem3.setEnabled(true);
        this.setIconImage(new ImageIcon(getClass().getResource("/images/download-cloud3.png")).getImage());
    }
    public Interface_principale(DbxClient jeton,Drive serviceGoogle,String googleDirectory,String password)
    {
        this.serviceGoogle=serviceGoogle;
        this.jeton=jeton;
        this.googleDirectory=googleDirectory;
        this.password=AESFile.encryptPass(password);
        
        initComponents();
        jMenuItem3.setEnabled(true);
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

        jPanel1 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("RAID Web by Porret & Munoz");
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setResizable(false);

        jPanel1.setAutoscrolls(true);
        jPanel1.setMinimumSize(new java.awt.Dimension(805, 690));
        jPanel1.setLayout(new java.awt.GridLayout(23, 1, 10, 2));

        jMenu1.setText("Fichier");

        jMenuItem2.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/add_file.gif"))); // NOI18N
        jMenuItem2.setText("Ajouter un fichier");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/close.png"))); // NOI18N
        jMenuItem1.setText("Fermer");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Lister");

        jMenuItem3.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_L, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Cloud_Reload.png"))); // NOI18N
        jMenuItem3.setText("Listing des fichiers ");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem3);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Chiffrement");

        jMenuItem4.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Login.png"))); // NOI18N
        jMenuItem4.setText("Changer de clé ");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem4);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 805, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 805, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    public void setKey(String pass)
    {
        this.password=AESFile.encryptPass(pass);
        System.out.println(password);
    }
    public void setPathDownload(String pathDownload) {
        this.pathDownload = pathDownload;
    }
    
    public void setListeFichier(Vector<Fichier> listeFichier) {
        this.listeFichier = listeFichier;
    }
    //Action effectuée lor du clic sur le menu Ajouter un fichier
    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        Choisir_fichier choisir=new Choisir_fichier(this);
        choisir.setVisible(true);
        this.setEnabled(false);
    }//GEN-LAST:event_jMenuItem2ActionPerformed
    //Action effectuée lor du clic sur le menu Fermer
    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        dispose();
    }//GEN-LAST:event_jMenuItem1ActionPerformed
    //Action effectuée lors du clic sur Listing des fichiers 
    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        // TODO add your handling code here:
        
        Relisting relisting = new Relisting(this, resultat, jPanel1, this);
        relisting.setVisible(true);
        
        
    }//GEN-LAST:event_jMenuItem3ActionPerformed
    
    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        // TODO add your handling code here:
        Change_Password change_Password=new Change_Password(this, true,this);
        change_Password.setVisible(true);
        
        
    }//GEN-LAST:event_jMenuItem4ActionPerformed
    
    public void relist()
    {
        ProgressBar_resynchro progressBar_resynchro=new ProgressBar_resynchro();
        Resynchro_after resynchro_after=new Resynchro_after(progressBar_resynchro, serviceGoogle, jeton, googleDirectory,this);
        resynchro_after.resynchro();
    }
    //Action effectuée lors du clic sur le bouton Découper
    private void cutButtonActionPerformed(ActionEvent evt,Fichier fichier,JButton cutButton,JButton sendButton)
    {
        Choisir_dest_decoupage choisir_dest=new Choisir_dest_decoupage(this,fichier,cutButton,sendButton);
        choisir_dest.setVisible(true);
        this.setEnabled(false);
    }
    
    
    //Action effectuée lors du clic sur le bouton Envoyer
    private void sendButtonActionPerformed(ActionEvent evt, JButton sendButton,final Fichier fichier,final JPanel jpanel2,JButton cutButton,JButton removeButton)
    {
        
        progressBar_resynchro=new ProgressBar_resynchro();
        progressBar_resynchro.setVisible(true);
        progressBar_resynchro.setjLabeltext("Chiffrement des parties du fichier avant envoi");
        
        //Modification du chemin absolu du fichier la méthode d'upload de l'API dropbox et Google
        path1=fichier.getPath1();
        newpath1=path1.replace('\\','/');
        newpath1='/'+newpath1;
        //System.out.println(newpath1);
        path2=fichier.getPath2();
        newpath2=path2.replace('\\', '/');
        newpath2='/'+newpath2;
        //System.out.println(newpath2);
        path3=fichier.getPath3();
        newpath3=path3.replace('\\', '/');
        //System.out.println(newpath3);
        
        path4=fichier.getPath4();
        newpath4=path4.replace('\\', '/');
        //System.out.println(newpath4);
        send=sendButton;
        cut=cutButton;
        remove=removeButton;
        fichier_traitement=fichier;
        jpanel_traitement=jpanel2;
        //On désactive le bouton d'envoi pour éviter les duplicats sur les clouds
        
        send.setEnabled(false);
        new Thread(this).start();
        
    }
    
    @Override
    //Création du thread qui gere l'upload
    public void run() {
        
        try {
            //Chiffrement des fichiers issus du découpage
            AESFile.encryptfinal(fichier_traitement.getPath1(), fichier_traitement.getPath1()+".enc",password);
            AESFile.encryptfinal(fichier_traitement.getPath2(), fichier_traitement.getPath2()+".enc",password);
            AESFile.encryptfinal(fichier_traitement.getPath3(), fichier_traitement.getPath3()+".enc",password);
            AESFile.encryptfinal(fichier_traitement.getPath4(), fichier_traitement.getPath4()+".enc",password);
        } catch (Exception ex) {
            Logger.getLogger(Choisir_dest_decoupage.class.getName()).log(Level.SEVERE, null, ex);
        }
        UploadThread uploadThread= new UploadThread(jeton, newpath1, newpath2, fichier_traitement, send, this,serviceGoogle, newpath3, newpath4,googleDirectory,progressBar_resynchro,resultat);
        uploadThread.start();
        
        
        try {
            uploadThread.join();
            if(resultat)
            { //On retire les trois boutons qui ne servent plus à rien
                jpanel_traitement.remove(cut);
                jpanel_traitement.remove(send);
                jpanel_traitement.remove(remove);
                //on réactulise l'affichage
                jpanel_traitement.repaint();
                //on recrée deux nouveaux boutons pour le téléchargement et le post-traitement
                final JButton downloadButton=new JButton("Télécharger");
                downloadButton.setBounds(350, 10, 100, 20);
                downloadButton.setFont(new java.awt.Font("Verdana", 0, 12));
                
                final JButton rebuildButton=new JButton("Reconstruire");
                rebuildButton.setBounds(460, 10, 100, 20);
                rebuildButton.setFont(new java.awt.Font("Verdana", 0, 12));
                rebuildButton.setEnabled(false);
                
                final JButton removeCloudButton=new JButton("Suppression Distante");
                removeCloudButton.setBounds(570, 10, 100, 20);
                removeCloudButton.setFont(new java.awt.Font("Verdana", 0, 12));
                //On ajoute les boutons à l'élément graphique parent
                jpanel_traitement.add(downloadButton);
                jpanel_traitement.add(rebuildButton);
                jpanel_traitement.add(removeCloudButton);
                //On réactualise l'affichage
                jpanel_traitement.doLayout();
                jpanel_traitement.repaint();
                //On supprime les fichiers issus du découpage
                File file1=new File(path1);
                File file2=new File(path2);
                File file3=new File(path3);
                File file4=new File(path4);
                file3.delete();
                file4.delete();
                file1.delete();
                file2.delete();
                
                progressBar_resynchro.dispose();
                //Affichage de la réussite de l'envoi des fichiers sur les 2 clouds
                String message="L'envoi des fichiers s'est déroulé corectement.";
                ReussiteDialog reussiteDialog=new ReussiteDialog(this, true,message);
                reussiteDialog.setVisible(true);
                //Définition de l'évènement issu du clic effectué sur le bouton Télécharger
                
                downloadButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        downloadButtonActionPerformed(evt,downloadButton,rebuildButton,fichier_traitement);
                        
                    };
                });
                
                rebuildButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        rebuildButtonActionPerformed(evt,rebuildButton,fichier_traitement);
                        
                    };
                    
                    
                });
                
                removeCloudButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        removeCloudButtonActionPerformed(evt,fichier_traitement,jpanel_traitement);
                        
                    };
                    
                    
                    
                });
            }
            
            
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Interface_principale.class.getName()).log(Level.SEVERE, null, ex);
            
        }
    }
    
    public void setResultat(boolean resultat) {
        this.resultat = resultat;
    }
    //Méthode effectuant l'action issus d'un clic sur le bouton Télécharger
    private void downloadButtonActionPerformed(ActionEvent evt, JButton downloadButton, JButton rebuildButton, Fichier fichier) {
        //Ouverture d'une fenêtre de séléction du dossier de téléchargement
        Choisir_dest_download choisir_dest_download=new Choisir_dest_download(downloadButton,rebuildButton,fichier,this,jeton,serviceGoogle,password,googleDirectory);
        choisir_dest_download.setVisible(true);
        this.setEnabled(false);
    }
    //Méthode effectuant l'action issus d'un clic sur le bouton Reconstruire
    private void rebuildButtonActionPerformed(ActionEvent evt, JButton rebuildButton, Fichier fichier) {
        //Ouverture d'une fenêtre de séléction du dossier de reconstruction
        Rebuild_Action rebuild_Action=new Rebuild_Action(this, pathDownload, rebuildButton, fichier);
        this.setEnabled(false);
        rebuild_Action.rebuid();
        String message="La reconstruction s'est bien déroulée.";
        NewReussiteDialog newReussiteDialog=new NewReussiteDialog(this, true, pathDownload, this,message);
        newReussiteDialog.setVisible(true);
        
    }
    //Méthode effectuant l'action issus d'un clic sur le bouton Suppresion distante
    private void removeCloudButtonActionPerformed(ActionEvent evt,Fichier fichier,JPanel jPanel2) {
        //Ouverture d'une fenêtre demandant l'accord de suppression
        String message="Vous êtes sur le point de supprimer les fichiers présents sur les clouds!!!";
        Remove_adv remove_adv=new Remove_adv(this, true, fichier, jPanel2,jPanel1,jeton,serviceGoogle,googleDirectory);
        remove_adv.setjLabel2text(message);
        remove_adv.setVisible(true);
    }
    //méthode effectuant le traitement de la liste issus de la synchronisation avec les clouds
    public void traitement_vecteur()
    {
        //Test si il y des fichiers présents sur les clouds
        if (listeFichier.isEmpty())
        {
            String message="Il n'y a pas de fichiers présents sur les Clouds.";
            ReussiteDialog reussiteDialog = new ReussiteDialog(this, true,message);
            
            reussiteDialog.setVisible(true);
        }
        else
        {
            Iterator<Fichier> it=listeFichier.iterator();
            
            //Parcours de la liste
            while(it.hasNext())
            {
                Fichier fichier=it.next();
                //Appel de la méthode pour afficher les fichiers présents sur les clouds
                affichage_demarrage(fichier);
            }
        }
    }
    // méthode permettant l'affichage des fichiers
    public void affichage_demarrage(final Fichier fichier)
    {
        String image;
        String filename=fichier.getName();
        
        String[] search_extension=filename.split("\\.");
        //Choix des icones en fonctions des extensions des fichiers
        switch(search_extension[search_extension.length-1])
        {
            case "txt" :
                image="/images/doc.png";
                break;
            case "doc" :
                image="/images/doc.png";
                break;
            case "xls" :
                image="/images/excel.png";
                break;
            case "exe" :
                image="/images/executable.png";
                break;
            case "jpg" :
                image="/images/jpg.png";
                break;
            case "jpeg" :
                image="/images/jpg.png";
                break;
            case "pdf" :
                image="/images/pdf.png";
                break;
            case "ppt" :
                image="/images/powerpoint.png";
                break;
            case "rar" :
                image="/images/rar.png";
                break;
            case "avi" :
                image="/images/video.png";
                break;
            case "wmv" :
                image="/images/video.png";
                break;
            case "mp4" :
                image="/images/video.png";
                break;
            case "mkv" :
                image="/images/executable.png";
                break;
            default :
                image="/images/file.png";
                break;
                
        }
        //Création du Jpanel et de tous les composants nécessaires pour le traitement des fichiers
        final JPanel jPanel2=new JPanel();
        
        jPanel2.setBounds(12, 0, 780, 30);
        
        JLabel jlabel1=new JLabel(fichier.getName(),new javax.swing.ImageIcon(getClass().getResource(image)),JLabel.CENTER );
        jlabel1.setToolTipText(fichier.getName());
        jlabel1.setBounds(0, 10, 320, 20);
        jlabel1.setFont(new java.awt.Font("Verdana", 1, 12));
        
        
        jPanel1.add(jPanel2);
        jPanel2.add(jlabel1);
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        GridLayout gridLayout= new GridLayout(1,4);
        jPanel2.setLayout(gridLayout);
        
        
        final JButton downloadButton=new JButton("Télécharger");
        downloadButton.setBounds(350, 10, 100, 20);
        downloadButton.setFont(new java.awt.Font("Verdana", 0, 12));
        
        final JButton rebuildButton=new JButton("Reconstruire");
        rebuildButton.setBounds(460, 10, 100, 20);
        rebuildButton.setFont(new java.awt.Font("Verdana", 0, 12));
        rebuildButton.setEnabled(false);
        
        final JButton removeCloudButton=new JButton("Suppression Distante");
        removeCloudButton.setBounds(570, 10, 100, 20);
        removeCloudButton.setFont(new java.awt.Font("Verdana", 0, 12));
        //On ajoute les boutons à l'élément graphique parent
        jPanel2.add(downloadButton);
        jPanel2.add(rebuildButton);
        jPanel2.add(removeCloudButton);
        //On réactualise l'affichage
        jPanel2.doLayout();
        jPanel2.repaint();
        
        downloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadButtonActionPerformed(evt,downloadButton,rebuildButton,fichier);
                
            };
        });
        
        rebuildButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rebuildButtonActionPerformed(evt,rebuildButton,fichier);
                
            };
            
            
        });
        
        removeCloudButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeCloudButtonActionPerformed(evt,fichier,jPanel2);
                
            };
            
            
            
        });
        
        
        
    }
    
    
    public void setFichier(final Fichier fichier_choisir)
    {
        
        //Création du Jpanel et de tous les composants nécessaires pour le traitement des fichiers
        final JPanel jPanel2=new JPanel();
        jPanel2.setBounds(12, 0, 780, 30);
        
        
        
        
        JLabel jlabel1=new JLabel(fichier_choisir.getName(), fichier_choisir.getIcone(),JLabel.CENTER );
        jlabel1.setBounds(0, 10, 320, 20);
        jlabel1.setFont(new java.awt.Font("Verdana", 1, 12));
        jlabel1.setToolTipText(fichier_choisir.getName());
        final JButton cutButton=new JButton("Découper");
        cutButton.setFont(new java.awt.Font("Verdana", 0, 12));
        cutButton.setBounds(350, 10, 100, 20);
        
        final JButton sendButton= new JButton("Envoyer");
        sendButton.setBounds(460, 10, 100, 20);
        sendButton.setFont(new java.awt.Font("Verdana", 0, 12));
        sendButton.setEnabled(false);
        
        final JButton removeLocalButton=new JButton("Suppression Locale");
        removeLocalButton.setBounds(570, 10, 100, 20);
        removeLocalButton.setFont(new java.awt.Font("Verdana", 0, 12));
        
        
        jPanel1.add(jPanel2);
        jPanel2.add(jlabel1);
        
        
        
        jPanel2.add(cutButton);
        jPanel2.add(sendButton);
        
        jPanel2.add(removeLocalButton);
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        GridLayout gridLayout= new GridLayout(1,4);
        jPanel2.setLayout(gridLayout);
        
        jPanel2.repaint(0, 0, 780, 40);
        
        
        
        
        cutButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cutButtonActionPerformed(evt,fichier_choisir,cutButton,sendButton);
                
            };
        });
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                
                sendButtonActionPerformed(evt,sendButton,fichier_choisir,jPanel2,cutButton,removeLocalButton);
                
            };
        });
        removeLocalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPanel1.remove(jPanel2);
                jPanel1.doLayout();
                jPanel1.repaint();
                String cutpath1=fichier_choisir.getPath1();
                String cutpath2=fichier_choisir.getPath2();
                String cutpath3=fichier_choisir.getPath3();
                String cutpath4=fichier_choisir.getPath4();
                if(cutpath1!="")
                {
                    File file1=new File(cutpath1);
                    File file2=new File(cutpath2);
                    File file3=new File(cutpath3);
                    File file4=new File(cutpath4);
                    file1.delete();
                    file2.delete();
                    file3.delete();
                    file4.delete();
                    
                }
                
            };
            
            
        });
        
    }
    //méthode premetant l'affichage des erreurs
    public void afficher_Erreur(String message)
    {
        ExceptionDialog exceptionDialog=new ExceptionDialog(this, true, message);
        exceptionDialog.setVisible(true);
    }
    
    
    
    
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
    
    
    
    
    
}
