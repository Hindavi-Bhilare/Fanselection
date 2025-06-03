package com.velotech.fanselection.utils;

import java.util.Properties;


import javax.mail.Message;

import javax.mail.MessagingException;

import javax.mail.Session;

import javax.mail.Transport;

import javax.mail.internet.InternetAddress;

import javax.mail.internet.MimeMessage;

public class SendZeptoMail {

	public static void main(String[] args) throws Exception {

		Properties properties = System.getProperties();

		properties.setProperty("mail.smtp.host", "smtp.zeptomail.in");

		properties.put("mail.smtp.port", "587");

		properties.put("mail.smtp.auth", "true");

		properties.put("mail.smtp.starttls.enable", "true");

		properties.put("mail.smtp.from", "fromaddress");

		properties.put("mail.smtp.ssl.protocols", "SSLv2Hello  SSLv3  TLSv1  TLSv1.1 TLSv1.2");
		
		 properties.put("mail.smtp.socketFactory.port", "587");
	        properties.put("mail.smtp.socketFactory.class", "javax.net.SocketFactory");
	        properties.put("mail.smtp.ssl.enable", "false");
	        properties.put("mail.smtp.ssl.trust", "smtp.zeptomail.com");

		Session session = Session.getDefaultInstance(properties);

		try {

			MimeMessage message = new MimeMessage(session);

			message.setFrom(new InternetAddress("noreply@ksbindia.co.in"));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress("datta.katore@gmail.com"));

			message.setSubject("Test Email");

			message.setText("Test email sent successfully.");

			Transport transport = session.getTransport("smtp");

			transport.connect("smtp.zeptomail.com", 587, "emailapikey",
					"PHtE6r0NS+/siDYqpkQD5PDpFJT1MYgr+Lg1LAhE5N4WCfQETk1Qrt0jxDTmrUooUaZKFqScyNo75+nOsriBJD25ZmpLVGqyqK3sx/VYSPOZsbq6x00ZtVkec0DaXYPoetFr0SzVudvbNA==");

			transport.sendMessage(message, message.getAllRecipients());

			transport.close();

			System.out.println("Mail successfully sent");

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.print(ex.getMessage());

		}
	}

}
