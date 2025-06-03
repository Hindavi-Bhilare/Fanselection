
package com.velotech.fanselection.selection.dao;

import java.util.List;

import com.velotech.fanselection.models.Tbl01CentrifugalModelMaster;
import com.velotech.fanselection.models.Tbl01Fantype;
import com.velotech.fanselection.models.Tbl1401Motortype;
import com.velotech.fanselection.models.Tbl14PrimemoverMaster;
import com.velotech.fanselection.models.Tbl28CompanyMaster;
import com.velotech.fanselection.models.Tbl95TxPumpSelectionModel;
import com.velotech.fanselection.models.Tbl95TxSelectedPumps;
import com.velotech.fanselection.selection.models.CentrifugalFanSelectionModel;

public interface CentrifugalFanSelectionDao {

	List<Tbl01Fantype> getFanType(String fanSeries);

	Tbl28CompanyMaster getDefaultCompany(String user);

	boolean isUniqueTagNumber(String tagNo, Integer offerRevId);

	void saveTbl95TxSelectedPumps(Tbl95TxSelectedPumps txSelectedPump);

	void deleteTbl95TxSelectedPumps();

	void deleteTbl95TxPumpSelectionModel();

	void saveTbl95TxPumpSelectionModel(Tbl95TxPumpSelectionModel tbl95TxPumpSelectionModel);

	List<Tbl01CentrifugalModelMaster> getmodelMaster(CentrifugalFanSelectionModel fanSelectionModel);

	List<Tbl14PrimemoverMaster> getPrimeMoverForMotorSelection(double primemoverCal,
			Tbl01CentrifugalModelMaster modelmaster, CentrifugalFanSelectionModel fanSelectionModel, Integer selectedSpeed);

	Tbl14PrimemoverMaster getPrimeMoverData(Integer primemoverId);

	public Tbl1401Motortype gettbl1401Motortype(int id);

	List<String> getEffclass(String projection, String restType, String restValue);

	List<String> getAmbientemp(String string, String string2, String string3);

	List<Double> getTempPrimemoverSpeed(CentrifugalFanSelectionModel selectionModel);

}
