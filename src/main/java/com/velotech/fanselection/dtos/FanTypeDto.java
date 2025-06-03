
package com.velotech.fanselection.dtos;

import java.util.List;

public class FanTypeDto {

	private int id;

	private String series;

	private String fanType;

	private String bladeType;

	private String driveType;

	private String mounting;

	private String uuid;

	private Integer documentId;

	private String fileName;

	private String imageName;

	private Integer imageId;

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getSeries() {

		return series;
	}

	public void setSeries(String series) {

		this.series = series;
	}

	public String getFanType() {

		return fanType;
	}

	public void setFanType(String fanType) {

		this.fanType = fanType;
	}

	public String getBladeType() {
		return bladeType;
	}

	public void setBladeType(String bladeType) {
		this.bladeType = bladeType;
	}

	public String getDriveType() {
		return driveType;
	}

	public void setDriveType(String driveType) {
		this.driveType = driveType;
	}

	public String getMounting() {

		return mounting;
	}

	public void setMounting(String mounting) {

		this.mounting = mounting;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Integer getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Integer getImageId() {
		return imageId;
	}

	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

}
