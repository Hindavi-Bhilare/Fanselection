package com.velotech.fanselection.design.dao;

import java.util.List;

import com.velotech.fanselection.models.Tbl01Fantype;
import com.velotech.fanselection.utils.RequestWrapper;

public interface FanTypeDao {

	List<Tbl01Fantype> getSeries(RequestWrapper requestWrapper);

}
