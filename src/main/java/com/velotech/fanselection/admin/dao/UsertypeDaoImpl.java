
package com.velotech.fanselection.admin.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.velotech.fanselection.generic.dao.GenericDao;
import com.velotech.fanselection.utils.HibernateSession;

@Repository
public class UsertypeDaoImpl extends HibernateSession implements UsertypeDao {

	static Logger log = LogManager.getLogger(UsertypeDaoImpl.class.getName());

	@Autowired
	private GenericDao genericDao;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Boolean deleteUsertypePumptype(Integer usertypeId) {

		boolean ans = false;
		try {
			String query = "DELETE FROM tbl_51_usertype_fantype WHERE userTypeId = " + usertypeId;

			genericDao.executeUpdate(query);

			ans = true;
		} catch (Exception e) {
			ans = false;
			log.error(e.getMessage(), e);
		}
		return ans;
	}

}
