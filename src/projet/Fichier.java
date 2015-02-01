/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package projet;

import javax.swing.Icon;

/**
 *
 * @author Munoz and Porret
 */
public class Fichier {
    
    private String absolute_path;//chemin absolu du fichier en local
    private String name;//Nom du fichier et son extension si existe
    private Icon icone; //icone du fichier
    private Long length;//taille du fichier
    private String cutFilePath1="";//chemin absolu des fichiers obtenus lors du découpage
    private String cutFilePath2="";
    private String cutFilePath3="";
    private String cutFilePath4="";
    
    private String dropboxId1;//IDs et nom du fichier en même temps des fichiers sur dropbox
    private String dropboxId2;
    
    private String googleId_File1;//IDs des fichiers sur GOOGLE DRIVE
    private String googleId_File2;
    
    private String googleUrl_File1;//URLs de download
    private String googleUrl_File2;
    
    private String googleFilename1;//Noms des fichiers sur google
    private String googleFilename2;
    
    private String downloadDropbox_Path1,downloadDropbox_Path2,downloadGoogle_Path3,downloadGoogle_Path4;//chemins absolus des fichiers téléchargés depuis les clouds
    
    
    
    
    public Fichier(String chemin, String nom,Icon icone,Long taillefich)
    {
        this.absolute_path=chemin;
        this.name=nom;
        this.icone=icone;
        this.length=taillefich;
        
    }
    public Fichier(String name)
    {
        
        this.name=name;
      
    }
    public Fichier(String name,String dropboxId1,String dropboxId2,String googleUrl_File1,String googleUrl_File2,String googleId_File1,String googleId_File2,String googleFilename1,String googleFilename2 )
    {
        this.name=name;
        this.dropboxId1=dropboxId1;
        this.dropboxId2=dropboxId2;
        this.googleUrl_File1=googleUrl_File1;
        this.googleUrl_File2=googleUrl_File2;
        this.googleFilename1=googleFilename1;
        this.googleFilename2=googleFilename2;
        this.googleId_File1=googleId_File1;
        this.googleId_File2=googleId_File2;
    }
    
    public Fichier(String downloadDropbox_Path1,String downloadDropbox_Path2,String downloadGoogle_Path3, String downloadGoogle_Path4)
    {
        this.downloadDropbox_Path1=downloadDropbox_Path1;
        this.downloadDropbox_Path2=downloadDropbox_Path2;
        this.downloadGoogle_Path3=downloadGoogle_Path3;
        this.downloadGoogle_Path4=downloadGoogle_Path4;
    }
      public Fichier(String name,String googleId1,String googleId2,String dropboxId, String dropboxId2)
    {
         this.name=name;
         this.googleId_File1=googleId1;
         this.googleId_File2=googleId1;
         this.dropboxId1=dropboxId;
         this.dropboxId2=dropboxId2;
    }
    public Long getTaille()
    {
        
        return length;
    }
    
    public Icon getIcone()
    {
        
        return icone;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    
    
    public String getChemin()
    {
        return absolute_path;
    }
    
    public void setCutfilePath(String path1,String path2,String path3,String path4)
    {
        this.cutFilePath1=path1;
        this.cutFilePath2=path2;
        this.cutFilePath3=path3;
        this.cutFilePath4=path4;
    }
    
    public String getPath1()
    {
        return cutFilePath1;
    }
    
    public String getPath2()
    {
        return cutFilePath2;
    }
    
    public String getPath3()
    {
        return cutFilePath3;
    }
    
    public String getPath4()
    {
        return cutFilePath4;
    }
    
    public void setDropboxId1(String dropboxId1) {
        this.dropboxId1 = dropboxId1;
    }
    
    public void setDropboxId2(String dropboxId2) {
        this.dropboxId2 = dropboxId2;
    }
    
    public void setGoogleId_File1(String GoogleId1) {
        this.googleId_File1 = GoogleId1;
    }
    
    public void setGoogleId_File2(String GoogleId2) {
        this.googleId_File2 = GoogleId2;
    }
    
    public String getDropboxId1() {
        return dropboxId1;
    }
    
    public String getDropboxId2() {
        return dropboxId2;
    }
    
    public String getGoogleId_File1() {
        return googleId_File1;
    }
    
    public String getGoogleId_File2() {
        return googleId_File2;
    }
    
    public String getDownloadDropbox_Path1() {
        return downloadDropbox_Path1;
    }
    
    public void setDownloadDropbox_Path1(String downloadDropbox_Path1) {
        this.downloadDropbox_Path1 = downloadDropbox_Path1;
    }
    
    public String getDownloadDropbox_Path2() {
        return downloadDropbox_Path2;
    }
    
    public void setDownloadDropbox_Path2(String downloadDropbox_Path2) {
        this.downloadDropbox_Path2 = downloadDropbox_Path2;
    }
    
    public String getDownloadGoogle_Path3() {
        return downloadGoogle_Path3;
    }
    
    public void setDownloadGoogle_Path3(String downloadGoogle_Path3) {
        this.downloadGoogle_Path3 = downloadGoogle_Path3;
    }
    
    public String getDownloadGoogle_Path4() {
        return downloadGoogle_Path4;
    }
    
    public void setDownloadGoogle_Path4(String downloadGoogle_Path4) {
        this.downloadGoogle_Path4 = downloadGoogle_Path4;
    }
    
    
    
    public String getGoogleUrl_File1() {
        return googleUrl_File1;
    }
    
    public void setGoogleUrl_File1(String googleUrl_File1) {
        this.googleUrl_File1 = googleUrl_File1;
    }
    
    public String getGoogleUrl_File2() {
        return googleUrl_File2;
    }
    
    public void setGoogleUrl_File2(String googleUrl_File2) {
        this.googleUrl_File2 = googleUrl_File2;
    }
    
    public String getGoogleFilename1() {
        return googleFilename1;
    }
    
    public void setGoogleFilename1(String googleFilename1) {
        this.googleFilename1 = googleFilename1;
    }
    
    public String getGoogleFilename2() {
        return googleFilename2;
    }
    
    public void setGoogleFilename2(String googleFilename2) {
        this.googleFilename2 = googleFilename2;
    }
    
    
}
