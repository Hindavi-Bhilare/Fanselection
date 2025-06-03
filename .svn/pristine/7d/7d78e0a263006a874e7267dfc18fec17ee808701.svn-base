package com.velotech.fanselection.utils;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class SendZeptoMailVelotech {
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
            conn.setRequestProperty("Authorization", "Zoho-enczapikey wSsVR61zrBDzBqd7yGGrJL9tngtXAlyjHEgujVTy6CD/S6vKoMdulhHPAgXxG6cZGWQ4F2cW9rghyx4GhDBYj9t5ygkGDiiF9mqRe1U4J3x17qnvhDzIVmtckhCLK4MKxwVummdkE80q+g==");
            JSONObject object = new JSONObject("{\n" + "\"bounce_address\":\"bounce@bounce.pumpsearch.com\",\n" + "\"from\": { \"address\": \"noreply@pumpsearch.com\"},\n" + "\"to\": [{\"email_address\": {\"address\": \"datta@velotechsolutions.com\",\"name\": \"Pranav\"}}],\n" + "\"subject\":\"Test Email\",\n" + "\"htmlbody\":\"<div><b> Test email sent successfully.  </b></div>\"\n" + "}");
            OutputStream os = conn.getOutputStream();
            os.write(object.toString().getBytes());
            os.flush();
            br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            System.out.println(sb.toString());
        } catch (Exception e) {
        	 System.out.println(e.getMessage());
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
              //  e.printStackTrace();
                System.out.println(e.getMessage());

            }
        }
    }

}