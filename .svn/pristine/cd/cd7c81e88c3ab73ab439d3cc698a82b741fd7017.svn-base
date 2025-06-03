
package com.velotech.fanselection.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.velotech.fanselection.dtos.CompanyMasterDto;
import com.velotech.fanselection.dtos.PerformanceDataDto;
import com.velotech.fanselection.dtos.TemplateMasterDto;
import com.velotech.fanselection.generic.util.SearchCriteria;

public class RequestWrapper {

	private int start;

	private int limit;

	private String sort;

	private Date startDate;

	private Date endDate;

	private String modelName;

	private String serviceName;

	private String displayField;

	private String valueField;

	private List<Integer> ids;

	private String requestPayload;

	private List<SearchCriteria> searchCriterias;

	private String searchProperty;

	private Object searchValue;

	private Integer parentId;

	private List<PerformanceDataDto> performanceDataDTOs = new ArrayList<PerformanceDataDto>();

	//private QapMasterDto qapMasterDto = new QapMasterDto();

	private TemplateMasterDto templateMasterDto = new TemplateMasterDto();
	
	private CompanyMasterDto companyMasterDto=new CompanyMasterDto();

	public int getStart() {

		return start;
	}

	public void setStart(int start) {

		this.start = start;
	}

	public int getLimit() {

		return limit;
	}

	public void setLimit(int limit) {

		this.limit = limit;
	}

	public String getSort() {

		return sort;
	}

	public void setSort(String sort) {

		this.sort = sort;
	}

	public Date getStartDate() {

		return startDate;
	}

	public void setStartDate(Date startDate) {

		this.startDate = startDate;
	}

	public Date getEndDate() {

		return endDate;
	}

	public void setEndDate(Date endDate) {

		this.endDate = endDate;
	}

	public String getModelName() {

		return modelName;
	}

	public void setModelName(String modelName) {

		this.modelName = modelName;
	}

	public String getServiceName() {

		return serviceName;
	}

	public void setServiceName(String serviceName) {

		this.serviceName = serviceName;
	}

	public String getDisplayField() {

		return displayField;
	}

	public void setDisplayField(String displayField) {

		this.displayField = displayField;
	}

	public String getValueField() {

		return valueField;
	}

	public void setValueField(String valueField) {

		this.valueField = valueField;
	}

	public List<Integer> getIds() {

		return ids;
	}

	public void setIds(List<Integer> ids) {

		this.ids = ids;
	}

	public String getRequestPayload() {

		return requestPayload;
	}

	public void setRequestPayload(String requestPayload) {

		this.requestPayload = requestPayload;
	}

	public List<SearchCriteria> getSearchCriterias() {

		return searchCriterias;
	}

	public void setSearchCriterias(List<SearchCriteria> searchCriterias) {

		this.searchCriterias = searchCriterias;
	}

	public String getSearchProperty() {

		return searchProperty;
	}

	public void setSearchProperty(String searchProperty) {

		this.searchProperty = searchProperty;
	}

	public Object getSearchValue() {

		return searchValue;
	}

	public void setSearchValue(Object searchValue) {

		this.searchValue = searchValue;
	}

	public Pagination getPagination() {

		// TODO Auto-generated method stub
		return new Pagination(this.start, this.limit);
	}

	/**
	 * @return the sqlOrder
	 */
	public SqlOrder getSqlOrder() {

		return new SqlOrder(this.sort);
	}

	public Integer getParentId() {

		return parentId;
	}

	public void setParentId(Integer parentId) {

		this.parentId = parentId;
	}

	public List<PerformanceDataDto> getPerformanceDataDTOs() {

		return performanceDataDTOs;
	}

	public void setPerformanceDataDTOs(List<PerformanceDataDto> performanceDataDTOs) {

		this.performanceDataDTOs = performanceDataDTOs;
	}

	/*
	 * public QapMasterDto getQapMasterDto() {
	 * 
	 * return qapMasterDto; }
	 * 
	 * public void setQapMasterDto(QapMasterDto qapMasterDto) {
	 * 
	 * this.qapMasterDto = qapMasterDto; }
	 */

	public TemplateMasterDto getTemplateMasterDto() {

		return templateMasterDto;
	}

	public void setTemplateMasterDto(TemplateMasterDto templateMasterDto) {

		this.templateMasterDto = templateMasterDto;
	}

	
	public CompanyMasterDto getCompanyMasterDto() {
	
		return companyMasterDto;
	}

	
	public void setCompanyMasterDto(CompanyMasterDto companyMasterDto) {
	
		this.companyMasterDto = companyMasterDto;
	}
    
}
