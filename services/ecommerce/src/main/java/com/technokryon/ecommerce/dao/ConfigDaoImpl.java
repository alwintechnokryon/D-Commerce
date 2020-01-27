package com.technokryon.ecommerce.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.model.TKECMATTRIBUTE;
import com.technokryon.ecommerce.model.TKECMCOUNTRY;
import com.technokryon.ecommerce.model.TKECMPRODUCTPAYMENTTYPE;
import com.technokryon.ecommerce.model.TKECMPRODUCTTYPE;
import com.technokryon.ecommerce.model.TKECTSTATE;
import com.technokryon.ecommerce.pojo.Attribute;
import com.technokryon.ecommerce.pojo.Country;
import com.technokryon.ecommerce.pojo.ProductPaymentType;
import com.technokryon.ecommerce.pojo.ProductType;
import com.technokryon.ecommerce.pojo.State;

@Repository("ConfigDao")
@Transactional
@Component
public class ConfigDaoImpl implements ConfigDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<Country> countryList() {

		List<Country> country = new ArrayList<Country>();

		String countryList = "FROM TKECMCOUNTRY";

		Query countryListQuery = sessionFactory.getCurrentSession().createQuery(countryList);

		List<TKECMCOUNTRY> tKECMCOUNTRY = (List<TKECMCOUNTRY>) countryListQuery.getResultList();

		for (TKECMCOUNTRY tKECMCOUNTRY1 : tKECMCOUNTRY) {

			Country country1 = modelMapper.map(tKECMCOUNTRY1, Country.class);
			country.add(country1);
		}
		return country;
	}

	@Override
	public List<ProductType> productTypeList() {

		List<ProductType> productType = new ArrayList<ProductType>();

		String productTypeList = "FROM TKECMPRODUCTTYPE";

		Query productTypeListQuery = sessionFactory.getCurrentSession().createQuery(productTypeList);

		List<TKECMPRODUCTTYPE> tKECMPRODUCTTYPE = (List<TKECMPRODUCTTYPE>) productTypeListQuery.getResultList();

		for (TKECMPRODUCTTYPE tKECMPRODUCTTYPE1 : tKECMPRODUCTTYPE) {

			ProductType productType1 = modelMapper.map(tKECMPRODUCTTYPE1, ProductType.class);
			productType.add(productType1);
		}
		return productType;
	}

	@Override
	public List<ProductPaymentType> productPaymentTypeList() {

		List<ProductPaymentType> productPaymentType = new ArrayList<ProductPaymentType>();

		String productPaymentTypeList = "FROM TKECMPRODUCTPAYMENTTYPE";

		Query productPaymentTypeListQuery = sessionFactory.getCurrentSession().createQuery(productPaymentTypeList);

		List<TKECMPRODUCTPAYMENTTYPE> tKECMPRODUCTPAYMENTTYPE = (List<TKECMPRODUCTPAYMENTTYPE>) productPaymentTypeListQuery
				.getResultList();

		for (TKECMPRODUCTPAYMENTTYPE tKECMPRODUCTPAYMENTTYPE1 : tKECMPRODUCTPAYMENTTYPE) {

			ProductPaymentType productPaymentType1 = modelMapper.map(tKECMPRODUCTPAYMENTTYPE1,
					ProductPaymentType.class);
			productPaymentType.add(productPaymentType1);
		}
		return productPaymentType;
	}

	@Override
	public List<State> stateListById(Integer sTkecmcnAgId) {

		List<State> state = new ArrayList<State>();

		String stateListById = "FROM TKECTSTATE WHERE sTkecmcnAgId.cnAgId =:countryAgId";

		Query stateListByIdQuery = sessionFactory.getCurrentSession().createQuery(stateListById);

		stateListByIdQuery.setParameter("countryAgId", sTkecmcnAgId);

		List<TKECTSTATE> tKECTSTATE = stateListByIdQuery.getResultList();

		PropertyMap<TKECTSTATE, State> propertyMap = new PropertyMap<TKECTSTATE, State>() {
			protected void configure() {

				skip().setSFipsCode(null);
				skip().setSTkecmcnAgId(null);
			}
		};
		TypeMap<TKECTSTATE, State> typeMap = modelMapper.getTypeMap(TKECTSTATE.class, State.class);

		if (typeMap == null) {
			modelMapper.addMappings(propertyMap);
		}

		for (TKECTSTATE tKECTSTATE1 : tKECTSTATE) {

			State state1 = modelMapper.map(tKECTSTATE1, State.class);

			state.add(state1);
		}

		return state;
	}

	@Override
	public List<Attribute> attributeList() {

		List<Attribute> attribute = new ArrayList<Attribute>();

		String attributeList = "FROM TKECMATTRIBUTE";

		Query attributeListQuery = sessionFactory.getCurrentSession().createQuery(attributeList);

		List<TKECMATTRIBUTE> tKECMATTRIBUTE = (List<TKECMATTRIBUTE>) attributeListQuery.getResultList();

		for (TKECMATTRIBUTE tKECMATTRIBUTE1 : tKECMATTRIBUTE) {

			Attribute attribute1 = modelMapper.map(tKECMATTRIBUTE1, Attribute.class);
			attribute.add(attribute1);
		}
		return attribute;
	}

}
