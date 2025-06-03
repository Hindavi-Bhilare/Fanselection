
package com.velotech.fanselection.admin.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.velotech.fanselection.utils.ApplicationConstants;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.ApplicationResponseUtil;
import com.velotech.fanselection.utils.VelotechUtil;

@Service
@Transactional
public class LogServiceImpl implements LogService {

	static Logger log = LogManager.getLogger(LogServiceImpl.class.getName());

	@Autowired
	private VelotechUtil velotechUtil;

	@Autowired
	private ServletContext servletContext;

	@Override
	public ApplicationResponse getLog(Date date) {

		ApplicationResponse applicationResponse = new ApplicationResponse();
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			String logDate = formatter.format(date);
			String currentDate = formatter.format(new Date());
			String path = "";
			if (logDate.equals(currentDate))
				path = velotechUtil.getRealPath("") + "/logs/log.htm";
			else
				path = velotechUtil.getRealPath("") + "/logs/log.htm-" + logDate + ".htm";
			File file = new File(path);
			if (file.exists()) {
				if (logDate.equals(currentDate))
					path = servletContext.getContextPath() + "/logs/log.htm";
				else
					path = servletContext.getContextPath() + "/logs/log.htm-" + logDate + ".htm";
				applicationResponse = ApplicationResponseUtil.getResponseToGetData(true, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, path);
			} else {
				applicationResponse = ApplicationResponseUtil.getResponseToGetData(false, ApplicationConstants.DATA_LOAD_SUCCESS_MSG, path);
			}

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return applicationResponse;
	}

}
