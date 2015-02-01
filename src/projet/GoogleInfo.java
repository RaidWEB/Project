/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/

package projet;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;

/**
 *
 * @author Munoz and Porret
 */
public class GoogleInfo {
    final private HttpTransport httpTransport;
    final private JsonFactory jsonFactory;
    final private GoogleAuthorizationCodeFlow flow;
    
    public GoogleInfo(HttpTransport httpTransport,JsonFactory jsonFactory,GoogleAuthorizationCodeFlow flow)
    {
        this.httpTransport=httpTransport;
        this.jsonFactory=jsonFactory;
        this.flow=flow;
    }
    
    public HttpTransport getHttpTransport() {
        return httpTransport;
    }
    
    public JsonFactory getJsonFactory() {
        return jsonFactory;
    }
    
    public GoogleAuthorizationCodeFlow getFlow() {
        return flow;
    }
    
    
    
}
