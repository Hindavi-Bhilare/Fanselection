
package com.velotech.fanselection.dxf.dao;

import java.util.List;



import com.velotech.fanselection.models.Tbl03GenericBomData;
//import com.velotech.fanselection.models.Tbl05FlangeMaster;
import com.velotech.fanselection.models.Tbl14PrimemoverMaster;
import com.velotech.fanselection.models.Tbl28SelectedPrimemover;
import com.velotech.fanselection.models.Tbl28SelectedFanVariant;
import com.velotech.fanselection.models.Tbl58UserCompany;

public interface DxfDao {

//	List<String> getTab4DatasFromFlangeMaster(Integer mocId, Integer seriesId);

	List<Tbl58UserCompany> getUserCompany();

	Integer getVariantId(Integer pumpTypeId, List<Tbl28SelectedFanVariant> tbl28SelectedPumpVariants);

	//Tbl13Barepumpga getBarePumpga(Tbl02Modelmaster tbl02Modelmaster, Integer stage, String mocID);

	//Tbl15Pumpsetga getPumpsetga(Tbl02Modelmaster tbl02Modelmaster, Integer stage, String mocID);

	//Tbl05FlangeMaster getFlangeMaster(Integer pumpTypeId, String size, String flangetype);

	//Tbl18Csd getCsd(Tbl02Modelmaster tbl02Modelmaster, Integer stage, String mocID);

	List<Tbl03GenericBomData> getGenericbomData(int seriesId, String shaftGroup, String mocStd, int stage, Integer variantId);

	Tbl14PrimemoverMaster getPrimeMover(Tbl28SelectedPrimemover tbl28SelectedPrimemover);


}
