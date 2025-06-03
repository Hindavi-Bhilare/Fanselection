
package com.velotech.fanselection.others.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.velotech.fanselection.others.service.ViscosityCorrectionService;
import com.velotech.fanselection.selection.models.Viscosity;
import com.velotech.fanselection.utils.ApplicationResponse;

@RestController
@RequestMapping(value = "/others/viscositycorrection")
public class ViscosityCorrectionController {

	static Logger log = LogManager.getLogger(ViscosityCorrectionController.class.getName());

	@Autowired
	private ViscosityCorrectionService viscosityCorrectionService;

	@RequestMapping(value = "/getviscositycorrection", method = RequestMethod.POST)
	public ApplicationResponse calculateCorrection(Viscosity viscosity) {

		ApplicationResponse result = new ApplicationResponse();
		try {
			result = viscosityCorrectionService.calculateViscosity(viscosity);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return result;
	}

}
