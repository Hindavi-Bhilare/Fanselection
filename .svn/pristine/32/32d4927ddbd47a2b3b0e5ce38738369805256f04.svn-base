
package com.velotech.fanselection.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

import javax.mail.internet.MimeMessage;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.velotech.fanselection.propertybean.MailProperties;

@Component
public class MailUtility {

	static Logger log = LogManager.getLogger(MailUtility.class.getName());

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private MailProperties mailProperties;

	public void sendMail(String emailTo, String subject, String body) {

		try {

			MimeMessage msg = javaMailSender.createMimeMessage();

			// true = multipart message
			MimeMessageHelper helper = new MimeMessageHelper(msg, true);
			helper.setTo(emailTo);
			helper.setFrom(mailProperties.getFrom());
			helper.setSubject(subject);
			helper.setText(body + getSignature(), true);

			javaMailSender.send(msg);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	public void sendEmailByTransMail(String emailTo, String subject, String body) {
		try {
			JsonObject object = configureJsonObject(subject, body);
			setTo(emailTo, object, "to");
			sendEmailByTransMail(object);
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public boolean sendEmailByTransMailAttachment(String[] emailTo, String subject, String body, String[] cc,
			String[] bcc, String attachedFilePath, String fileName) {
		boolean isSendEmail = false;
		try {
			JsonObject object = configureJsonObject(subject, body);
			setTo(emailTo, object, "to");
			setTo(cc, object, "cc");
			setTo(bcc, object, "bcc");
			setAttachemnt(attachedFilePath, fileName, object);
			sendEmailByTransMail(object);
			isSendEmail = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isSendEmail;
	}

	private void setAttachemnt(String attachedFilePath, String fileName, JsonObject object) {
		try {

			System.out.println("attachedFilePath path:-" + attachedFilePath);
			byte[] byteData = Files.readAllBytes(Paths.get(attachedFilePath));
			String base64String = Base64.getEncoder().encodeToString(byteData);
			JsonArray attachmentArray = new JsonArray();
			JsonObject o = new JsonObject();
			o.addProperty("content", base64String);
			o.addProperty("mime_type", "image/jpg");
			o.addProperty("name", fileName);
			attachmentArray.add(o);
			object.add("attachments", attachmentArray);

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	public boolean sendMailWithAttachment(String[] emailTo, String subject, String body, String[] cc, String[] bcc,
			String attachedFilePath, String fileName) {

		boolean isSendEmail = false;
		try {

			MimeMessage msg = javaMailSender.createMimeMessage();

			// true = multipart message
			MimeMessageHelper helper = new MimeMessageHelper(msg, true);
			helper.setTo(emailTo);
			helper.setFrom(mailProperties.getFrom());
			helper.setSubject(subject);
			helper.setText(body + getSignature(), true);
			helper.addAttachment(fileName, new File(attachedFilePath));

			byte[] byteData = Files.readAllBytes(Paths.get("D:\\SimpleSolution\\qrcode.png"));
			String base64String = Base64.getEncoder().encodeToString(byteData);

			if (cc != null)
				helper.setCc(cc);
			if (bcc != null)
				helper.setBcc(bcc);

			javaMailSender.send(msg);
			isSendEmail = true;

		} catch (Exception e) {
			isSendEmail = false;
			log.error(e.getMessage(), e);
		}
		return isSendEmail;
	}

	public String getSignature() {

		String signatureText = "<br><br><font color='#808080'>Please do not reply to this message, this email was sent from an unmonitored mailbox."
				+ "<br><br>Velotech Solution<br>Mumbai-Pune Road, Pimpri, Pune - 411 018. INDIA.";
		return signatureText;
	}

	private void setTo(String email, JsonObject object, String type) {

		try {
			JsonArray toArray = new JsonArray();
			JsonObject email_address = new JsonObject();
			JsonObject email_address1 = new JsonObject();
			email_address.addProperty("address", email);
			email_address1.add("email_address", email_address);
			toArray.add(email_address1);
			object.add(type, toArray);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private void setTo(String[] email, JsonObject object, String type) {

		try {
			JsonArray toArray = new JsonArray();
			if (email != null)
				for (String string : email) {
					JsonObject email_address = new JsonObject();
					JsonObject email_address1 = new JsonObject();
					email_address.addProperty("address", string);
					email_address1.add("email_address", email_address);
					toArray.add(email_address1);
				}
			if (toArray.size() > 0)
				object.add(type, toArray);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	private HttpURLConnection configureConnection() {
		HttpURLConnection conn = null;
		try {
			URL url = new URL(mailProperties.getPostUrl());
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestProperty("Accept", "application/json");
			conn.setRequestProperty("Authorization", mailProperties.getAuthorization());

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return conn;
	}

	private JsonObject configureJsonObject(String subject, String body) {
		JsonObject ans = new JsonObject();
		try {
			ans.addProperty("bounce_address", mailProperties.getBounce_address());
			JsonObject fromObject = new JsonObject();
			fromObject.addProperty("address", mailProperties.getAddress());
			ans.add("from", fromObject);
			ans.addProperty("subject", subject);
			ans.addProperty("htmlbody", body + getSignature());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return ans;
	}

	private void sendEmailByTransMail(JsonObject object) throws Exception {
		BufferedReader br = null;
		HttpURLConnection conn = null;
		try {
			conn = configureConnection();

			OutputStream os = conn.getOutputStream();
			os.write(object.toString().getBytes());
			os.flush();
			br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			String output = null;
			StringBuffer sb = new StringBuffer();
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			System.out.println(sb.toString());

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			br.close();
			conn.disconnect();
		}
	}
}
