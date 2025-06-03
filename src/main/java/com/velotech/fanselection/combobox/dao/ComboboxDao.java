
package com.velotech.fanselection.combobox.dao;

import java.util.List;

import com.velotech.fanselection.models.Tbl09Dropdownlist;
import com.velotech.fanselection.models.Tbl1401Motortype;
import com.velotech.fanselection.models.Tbl14PrimemoverMaster;
import com.velotech.fanselection.models.Tbl52Usermaster;
import com.velotech.fanselection.models.Tbl58UserCompany;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.Pagination;

public interface ComboboxDao {

	ApplicationResponse getOffers(String query, Pagination pagination);

	List<Tbl58UserCompany> getUserCompanies();

	List<Tbl14PrimemoverMaster> getPrimeMover();

	//List<String> getCanopy();

	//List<String> getDrainRim();

	//List<String> getEarthing();

	//List<String> getLiftingLug();

	//List<String> getStandard();

	//ApplicationResponse getPerformaneCurve(Integer modelmasterid);

	List<Tbl09Dropdownlist> getType();

	List<Tbl09Dropdownlist> getCategory();

	//List<Tbl02ModelNrv> getModelNrv(Integer pumpModelId);

	String getStartingMethod(Integer primeMoverId);

	//List<Tbl00SelectiontypeMaster> getSelectionType();

	//List<Tbl03QapMaster> getQap(String motorSeries, String pumpSeries);

	Tbl52Usermaster getSalesPerson();

	List<Tbl52Usermaster> getSalesPerson(Integer organisationId);

	//List<String> getStartingMethod();

	List<Tbl14PrimemoverMaster> getPrimeMoverCombo(Integer motorSeriesId,Double power);

	List<Tbl1401Motortype> getMotorDescription(String motorSeriesId);

	//List<String> getPerformanceDiaList(Integer pumpModelId);



	

}
