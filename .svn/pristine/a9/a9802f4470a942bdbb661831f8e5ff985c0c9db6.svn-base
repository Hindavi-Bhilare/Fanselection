
package com.velotech.fanselection.design.dao;

import java.util.List;

import com.velotech.fanselection.models.Tbl90XmlCentrifugalFanSpeed;
import com.velotech.fanselection.models.Tbl90XmlCentrifugalModelMaster;
import com.velotech.fanselection.models.Tbl90XmlCentrifugalPerformanceDataHead;


public interface CentrifugalXmlAttachmentDao {

	boolean checkCentrifugalModelMaster(String fanModel);

	Tbl90XmlCentrifugalFanSpeed checkCentrifugalFanSpeed(Tbl90XmlCentrifugalFanSpeed tbl90XmlCentrifugalFanSpeed, Integer fanModelId);

	List<Tbl90XmlCentrifugalPerformanceDataHead> gettbl90XmlCentrifugalPerformanceDataHead(Tbl90XmlCentrifugalFanSpeed tbl90XmlCentrifugalFanSpeed);

	List<Tbl90XmlCentrifugalFanSpeed> getTbl90XmlCentrifugalFanSpeed(Integer fanModelId);

	void saveTbl90CentrifugalXmlModelmaster(Tbl90XmlCentrifugalModelMaster modelmaster);
	
	//void saveTbl02Modelmaster(Tbl02Modelmaster modelMaster);

	Tbl90XmlCentrifugalFanSpeed saveTbl90XmlCentrifugalFanSpeed(Tbl90XmlCentrifugalFanSpeed tbl90XmlCentrifugalFanSpeed);
	

}
