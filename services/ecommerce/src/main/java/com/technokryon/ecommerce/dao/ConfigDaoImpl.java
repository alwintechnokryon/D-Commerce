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
import com.technokryon.ecommerce.model.TKECTUSERADDRESS;
import com.technokryon.ecommerce.pojo.Attribute;
import com.technokryon.ecommerce.pojo.Country;
import com.technokryon.ecommerce.pojo.ProductPaymentType;
import com.technokryon.ecommerce.pojo.ProductType;
import com.technokryon.ecommerce.pojo.State;
import com.technokryon.ecommerce.pojo.UserAddress;

@Repository("ConfigDao")
@Transactional
@Component
public class ConfigDaoImpl implements ConfigDao {

	@Autowired
	private SessionFactory O_SessionFactory;

	@Autowired
	private ModelMapper O_ModelMapper;

	@Override
	public List<Country> countryList() {

		List<Country> LO_Country = new ArrayList<Country>();

		String countryList = "FROM TKECMCOUNTRY";

		Query countryListQuery = O_SessionFactory.getCurrentSession().createQuery(countryList);

		List<TKECMCOUNTRY> LO_TKECMCOUNTRY = (List<TKECMCOUNTRY>) countryListQuery.list();

		for (TKECMCOUNTRY O_TKECMCOUNTRY : LO_TKECMCOUNTRY) {

			Country O_Country = O_ModelMapper.map(O_TKECMCOUNTRY, Country.class);
			LO_Country.add(O_Country);
		}
		return LO_Country;
	}

	@Override
	public List<ProductType> productTypeList() {

		List<ProductType> LO_ProductType = new ArrayList<ProductType>();

		String productTypeList = "FROM TKECMPRODUCTTYPE";

		Query productTypeListQuery = O_SessionFactory.getCurrentSession().createQuery(productTypeList);

		List<TKECMPRODUCTTYPE> LO_TKECMPRODUCTTYPE = (List<TKECMPRODUCTTYPE>) productTypeListQuery.list();

		for (TKECMPRODUCTTYPE O_TKECMPRODUCTTYPE : LO_TKECMPRODUCTTYPE) {

			ProductType O_ProductType = O_ModelMapper.map(O_TKECMPRODUCTTYPE, ProductType.class);
			LO_ProductType.add(O_ProductType);
		}
		return LO_ProductType;
	}

	@Override
	public List<ProductPaymentType> productPaymentTypeList() {

		List<ProductPaymentType> LO_ProductPaymentType = new ArrayList<ProductPaymentType>();

		String productPaymentTypeList = "FROM TKECMPRODUCTPAYMENTTYPE";

		Query productPaymentTypeListQuery = O_SessionFactory.getCurrentSession().createQuery(productPaymentTypeList);

		List<TKECMPRODUCTPAYMENTTYPE> LO_TKECMPRODUCTPAYMENTTYPE = (List<TKECMPRODUCTPAYMENTTYPE>) productPaymentTypeListQuery
				.list();

		for (TKECMPRODUCTPAYMENTTYPE O_TKECMPRODUCTPAYMENTTYPE : LO_TKECMPRODUCTPAYMENTTYPE) {

			ProductPaymentType O_ProductPaymentType = O_ModelMapper.map(O_TKECMPRODUCTPAYMENTTYPE,
					ProductPaymentType.class);
			LO_ProductPaymentType.add(O_ProductPaymentType);
		}
		return LO_ProductPaymentType;
	}

	@Override
	public List<State> stateListById(Integer sTkecmcnAgId) {

		List<State> LO_State = new ArrayList<State>();

		String stateListById = "FROM TKECTSTATE WHERE sTkecmcnAgId.cnAgId =:countryAgId";

		Query stateListByIdQuery = O_SessionFactory.getCurrentSession().createQuery(stateListById);

		stateListByIdQuery.setParameter("countryAgId", sTkecmcnAgId);

		List<TKECTSTATE> LO_TKECTSTATE = stateListByIdQuery.getResultList();

		PropertyMap<TKECTSTATE, State> O_PropertyMap = new PropertyMap<TKECTSTATE, State>() {
			protected void configure() {

				skip().setSFipsCode(null);
				skip().setSTkecmcnAgId(null);
			}
		};
		TypeMap<TKECTSTATE, State> O_TypeMap = O_ModelMapper.getTypeMap(TKECTSTATE.class, State.class);

		if (O_TypeMap == null) {
			O_ModelMapper.addMappings(O_PropertyMap);
		}

		for (TKECTSTATE O_TKECTSTATE : LO_TKECTSTATE) {

			State O_State = O_ModelMapper.map(O_TKECTSTATE, State.class);

			LO_State.add(O_State);
		}

		return LO_State;
	}

	@Override
	public List<Attribute> attributeList() {

		List<Attribute> LO_Attribute = new ArrayList<Attribute>();

		String attributeList = "FROM TKECMATTRIBUTE";

		Query attributeListQuery = O_SessionFactory.getCurrentSession().createQuery(attributeList);

		List<TKECMATTRIBUTE> LO_TKECMATTRIBUTE = (List<TKECMATTRIBUTE>) attributeListQuery.list();

		for (TKECMATTRIBUTE O_TKECMATTRIBUTE : LO_TKECMATTRIBUTE) {

			Attribute O_Attribute = O_ModelMapper.map(O_TKECMATTRIBUTE, Attribute.class);
			LO_Attribute.add(O_Attribute);
		}
		return LO_Attribute;
	}

}
