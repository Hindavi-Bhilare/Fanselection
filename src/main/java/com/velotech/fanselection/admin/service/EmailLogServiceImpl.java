
package com.velotech.fanselection.admin.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.criterion.Conjunction;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.velotech.fanselection.dtos.EmailLogDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.models.Tbl90EmailLog;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.SqlOrder;

@Service
@Transactional
public class EmailLogServiceImpl implements EmailLogService {

	static Logger log = LogManager.getLogger(EmailLogServiceImpl.class.getName());

	@Autowired
	GenericDao genericDao;

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		List<SearchCriteria> searchCriterias = new ArrayList<>();
		try {

			if (requestWrapper.getSearchValue() != "") {
				SearchCriteria searchCriteria = new SearchCriteria(requestWrapper.getSearchProperty(), requestWrapper.getSearchValue());
				searchCriterias.add(searchCriteria);
			}

			Conjunction conjunction = GenericUtil.getConjuction(Tbl90EmailLog.class, searchCriterias);
			SqlOrder sqlOrder = new SqlOrder("id", "DESC");
			applicationResponse = genericDao.getRecords(Tbl90EmailLog.class, conjunction, requestWrapper.getPagination(), sqlOrder);

			List<Tbl90EmailLog> models = (List<Tbl90EmailLog>) applicationResponse.getData();
			long total = applicationResponse.getTotal();

			List<EmailLogDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return applicationResponse;
	}

	private List<EmailLogDto> getData(List<Tbl90EmailLog> models) {

		List<EmailLogDto> dtos = new ArrayList<>();
		try {
			for (Tbl90EmailLog model : models) {
				EmailLogDto dto = new EmailLogDto();
				BeanUtils.copyProperties(model, dto);
				dto.setCc(model.getCc().split(","));
				dto.setBcc(model.getBcc().split(","));
				dto.setEmailTo(model.getEmailTo().split(","));
				dtos.add(dto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dtos;
	}
}
