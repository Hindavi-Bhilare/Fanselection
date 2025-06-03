
package com.velotech.fanselection.company.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.velotech.fanselection.company.service.CompanyService;

@Component
public class CompanyServiceUtil {

	private final Map<String, CompanyService> servicesById = new HashMap<>();

	private List<String> keys = new ArrayList<>();

	@Autowired
	public void companyServices(List<CompanyService> companyServices) {

		for (CompanyService service : companyServices) {
			String serviceId = service.getClass().getSimpleName().split("ServiceImpl")[0];
			keys.add(serviceId);
			registerService(serviceId, service);
		}
	}

	public void registerService(String serviceId, CompanyService companyService) {

		this.servicesById.put(serviceId, companyService);
	}

	public CompanyService getCompanyService(String companyCode) {

		String serviceId;
		switch (companyCode) {
		case "CT01":
			serviceId = "Chemtrol";
			break;
		case "DP01":
			serviceId = "Darling";
			break;
		case "MBH01":
			serviceId = "Mbh";
			break;
		case "Weltech01":
			serviceId = "Weltech";
			break;
		default:
			serviceId = "Velotech";
		}
		return this.servicesById.get(serviceId);
	}
}
