package com.technokryon.ecommerce.service;

import java.util.List;

import com.technokryon.ecommerce.pojo.Attribute;
import com.technokryon.ecommerce.pojo.Country;
import com.technokryon.ecommerce.pojo.ProductPaymentType;
import com.technokryon.ecommerce.pojo.ProductType;
import com.technokryon.ecommerce.pojo.State;

public interface ConfigService {

	List<Country> countryList();

	List<ProductType> productTypeList();

	List<ProductPaymentType> productPaymentTypeList();

	List<State> stateListById(Integer sTkecmcnAgId);

	List<Attribute> attributeList();

}
