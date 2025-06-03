
package com.velotech.fanselection.admin.dao;

import java.util.List;

import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.RequestWrapper;

public interface LoginHistoryDao {

	ApplicationResponse getRecords(RequestWrapper requestWrapper);

	List<Object> getCountRecords(RequestWrapper requestWrapper);

	ApplicationResponse getExcelRecords(RequestWrapper requestWrapper);

}
