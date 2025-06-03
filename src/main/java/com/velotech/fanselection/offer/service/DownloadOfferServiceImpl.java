
package com.velotech.fanselection.offer.service;

import java.io.PrintWriter;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lowagie.text.pdf.PdfReader;
import com.velotech.fanselection.dtos.EmailLogDto;
import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.models.Tbl23OfferRev;
import com.velotech.fanselection.models.Tbl26OfferFan;
import com.velotech.fanselection.models.Tbl27RequirementsDp;
import com.velotech.fanselection.models.Tbl28SelectedFan;
import com.velotech.fanselection.models.Tbl52Usermaster;
import com.velotech.fanselection.models.Tbl90EmailLog;
import com.velotech.fanselection.offer.dao.DownloadOfferDao;
import com.velotech.fanselection.offer.dto.OfferMasterDownloadDto;
import com.velotech.fanselection.offer.dto.OfferFanDownloadDto;
import com.velotech.fanselection.propertybean.MailProperties;
import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.CommonList;
import com.velotech.fanselection.utils.MailUtility;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class DownloadOfferServiceImpl implements DownloadOfferService {

	static Logger log = LogManager.getLogger(DownloadOfferServiceImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Autowired
	private DownloadOfferDao dao;

	@Autowired
	private CommonList commonList;

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private HttpSession session;

	@Autowired
	private OfferDetailsService offerDetailsService;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	MailUtility mailUtility;

	@Autowired
	private MailProperties mailProperties;

	@Override
	public ApplicationResponse getDownloadOfferData(Integer offerRevId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			List<Tbl26OfferFan> models = dao.getDownloadOfferData(offerRevId);
			List<OfferFanDownloadDto> dtos = getData(models);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private List<OfferFanDownloadDto> getData(List<Tbl26OfferFan> models) {

		List<OfferFanDownloadDto> dtos = new ArrayList<>();
		try {
			for (Tbl26OfferFan model : models) {
				OfferFanDownloadDto dto = new OfferFanDownloadDto();
				BeanUtils.copyProperties(model, dto);
				dtos.add(dto);
			}
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}
		return dtos;
	}

	@Override
	public String downloadOfferMaster(String requestPayload, boolean isCalledForEmail) {

		List<PdfReader> pdfReaderList = new ArrayList<PdfReader>();
		ApplicationResponse quotationPDF = new ApplicationResponse();
		ApplicationResponse summarySheetPDF = new ApplicationResponse();
		String path = "false";
		try {

			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

			OfferMasterDownloadDto dto = mapper.readValue(requestPayload, OfferMasterDownloadDto.class);

			if (dto.getQuotationPDF() == true)
				quotationPDF = offerDetailsService.getQuotation(dto.getOfferRevId());

			if (dto.getSummarySheetPDF() == true)
				summarySheetPDF = offerDetailsService.getSummarySheet(dto.getOfferRevId());
			if (quotationPDF.getData() != null)
				pdfReaderList.add(new PdfReader(velotechUtil.getUserRealPath() + velotechUtil.getLastName(quotationPDF.getData().toString())));

			if (summarySheetPDF.getData() != null)
				pdfReaderList.add(new PdfReader(velotechUtil.getUserRealPath() + velotechUtil.getLastName(summarySheetPDF.getData().toString())));

			if (dto.getOfferFanDownloadDtoList() != null) {
				for (int i = 0; i < dto.getOfferFanDownloadDtoList().size(); i++)
					downloadSingleOfferFan(dto.getOfferFanDownloadDtoList().get(i), pdfReaderList);
			}
			if (pdfReaderList.size() != 0)
				path = velotechUtil.mergePDF(pdfReaderList, "OfferMasterReport" + velotechUtil.getRandomNumber() + ".pdf");

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			path = "false";
		}
		return path;
	}

	public List<PdfReader> downloadSingleOfferFan(OfferFanDownloadDto offerFanDownload, List<PdfReader> pdfReaderList) {

		ApplicationResponse perfChartPDF = new ApplicationResponse();
		ApplicationResponse dataSheetPDF = new ApplicationResponse();
		ApplicationResponse barebumpPDF = new ApplicationResponse();
		ApplicationResponse csdPDF = new ApplicationResponse();
		ApplicationResponse qapPDF = new ApplicationResponse();
		ApplicationResponse motorQapPDF = new ApplicationResponse();
		ApplicationResponse motorPDF = new ApplicationResponse();

		try {
			
			  if (offerFanDownload.getPerformancePDF() != null && offerFanDownload.getPerformancePDF() == true) {
				  perfChartPDF = offerDetailsService.getPerformanceChart(offerFanDownload.getId()); 
			  
			  }
			  
			  if (offerFanDownload.getDataSheetPDF() != null &&  offerFanDownload.getDataSheetPDF() == true) dataSheetPDF =
			  offerDetailsService.getDataSheet(offerFanDownload.getId());
			 
			/*if (offerPumpDownload.getBarePumpPDF() != null && offerPumpDownload.getBarePumpPDF() == true)
				barebumpPDF = offerDetailsService.getBarePumpDrawing(offerPumpDownload.getId(), false);*/

			if (offerFanDownload.getCsPDF() != null && offerFanDownload.getCsPDF() == true)
				csdPDF = offerDetailsService.getCsdDrawing(offerFanDownload.getId(), false);

//			if (offerPumpDownload.getQapPDF() != null && offerPumpDownload.getQapPDF() == true) {
//				Tbl26OfferPump offerPump = (Tbl26OfferPump) genericDao.getRecordById(Tbl26OfferPump.class, offerPumpDownload.getId());
//				Tbl28SelectedPump tbl28SelectedPump = offerPump.getTbl28SelectedPump();
//				Integer qapId = tbl28SelectedPump.getQapId();
//				qapPDF = offerDetailsService.showSelectedPumpQap(qapId);
//				motorQapPDF = offerDetailsService.showSelectedPumpQap(tbl28SelectedPump.getMotorQapId());
//			}
			if (offerFanDownload.getQapPDF() != null && offerFanDownload.getQapPDF() == true) {
				Tbl26OfferFan offerFan = (Tbl26OfferFan) genericDao.getRecordById(Tbl26OfferFan.class, offerFanDownload.getId());
				Tbl28SelectedFan tbl28SelectedFan = offerFan.getTbl28SelectedFan();
				//qapPDF = offerDetailsService.showSelectedPumpQap(null, qapId);
			}
			if (offerFanDownload.getMotorPDF() != null && offerFanDownload.getMotorPDF() == true) {
				motorPDF = offerDetailsService.getPrimeMoverDrawing(offerFanDownload.getId(), false);
			}
			if (dataSheetPDF.getData() != null && dataSheetPDF.getData() != "")
				pdfReaderList.add(new PdfReader(velotechUtil.getUserRealPath() + velotechUtil.getLastName(dataSheetPDF.getData().toString())));

			if (perfChartPDF.getData() != null && perfChartPDF.getData() != "")
				pdfReaderList.add(new PdfReader(velotechUtil.getUserRealPath() + velotechUtil.getLastName(perfChartPDF.getData().toString())));

			if (barebumpPDF.getData() != null && barebumpPDF.getData() != "")
				pdfReaderList.add(new PdfReader(velotechUtil.getUserRealPath() + velotechUtil.getLastName(barebumpPDF.getData().toString())));

			if (csdPDF.getData() != null && csdPDF.getData() != "")
				pdfReaderList.add(new PdfReader(velotechUtil.getUserRealPath() + velotechUtil.getLastName(csdPDF.getData().toString())));

			if (motorPDF.getData() != null && motorPDF.getData() != "")
				pdfReaderList.add(new PdfReader(velotechUtil.getUserRealPath() + velotechUtil.getLastName(motorPDF.getData().toString())));

			if (qapPDF.getData() != null && qapPDF.getData() != "")
				pdfReaderList.add(new PdfReader(velotechUtil.getUserRealPath() + velotechUtil.getLastName(qapPDF.getData().toString())));

			if (motorQapPDF.getData() != null && motorQapPDF.getData() != "")
				pdfReaderList.add(new PdfReader(velotechUtil.getUserRealPath() + velotechUtil.getLastName(motorQapPDF.getData().toString())));

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return pdfReaderList;
	}

	@Override
	public void downloadOfferFan(String requestPayload) {
		// TODO Auto-generated method stub

		String path = "false";
		List<PdfReader> pdfReaderList = new ArrayList<PdfReader>();
		String fileName = "";
		PrintWriter out;
		try {
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			List<OfferFanDownloadDto> dtos = new ArrayList<>();
			out = response.getWriter();
			response.setContentType("text/html");
			response.setHeader("Cache-Control", "no-store");
			dtos = Arrays.asList(mapper.readValue(requestPayload, OfferFanDownloadDto[].class));
			Tbl27RequirementsDp tbl27RequirementsDp = (Tbl27RequirementsDp) genericDao.getRecordById(Tbl27RequirementsDp.class, dtos.get(0).getId());
			dtos.get(0).setSystem(tbl27RequirementsDp.getSystem());
			downloadSingleOfferFan(dtos.get(0), pdfReaderList);
			if (pdfReaderList.size() > 0)
				path = velotechUtil.mergePDF(pdfReaderList, "OfferFanReport_" + dtos.get(0).getId() + ".pdf");
			out.print(path);
		} catch (final JsonMappingException | JsonParseException e) {
			System.out.println(e.getMessage());
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}

	}

	@Override
	public ApplicationResponse getDownloadOfferFanData(Integer offerFanId) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Tbl26OfferFan model = (Tbl26OfferFan) genericDao.getRecordById(Tbl26OfferFan.class, offerFanId);
			List<OfferFanDownloadDto> dtos = getFanData(model);
			applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, dtos);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	private List<OfferFanDownloadDto> getFanData(Tbl26OfferFan model) {

		List<OfferFanDownloadDto> dtos = new ArrayList<>();
		try {
			OfferFanDownloadDto dto = new OfferFanDownloadDto();
			BeanUtils.copyProperties(model, dto);
			dtos.add(dto);

		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}
		return dtos;
	}

	@Override
	public ApplicationResponse getUserEmailStatus() {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			Tbl52Usermaster tbl52Usermaster = (Tbl52Usermaster) genericDao.getRecordById(Tbl52Usermaster.class, velotechUtil.getLoginId());
			//if (tbl52Usermaster.getIsEmail() != null && tbl52Usermaster.getIsEmail())
				applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, true);
			//else
				//applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, false);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

	@Override
	public ApplicationResponse sendEmail(String requestPayload) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
			EmailLogDto dto = mapper.readValue(requestPayload, EmailLogDto.class);
			OfferMasterDownloadDto offerMasterDto = dto.getOfferMasterDownloadDto();
			String jsonOfferMaster = mapper.writeValueAsString(offerMasterDto);
			Tbl23OfferRev offerRev = (Tbl23OfferRev) genericDao.getRecordById(Tbl23OfferRev.class, offerMasterDto.getOfferRevId());
			String path = downloadOfferMaster(jsonOfferMaster, true);

			String fileName = offerRev.getTbl23OfferMaster().getOfferNo() + "(" + offerRev.getRev() + ")_" + velotechUtil.getTimeStamp() + ".pdf";

			boolean isSendEmail = mailUtility.sendEmailByTransMailAttachment(dto.getEmailTo(), dto.getSubject(), dto.getEmailbody(), dto.getCc(),
					dto.getBcc(), velotechUtil.getUserRealPath() + velotechUtil.getFileName(path), fileName);

			Tbl90EmailLog model = new Tbl90EmailLog();
			BeanUtils.copyProperties(dto, model);
			String emailTo = StringUtils.arrayToCommaDelimitedString(dto.getEmailTo());
			String cc = StringUtils.arrayToCommaDelimitedString(dto.getCc());
			String bcc = StringUtils.arrayToCommaDelimitedString(dto.getBcc());
			model.setEmailTo(emailTo);
			model.setCc(cc);
			model.setBcc(bcc);
			model.setEmailStatus(isSendEmail);
			model.setEmailFrom(mailProperties.getFrom());
			Tbl23OfferRev tbl23OfferRev = (Tbl23OfferRev) genericDao.getRecordById(Tbl23OfferRev.class, dto.getOfferRevId());
			model.setOfferRev(tbl23OfferRev.getRev());
			model.setOfferNo(tbl23OfferRev.getTbl23OfferMaster().getOfferNo());
			genericDao.save(model);

			if (isSendEmail) {
				applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, "Mail Send Successfull");
			} else
				applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, "Mail Send Fail");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}
}
