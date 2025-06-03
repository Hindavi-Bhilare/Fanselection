
package com.velotech.fanselection.company.service;

import java.util.List;




import java.util.Set;

import com.velotech.fanselection.models.Tbl01Fantype;
import com.velotech.fanselection.models.Tbl14PrimemoverMaster;
import com.velotech.fanselection.models.Tbl28SelectedFan;
import com.velotech.fanselection.models.Tbl28SelectedFanVariant;
import com.velotech.fanselection.models.Tbl28SelectedpricingDetails;
import com.velotech.fanselection.selection.models.SelectedCentrifugalFan;

public interface VelotechService {

	List<Tbl28SelectedpricingDetails> getPrimeMoverPricingDetails(Tbl14PrimemoverMaster tbl14PrimemoverMaster);

	List<Tbl28SelectedpricingDetails> getFanPricingDetails(SelectedCentrifugalFan selectedFan, Tbl28SelectedFan tbl28SelectedFan,
			Set<Tbl28SelectedFanVariant> tbl28SelectedFanVariants, Tbl01Fantype tbl01Fantype);
}
