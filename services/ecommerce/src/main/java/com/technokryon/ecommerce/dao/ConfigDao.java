package com.technokryon.ecommerce.dao;

import java.util.List;

import com.technokryon.ecommerce.pojo.ATTRIBUTE;
import com.technokryon.ecommerce.pojo.COUNTRY;
import com.technokryon.ecommerce.pojo.PRODUCTPAYMENTTYPE;
import com.technokryon.ecommerce.pojo.PRODUCTTYPE;
import com.technokryon.ecommerce.pojo.STATE;

public interface ConfigDao {

	List<COUNTRY> countryList();

	List<PRODUCTTYPE> productTypeList();

	List<PRODUCTPAYMENTTYPE> productPaymentTypeList();

	List<STATE> stateListById(Integer sTkecmcnAgId);

	List<ATTRIBUTE> attributeList();

}
