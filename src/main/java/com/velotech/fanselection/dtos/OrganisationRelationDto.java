
package com.velotech.fanselection.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrganisationRelationDto {

	private int id;

	private Integer parentOrganizationId;

	private Integer organizationId;

	private String parentOrganizationCode;

	private String organizationCode;

	public int getId() {

		return id;
	}

	public void setId(int id) {

		this.id = id;
	}

	public Integer getParentOrganizationId() {

		return parentOrganizationId;
	}

	public void setParentOrganizationId(Integer parentOrganizationId) {

		this.parentOrganizationId = parentOrganizationId;
	}

	public Integer getOrganizationId() {

		return organizationId;
	}

	public void setOrganizationId(Integer organizationId) {

		this.organizationId = organizationId;
	}

	public String getParentOrganizationCode() {

		return parentOrganizationCode;
	}

	public void setParentOrganizationCode(String parentOrganizationCode) {

		this.parentOrganizationCode = parentOrganizationCode;
	}

	public String getOrganizationCode() {

		return organizationCode;
	}

	public void setOrganizationCode(String organizationCode) {

		this.organizationCode = organizationCode;
	}

}
