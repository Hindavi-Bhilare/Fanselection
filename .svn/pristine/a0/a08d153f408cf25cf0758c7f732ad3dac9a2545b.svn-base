
package com.velotech.fanselection.admin.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.criterion.Conjunction;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velotech.fanselection.dtos.NewRegistrationDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.models.Tbl52RegistrationTable;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.MailUtility;
import com.velotech.fanselection.utils.RequestWrapper;

@Service
@Transactional
public class NewRegistrationServiceImpl implements NewRegistrationService {

	static Logger log = LogManager.getLogger(NewRegistrationServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private MailUtility mailUtility;

	@Override
	public ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		try {
			Tbl52RegistrationTable model = new Tbl52RegistrationTable();
			NewRegistrationDto dto = new NewRegistrationDto();
			ObjectMapper mapper = new ObjectMapper();
			dto = mapper.readValue(requestPayload, NewRegistrationDto.class);
			BeanUtils.copyProperties(dto, model);
			if (genericDao.save(model)) {

				Properties prop = new Properties();
				String propFileName = "application.properties";

				java.io.InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

				if (inputStream != null) {
					prop.load(inputStream);
				}
				String boudyText = "<font color='#034782'>" + "Dear Admin," + "<br><br>" + "	There is a new request for accessing Software<br>"
						+ "Details are as follows :<br>" + "Name :" + model.getName() + "<br>Email :" + model.getEmailId() + "<br>Organization :"
						+ model.getOrganizationName() + "<br>Contact No :" + model.getContactNo() + "<br><br>This is FYI<br><br><br>";

				String signaturText = "<font color='#808080'>Please do not reply to this message, this email was sent from an unmonitored mailbox.<br><br>"
						+ "Velotech Solutions<br>" + "Mumbai-Pune Road, Pimpri, Pune - 411 018. INDIA.";
				String subjectText = "New User Request";
				String regardText = "<font color='#034782'>Best regards,<br>" + "System Administrator<br>"
						+ "Velotech Solutions – Submersible Borehole Pump Selection Software<br><br>";
				// helper.addAttachment("my_photo.png", new
				// ClassPathResource("android.png"));

				mailUtility.sendEmailByTransMail(prop.getProperty("adminemail"), subjectText, boudyText + regardText + signaturText);

				/////////////////////////////////////////////////////////////////////////////////////////////////
				// Send email to newly added user

				String userBodyText = "<font color='#034782'><i>" + "Dear User," + "<br><br>" + "Thanks for your request to access the Software.<br>"
						+ "Your request has been forwarded.<br>" + "You will be notified once your request has been approved by the Administrator."
						+ "<br><BR>Thank you for your understanding and cooperation.<br><br>";

				// helper.addAttachment("my_photo.png", new
				// ClassPathResource("android.png"));

				mailUtility.sendMail(dto.getEmailId(), subjectText, userBodyText + regardText + signaturText);
				applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, "User registration request has been sent successfully.");
			} else {
				applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, "User registration request failed");
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		try {
			NewRegistrationDto dto = new NewRegistrationDto();
			ObjectMapper mapper = new ObjectMapper();
			dto = mapper.readValue(requestPayload, NewRegistrationDto.class);
			Tbl52RegistrationTable model = (Tbl52RegistrationTable) genericDao.getRecordById(Tbl52RegistrationTable.class, dto.getId());
			BeanUtils.copyProperties(dto, model);
			genericDao.update(model);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.UPDATE_SUCCESS_MSG);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = null;
		genericDao.deleteAll(Tbl52RegistrationTable.class, ids);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<SearchCriteria> searchCriterias = new ArrayList<>();
			if (requestWrapper.getSearchValue() != "") {
				SearchCriteria searchCriteria = new SearchCriteria(requestWrapper.getSearchProperty(), requestWrapper.getSearchValue());
				searchCriterias.add(searchCriteria);
			}

			Conjunction conjunction = GenericUtil.getConjuction(Tbl52RegistrationTable.class, searchCriterias);
			applicationResponse = genericDao.getRecords(Tbl52RegistrationTable.class, conjunction, requestWrapper.getPagination());

			List<Tbl52RegistrationTable> models = (List<Tbl52RegistrationTable>) applicationResponse.getData();
			long total = applicationResponse.getTotal();

			List<NewRegistrationDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private List<NewRegistrationDto> getData(List<Tbl52RegistrationTable> models) {

		List<NewRegistrationDto> dtos = new ArrayList<>();
		for (Tbl52RegistrationTable model : models) {
			NewRegistrationDto dto = new NewRegistrationDto();
			BeanUtils.copyProperties(model, dto);
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public void downloadExcel(RequestWrapper requestWrapper) {

		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + "Application Master" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

			List<SearchCriteria> criterias = new ArrayList<>();
			if (requestWrapper.getSearchValue() != null) {
				SearchCriteria criteria = new SearchCriteria(requestWrapper.getSearchProperty(), requestWrapper.getSearchValue());
				criterias.add(criteria);
			}

			@SuppressWarnings("unchecked")
			List<Tbl52RegistrationTable> models = (List<Tbl52RegistrationTable>) genericDao.getRecords(Tbl52RegistrationTable.class,
					GenericUtil.getConjuction(Tbl52RegistrationTable.class, criterias));
			List<NewRegistrationDto> dtos = getData(models);

			Field[] propertyFields = NewRegistrationDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (NewRegistrationDto record : dtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

}
