package projet;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
public class Reconstruction {
    
 /**
 *
 * @author Munoz and Porret
 * * Cette oeuvre, création, site ou texte est sous licence Creative Commons Attribution -
* Pas d’Utilisation Commerciale 4.0 International.
* Pour accéder à une copie de cette licence, merci de vous rendre à l'adresse suivante http://creativecommons.org/licenses/by-nc/4.0/ ou envoyez un courrier à Creative Commons, 444 Castro Street, Suite 900, Mountain View, California, 94041, USA.
 */
    public static void reconstruire (String src, String src1, String src2, String dst,JButton rebuildButton,Interface_principale interface_principale,ProgressBar_resynchro progressBar_resynchro) {
        final int MAX =3;
        int taille=7131;
        
        int taille1=taille%3;
        
        try {
            
            //FileInputStream(ouvre unfichier en lecture),BufferedInputStream(permet de bufferiser l'acces au fichier,flux d'entree)
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
            //ouvre unfichier en lecture
            BufferedInputStream bis1 = new BufferedInputStream(new FileInputStream(src1));
            //ouvre unfichier en lecture
            BufferedInputStream bis2 = new BufferedInputStream(new FileInputStream(src2));
            //BufferedInputStream bis3 = new BufferedInputStream(new FileInputStream(src3));//ouvre unfichier en lecture
            //FileOutputStream(ouvre unfichier en ecriture),BufferedOutputStream(permet de bufferiser l'acces au fichier,flux de sortie)
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dst));
            
            
            byte buffer [] = new byte[MAX];
            byte buffer1 [] = new byte[MAX];
            byte buffer2 [] = new byte[MAX];
            boolean fini = false;
            
