package com.velotech.fanselection.velotech;

import java.io.IOException;

import com.velotech.fanselection.utils.ResourceUtil;
import com.velotech.fanselection.utils.StringEncryptor;

public class PasswordCheck {

	public static void main(String[] args) throws IOException {
		String password="velotech";
		
		StringEncryptor stringEncrypter = new StringEncryptor(new ResourceUtil().getPropertyValues("encryptionScheme"),
				new ResourceUtil().getPropertyValues("encryptionKey"));
		String encryptedPassword = stringEncrypter.encrypt(password);
		System.out.println(encryptedPassword);

	}

}
