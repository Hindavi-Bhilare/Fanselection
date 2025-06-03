
package com.velotech.fanselection.admin.service;

import java.io.IOException;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velotech.fanselection.admin.dao.ResetPasswordDao;
import com.velotech.fanselection.dtos.ResetPasswordDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.models.Tbl52Usermaster;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.MailUtility;
import com.velotech.fanselection.utils.PasswordUtils;
import com.velotech.fanselection.utils.ResourceUtil;
import com.velotech.fanselection.utils.StringEncryptor;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class ResetPasswordServiceImpl implements ResetPasswordService {

	static Logger log = LogManager.getLogger(ForgotPasswordServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private ResetPasswordDao dao;

	@Autowired
	private MailUtility mailUtility;

	@Autowired
	private VelotechUtil velo;
	
	@Autowired
	private PasswordUtils passwordUtils;

	@Override
	public ApplicationResponse resetUserPassword(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			ResetPasswordDto dto = new ResetPasswordDto();
			ObjectMapper mapper = new ObjectMapper();
			dto = mapper.readValue(requestPayload, ResetPasswordDto.class);

			StringEncryptor stringEncrypter = new StringEncryptor(new ResourceUtil().getPropertyValues("encryptionScheme"),
					new ResourceUtil().getPropertyValues("encryptionKey"));

			Tbl52Usermaster tbl52Usermaster = (Tbl52Usermaster) genericDao.getRecordById(Tbl52Usermaster.class, velo.getLoginId());

			if (tbl52Usermaster != null) {
				if (stringEncrypter.decrypt(tbl52Usermaster.getPassword()).equals(dto.getOldPassword())) {
					tbl52Usermaster.setPassword(stringEncrypter.encrypt(dto.getNewPassword()));
					if (genericDao.update(tbl52Usermaster)) {

						String bodyText = "<font color='#034782'><i>" + "Dear User,<br> <br>"
								+ "As per your request password for Pump Selection Software has been changed successfully<br><br>Your new password is "
								+ stringEncrypter.decrypt(tbl52Usermaster.getPassword())
								+ "<br><br>Best regards,<br>System Administrator<br>Velotech Solutions – Submersible Borehole Pump Selection Software<br><br>";
						String signatureText = "<font color='#808080'>Please do not reply to this message, this email was sent from an unmonitored mailbox."
								+ "<br><br>Velotech Solutions<br>Mumbai-Pune Road, Pimpri, Pune - 411 018. INDIA.";
						// helper.addAttachment("my_photo.png", new
						// ClassPathResource("android.png"));
						// tbl52Usermaster.getEmail()
						mailUtility.sendEmailByTransMail(tbl52Usermaster.getEmail(),
								"Change of password for your login id for Velotech Solutions - Submersible Selection Software", bodyText + signatureText);

						applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, "Successfully Reset Password.");
					} else
						applicationResponse = ApplicationResponseUtil.getResponseToGetData(false, "Failed Reset Password");

				} else
					applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, "Old Password Is Wrong .");
			} else
				applicationResponse = ApplicationResponseUtil.getResponseToGetData(false, "Failed Reset Password");

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	// @Scheduled(cron = "0 0 12 1 * ?") // for 1st of Every month
	// @Scheduled(cron = "0 * * ? * * ") // for every minute
	//@Scheduled(cron = "0 0 1 1 * ?") // At 01:00:00am, on the 1st day, every
										// month
	public void changePasswordUsingCronExpression() {

		try {
			StringEncryptor stringEncrypter = new StringEncryptor(new ResourceUtil().getPropertyValues("encryptionScheme"),
					new ResourceUtil().getPropertyValues("encryptionKey"));
			List<Tbl52Usermaster> tbl52UsermasterList = dao.getUserMasters();
			for (Tbl52Usermaster tbl52Usermaster : tbl52UsermasterList) {
				//tbl52Usermaster.setPassword(stringEncrypter.encrypt(RandomStringUtils.randomAlphanumeric(5)));
				   //String regex = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%=:\\?]).{12,})";
					 
				    // execute
				    String thePassword = passwordUtils.generatePassword();
				    System.out.println("thePassword:-"+thePassword);
				    tbl52Usermaster.setPassword(stringEncrypter.encrypt(thePassword));        
				if (dao.updateUserMaster(tbl52Usermaster)) {
					String bodyText = "<font color='#034782'><i>" + "Dear User," + "<br><br>"
							+ "<br>	You are receiving this email as you are a registered user of Velotech Solutions – Submersible Selection Software.For security reasons, all user passwords are reset monthly basis.  Accordingly, your password has also been reset.<br>"
							+ "Your new password is " + stringEncrypter.decrypt(tbl52Usermaster.getPassword())
							+ "<br>	<br>Please logon to Velotech Solutions Submersible Selection Software with above password and then you may change the password again to your choice.<br><br>"
							+ "Thank you for your understanding and cooperation.<br><br><br>"
							+ "Best regards,<br>System Administrator<br>Velotech Solutions – Submersible Borehole Pump Selection Software<br><br>";
					String signatureText = "<font color='#808080'>Please do not reply to this message, this email was sent from an unmonitored mailbox."
							+ "<br><br>Velotech Solutions<br>Mumbai-Pune Road, Pimpri, Pune - 411 018. INDIA.";

					mailUtility.sendEmailByTransMail(tbl52Usermaster.getEmail(), "Reset Password", bodyText + signatureText);
					System.out.println("Mail Send Successfull");
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}
}