            while(!fini) {
                //remplit un tableau d'octet et retourne le nombre d'octet lus
                int nbeLus = bis.read(buffer, 0,buffer.length);
                //remplit un tableau d'octet et retourne le nombre d'octet lus
                int nbeLus1 = bis1.read(buffer1, 0,buffer1.length);
                //remplit un tableau d'octet et retourne le nombre d'octet lus
                int nbeLus2 = bis2.read(buffer2, 0,buffer2.length);
                
                
                
                
                
                if(nbeLus != -1) {
                    if(nbeLus==3){
                        for (int j = 0; j < MAX; j++) {
                            //correspond  octets 0.... du fichier src
                            byte a=buffer[j];
                            //correspond  octet 0   du fichier src1
                            byte aa=buffer1[j];
                            //correspond  octet 0   du fichier src2
                            byte aaa=buffer2[j];
                            // Recupere les 3 premiers bit du premiere octet dans b1
                            byte a1= (byte) ((a)& 0xE0);
                            // recupere les 3 premiers bit(+decalage de 3 a droite) du deuxieme octet dans b2
                            byte a2=(byte) ((aa>>>3) & 0x1C);
                            // recupere les 3 premiers(+decalage de 6 a droite) du troisieme octet b3
                            byte a3=(byte) ((aaa>>>6) & 0x03);
                            //reconstruit le premiere octet du fichier de base
                            byte an=(byte) (a1|a2|a3);
                            
                            
                            //Recupere les bit 4-6(+decalge de 3 a gauche) du premiere octet dans b4
                            byte a4=(byte) ((a<<3) & 0xE0);
                            //Recupere les bit 4-6 du 2ieme octet dans b5
                            byte a5=(byte) ((aa) & 0x18);
                            //Recupere les bit 4-6(+decalage de 3 a droite) du 3ieme octet dans b6
                            byte a6=(byte) ((aaa>>>3) & 0x07);
                            byte an1= (byte) (a4|a5|a6);
                            
                            
                            //Recupere les bit 7-8(+decalge de 6 a gauche) du premiere octet dans b7
                            byte a7=(byte) ((a<<6) & 0xC0);
                            //Recupere les bit 7-8(+decalage de 3 a gauche du 2ieme octet dans b8
                            byte a8=(byte) ((aa<<3) & 0x38);
                            //Recupere les bit 7-8 du 3ieme octet dans b9
                            byte a9=(byte) ((aaa) & 0x07);
                            //reconstruit le troisieme octet du fichier de base
                            byte an2= (byte) (a7|a8|a9);
                            
                            bos.write(an);
                            bos.write(an1);
                            bos.write(an2);
                        }
                    }
                    
                    
                    
                    else if(nbeLus==1){
                        if(taille1==0){
                            
                            byte a=buffer[0];//correspond  octets 0.... du fichier src
                            byte aa=buffer1[0];//correspond  octet 0   du fichier src1
                            byte aaa=buffer2[0];//correspond  octet 0   du fichier src2
                            
                            byte a1= (byte) ((a)& 0xE0);// Recupere les 3 premiers bit du premiere octet dans b1
                            byte a2=(byte) ((aa>>>3) & 0x1C);// recupere les 3 premiers bit(+decalage de 3 a droite) du deuxieme octet dans b2
                            byte a3=(byte) ((aaa>>>6) & 0x03);// recupere les 3 premiers(+decalage de 6 a droite) du troisieme octet b3
                            byte an=(byte) (a1|a2|a3);//reconstruit le premiere octet du fichier de base
                            
                            
                            
                            byte a4=(byte) ((a<<3) & 0xE0);//Recupere les bit 4-6(+decalge de 3 a gauche) du premiere octet dans b4
                            byte a5=(byte) ((aa) & 0x18);//Recupere les bit 4-6 du 2ieme octet dans b5
                            byte a6=(byte) ((aaa>>>3) & 0x07);//Recupere les bit 4-6(+decalage de 3 a droite) du 3ieme octet dans b6
                            byte an1= (byte) (a4|a5|a6);
                            
                            
                            
                            byte a7=(byte) ((a<<6) & 0xC0);//Recupere les bit 7-8(+decalge de 6 a gauche) du premiere octet dans b7
                            byte a8=(byte) ((aa<<3) & 0x38);//Recupere les bit 7-8(+decalage de 3 a gauche du 2ieme octet dans b8
                            byte a9=(byte) ((aaa) & 0x07);//Recupere les bit 7-8 du 3ieme octet dans b9
                            byte an2= (byte) (a7|a8|a9);////reconstruit le troisieme octet du fichier de base
                            
                            bos.write(an);
                            bos.write(an1);//reconstruit le deuxieme octet du fichier de base
                            bos.write(an2);
                        }
                        
                        else if(taille1==1){
                            byte a=buffer[0];//correspond  octets 0.... du fichier src
                            byte aa=buffer1[0];//correspond  octet 0   du fichier src1
                            byte aaa=buffer2[0];//correspond  octet 0   du fichier src2
                            
                            byte a1= (byte) ((a)& 0xE0);// Recupere les 3 premiers bit du premiere octet dans b1
                            byte a2=(byte) ((aa>>>3) & 0x1C);// recupere les 3 premiers bit(+decalage de 3 a droite) du deuxieme octet dans b2
                            byte a3=(byte) ((aaa>>>6) & 0x03);// recupere les 3 premiers(+decalage de 6 a droite) du troisieme octet b3
                            byte an=(byte) (a1|a2|a3);//reconstruit le premiere octet du fichier de base
                            
                            
                            
                            
                            
                            bos.write(an);
                            
                            
                            
                        }
                        else if(taille1==2){
                            
                            
                            byte a=buffer[0];//correspond  octets 0.... du fichier src
                            byte aa=buffer1[0];//correspond  octet 0   du fichier src1
                            byte aaa=buffer2[0];//correspond  octet 0   du fichier src2
                            
                            byte a1= (byte) ((a)& 0xE0);// Recupere les 3 premiers bit du premiere octet dans b1
                            byte a2=(byte) ((aa>>>3) & 0x1C);// recupere les 3 premiers bit(+decalage de 3 a droite) du deuxieme octet dans b2
                            byte a3=(byte) ((aaa>>>6) & 0x03);// recupere les 3 premiers(+decalage de 6 a droite) du troisieme octet b3
                            byte an=(byte) (a1|a2|a3);//reconstruit le premiere octet du fichier de base
                            
                            
                            
                            byte a4=(byte) ((a<<3) & 0xE0);//Recupere les bit 4-6(+decalge de 3 a gauche) du premiere octet dans b4
                            byte a5=(byte) ((aa) & 0x18);//Recupere les bit 4-6 du 2ieme octet dans b5
                            byte a6=(byte) ((aaa>>>3) & 0x07);//Recupere les bit 4-6(+decalage de 3 a droite) du 3ieme octet dans b6
                            byte an1= (byte) (a4|a5|a6);
                            
                            
                            
                            
                            
                            bos.write(an);
                            bos.write(an1);//reconstruit le deuxieme octet du fichier de base
                            
                        }
                    }
                    else if(nbeLus==2){
                        
                        byte a=buffer[0];//correspond  octets 0.... du fichier src
                        byte aa=buffer1[0];//correspond  octet 0   du fichier src1
                        byte aaa=buffer2[0];//correspond  octet 0   du fichier src2
                        
                        byte a1= (byte) ((a)& 0xE0);// Recupere les 3 premiers bit du premiere octet dans b1
                        byte a2=(byte) ((aa>>>3) & 0x1C);// recupere les 3 premiers bit(+decalage de 3 a droite) du deuxieme octet dans b2
                        byte a3=(byte) ((aaa>>>6) & 0x03);// recupere les 3 premiers(+decalage de 6 a droite) du troisieme octet b3
                        byte an=(byte) (a1|a2|a3);//reconstruit le premiere octet du fichier de base
                        
                        
                        
                        byte a4=(byte) ((a<<3) & 0xE0);//Recupere les bit 4-6(+decalge de 3 a gauche) du premiere octet dans b4
                        byte a5=(byte) ((aa) & 0x18);//Recupere les bit 4-6 du 2ieme octet dans b5
                        byte a6=(byte) ((aaa>>>3) & 0x07);//Recupere les bit 4-6(+decalage de 3 a droite) du 3ieme octet dans b6
                        byte an1= (byte) (a4|a5|a6);
                        
                        
                        
                        byte a7=(byte) ((a<<6) & 0xC0);//Recupere les bit 7-8(+decalge de 6 a gauche) du premiere octet dans b7
                        byte a8=(byte) ((aa<<3) & 0x38);//Recupere les bit 7-8(+decalage de 3 a gauche du 2ieme octet dans b8
                        byte a9=(byte) ((aaa) & 0x07);//Recupere les bit 7-8 du 3ieme octet dans b9
                        byte an2= (byte) (a7|a8|a9);////reconstruit le troisieme octet du fichier de base
                        
                        bos.write(an);
                        bos.write(an1);
                        bos.write(an2);
                        if(taille1==0){
                            byte b=buffer[1];//correspond  octets 0.... du fichier src
                            byte bb=buffer1[1];//correspond  octet 0   du fichier src1
                            byte bbb=buffer2[1];//correspond  octet 0   du fichier src2
                            
                            byte b1= (byte) ((b)& 0xE0);// Recupere les 3 premiers bit du premiere octet dans b1
                            byte b2=(byte) ((bb>>>3) & 0x1C);// recupere les 3 premiers bit(+decalage de 3 a droite) du deuxieme octet dans b2
                            byte b3=(byte) ((bbb>>>6) & 0x03);// recupere les 3 premiers(+decalage de 6 a droite) du troisieme octet b3
                            byte bn=(byte) (b1|b2|b3);//reconstruit le premiere octet du fichier de base
                            
                            
                            
                            byte b4=(byte) ((b<<3) & 0xE0);//Recupere les bit 4-6(+decalge de 3 a gauche) du premiere octet dans b4
                            byte b5=(byte) ((bb) & 0x18);//Recupere les bit 4-6 du 2ieme octet dans b5
                            byte b6=(byte) ((bbb>>>3) & 0x07);//Recupere les bit 4-6(+decalage de 3 a droite) du 3ieme octet dans b6
                            byte bn1= (byte) (b4|b5|b6);
                            
                            
                            
                            byte b7=(byte) ((b<<6) & 0xC0);//Recupere les bit 7-8(+decalge de 6 a gauche) du premiere octet dans b7
                            byte b8=(byte) ((bb<<3) & 0x38);//Recupere les bit 7-8(+decalage de 3 a gauche du 2ieme octet dans b8
                            byte b9=(byte) ((bbb) & 0x07);//Recupere les bit 7-8 du 3ieme octet dans b9
                            byte bn2= (byte) (b7|b8|b9);////reconstruit le troisieme octet du fichier de base
                            
                            bos.write(bn);
                            bos.write(bn1);
                            bos.write(bn2);
                        }
                        if(taille1==1){
                            byte b=buffer[1];//correspond  octets 0.... du fichier src
                            byte bb=buffer1[1];//correspond  octet 0   du fichier src1
                            byte bbb=buffer2[1];//correspond  octet 0   du fichier src2
                            
                            byte b1= (byte) ((b)& 0xE0);// Recupere les 3 premiers bit du premiere octet dans b1
                            byte b2=(byte) ((bb>>>3) & 0x1C);// recupere les 3 premiers bit(+decalage de 3 a droite) du deuxieme octet dans b2
                            byte b3=(byte) ((bbb>>>6) & 0x03);// recupere les 3 premiers(+decalage de 6 a droite) du troisieme octet b3
                            byte bn=(byte) (b1|b2|b3);//reconstruit le premiere octet du fichier de base
                            
                            
                            
                            
                            
                            bos.write(bn);
                            
                            
                            
                        }
                        else if(taille1==2){
                            
                            
                            byte b=buffer[1];//correspond  octets 0.... du fichier src
                            byte bb=buffer1[1];//correspond  octet 0   du fichier src1
                            byte bbb=buffer2[1];//correspond  octet 0   du fichier src2
                            
                            byte b1= (byte) ((b)& 0xE0);// Recupere les 3 premiers bit du premiere octet dans b1
                            byte b2=(byte) ((bb>>>3) & 0x1C);// recupere les 3 premiers bit(+decalage de 3 a droite) du deuxieme octet dans b2
                            byte b3=(byte) ((bbb>>>6) & 0x03);// recupere les 3 premiers(+decalage de 6 a droite) du troisieme octet b3
                            byte bn=(byte) (b1|b2|b3);//reconstruit le premiere octet du fichier de base
                            
                            
                            
                            byte b4=(byte) ((b<<3) & 0xE0);//Recupere les bit 4-6(+decalge de 3 a gauche) du premiere octet dans b4
                            byte b5=(byte) ((bb) & 0x18);//Recupere les bit 4-6 du 2ieme octet dans b5
                            byte b6=(byte) ((bbb>>>3) & 0x07);//Recupere les bit 4-6(+decalage de 3 a droite) du 3ieme octet dans b6
                            byte bn1= (byte) (b4|b5|b6);
                            
                            
                            
                            
                            bos.write(bn);
                            bos.write(bn1);//reconstruit le deuxieme octet du fichier de base
                            
                            
                        }
                    }
                    
                }
                else { fini = true; }
            }
            bis.close() ;//permet de fermer le flux
            bos.close() ;//permet de fermer le flux
            bis1.close();//permet de fermer le flux
            bis2.close();//permet de fermer le flux
            
            System.out.println("\nCopie terminee");
            
        }
        
        
        
        
        catch (Exception e) {
          
            rebuildButton.setEnabled(true);
            progressBar_resynchro.dispose();
            String message="L'application n'a pas le droit d'écriture pour la destination sélectionnée.";
            interface_principale.afficher_Erreur(message);
            
        }
      
        
    }
    
    
}

