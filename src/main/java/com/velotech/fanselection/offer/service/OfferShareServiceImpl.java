
package com.velotech.fanselection.offer.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velotech.fanselection.dtos.OfferShareDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.models.Tbl23OfferRev;
import com.velotech.fanselection.models.Tbl23OfferShare;
import com.velotech.fanselection.models.Tbl52Usermaster;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;

@Service
@Transactional
public class OfferShareServiceImpl implements OfferShareService {

	static Logger log = LogManager.getLogger(OfferShareServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse addRecord(String requestPayload) throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Tbl23OfferShare model = new Tbl23OfferShare();
			OfferShareDto dto = new OfferShareDto();
			ObjectMapper mapper = new ObjectMapper();
			dto = mapper.readValue(requestPayload, OfferShareDto.class);
			BeanUtils.copyProperties(dto, model);

			Tbl23OfferRev tbl23OfferRev = (Tbl23OfferRev) genericDao.getRecordById(Tbl23OfferRev.class, dto.getOfferRevId());
			tbl23OfferRev.setId(dto.getOfferRevId());
			model.setTbl23OfferRev(tbl23OfferRev);

			List<Tbl52Usermaster> tbl52Usermasters = (List<Tbl52Usermaster>) genericDao.findByParam(Tbl52Usermaster.class, "loginId",
					dto.getUserMasterLoginId());
			model.setTbl52Usermaster(tbl52Usermasters.get(0));

			genericDao.save(model);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse updateRecord(String requestPayload) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			OfferShareDto dto = new OfferShareDto();
			ObjectMapper mapper = new ObjectMapper();
			dto = mapper.readValue(requestPayload, OfferShareDto.class);
			Tbl23OfferShare model = (Tbl23OfferShare) genericDao.getRecordById(Tbl23OfferShare.class, dto.getId());
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

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			genericDao.deleteAll(Tbl23OfferShare.class, ids);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.DELETE_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ApplicationResponse getRecords(Integer offerRevId) throws Exception {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {

			List<Tbl23OfferShare> models = (List<Tbl23OfferShare>) genericDao.getRecordsByParentId(Tbl23OfferShare.class, "tbl23OfferRev.id",
					offerRevId);
			long total = models.size();

			List<OfferShareDto> dtos = geOfferShareData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private List<OfferShareDto> geOfferShareData(List<Tbl23OfferShare> models) {

		List<OfferShareDto> dtos = new ArrayList<>();
		try {
			for (Tbl23OfferShare model : models) {
				OfferShareDto dto = new OfferShareDto();
				BeanUtils.copyProperties(model, dto);
				dto.setOfferRevId(model.getTbl23OfferRev().getId());
				dto.setUserMasterLoginId(model.getTbl52Usermaster().getLoginId());
				dto.setUserMasterName(model.getTbl52Usermaster().getUserName());
				dtos.add(dto);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return dtos;
	}

}
