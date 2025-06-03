
package com.velotech.fanselection.design.service;

import java.io.IOException;
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

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velotech.fanselection.design.dao.GenericBomDao;
import com.velotech.fanselection.dtos.GenericBomDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.master.service.MasterService;
import com.velotech.fanselection.models.Tbl01Fantype;
import com.velotech.fanselection.models.Tbl03GenericBom;
import com.velotech.fanselection.models.Tbl03GenericBomData;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class GenericBomServiceImpl implements MasterService, GenericBomService {

	static Logger log = LogManager.getLogger(GenericBomServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private GenericBomDao dao;

	@Override
	public ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		Tbl03GenericBom model = new Tbl03GenericBom();
		GenericBomDto dto = new GenericBomDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, GenericBomDto.class);
		BeanUtils.copyProperties(dto, model);
		if (dto.getFanTypeId() != null)
			model.setTbl01Fantype(new Tbl01Fantype(dto.getFanTypeId()));
		
		model.setCompany(velotechUtil.getCompany());
		genericDao.save(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		GenericBomDto dto = new GenericBomDto();
		ObjectMapper mapper = new ObjectMapper();
		dto = mapper.readValue(requestPayload, GenericBomDto.class);
		Tbl03GenericBom model = (Tbl03GenericBom) genericDao.getRecordById(Tbl03GenericBom.class, dto.getGenericBomId());
		BeanUtils.copyProperties(dto, model);
		if (dto.getFanTypeId() != null)
			model.setTbl01Fantype(new Tbl01Fantype(dto.getFanTypeId()));
		
		genericDao.update(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);

		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = null;
		genericDao.deleteAll(Tbl03GenericBom.class, ids);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		ApplicationResponse applicationResponse = new ApplicationResponse();

		applicationResponse = dao.getRecords(requestWrapper);

		List<Tbl03GenericBom> models = (List<Tbl03GenericBom>) applicationResponse.getData();
		long total = applicationResponse.getTotal();

		List<GenericBomDto> dtos = getData(models);
		applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		return applicationResponse;
	}

	private List<GenericBomDto> getData(List<Tbl03GenericBom> models) {

		List<GenericBomDto> dtos = new ArrayList<>();
		for (Tbl03GenericBom model : models) {
			GenericBomDto dto = new GenericBomDto();
			BeanUtils.copyProperties(model, dto);
			if (model.getTbl01Fantype() != null) {
				dto.setFanTypeId(model.getTbl01Fantype().getId());
				dto.setFanSeries(model.getTbl01Fantype().getSeries());
			}
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public void downloadExcel(RequestWrapper requestWrapper) {

		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + "GenericBom" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

			List<Tbl03GenericBom> models = (List<Tbl03GenericBom>) dao.getExcelRecords(requestWrapper).getData();
			List<GenericBomDto> dtos = getData(models);

			Field[] propertyFields = GenericBomDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (GenericBomDto record : dtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

	}

	@Override
	public ApplicationResponse copygenericbom(Integer genericBomId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();

		try {
			Tbl03GenericBom tbl03GenericBomOld = (Tbl03GenericBom) genericDao.getRecordById(Tbl03GenericBom.class, genericBomId);
			Tbl03GenericBom tbl03GenericBomNew = new Tbl03GenericBom();
			BeanUtils.copyProperties(tbl03GenericBomOld, tbl03GenericBomNew);

			if (genericDao.save(tbl03GenericBomNew)) {
				List<Tbl03GenericBomData> tbl03GenericBomData = (List) genericDao.getRecordsByParentId(Tbl03GenericBomData.class,
						"tbl03GenericBom.genericBomId", tbl03GenericBomOld.getGenericBomId());

				List<Object> tbl03GenericBomDataList = new ArrayList<>();
				for (Tbl03GenericBomData tbl03GenericBomDataOld : tbl03GenericBomData) {
					Tbl03GenericBomData tbl03GenericBomDataNew = new Tbl03GenericBomData();
					BeanUtils.copyProperties(tbl03GenericBomDataOld, tbl03GenericBomDataNew);
					tbl03GenericBomDataNew.setTbl03GenericBom(tbl03GenericBomNew);
					tbl03GenericBomDataList.add(tbl03GenericBomDataNew);
				}
				genericDao.saveAll(tbl03GenericBomDataList);
			}
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.COPY_SUCCESS_MSG);

		} catch (Exception e) {

			log.error(e.getMessage(), e);
		}

		return applicationResponse;
	}
}
