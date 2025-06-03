package com.velotech.fanselection.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;


public class EncodeFileToBase64Binary {

	public static void main(String[] args) {
		try {
		

		        // Read all bytes from a file and convert to Base64 String
		        byte[] byteData = Files.readAllBytes(Paths.get("C:\\Users\\Velotech-PC1\\Desktop\\weltechissue.PNG"));
		        String base64String = Base64.getEncoder().encodeToString(byteData);
		        System.out.println(base64String);
		} catch (Exception e) {
			e.printStackTrace();
		}
	

	}
	

}
