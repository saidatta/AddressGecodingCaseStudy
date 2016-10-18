package com.adbobe.app.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.*;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.Collections;
import java.util.List;

/**
 * Created by venkatamunnangi on 10/5/16.
 */
@Service
public class GoogleCloudAuthenticationService {


    private static final List<String> SCOPES = Collections.singletonList(ServiceConstants.STORAGE_SCOPE);

    @Value("${google.cliendId}")
    private String googleClientId;

    @Value("${google.clientSecret}")
    private String googleClientSecret;

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    public static GoogleTokenResponse getTokenResponse(String authCode) throws GeneralSecurityException {
        GoogleTokenResponse tokenResponse = null;
        try {
            tokenResponse=
                    new GoogleAuthorizationCodeTokenRequest(
                            GoogleNetHttpTransport.newTrustedTransport(),
                            JacksonFactory.getDefaultInstance(),
                            "https://www.googleapis.com/oauth2/v4/token",
                            "1011086681758-dpv7fk7jva7fd7r0vl972e3tevtrvos4.apps.googleusercontent.com",
                            "EZST1J_ySOSn4NOso9ZiDXSx",
                            authCode,
                            "")
                            .execute();
        } catch (IOException e) {
            System.err.println("TokenUtil:: invalid grant, bad request");
            return null;
        }
        return tokenResponse;
    }

    public GoogleCredential getCredentials() throws Exception {
        JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();

        GoogleCredential credential = new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(JSON_FACTORY)
                .setServiceAccountId(ServiceConstants.CLIENT_ID)
                .setServiceAccountPrivateKey(loadKeyFromPkcs12("/Users/venkatamunnangi/Programming/AddressGeocoding/AdobeCaseStudy-6f1c68eac6aa.p12",  "notasecret".toCharArray()))
                .setServiceAccountScopes(SCOPES).build();
        return credential;
    }

    /**
     * Creates an authorized Credential object.
     * @return an authorized Credential object.
     * @throws IOException
     */
    public static Credential authorize() throws IOException, GeneralSecurityException {
        JacksonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
        HttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        // Load client secrets.
        InputStream in = GoogleCloudAuthenticationService.class.getResourceAsStream("/client_secret.json");
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow =
                new GoogleAuthorizationCodeFlow.Builder(
                        HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                        //.setDataStoreFactory(DATA_STORE_FACTORY)
                        .setAccessType("offline")
                        .build();
        Credential credential =  null;//new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
        //Credential credential = flow.createAndStoreCredential(response, userId);
        //System.out.println("Credentials saved to " + DATA_STORE_DIR.getAbsolutePath());
        return credential;
    }

    private static PrivateKey loadKeyFromPkcs12(String filename, char[] password) throws Exception {
        FileInputStream fis = new FileInputStream(filename);
        KeyStore ks = KeyStore.getInstance("PKCS12");
        ks.load(fis, password);
        return (PrivateKey) ks.getKey("privatekey", password);
    }

    private String p12FileToRead() {
        return "/Users/venkatamunnangi/Programming/AddressGeocoding/src/main/resources/data/key.pem";
    }

    private void readFile(File file) {
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        DataInputStream dis = null;

        try {
            fis = new FileInputStream(file);

            // Here BufferedInputStream is added for fast reading.
            bis = new BufferedInputStream(fis);
            dis = new DataInputStream(bis);

            // dis.available() returns 0 if the file does not have more lines.
            while (dis.available() != 0) {

                // this statement reads the line from the file and print it to
                // the console.
                System.out.println(dis.readLine());
            }

            // dispose all the resources after using them.
            fis.close();
            bis.close();
            dis.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

