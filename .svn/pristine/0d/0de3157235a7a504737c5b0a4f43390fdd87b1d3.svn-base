
package com.velotech.fanselection.admin.service;

import java.io.IOException;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velotech.fanselection.admin.dao.ForgotPasswordDao;
import com.velotech.fanselection.dtos.ForgotPasswordDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.models.Tbl52Usermaster;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.MailUtility;
import com.velotech.fanselection.utils.PasswordUtils;
import com.velotech.fanselection.utils.ResourceUtil;
import com.velotech.fanselection.utils.StringEncryptor;

@Service
@Transactional
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

	static Logger log = LogManager.getLogger(ForgotPasswordServiceImpl.class.getName());

	@Autowired
	private ForgotPasswordDao dao;

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private MailUtility mailUtility;
	
	@Autowired
	private PasswordUtils passwordUtils;

	@Override
	public ApplicationResponse getRecords(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			ForgotPasswordDto dto = new ForgotPasswordDto();
			ObjectMapper mapper = new ObjectMapper();
			dto = mapper.readValue(requestPayload, ForgotPasswordDto.class);

			StringEncryptor stringEncrypter = new StringEncryptor(new ResourceUtil().getPropertyValues("encryptionScheme"),
					new ResourceUtil().getPropertyValues("encryptionKey"));

			Tbl52Usermaster tbl52Usermaster = (Tbl52Usermaster) genericDao.getRecordById(Tbl52Usermaster.class, dto.getLoginId());
			if (tbl52Usermaster != null) {
				//tbl52Usermaster.setPassword(stringEncrypter.encrypt(RandomStringUtils.randomAlphanumeric(10)));
				  String thePassword = passwordUtils.generatePassword();
				 /// System.out.println("thePassword:-"+thePassword);
				  System.out.println("stringEncrypter.encrypt(thePassword):-"+stringEncrypter.encrypt(thePassword));
				 
				  tbl52Usermaster.setPassword(stringEncrypter.encrypt(thePassword));        
				if (genericDao.update(tbl52Usermaster)) {
					
					 System.out.println("stringEncrypter.decrypt(tbl52Usermaster.getPassword()):-"+stringEncrypter.decrypt(tbl52Usermaster.getPassword()));
					String bodyText = "<font color='#034782'><i>" + "Dear User,<br><br>"
							+ "As per your request the new password for Pump Selection Software is mentioned below." + "<br>Login Id :"
							+ tbl52Usermaster.getLoginId() + "<br>Password :" + stringEncrypter.decrypt(tbl52Usermaster.getPassword())
							+ "<br>		Please login with the same and change the password."
							+ "<br><br>Best regards,<br>System Administrator<br>Velotech Solutions – Submersible Borehole Pump Selection Software<br>";

					// helper.addAttachment("my_photo.png", new
					// ClassPathResource("android.png"));
					// tbl52Usermaster.getEmail()
					mailUtility.sendEmailByTransMail(tbl52Usermaster.getEmail(), "Forgot Password", bodyText);

					applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, "Password Set Successfully..Check Your Mail");
				} else
					applicationResponse = ApplicationResponseUtil.getResponseToGetData(false, "Forgot Password Failed..");
			} else
				applicationResponse = ApplicationResponseUtil.getResponseToGetData(false, "Invalid Username");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;

	}

}
