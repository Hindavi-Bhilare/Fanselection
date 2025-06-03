
package com.velotech.fanselection.admin.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velotech.fanselection.admin.dao.CustomerDao;
import com.velotech.fanselection.dtos.CustomerMasterDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.models.Tbl25CustomerMaster;
import com.velotech.fanselection.models.Tbl28CompanyMaster;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class CustomerMasterServiceImpl implements MasterService {

	static Logger log = LogManager.getLogger(CustomerMasterServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private CustomerDao dao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		Tbl25CustomerMaster model = new Tbl25CustomerMaster();
		CustomerMasterDto dto = new CustomerMasterDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, CustomerMasterDto.class);

		List<Tbl25CustomerMaster> models = (List<Tbl25CustomerMaster>) genericDao.findByParam(Tbl25CustomerMaster.class, "customerName",
				dto.getCustomerName());

		if (models.size() < 1) {
			BeanUtils.copyProperties(dto, model);
			model.setCompany(velotechUtil.getCompany());
			model.setTbl28CompanyMaster(new Tbl28CompanyMaster(dto.getCompanyId()));
			genericDao.save(model);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
		} else
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.RECORD_EXISTS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		CustomerMasterDto dto = new CustomerMasterDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, CustomerMasterDto.class);
		Tbl25CustomerMaster model = (Tbl25CustomerMaster) genericDao.getRecordById(Tbl25CustomerMaster.class, dto.getCustomerId());
		BeanUtils.copyProperties(dto, model);
		model.setTbl28CompanyMaster(new Tbl28CompanyMaster(dto.getCompanyId()));
		genericDao.update(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = null;
		genericDao.deleteAll(Tbl25CustomerMaster.class, ids);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		return applicationResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			applicationResponse = dao.getCustomerRecords(requestWrapper);

			List<Tbl25CustomerMaster> models = (List<Tbl25CustomerMaster>) applicationResponse.getData();
			long total = applicationResponse.getTotal();

			List<CustomerMasterDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private List<CustomerMasterDto> getData(List<Tbl25CustomerMaster> models) {

		List<CustomerMasterDto> dtos = new ArrayList<>();
		for (Tbl25CustomerMaster model : models) {
			CustomerMasterDto dto = new CustomerMasterDto();
			BeanUtils.copyProperties(model, dto);

			if (model.getTbl28CompanyMaster() != null) {
				dto.setCompanyId(model.getTbl28CompanyMaster().getId());
				dto.setCompanyName(model.getTbl28CompanyMaster().getCompanyName());
			}
			dtos.add(dto);
		}

		return dtos;

	}

	@SuppressWarnings("unchecked")
	@Override
	public void downloadExcel(RequestWrapper requestWrapper) {

		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + "CustomerMaster" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

			ApplicationResponse applicationResponse = dao.getCustomerRecords(requestWrapper);

			List<Tbl25CustomerMaster> models = (List<Tbl25CustomerMaster>) applicationResponse.getData();
			List<CustomerMasterDto> dtos = getData(models);

			Field[] propertyFields = CustomerMasterDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (CustomerMasterDto record : dtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
