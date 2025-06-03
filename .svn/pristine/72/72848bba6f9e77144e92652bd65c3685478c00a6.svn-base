
package com.velotech.fanselection.admin.service;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.velotech.fanselection.dtos.UtilityExcelDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.models.Tbl01CentrifugalModelMaster;
import com.velotech.fanselection.models.Tbl14PrimemoverMaster;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class UtilityServiceImpl implements UtilityService {

	static Logger log = LogManager.getLogger(UtilityServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Override
	public ApplicationResponse exportRecord(Integer pumpTypeId, Integer motorSeriesId) {

		List<UtilityExcelDto> utilityExcelDtos = new ArrayList<UtilityExcelDto>();
		try {

			List<Tbl01CentrifugalModelMaster> modelMasterList = (List<Tbl01CentrifugalModelMaster>) genericDao.getRecordsByParentId(Tbl01CentrifugalModelMaster.class,
					"tbl01Fantype.id", pumpTypeId);
			List<Tbl14PrimemoverMaster> primeMoverList = (List<Tbl14PrimemoverMaster>) genericDao.getRecordsByParentId(Tbl14PrimemoverMaster.class,
				"tbl1401Motortype.id", motorSeriesId);
			
			for (Tbl01CentrifugalModelMaster modelMaster : modelMasterList) {

				Integer minStage = 1;
				Integer maxStage = 1;
				for (int s = minStage; s <= maxStage; s++) {
					for (Tbl14PrimemoverMaster tbl14PrimemoverMaster : primeMoverList) {
						UtilityExcelDto utilityExcelDto = new UtilityExcelDto();
						utilityExcelDto.setPumpSeriesId(modelMaster.getTbl01fantype().getId());
						utilityExcelDto.setPumpSeries(modelMaster.getTbl01fantype().getSeries());
						utilityExcelDto.setModelId(modelMaster.getId());
						utilityExcelDto.setModelName(modelMaster.getFanModel());
						utilityExcelDto.setStage(s);
						utilityExcelDto.setPrimeMoverId(tbl14PrimemoverMaster.getPrimemoverId());
						utilityExcelDto.setPrimeMoverSeries(tbl14PrimemoverMaster.getSeries());
						utilityExcelDto.setPower(tbl14PrimemoverMaster.getPower());
						utilityExcelDto.setPowerHp(tbl14PrimemoverMaster.getPowerHp());
						utilityExcelDto.setMotorSeriesId(tbl14PrimemoverMaster.getTbl1401Motortype().getId());
						utilityExcelDto.setMotorSeries(tbl14PrimemoverMaster.getTbl1401Motortype().getSeries());
						utilityExcelDtos.add(utilityExcelDto);
					}
				}

			}
			
			downloadExcel(utilityExcelDtos);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		// TODO Auto-generated method stub
		return null;
	}

	private void downloadExcel(List<UtilityExcelDto> utilityExcelDtos) {

		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + "UtilityExcel" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

			Field[] propertyFields = UtilityExcelDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (UtilityExcelDto record : utilityExcelDtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
