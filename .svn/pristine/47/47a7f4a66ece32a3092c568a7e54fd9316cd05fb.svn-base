package com.velotech.fanselection.utils;

import java.io.BufferedReader;

import java.io.InputStreamReader;

import java.io.OutputStream;

import java.net.HttpURLConnection;

import java.net.URL;

 

import org.json.JSONObject;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

 

public class Testtransmail {

    public static void main(String[] args) {
    	String[] a=new String[] {"datta@velotechsolutions.com", "pranav@velotechsolutions.com"};
    	System.out.println(a.toString());
    	JsonArray ans = new JsonArray();
    	for (String string : a) {
			JsonObject email_address = new JsonObject();
			JsonObject email_address1 = new JsonObject();
			email_address.addProperty("address", string);
			email_address1.add("email_address", email_address);
			ans.add(email_address1);
		}
    	System.out.println(ans);
    	
    	ans = new JsonArray();
    	JsonObject email_address = new JsonObject();
		JsonObject email_address1 = new JsonObject();
		email_address.addProperty("address", "pranav@velotechsolutions.com");
		email_address1.add("email_address", email_address);
		ans.add(email_address1);
		System.out.println(ans);
		
    }

 

}