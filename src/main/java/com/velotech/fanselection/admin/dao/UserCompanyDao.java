
package com.velotech.fanselection.admin.dao;

import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.Pagination;

public interface UserCompanyDao {

	ApplicationResponse getRecords(String searchProperty, String searchValue, Pagination pagination);

}
