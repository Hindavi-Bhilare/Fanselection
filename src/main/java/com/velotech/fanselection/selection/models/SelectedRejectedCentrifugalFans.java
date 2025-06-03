
package com.velotech.fanselection.selection.models;

import java.util.ArrayList;

import java.util.List;

public class SelectedRejectedCentrifugalFans { 

	private List<SelectedCentrifugalFan> selectedFans = new ArrayList<>();

	private List<RejectedCentrifugalFan> rejectedFans = new ArrayList<>();

	public List<SelectedCentrifugalFan> getSelectedFans() {
		return selectedFans;
	}

	public void setSelectedFans(List<SelectedCentrifugalFan> selectedFans) {
		this.selectedFans = selectedFans;
	}

	public List<RejectedCentrifugalFan> getRejectedFans() {
		return rejectedFans;
	}

	public void setRejectedFans(List<RejectedCentrifugalFan> rejectedFans) {
		this.rejectedFans = rejectedFans;
	}

}
