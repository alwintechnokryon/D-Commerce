package com.technokryon.ecommerce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.dao.ConfigDao;
import com.technokryon.ecommerce.pojo.Attribute;
import com.technokryon.ecommerce.pojo.Country;
import com.technokryon.ecommerce.pojo.ProductPaymentType;
import com.technokryon.ecommerce.pojo.ProductType;
import com.technokryon.ecommerce.pojo.State;


@Service("ConfigService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ConfigServiceImpl implements ConfigService {

	@Autowired
	private ConfigDao configDao;

	@Override
	public List<Country> countryList() {
		
		return configDao.countryList();
	}

	@Override
	public List<ProductType> productTypeList() {
		
		return configDao.productTypeList();
	}

	@Override
	public List<ProductPaymentType> productPaymentTypeList() {
		
		return configDao.productPaymentTypeList();
	}

	@Override
	public List<State> stateListById(Integer sTkecmcnAgId) {
		
		return configDao.stateListById(sTkecmcnAgId);
	}

	@Override
	public List<Attribute> attributeList() {
		
		return configDao.attributeList();
	}

}
