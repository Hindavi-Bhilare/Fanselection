
package com.velotech.fanselection.dtos;

import java.util.List;

public class TemplateMasterDto {

	private int id;

	private String fileName;

	private String layout;

	private String layersList;

	private String description;

	private String name;

	private String[] attachedFile;

	private String parameterList;

	private String[] fileNames;

	private List<Integer> documentIds;

	private Integer documentId;

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public String getFileName() {

		return fileName;
	}

	public void setFileName(String fileName) {

		this.fileName = fileName;
	}

	public String getLayout() {

		return layout;
	}

	public void setLayout(String layout) {

		this.layout = layout;
	}

	public String getLayersList() {

		return layersList;
	}

	public void setLayersList(String layersList) {

		this.layersList = layersList;
	}

	public String getDescription() {

		return description;
	}

	public void setDescription(String description) {

		this.description = description;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String[] getAttachedFile() {

		return attachedFile;
	}

	public void setAttachedFile(String[] attachedFile) {

		this.attachedFile = attachedFile;
	}

	public String getParameterList() {
		return parameterList;
	}

	public void setParameterList(String parameterList) {
		this.parameterList = parameterList;
	}

	public String[] getFileNames() {
		return fileNames;
	}

	public void setFileNames(String[] fileNames) {
		this.fileNames = fileNames;
	}

	public List<Integer> getDocumentIds() {
		return documentIds;
	}

	public void setDocumentIds(List<Integer> documentIds) {
		this.documentIds = documentIds;
	}

	public Integer getDocumentId() {
		return documentId;
	}

	public void setDocumentId(Integer documentId) {
		this.documentId = documentId;
	}

}
