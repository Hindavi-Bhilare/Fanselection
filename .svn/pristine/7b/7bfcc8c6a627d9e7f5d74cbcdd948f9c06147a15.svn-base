
package com.velotech.fanselection.design.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.criterion.Conjunction;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.velotech.fanselection.dtos.TemplateMasterDto;
import com.velotech.fanselection.dxf.model.OfferDrawingPojo;
import com.velotech.fanselection.dxf.util.OfferDrawingBlock;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.generic.util.GenericUtil;
import com.velotech.fanselection.generic.util.SearchCriteria;
import com.velotech.fanselection.models.Tbl80TemplateMaster;
import com.velotech.fanselection.models.Tbl90DocumentMaster;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.FileUtil;
import com.velotech.fanselection.utils.RequestWrapper;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class TemplateMasterServiceImpl implements TemplateMasterSerive {

	static Logger log = LogManager.getLogger(TemplateMasterServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private ServletContext servletContext;

	@Autowired
	private OfferDrawingBlock offerDrawingBlock;

	@Override
	public ApplicationResponse addRecord(RequestWrapper requestWrapper)
			throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		try{
		Tbl80TemplateMaster model = new Tbl80TemplateMaster();
		TemplateMasterDto dto = requestWrapper.getTemplateMasterDto();
		
		List<SearchCriteria> searchCriterias = new ArrayList<>();
		Tbl90DocumentMaster documentMaster=(Tbl90DocumentMaster) genericDao.getRecordById(Tbl90DocumentMaster.class, dto.getDocumentId());
		SearchCriteria fileName = new SearchCriteria("fileName", documentMaster.getFileName());
		searchCriterias.add(fileName);
		
		List<Tbl80TemplateMaster> templatemasters = (List<Tbl80TemplateMaster>) genericDao.getRecords(
				Tbl80TemplateMaster.class,
				GenericUtil.getConjuction(Tbl80TemplateMaster.class, fileName));
		if (templatemasters.size() == 0) {
		
		
		BeanUtils.copyProperties(dto, model);
		String attachedFiles = String.join(",", dto.getAttachedFile());
		model.setAttachedFile(attachedFiles);
		model.setTbl90DocumentMaster(new Tbl90DocumentMaster(dto.getDocumentId()));
		
		model.setFileName(documentMaster.getFileName());
		model.setDescription(documentMaster.getFileName());
		model.setName(documentMaster.getFileName());
		genericDao.save(model);
		
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
				ApplicationConstants.DATA_LOAD_SUCCESS_MSG);
		}
		else
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.RECORD_EXIST_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}

		return applicationResponse;

	}

	@Override
	public ApplicationResponse updateRecord(RequestWrapper requestWrapper)
			throws JsonParseException, JsonMappingException, IOException {

		ApplicationResponse applicationResponse = null;
		try{
		
		TemplateMasterDto dto = requestWrapper.getTemplateMasterDto();
		Tbl80TemplateMaster model = (Tbl80TemplateMaster) genericDao.getRecordById(Tbl80TemplateMaster.class,
				dto.getId());
		String attachedFiles = String.join(",", dto.getAttachedFile());
		model.setAttachedFile(attachedFiles);
		model.setTbl90DocumentMaster(new Tbl90DocumentMaster(dto.getDocumentId()));
		BeanUtils.copyProperties(dto, model);	
		
		
		genericDao.update(model);
		applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, ApplicationConstants.INSERT_SUCCESS_MSG);
		
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}

		return applicationResponse;
	}

	@Override
	public ApplicationResponse deleteRecords(List<Integer> ids) {

		ApplicationResponse applicationResponse = null;
		try {
			/*for (Integer id : ids) {
				Tbl80TemplateMaster tbl80TemplateMaster = (Tbl80TemplateMaster) genericDao
						.getRecordById(Tbl80TemplateMaster.class, id);
				String templatePath = velotechUtil.getCompanyDocumentPath();
				templatePath = templatePath + tbl80TemplateMaster.getTemplateName();
				File file = new File(templatePath);
				if (file.exists())
					file.delete();
			}*/
			genericDao.deleteAll(Tbl80TemplateMaster.class, ids);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.DELETE_SUCCESS_MSG);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse getRecords(RequestWrapper requestWrapper) throws Exception {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<SearchCriteria> searchCriterias = new ArrayList<>();
			if (requestWrapper.getSearchValue() != null && requestWrapper.getSearchValue() != "") {
				SearchCriteria searchCriteria = new SearchCriteria(requestWrapper.getSearchProperty(),
						requestWrapper.getSearchValue());
				searchCriterias.add(searchCriteria);
			}

//			SqlOrder sqlOrder = new SqlOrder("id", "ASC");

			Conjunction conjunction = GenericUtil.getConjuction(Tbl80TemplateMaster.class, searchCriterias);
			applicationResponse = genericDao.getRecords(Tbl80TemplateMaster.class, conjunction,
					requestWrapper.getPagination());

			List<Tbl80TemplateMaster> models = (List<Tbl80TemplateMaster>) applicationResponse.getData();

			long total = applicationResponse.getTotal();

			List<TemplateMasterDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true,
					ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos, total);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private List<TemplateMasterDto> getData(List<Tbl80TemplateMaster> models) {

		List<TemplateMasterDto> dtos = new ArrayList<>();
		for (Tbl80TemplateMaster model : models) {
			TemplateMasterDto dto = new TemplateMasterDto();
			dto.setAttachedFile(model.getAttachedFile().split(","));

			if (model.getDocumentIds() != null) {

				List<Integer> documentIds = new ArrayList<>();
				String[] parts = model.getDocumentIds().split(",");

				List<String> fileNames = new ArrayList<>();
				for (String part : parts) {
					documentIds.add(Integer.parseInt(part.trim()));
					Tbl90DocumentMaster tbl90DocumentMaster = (Tbl90DocumentMaster) genericDao
							.getRecordById(Tbl90DocumentMaster.class, Integer.parseInt(part.trim()));
					fileNames.add(tbl90DocumentMaster.getFileName());

				}
				String[] stringArray = fileNames.toArray(new String[0]);
				dto.setFileNames(stringArray);
				dto.setDocumentIds(documentIds);
			}
			if (model.getTbl90DocumentMaster() != null) 
			dto.setDocumentId(model.getTbl90DocumentMaster().getId());

			BeanUtils.copyProperties(model, dto);
			dtos.add(dto);
		}

		return dtos;
	}

	@Override
	public void downloadExcel(RequestWrapper requestWrapper) {

		try {
			response.setContentType("text/csv");
			response.setHeader("Content-Disposition", "attachment; filename=" + "TemplateMaster" + ".csv");
			ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
			List<SearchCriteria> criterias = new ArrayList<>();

			if (requestWrapper.getSearchValue() != null) {
				SearchCriteria criteria = new SearchCriteria(requestWrapper.getSearchProperty(),
						requestWrapper.getSearchValue());
				criterias.add(criteria);
			}

			List<Tbl80TemplateMaster> models = (List<Tbl80TemplateMaster>) genericDao.getRecords(
					Tbl80TemplateMaster.class, GenericUtil.getConjuction(Tbl80TemplateMaster.class, criterias));
			List<TemplateMasterDto> dtos = getData(models);

			Field[] propertyFields = TemplateMasterDto.class.getDeclaredFields();
			ArrayList<String> header = new ArrayList<String>();

			for (int i = 0; i < propertyFields.length; i++) {
				header.add(propertyFields[i].getName());
			}

			String[] finalHeader = new String[header.size()];
			finalHeader = header.toArray(finalHeader);
			csvWriter.writeHeader(finalHeader);

			for (TemplateMasterDto record : dtos) {
				csvWriter.write(record, finalHeader);
			}
			csvWriter.close();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}

	@Override
	public ApplicationResponse viewExistingTemplate(Integer id) {

		ApplicationResponse applicationResponse = null;
		Tbl80TemplateMaster tbl80TemplateMaster = (Tbl80TemplateMaster) genericDao
				.getRecordById(Tbl80TemplateMaster.class, id);

		String outputFileName = tbl80TemplateMaster.getTbl90DocumentMaster().getFileName();
		String newFilePath = "";
		OfferDrawingPojo dxfPojo = new OfferDrawingPojo();
		File oldDxf = new File(
				velotechUtil.getCompanyDocumentPath() + System.getProperty("file.separator") + outputFileName);
		File destFile = new File(velotechUtil.getUserRealPath(), oldDxf.getName());
		HashMap<String, String> blockValues = new HashMap<String, String>();
		String path = servletContext.getRealPath("");
		try {

			dxfPojo.setTbl80TemplateMaster(tbl80TemplateMaster);
			dxfPojo.setPdf(true);
			dxfPojo.setOutputPdfName();

			FileOutputStream fileOutputStream = new FileOutputStream(destFile);
			// blockValues = dxfBlocks1.fillImageBlocks(blockValues, dxfPojo);
			// DxfParser.parseDXF(oldDxf, fileOutputStream, blockValues, new
			// HashMap(), dxfPojo.getImageList());
			dxfPojo.setDXF_PATH(velotechUtil.getCompanyPath() + "Template" + System.getProperty("file.separator")
					+ oldDxf.getName());
			// dxfPojo.setPath(velobj.getUserContextPath() + oldDxf.getName());

			fileOutputStream.flush();
			fileOutputStream.close();
			dxfPojo.setIsTemplate(true);
			offerDrawingBlock.getGeneratedDxfViewAspose(dxfPojo);

			dxfPojo.setCheckPath(true);
			newFilePath = dxfPojo.getPath();
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, newFilePath);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;

	}

	private String uploadTemplateFile(MultipartFile file, String templateName, String companyTemplatePath) {

		String confirmation = "true";
		try {
			File destFile = new File(companyTemplatePath, templateName);
			if (!destFile.exists())
				// FileUtils.moveFile((File) file, destFile);
				FileUtil.convertMultipartFile(file, companyTemplatePath, templateName);
			else
				confirmation = "'" + templateName + "_" + "  Already Exists";
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return confirmation;

	}

	@Override
	public ApplicationResponse overWriteTemplate(MultipartFile file, Integer id) {

		ApplicationResponse applicationResponse = null;
		// TemplateMasterDto dto = new TemplateMasterDto();

		Tbl80TemplateMaster model = (Tbl80TemplateMaster) genericDao.getRecordById(Tbl80TemplateMaster.class, id);

		// BeanUtils.copyProperties(dto, model);
		String templateName = file.getOriginalFilename();

//		if (model.getTemplateName().equals(templateName)) {
////			model.setTemplateName(templateName);
//			model.setDescription(templateName);
//			model.setName(templateName);
//			genericDao.save(model);
//
//			String ans = overwriteTemplateFile(file, templateName, velotechUtil.getCompanyDocumentPath());
//			if (ans.equals("true"))
//				applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, "File Uploaded Succesfully");
//		} else
//			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true, "Please Select Correct Template");

		return applicationResponse;

	}

	private String overwriteTemplateFile(MultipartFile file, String templateName, String companyTemplatePath) {

		String confirmation = "true";
		try {
			File destFile = new File(companyTemplatePath, templateName);
			// FileUtils.moveFile((File) file, destFile);
			FileUtil.convertMultipartFile(file, companyTemplatePath, templateName);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return confirmation;

	}

	@Override
	public ApplicationResponse upload(String requestPayload) {
		ApplicationResponse applicationResponse = null;
		try {
			TemplateMasterDto dto = new TemplateMasterDto();
			ObjectMapper mapper = new ObjectMapper();
			dto = mapper.readValue(requestPayload, TemplateMasterDto.class);
			Tbl80TemplateMaster model = (Tbl80TemplateMaster) genericDao.getRecordById(Tbl80TemplateMaster.class,
					dto.getId());
			/*
			 * if(dto.getDocumentId()!= null) model.setTbl90DocumentMaster(new
			 * Tbl90DocumentMaster(dto.getDocumentId())); else
			 * model.setTbl90DocumentMaster(null);
			 */

			if (dto.getDocumentIds() != null) {

				List<Integer> documentIds = dto.getDocumentIds();
				List<String> stringIds = new ArrayList<>();
				for (Integer id : documentIds) {
					stringIds.add(String.valueOf(id));

				}
				String filenames = String.join(",", stringIds);
				model.setDocumentIds(filenames);
			}

			genericDao.update(model);
			applicationResponse = ApplicationResponseUtil.getResponseToCRUD(true,
					ApplicationConstants.INSERT_SUCCESS_MSG);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

}
