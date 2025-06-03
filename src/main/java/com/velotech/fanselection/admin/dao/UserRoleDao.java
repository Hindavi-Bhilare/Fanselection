
package com.velotech.fanselection.admin.dao;

import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.Pagination;

public interface UserRoleDao {

	ApplicationResponse getRecords(String searchProperty, String searchValue, Pagination pagination);

}
