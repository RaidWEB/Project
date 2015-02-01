package projet;

import java.io.FileOutputStream;
import java.io.IOException;

import java.util.Arrays;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.ParentReference;
/**
 *
 * @author Munoz and Porret
 */

public class GoogleFunction {
    private static final String CLIENT_ID = "613762133068-ts4b4j45upg0b6i4prck8ctrfrabc1si.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "oLZg-9VnQmMSfv3gBs8-NXo4";
    
    private static final String REDIRECT_URI = "urn:ietf:wg:oauth:2.0:oob";
    
    public static GoogleInfo  authGoogle()  {
        HttpTransport httpTransport = new NetHttpTransport();
        //System.out.println(httpTransport);
        JsonFactory jsonFactory = new JacksonFactory();
        // System.out.println(jsonFactory);
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE))
                .setAccessType("online")
                .setApprovalPrompt("auto").build();
        
        String url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
        BrowserControl.displayURL(url);
        GoogleInfo googleInfo=new GoogleInfo(httpTransport, jsonFactory, flow);
        return googleInfo;
    }
    
    
    public static Drive getDriveGoogle(GoogleInfo googleInfo,String code) throws IOException{
        HttpTransport httpTransport=googleInfo.getHttpTransport();
        GoogleAuthorizationCodeFlow flow=googleInfo.getFlow();
        JsonFactory jsonFactory=googleInfo.getJsonFactory();
        
        GoogleTokenResponse response = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
        GoogleCredential credential = new GoogleCredential().setFromTokenResponse(response);
        
        //Create a new authorized API client
        Drive service = new Drive.Builder(httpTransport, jsonFactory, credential).build();
        return service;
    }
    
    
    
    
    
    
    
    
    public static String[] upload( Drive d,String s,String s1,String parentId) throws IOException {
        
        String[] googleList =new String[4];
        File body = new File();
        File body1 =new File();
        String[] str=s.split("/");
        String str1=str[str.length -1 ];
        
        
        String[] str2=s1.split("/");
        String str3=str2[str2.length -1];
        
        body.setTitle(str1);
        body1.setTitle(str3);
        body1.setMimeType("text/plain");
        body.setMimeType("text/plain");
        
        java.io.File fileContent = new java.io.File(s);
        java.io.File fileContent1 = new java.io.File(s1);
        
        // Set the parent folder.
        if (parentId != null && parentId.length() > 0) {
            body.setParents(
                    Arrays.asList(new ParentReference().setId(parentId)));
            body1.setParents(
                    Arrays.asList(new ParentReference().setId(parentId)));
        }
        
        FileContent mediaContent = new FileContent("text/plain", fileContent);
        FileContent mediaContent1 = new FileContent("text/plain", fileContent1);
        
        File file = d.files().insert(body, mediaContent).execute();
        File file1 = d.files().insert(body1, mediaContent1).execute();
        String file1Id=file1.getId();
        String fileId=file.getId();
        String fileDownloadUrl=file.getDownloadUrl();
        String file1DownloadUrl=file1.getDownloadUrl();
        googleList[0]=fileId;
        googleList[1]=fileDownloadUrl;
        googleList[2]=file1Id;
        googleList[3]=file1DownloadUrl;
        
        return googleList;
        
    }
    private static HttpResponse downloadFile(Drive service, File file,Interface_principale interface_principale)throws IOException{
        if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0) {
            HttpResponse resp =service.getRequestFactory().buildGetRequest(new GenericUrl(file.getDownloadUrl())).execute();
            return resp;
        }
        else {
            String message="Le fichier n'existe pas sur GoogleDrive.";
            ExceptionDialog exceptionDialog=new ExceptionDialog(interface_principale, true, message);// The file doesn't have any content stored on Drive.
            return null;
        }
    }
    public static void download(Drive d,String downloadUrl,String path ,String filename,Interface_principale interface_principale) throws IOException {
        
        File f= new File();
        f.setTitle(filename);
        f.setDownloadUrl(downloadUrl);
        HttpResponse resp= downloadFile(d,f,interface_principale);
        java.io.File ff = new java.io.File(path+f.getTitle());
        FileOutputStream fout = new FileOutputStream(ff);
        
        resp.download(fout);
        
        fout.close();
    }
    
    public static void deleteFile(Drive service, String fileId) {
        try {
            service.files().delete(fileId).execute();
        } catch (IOException e) {
            System.out.println("An error occurred: " + e);
        }
    }
    
    
  
    
    
}

