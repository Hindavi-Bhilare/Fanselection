
package com.velotech.fanselection.design.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.velotech.fanselection.utils.HibernateSession;
import com.velotech.fanselection.utils.VelotechUtil;

@Repository
public class StageCombinationDaoImpl extends HibernateSession implements StageCombinationDao {

	static Logger log = LogManager.getLogger(StageCombinationDaoImpl.class.getName());

	@Autowired
	VelotechUtil velotechUtil;

}
