
package com.velotech.fanselection.generic.dao;

import java.util.List;

import com.velotech.fanselection.utils.ApplicationResponse;
import com.velotech.fanselection.utils.Pagination;
import com.velotech.fanselection.utils.SqlOrder;

public interface GenericDao {

	boolean save(Object object);

	boolean saveAll(List<?> object);

	boolean updateAll(List<?> object);

	boolean update(Object object);

	boolean saveOrUpdate(Object object);

	boolean delete(Object object);

	boolean deleteAll(Object objects);

	boolean deleteAll(Class<?> o, List<Integer> ids);

	Object getRecordById(Class<?> o, Integer id);

	Object getRecordById(final Class<?> o, final String id);

	Object getRecordByIds(Class<?> o, List<Integer> ids);

	Object getRecordsByParentId(Class<?> o, String parentProperty, Object parentId);

	Object getRecords(Class<?> o);

	Object getRecords(Class<?> o, Object restrictions);

	ApplicationResponse getRecords(Class<?> o, Object restrictions, Pagination pagination);

	ApplicationResponse getRecords(Class<?> o, Object conjunction, Pagination pagination, SqlOrder sqlOrder);

	ApplicationResponse getRecords(Class<?> o, Object restrictions, Pagination pagination, List<SqlOrder> sqlOrders);

	ApplicationResponse getComboRecords(Class<?> o, String displayField, String valueField, Object restrictions);

	Object findByParam(Class<?> o, String fieldName, String value);

	Object findByParam(Class<?> o, String fieldName, List<String> values);

	Object getUniqueRecord(Class<?> o, String fieldName, String value);

	boolean executeUpdate(String query);

	List<String> getTables();

}
