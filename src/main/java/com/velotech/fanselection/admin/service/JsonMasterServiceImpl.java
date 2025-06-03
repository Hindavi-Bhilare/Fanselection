package com.velotech.fanselection.admin.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.octomix.josson.Josson;
import com.velotech.fanselection.admin.dao.JsonMasterDao;
import com.velotech.fanselection.dtos.JsonMasterDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.models.Tbl90JsonMaster;
import com.velotech.fanselection.models.Tbl90ParameterMaster;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class JsonMasterServiceImpl implements JsonMasterService {

	static Logger log = LogManager.getLogger(JsonMasterServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private JsonMasterDao jsonDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) {
		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			applicationResponse = jsonDao.getRecords(requestWrapper);

			List<Tbl90JsonMaster> models = (List<Tbl90JsonMaster>) applicationResponse.getData();
			long total = applicationResponse.getTotal();

			List<JsonMasterDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private List<JsonMasterDto> getData(List<Tbl90JsonMaster> models) {

		List<JsonMasterDto> dtos = new ArrayList<>();
		for (Tbl90JsonMaster model : models) {
			JsonMasterDto dto = new JsonMasterDto();
			BeanUtils.copyProperties(model, dto);
			if (model.getTbl90ParameterMaster() != null) {
				dto.setParameterName(model.getTbl90ParameterMaster().getParameter());

			}
			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public ApplicationResponse addRecord(String requestPayload) {
		ApplicationResponse applicationResponse = null;
		try {
			Tbl90JsonMaster model = new Tbl90JsonMaster();
			JsonMasterDto dto = new JsonMasterDto();
			ObjectMapper mapper = new ObjectMapper();
			dto = mapper.readValue(requestPayload, JsonMasterDto.class);
			BeanUtils.copyProperties(dto, model);
			if (dto.getParameterName() != null)
				model.setTbl90ParameterMaster(new Tbl90ParameterMaster(dto.getParameterName()));

			model.setCompany(velotechUtil.getCompany());
			boolean saveSuccessful = genericDao.save(model);

			if (saveSuccessful) {
				applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
						ApplicationConstants.INSERT_SUCCESS_MSG);
			} else {
				applicationResponse = ApplicationResponseUtil.getResponseToCRUD(false,
						ApplicationConstants.RECORD_EXIST_MSG);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload) {
		ApplicationResponse applicationResponse = null;
		try {
			JsonMasterDto dto = new JsonMasterDto();
			ObjectMapper mapper = new ObjectMapper();
			dto = mapper.readValue(requestPayload, JsonMasterDto.class);
			Tbl90JsonMaster model = (Tbl90JsonMaster) genericDao.getRecordById(Tbl90JsonMaster.class, dto.getId());
			BeanUtils.copyProperties(dto, model);
			if (dto.getParameterName() != null)
				model.setTbl90ParameterMaster(new Tbl90ParameterMaster(dto.getParameterName()));

			genericDao.update(model);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.UPDATE_SUCCESS_MSG);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {
		ApplicationResponse applicationResponse = null;
		try {
			genericDao.deleteAll(Tbl90JsonMaster.class, ids);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.DELETE_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse jsonEvaluate(String requestPayload) {
		ApplicationResponse applicationResponse = null;
		Josson josson = null;
		try {
			// String a =
			// "{"selectedPump":{"motorSeriesId":61,"stg":5},"eval":"calc(if((mid=61|mid=62),if(stg<=5,5.8,if(stg<=7,6.8,if(stg<=10,8.2,if(stg<=12,9.2,if(stg<=15,10.6,if(stg<=18,12.1,if(stg<=21,13.5,if(stg<=25,15.5,if(stg<=30,17.9,if(stg<=37,21.2,if(stg<=44,24.6,if(stg<=50,27.5,0)))))))))))),rnif(mid=50,if(stg<=30,19.7,if(stg<=37,23.2,if(stg<=44,26.6,if(stg<=50,29.5,if(stg<=58,33.4,if(stg<=66,37.7,if(stg<=73,40.7,if(stg<=82,45,if(stg<=91,49.4,if(stg<=100,53.8,if(stg<=110,58.6,0))))))))))))),mid:$.selectedPump.motorSeriesId,stg:$.selectedPump.stg)"}";

			josson = Josson.fromJsonString(requestPayload.toString());

			System.out.println(josson.getNode("eval(filter)"));
			if (josson.getNode("eval(filter)") != null)
				applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, "true",
						josson.getNode("eval(filter)"));

		} catch (Exception e) {
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(false, e.getMessage());
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}
}
