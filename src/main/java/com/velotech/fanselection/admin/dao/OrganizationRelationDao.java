
package com.velotech.fanselection.admin.dao;

import java.util.List;

import com.velotech.fanselection.models.Tbl59Organisation;
import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.Pagination;

public interface OrganizationRelationDao {

	List<Tbl59Organisation> getLoginIdForRelation(Integer organizationId);

	List<String> getRelation1(Integer organizationId);

	ApplicationResponse getTbl59OrganisationRelationData(Integer parentId, String organisationCode, Pagination pagination);

}
