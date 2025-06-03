
package com.velotech.fanselection.offer.dto;

import java.util.List;

public class OfferTreeDto {

	private Integer offerFanId;

	private String text;

	private boolean leaf;

	private boolean expanded;

	private String iconCls;

	// used in extjs card panel
	// this card value is used to open respective card in offer details panel
	private String card;

	private List<Object> data;

	public Integer getOfferFanId() {
		return offerFanId;
	}

	public void setOfferFanId(Integer offerFanId) {
		this.offerFanId = offerFanId;
	}

	public String getText() {

		return text;
	}

	public void setText(String text) {

		this.text = text;
	}

	public boolean isLeaf() {

		return leaf;
	}

	public void setLeaf(boolean leaf) {

		this.leaf = leaf;
	}

	public boolean isExpanded() {

		return expanded;
	}

	public void setExpanded(boolean expanded) {

		this.expanded = expanded;
	}

	public String getIconCls() {

		return iconCls;
	}

	public void setIconCls(String iconCls) {

		this.iconCls = iconCls;
	}

	public String getCard() {

		return card;
	}

	public void setCard(String card) {

		this.card = card;
	}

	public List<Object> getData() {

		return data;
	}

	public void setData(List<Object> data) {

		this.data = data;
	}

}
