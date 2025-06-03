
package com.velotech.fanselection.admin.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.velotech.fanselection.admin.dao.LoginHistoryDao;
import com.velotech.fanselection.dtos.LoginHistoryCountDto;
import com.velotech.fanselection.dtos.LoginHistoryDto;
import com.velotech.fanselection.models.Tbl57LoginHistory;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.RequestWrapper;

@Service
@Transactional
public class LoginHistoryServiceImpl implements LoginHistoryService {

	static Logger log = LogManager.getLogger(LoginHistoryServiceImpl.class.getName());

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private LoginHistoryDao dao;

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			applicationResponse = dao.getRecords(requestWrapper);

			List<Tbl57LoginHistory> models = (List<Tbl57LoginHistory>) applicationResponse.getData();
			long total = applicationResponse.getTotal();

			List<LoginHistoryDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return applicationResponse;
	}

	private List<LoginHistoryDto> getData(List<Tbl57LoginHistory> models) {

		List<LoginHistoryDto> dtos = new ArrayList<>();
		for (Tbl57LoginHistory model : models) {
			LoginHistoryDto dto = new LoginHistoryDto();
			BeanUtils.copyProperties(model, dto);
			dto.setLoginDate(model.getLoginDate().toString());
			if (model.getTbl52Usermaster() != null)
				dto.setUserMasterloginId(model.getTbl52Usermaster().getLoginId());
			dtos.add(dto);
		}
		return dtos;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse getcountrecords(RequestWrapper requestWrapper) {

		List<LoginHistoryCountDto> dtos = new ArrayList<>();

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<Object> models = dao.getCountRecords(requestWrapper);

			if (models.size() > 0) {
				for (int i = 0; i < models.size(); i++) {
					LoginHistoryCountDto dto = new LoginHistoryCountDto();
					Object[] o = new Object[2];
					o = (Object[]) models.get(i);
					String loginId = (String) o[0];
					long count = (Long) o[1];

					dto.setUserMasterloginId(loginId);
					dto.setCount((int) count);
					dtos.add(dto);
				}

			}
			long total = models.size();
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return applicationResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void downloadExcel(RequestWrapper requestWrapper) {

		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + "Login History" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

			List<Tbl57LoginHistory> models = (List<Tbl57LoginHistory>) dao.getExcelRecords(requestWrapper).getData();
			List<LoginHistoryDto> dtos = getData(models);

			Field[] propertyFields = LoginHistoryDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (LoginHistoryDto record : dtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

}
