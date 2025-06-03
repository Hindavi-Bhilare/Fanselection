
package com.velotech.fanselection.dtos;

public class OfferPriceAddonDto {

	private int id;

	private Integer offerRevId;

	private String offerRev;

	private String articleNo;

	private String description;

	private Double price;

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public Integer getOfferRevId() {

		return offerRevId;
	}

	public void setOfferRevId(Integer offerRevId) {

		this.offerRevId = offerRevId;
	}

	public String getOfferRev() {

		return offerRev;
	}

	public void setOfferRev(String offerRev) {

		this.offerRev = offerRev;
	}

	public String getArticleNo() {

		return articleNo;
	}

	public void setArticleNo(String articleNo) {

		this.articleNo = articleNo;
	}

	public String getDescription() {

		return description;
	}

	public void setDescription(String description) {

		this.description = description;
	}

	public Double getPrice() {

		return price;
	}

	public void setPrice(Double price) {

		this.price = price;
	}

}
