
package com.velotech.fanselection.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.velotech.fanselection.master.service.MasterService;

@Service
public class MasterSericeUtil {

	private final Map<String, MasterService> servicesById = new HashMap<>();

	private List<String> keys = new ArrayList<>();

	@Autowired
	public void Services(List<MasterService> masterServices) {

		for (MasterService masterService : masterServices) {
			String serviceId = masterService.getClass().getSimpleName().split("ServiceImpl")[0];
			keys.add(serviceId);
			registerService(serviceId, masterService);
		}
	}

	public void registerService(String serviceId, MasterService masterService) {

		this.servicesById.put(serviceId, masterService);
	}

	public MasterService getService(String serviceId) {

		MasterService service = this.servicesById.get(serviceId);
		return service;
	}

}
