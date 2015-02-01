/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package projet;

import java.util.Random;

/**
 *
 * @author munozer
 */
public class Salt {
    

static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ&é'(-_à@€azertyuiopqsdfghjklmwxcvbn,;:!?./§ù%*µ$£¨^";
static Random rnd = new Random();

public static String randomString( int len ) 
{
   StringBuilder sb = new StringBuilder( len );
   for( int i = 0; i < len; i++ ) 
      sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
   return sb.toString();
}

public static void main(String[] args)
{
    String s = Salt.randomString(25);
    System.out.println(s);
}
    
}
