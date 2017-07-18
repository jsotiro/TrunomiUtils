package com.trunomi.samples;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.squareup.okhttp.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.squareup.okhttp.OkHttpClient;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by John Sotiropoulos on 17/07/2017.
 */
public class BatchRevoke {

    static final Logger logger = LoggerFactory.getLogger(BatchRevoke.class);

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    public static final String CONSENT_UPDATE_PAYLOAD =
            " { \"previousLedgerEntryId\": \"%s\", \"payload\": %s}";

    public static final String CONSENT_GRANT = "consent-grant";
    public static final String CONSENT_DENY = "consent-deny";
    public static final String CONSENT_REVOKE = "consent-revoke";
    public static final String CONSENT_MESSAGE = "message";


    private String jWtToken;
    private String authToken;
    private String apiEndpoint;
    final Gson gson = new Gson();

    private int contextsUpdated = 0;


    public String getjWtToken() {
        return jWtToken;
    }

    public void setjWtToken(String jWtToken) {
        this.jWtToken = jWtToken;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getApiEndpoint() {
        return apiEndpoint;
    }

    public void setApiEndpoint(String apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }


    public Gson getGson() {
        return gson;
    }


    public int getContextsUpdated() {
       return contextsUpdated;
    }

    public void incContextsUpdated() {
         contextsUpdated++;
    }


    public static void main(String[] args) {

        String jwtToken = "";
        String changeReason = "System Data Reset";
        String ip = "https://api.trunomi.com";

        if (args.length < 0){
            System.out.println("Missing parameters. run as <RevokeConsents jwtToken, reason(optional) apiEndpoint(optional)'>");
            System.out.println("If no reason is specified '"+changeReason +"' is used");
            System.out.println("If no apiEndpoint is specified https://api.trunomi.com is used");
            System.exit(-1);
        }
        if ( args.length > 0)
            jwtToken=args[0];

        if ( args.length > 1)
            changeReason=args[1];

        if ( args.length > 2)
            ip=args[2];


        BatchRevoke updater = new BatchRevoke();
        updater.setApiEndpoint(ip);
        updater.setjWtToken(jwtToken);

        ArrayList<String> contexts = new ArrayList<>();

        try {
            String token = updater.authToken();
            updater.setAuthToken(token);
            contexts = updater.getAllContexts();
            for (String id : contexts) {
                LedgerEntry entry = updater.getLastLedgerEntryForContext(id);
                if (entry == null) continue;

                String event = entry.getEvent();
                if (event.equals(BatchRevoke.CONSENT_GRANT) || event.equals(BatchRevoke.CONSENT_MESSAGE)) {
                    updater.revokeConsent(entry,changeReason);
                }
            }
            logger.info(updater.getContextsUpdated()  + " contexts revoked");
        }
        catch (Exception e){
            logger.error(e.getLocalizedMessage());
        }
    }


    private void revokeConsent(LedgerEntry entry, String changeReason) throws IOException {
        String perviousId = entry.getId();

        Payload entryPayload = entry.getPayload();
        entryPayload.setCustomData(changeReason);

        entryPayload.setConsentDefinitionName(null);
        entryPayload.setContextName(null);
        entryPayload.setProgram(null);
        entryPayload.setDataTypeId(null);

        String payload= gson.toJson( entryPayload );
        try {
            String reqBody = String.format(CONSENT_UPDATE_PAYLOAD, perviousId, payload);
            RequestBody body = RequestBody.create(JSON, reqBody);

            String reqPath = "/ledger/context/" + entry.getContextId() + "/consent-revoke";
            Response response = postHttpResponse(reqPath, authToken, body);
            contextsUpdated++;
            logger.info(" New Entry Created for:" + perviousId + "Payload returned: " + response.body().string());
        }
        catch (Exception e){
            logger.error("Error when revoking consent for context id " + entry.getContextId() + ". Message: "+e.getLocalizedMessage() );
        }
    }


     Response postHttpResponse( String requestPath, String token, RequestBody body) throws IOException{

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(apiEndpoint +requestPath)
                .post(body)
                .addHeader("Content-Type", " application/json")
                .addHeader("x-trunomi-version", "2017-02-28")
                .addHeader("authorization",  "Bearer " + token)
                .addHeader("cache-control", "no-cache")
                .build();
        String headersStr = request.headers().toString();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        return response;
    }


     Response getHttpResponse(String requestPath, String token) throws IOException{

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url( apiEndpoint+requestPath)
                .get()
                .addHeader("content-type", "application/json")
                .addHeader("x-trunomi-version", "2017-02-28")
                .addHeader("authorization",  "Bearer " + token)
                .addHeader("cache-control", "no-cache")
                .build();
        String headersStr = request.headers().toString();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        return response;
    }


     String authToken() throws IOException{
        RequestBody body = RequestBody.create(JSON, "");
        Response response = postHttpResponse("/auth",jWtToken,body );  //client.newCall(request).execute();
        String msg= response.message();
        String token = response.header("Www-Authenticate");
        if (token.contains(" ")) token = token.split(" ")[1];
        return  token;

    }

  /*   ArrayList<String>getAllLedgerEntries() throws IOException {
        ArrayList<String> TxList  = new ArrayList<>();
        Response response = getHttpResponse("/ledger",authToken);
        JsonArray  tx = gson.fromJson(response.body().charStream(), JsonArray.class);

        for (JsonElement context : tx) {
            String id = context.getAsJsonObject().getAsJsonPrimitive("id").getAsString();
            TxList.add(id);
        }

        return TxList;

    }
*/
     LedgerEntry getLastLedgerEntryForContext(String contextId) {
        try {
            Response response = getHttpResponse("/ledger/context/" + contextId + "/last", authToken);
            LedgerEntry entry = gson.fromJson(response.body().charStream(), LedgerEntry.class);
            logger.info("Last ledger Entry for context id" + contextId + " retrieved. Ledger entry Id: "+entry.getId());
            return entry;
        }
        catch (Exception e){
            logger.error("Error when getting the last ledger entry for " + contextId + ". Message: "+e.getLocalizedMessage());
            return null;
        }
    }



     ArrayList<String> getAllContexts() {
        ArrayList<String> contextsList  = new ArrayList<>();
        try {
            Response response = getHttpResponse("/context", authToken);


            JsonArray contexts = gson.fromJson(response.body().charStream(), JsonArray.class);


            for (JsonElement context : contexts) {
                contextsList.add(context.getAsJsonObject().getAsJsonPrimitive("id").getAsString());
            }
            logger.info(contextsList.size() + "  contexts retrieved");
            return contextsList;

        }
        catch (Exception e){
            logger.info("Error when retrieving contexts. Message: " + e.getLocalizedMessage());
            return  null;
        }

    }

}

