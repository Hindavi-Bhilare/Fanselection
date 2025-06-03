
package com.velotech.fanselection.admin.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.criterion.Conjunction;
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
import com.velotech.fanselection.dtos.CustomerContactDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.models.Tbl25CustomerMaster;
import com.velotech.fanselection.models.Tbl25Customercontact;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.Pagination;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class CustomerContactServiceImpl implements MasterService {

	static Logger log = LogManager.getLogger(CustomerContactServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Override
	public ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		Tbl25Customercontact model = new Tbl25Customercontact();
		CustomerContactDto dto = new CustomerContactDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, CustomerContactDto.class);
		BeanUtils.copyProperties(dto, model);
		Tbl25CustomerMaster tbl25CustomerMaster = (Tbl25CustomerMaster) genericDao.getRecordById(Tbl25CustomerMaster.class, dto.getCustomerId());
		model.setTbl25CustomerMaster(tbl25CustomerMaster);
		model.setCompany(velotechUtil.getCompany());
		genericDao.save(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		CustomerContactDto dto = new CustomerContactDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, CustomerContactDto.class);
		Tbl25Customercontact model = (Tbl25Customercontact) genericDao.getRecordById(Tbl25Customercontact.class, dto.getId());
		BeanUtils.copyProperties(dto, model);
		genericDao.update(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = null;
		genericDao.deleteAll(Tbl25Customercontact.class, ids);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Conjunction conjunction = GenericUtil.getConjuction(Tbl25Customercontact.class, requestWrapper.getSearchCriterias());
			Pagination pagination = requestWrapper.getPagination();
			applicationResponse = genericDao.getRecords(Tbl25Customercontact.class, conjunction, pagination);

			List<Tbl25Customercontact> models = (List<Tbl25Customercontact>) applicationResponse.getData();
			long total = applicationResponse.getTotal();

			List<CustomerContactDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return applicationResponse;
	}

	private List<CustomerContactDto> getData(List<Tbl25Customercontact> models) {

		List<CustomerContactDto> dtos = new ArrayList<>();
		for (Tbl25Customercontact model : models) {
			CustomerContactDto dto = new CustomerContactDto();
			BeanUtils.copyProperties(model, dto);

			if (model.getTbl25CustomerMaster() != null) {
				dto.setCustomerId(model.getTbl25CustomerMaster().getCustomerId());
				dto.setCustomer(model.getTbl25CustomerMaster().getCustomerName());
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
			response.setHeader("Content-Disposition", "attachment; filename=" + "CustomerContact" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
			List<SearchCriteria> criterias = new ArrayList<>();

			Object searchValue = requestWrapper.getParentId();
			String searchProperty = "tbl25CustomerMaster.customerId";

			SearchCriteria cSearchCriteria = new SearchCriteria(searchProperty, searchValue);
			criterias.add(cSearchCriteria);
			if (requestWrapper.getSearchValue() != null) {
				SearchCriteria criteria = new SearchCriteria(requestWrapper.getSearchProperty(), requestWrapper.getSearchValue());
				criterias.add(criteria);
			}

			List<Tbl25Customercontact> models = (List<Tbl25Customercontact>) genericDao.getRecords(Tbl25Customercontact.class,
					GenericUtil.getConjuction(Tbl25Customercontact.class, criterias));

			List<CustomerContactDto> dtos = getData(models);

			Field[] propertyFields = CustomerContactDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (CustomerContactDto record : dtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
