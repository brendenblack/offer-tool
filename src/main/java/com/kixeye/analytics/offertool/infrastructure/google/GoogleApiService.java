package com.kixeye.analytics.offertool.infrastructure.google;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;


@Service
public class GoogleApiService
{
    private final Logger log = LoggerFactory.getLogger(GoogleApiService.class);
    private static final String APPLICATION_NAME = "Offer Tool";
    private static final String CREDENTIALS_FOLDER = "credentials"; // Directory to store user credentials.

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved credentials/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS_READONLY);
    private static final String CLIENT_SECRET_DIR = "client_secret.json";

    private final Sheets sheetService;
    private final Drive driveService;

    GoogleApiService() throws GeneralSecurityException, IOException
    {
        log.debug("Initializing GoogleApiService");
        final JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        NetHttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();



        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(
                jsonFactory,
                new FileReader(ResourceUtils.getFile("classpath:" + CLIENT_SECRET_DIR))
        );
        // log.debug("Client secrets: {}", clientSecrets.toPrettyString());
        log.debug("HTTP Transport: {}", httpTransport.toString());
        log.debug("JSON Factory: {}", jsonFactory.toString());
        log.debug("Scopes: {}", SCOPES);

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(CREDENTIALS_FOLDER)))
                .setAccessType("offline")
                .build();

        Credential credential = getCredentials(httpTransport, jsonFactory);

        this.sheetService = new Sheets.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName(APPLICATION_NAME)
                //.setSheetsRequestInitializer(SheetsRequestInitializer(""))
                .build();

        this.driveService = new Drive.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();

        log.debug("Finished initializing GoogleApiService");
    }


    public Sheets getSheets()
    {
        return this.sheetService;
    }

    public Drive getDrive()
    {
        return this.driveService;
    }



    public Sheets getService() throws IOException, GeneralSecurityException
    {
        final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        InputStream in = GoogleApiService.class.getResourceAsStream(CLIENT_SECRET_DIR);

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));


        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(CREDENTIALS_FOLDER)))
                .setAccessType("offline")
                .build();



        Credential credential = getCredentials(HTTP_TRANSPORT, JSON_FACTORY);

        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();


        Drive driveService = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
            .setApplicationName(APPLICATION_NAME)
            .build();

        return service;
    }

    public Credential getCredentials(NetHttpTransport httpTransport, JsonFactory jsonFactory) throws IOException
    {
        InputStream in = GoogleApiService.class.getResourceAsStream(CLIENT_SECRET_DIR);

        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(in));

        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(CREDENTIALS_FOLDER)))
                .setAccessType("offline")
                .build();

        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    public void getLastUpdate()
    {

    }



}
