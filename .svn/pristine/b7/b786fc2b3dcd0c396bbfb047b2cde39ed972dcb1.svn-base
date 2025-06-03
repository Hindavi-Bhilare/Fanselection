package com.velotech.fanselection.utils;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class JavaSendapi {
    public static void main(String[] args) throws Exception {
        String postUrl = "https://api.zeptomail.com/v1.1/email";
        BufferedReader br = null;
        HttpURLConnection conn = null;
        String output = null;
        StringBuffer sb = new StringBuffer();
        try {
            URL url = new URL(postUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Authorization", "Zoho-enczapikey PHtE6r0NS+/siDYqpkQD5PDpFJT1MYgr+Lg1LAhE5N4WCfQETk1Qrt0jxDTmrUooUaZKFqScyNo75+nOsriBJD25ZmpLVGqyqK3sx/VYSPOZsbq6x00ZtVkec0DaXYPoetFr0SzVudvbNA==");
            JSONObject object = new JSONObject("{\n" + "\"bounce_address\":\"bounce@bounce.pumpsearch.com\",\n" + "\"from\": { \"address\": \"noreply@ksbindia.co.in\"},\n" + "\"to\": [{\"email_address\": {\"address\": \"datta@velotechsolutions.com\",\"name\": \"Pranav\"}}],\n" + "\"subject\":\"Test Email\",\n" + "\"htmlbody\":\"<div><b> Test email sent successfully.  </b></div>\"\n" + "}");
            OutputStream os = conn.getOutputStream();
            os.write(object.toString().getBytes());
            os.flush();
            br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            System.out.println(sb.toString());
        } catch (Exception e) {
            br = new BufferedReader(new InputStreamReader((conn.getErrorStream())));
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            System.out.println(sb.toString());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.disconnect();
                }
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

}