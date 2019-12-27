package com.technokryon.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.dao.ConfigDao;
import com.technokryon.ecommerce.pojo.ATTRIBUTE;
import com.technokryon.ecommerce.pojo.COUNTRY;
import com.technokryon.ecommerce.pojo.PRODUCTPAYMENTTYPE;
import com.technokryon.ecommerce.pojo.PRODUCTTYPE;
import com.technokryon.ecommerce.pojo.STATE;

@Service("ConfigService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ConfigServiceImpl implements ConfigService {

	@Autowired
	private ConfigDao O_ConfigDao;

	@Override
	public List<COUNTRY> countryList() {
		// TODO Auto-generated method stub
		return O_ConfigDao.countryList();
	}

	@Override
	public List<PRODUCTTYPE> productTypeList() {
		// TODO Auto-generated method stub
		return O_ConfigDao.productTypeList();
	}

	@Override
	public List<PRODUCTPAYMENTTYPE> productPaymentTypeList() {
		// TODO Auto-generated method stub
		return O_ConfigDao.productPaymentTypeList();
	}

	@Override
	public List<STATE> stateListById(Integer sTkecmcnAgId) {
		// TODO Auto-generated method stub
		return O_ConfigDao.stateListById(sTkecmcnAgId);
	}

	@Override
	public List<ATTRIBUTE> attributeList() {
		// TODO Auto-generated method stub
		return O_ConfigDao.attributeList();
	}

}
