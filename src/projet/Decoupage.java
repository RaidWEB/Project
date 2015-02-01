package projet;

import java.io.*;

public class Decoupage {
    public static long nbre_Octets_Traites=0;
 /**
 *
 * @author Munoz and Porret
 */
    public static void cut (String src, String dst, String dst1, String dst2, String dst3,ProgressBar progressBar) throws IOException,FileNotFoundException {
        final int MAX =3;
        
        
        
        //FileInputStream(ouvre unfichier en lecture et en binaire),BufferedInputStream(permet de bufferiser l'acces au fichier,flux d'entree)
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
        //FileOutputStream(ouvre unfichier en ecriture),BufferedOutputStream(permet de bufferiser l'acces au fichier,flux de sortit)
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dst));
        //ouvre  des fichiers en ecriture
        BufferedOutputStream bos1 = new BufferedOutputStream(new FileOutputStream(dst1));
        BufferedOutputStream bos2 = new BufferedOutputStream(new FileOutputStream(dst2));
        BufferedOutputStream bos3 = new BufferedOutputStream(new FileOutputStream(dst3));
        
        
        
        byte buffer [] = new byte[MAX];
        boolean fini = false;
        while(!fini) {
            int nbeLus = bis.read(buffer, 0,buffer.length);//remplit un tableau d'octet et retourne le nombre d'octet lus
            
            if(nbeLus != -1) {
                
                nbre_Octets_Traites+=nbeLus;
                progressBar.setProgressbar(nbre_Octets_Traites);
                
                
                
                //correspond aux octets 0,3,6.... du fichier de base
                byte b=buffer[0];
                //correspond au octet 1,4,7...   du fichier de base
                byte bb=buffer[1];
                //correspond au octet 2,5,8...   du fichier de base
                byte bbb=buffer[2];
                //Tant que nbeLus est un  multiple de 3(du buffer)
                if (nbeLus ==3){
                    
                    
                    
                    
                    // Recupere les 3 premiers bit du premiere octet dans b1
                    byte b1= (byte) ((b)& 0xE0);
                    // recupere les 3 premiers bit(+decalage de 3 a droite) du deuxieme octet dans b2
                    byte b2=(byte) ((bb>>>3) & 0x1C);
                    // recupere les 3 premiers(+decalage de 6 a droite) du troisieme octet b3
                    byte b3=(byte) ((bbb>>>6) & 0x03);
                    //fait un "ou" des 3 byte recuperer(b1,b2,b3) dans bn qui est notre nouveau premier octet former.
                    byte bn=(byte) (b1|b2|b3);
                    
                    //Recupere les bit 4-6(+decalge de 3 a gauche) du premiere octet dans b4
                    byte b4=(byte) ((b<<3) & 0xE0);
                    //Recupere les bit 4-6 du 2ieme octet dans b5
                    byte b5=(byte) ((bb) & 0x18);
                    //Recupere les bit 4-6(+decalage de 3 a droite) du 3ieme octet dans b6
                    byte b6=(byte) ((bbb>>>3) & 0x07);
                    //fait un "ou" des 3 byte recuperer(b4,b5,b6) dans bn qui est notre nouveau deuxieme octet former.
                    byte bn1= (byte) (b4|b5|b6);
                    
                    //Recupere les bit 7-8(+decalge de 6 a gauche) du premiere octet dans b7
                    byte b7=(byte) ((b<<6) & 0xC0);
                    //Recupere les bit 7-8(+decalage de 3 a gauche du 2ieme octet dans b8
                    byte b8=(byte) ((bb<<3) & 0x38);
                    //Recupere les bit 7-8 du 3ieme octet dans b9
                    byte b9=(byte) ((bbb) & 0x07);
                    //fait un "ou" des 3 byte recuperer(b4,b5,b6) dans bn qui est notre nouveau troisieme octet former.
                    byte bn2= (byte) (b7|b8|b9);
                    
                    // fait un ou exclusif des 3 byte bn,bn1,bn2 qui forme l'octet de recuperation du raid 5
                    byte bn3=(byte) (bn^bn1^bn2);
                    //ecrit le nouveau premier octet(le 4,le 7...) depuis le tableau d'octet bn
                    bos.write(bn);
                    //ecrit le nouveau deuxieme octet(le 5,le 8...)depuis le tableau d'octet bn1
                    bos1.write(bn1);
                    //ecrit le nouveau troisieme octet(le 6,le 9...) depuis le tableau d'octet bn2
                    bos2.write(bn2);
                    //ecrit l'octet resulant du ou exclusif dans le quatrieme fichier(
                    //fichier permettant de reconstruire le fichier de base si un des fichier est perdu
                    bos3.write(bn3);
                    
                    
                }
                //quand nbe lus ==2 c'est a dire qu'il doit faire un bourrage d'un octet
                else if (nbeLus == 2)
                {
                    // Recupere les 3 premiers bit du premiere octet dans b1
                    byte b1= (byte) ((b)& 0xE0);
                    // recupere les 3 premiers bit(+decalage de 3 a droite) du deuxieme octet dans b2
                    byte b2=(byte) ((bb>>>3) & 0x1C);
                    // recupere les 3 premiers(+decalage de 6 a droite) du troisieme octet b3
                    byte b3=(byte) (bbb & 0x00);
                    //fait un "ou" des 3 byte recuperer(b1,b2,b3) dans bn qui est notre nouveau premier octet former.
                    byte bn=(byte) (b1|b2|b3);
                    
                    //Recupere les bit 4-6(+decalge de 3 a gauche) du premiere octet dans b4
                    byte b4=(byte) ((b<<3) & 0xE0);
                    //Recupere les bit 4-6 du 2ieme octet dans b5
                    byte b5=(byte) ((bb) & 0x18);
                    //Recupere les bit 4-6(+decalage de 3 a droite) du 3ieme octet dans b6
                    byte b6=(byte) (bbb & 0x00);
                    //fait un "ou" des 3 byte recuperer(b4,b5,b6) dans bn qui est notre nouveau deuxieme octet former.
                    byte bn1= (byte) (b4|b5|b6);
                    //Recupere les bit 7-8(+decalge de 6 a gauche) du premiere octet dans b7
                    byte b7=(byte) ((b<<6) & 0xC0);
                    //Recupere les bit 7-8(+decalage de 3 a gauche du 2ieme octet dans b8
                    byte b8=(byte) ((bb<<3) & 0x38);
                    //Recupere les bit 7-8 du 3ieme octet dans b9
                    byte b9=(byte) (bbb & 0x00);
                    //fait un "ou"  des 3 byte recuperer(b7,b8,b9) dans bn qui est notre nouveau troisieme octet former.
                    byte bn2= (byte) (b7|b8|b9);
                    
                    // fait un ou exclusif des 3 byte bn,bn1,bn2 qui forme l'octet de recuperation du raid 5
                    byte bn3=(byte) (bn^bn1^bn2);
                    //ecrit le nouveau premier octet(le 4,le 7...)
                    bos.write(bn);
                    //ecrit le nouveau deuxieme octet(le 5,le 8...)
                    bos1.write(bn1);
                    //ecrit le nouveau troisieme octet(le 6,le 9...)
                    bos2.write(bn2);
                    //ecrit l'octet resulant du ou exclusif dans le quatireme fichier
                    //(fichier permettant de reconstruire le fichier de base si un des fichier est perdu)
                    bos3.write(bn3);
                    
                }
                // quand nbe lus ==1 c'est a dire qu'il doit faire un bourrage de 2 octet
                else if(nbeLus==1){
                    // Recupere les 3 premiers bit du premiere octet dans b1
                    byte b1= (byte) ((b)& 0xE0);
                    // recupere les 3 premiers bit(+decalage de 3 a droite) du deuxieme octet dans b2
                    byte b2=(byte) (bb & 0x00);
                    // recupere les 3 premiers(+decalage de 6 a droite) du troisieme octet b3
                    byte b3=(byte) (bbb & 0x00);
                    //fait un "ou" des 3 byte recuperer(b1,b2,b3) dans bn qui est notre nouveau premier octet former.
                    byte bn=(byte) (b1|b2|b3);
                    
                    //Recupere les bit 4-6(+decalge de 3 a gauche) du premiere octet dans b4
                    byte b4=(byte) ((b<<3) & 0xE0);
                    //Recupere les bit 4-6 du 2ieme octet dans b5
                    byte b5=(byte) (bb & 0x00);
                    //Recupere les bit 4-6(+decalage de 3 a droite) du 3ieme octet dans b6
                    byte b6=(byte) (bbb & 0x00);
                    //fait un "ou"  des 3 byte recuperer(b4,b5,b6) dans bn qui est notre nouveau deuxieme octet former.
                    byte bn1= (byte) (b4|b5|b6);
                    //Recupere les bit 7-8(+decalge de 6 a gauche) du premiere octet dans b7
                    byte b7=(byte) ((b<<6) & 0xC0);
                    //Recupere les bit 7-8(+decalage de 3 a gauche du 2ieme octet dans b8
                    byte b8=(byte) (bb & 0x00);
                    //Recupere les bit 7-8 du 3ieme octet dans b9
                    byte b9=(byte) (bbb & 0x00);
                    //fait un "ou" des 3 byte recuperer(b4,b5,b6) dans bn qui est notre nouveau troisieme octet former.
                    byte bn2= (byte) (b7|b8|b9);
                    
                    // fait un ou exclusif des 3 byte bn,bn1,bn2 qui forme l'octet de recuperation du raid 5
                    byte bn3=(byte) (bn^bn1^bn2);
                    //ecrit le nouveau premier octet(le 4,le 7...) dans le premier fichier
                    bos.write(bn);
                    //ecrit le nouveau deuxieme octet(le 5,le 8...)dans le deuxieme fichier
                    bos1.write(bn1);
                    //ecrit le nouveau troisieme octet(le 6,le 9...)dans le troisieme fichier
                    bos2.write(bn2);
                    //ecrit l'octet resulant du ou exclusif dans le quatireme fichier
                    //(fichier permettant de reconstruire le fichier de base si un des fichier est perdu)
                    bos3.write(bn3);
                }
            }else { fini = true; }
        }
        //permet de fermer les flux
        bis.close() ;
        bos.close() ;
        bos1.close();
        bos2.close();
        bos3.close();
        
        
        
    }
}