package com.example.istvancsenkey_sinko.nadacovision.connector.server;

import android.util.Log;

import org.apache.http.HttpException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by istvancsenkey-sinko on 17/05/15.
 */
public class SendPost implements Runnable {

    public static final String UTF8 = "UTF-8";
    private String postRequest;
    private String requestURL;
    private String response = null;

    public SendPost(String requestURL, String postRequest) {
        this.postRequest = postRequest;
        this.requestURL = requestURL;
    }

    @Override
    public void run() {
        URL url;
        try {
            url = new URL(requestURL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("content-type", "application/json; charset=utf-8");


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, UTF8));


            Log.d("POST SENDING", postRequest);

            writer.write(postRequest);

            writer.flush();
            writer.close();
            os.close();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                response = "";
                throw new HttpException(responseCode + "");
            }
        } catch (Exception e) {
            Log.e("POST ERROR", e.getMessage());
        }
    }

    public String getResponse() {
        return response;
    }
}
