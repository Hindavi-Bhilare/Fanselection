
package com.velotech.fanselection.mis.service;

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

import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.mis.dao.MISOfferDao;
import com.velotech.fanselection.mis.dtos.MISOfferDto;
import com.velotech.fanselection.mis.dtos.MISOfferSearchDto;
import com.velotech.fanselection.models.views.MisOffer;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class MISOfferServiceImpl implements MISOfferService {

	static Logger log = LogManager.getLogger(MISOfferServiceImpl.class.getName());

	@Autowired
	private MISOfferDao dao;

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Override
	public ApplicationResponse getRecords(MISOfferSearchDto searchDto) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<MisOffer> models = dao.getMISOffer(searchDto);
			List<MISOfferDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private List<MISOfferDto> getData(List<MisOffer> models) {

		List<MISOfferDto> dtos = new ArrayList<>();
		for (MisOffer model : models) {
			MISOfferDto dto = new MISOfferDto();
			BeanUtils.copyProperties(model.getId(), dto);
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public void downloadExcel(RequestWrapper requestWrapper) {

		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + "MIS Offer Master" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

			List<SearchCriteria> criterias = new ArrayList<>();
			if (requestWrapper.getSearchValue() != null) {
				SearchCriteria criteria = new SearchCriteria(requestWrapper.getSearchProperty(), requestWrapper.getSearchValue());
				criterias.add(criteria);
			}

			@SuppressWarnings("unchecked")
			List<MisOffer> models = (List<MisOffer>) genericDao.getRecords(MisOffer.class, GenericUtil.getConjuction(MisOffer.class, criterias));
			List<MISOfferDto> dtos = getData(models);

			Field[] propertyFields = MISOfferDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (MISOfferDto record : dtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

}
