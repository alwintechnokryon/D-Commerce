package com.technokryon.ecommerce.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
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
import com.technokryon.ecommerce.pojo.ATTRIBUTE;
import com.technokryon.ecommerce.pojo.COUNTRY;
import com.technokryon.ecommerce.pojo.PRODUCTPAYMENTTYPE;
import com.technokryon.ecommerce.pojo.PRODUCTTYPE;
import com.technokryon.ecommerce.pojo.STATE;
import com.technokryon.ecommerce.pojo.USERADDRESS;

@Repository("ConfigDao")
@Transactional
@Component
public class ConfigDaoImpl implements ConfigDao {

	@Autowired
	private SessionFactory O_SessionFactory;

	@Autowired
	private ModelMapper O_ModelMapper;

	@Override
	public List<COUNTRY> countryList() {

		List<COUNTRY> LO_COUNTRY = new ArrayList<COUNTRY>();

		String countryList = "FROM TKECMCOUNTRY";

		Query countryListQuery = O_SessionFactory.getCurrentSession().createQuery(countryList);

		List<TKECMCOUNTRY> LO_TKECMCOUNTRY = (List<TKECMCOUNTRY>) countryListQuery.list();

		for (TKECMCOUNTRY O_TKECMCOUNTRY : LO_TKECMCOUNTRY) {

			COUNTRY O_COUNTRY = O_ModelMapper.map(O_TKECMCOUNTRY, COUNTRY.class);
			LO_COUNTRY.add(O_COUNTRY);
		}
		return LO_COUNTRY;
	}

	@Override
	public List<PRODUCTTYPE> productTypeList() {

		List<PRODUCTTYPE> LO_PRODUCTTYPE = new ArrayList<PRODUCTTYPE>();

		String productTypeList = "FROM TKECMPRODUCTTYPE";

		Query productTypeListQuery = O_SessionFactory.getCurrentSession().createQuery(productTypeList);

		List<TKECMPRODUCTTYPE> LO_TKECMPRODUCTTYPE = (List<TKECMPRODUCTTYPE>) productTypeListQuery.list();

		for (TKECMPRODUCTTYPE O_TKECMPRODUCTTYPE : LO_TKECMPRODUCTTYPE) {

			PRODUCTTYPE O_PRODUCTTYPE = O_ModelMapper.map(O_TKECMPRODUCTTYPE, PRODUCTTYPE.class);
			LO_PRODUCTTYPE.add(O_PRODUCTTYPE);
		}
		return LO_PRODUCTTYPE;
	}

	@Override
	public List<PRODUCTPAYMENTTYPE> productPaymentTypeList() {

		List<PRODUCTPAYMENTTYPE> LO_PRODUCTPAYMENTTYPE = new ArrayList<PRODUCTPAYMENTTYPE>();

		String productPaymentTypeList = "FROM TKECMPRODUCTPAYMENTTYPE";

		Query productPaymentTypeListQuery = O_SessionFactory.getCurrentSession().createQuery(productPaymentTypeList);

		List<TKECMPRODUCTPAYMENTTYPE> LO_TKECMPRODUCTPAYMENTTYPE = (List<TKECMPRODUCTPAYMENTTYPE>) productPaymentTypeListQuery
				.list();

		for (TKECMPRODUCTPAYMENTTYPE O_TKECMPRODUCTPAYMENTTYPE : LO_TKECMPRODUCTPAYMENTTYPE) {

			PRODUCTPAYMENTTYPE O_PRODUCTPAYMENTTYPE = O_ModelMapper.map(O_TKECMPRODUCTPAYMENTTYPE,
					PRODUCTPAYMENTTYPE.class);
			LO_PRODUCTPAYMENTTYPE.add(O_PRODUCTPAYMENTTYPE);
		}
		return LO_PRODUCTPAYMENTTYPE;
	}

	@Override
	public List<STATE> stateListById(Integer sTkecmcnAgId) {

		List<STATE> LO_STATE = new ArrayList<STATE>();

		String stateListById = "FROM TKECTSTATE WHERE sTkecmcnAgId.cnAgId =:countryAgId";

		Query stateListByIdQuery = O_SessionFactory.getCurrentSession().createQuery(stateListById);

		stateListByIdQuery.setParameter("countryAgId", sTkecmcnAgId);

		List<TKECTSTATE> LO_TKECTSTATE = stateListByIdQuery.getResultList();

		PropertyMap<TKECTSTATE, STATE> skipFieldState = new PropertyMap<TKECTSTATE, STATE>() {
			protected void configure() {

				skip().setSFipsCode(null);
				skip().setSTkecmcnAgId(null);
			}
		};
		O_ModelMapper.addMappings(skipFieldState);

		for (TKECTSTATE O_TKECTSTATE : LO_TKECTSTATE) {

			STATE O_STATE = O_ModelMapper.map(O_TKECTSTATE, STATE.class);

			LO_STATE.add(O_STATE);
		}

		return LO_STATE;
	}

	@Override
	public List<ATTRIBUTE> attributeList() {

		List<ATTRIBUTE> LO_ATTRIBUTE = new ArrayList<ATTRIBUTE>();

		String attributeList = "FROM TKECMATTRIBUTE";

		Query attributeListQuery = O_SessionFactory.getCurrentSession().createQuery(attributeList);

		List<TKECMATTRIBUTE> LO_TKECMATTRIBUTE = (List<TKECMATTRIBUTE>) attributeListQuery.list();

		for (TKECMATTRIBUTE O_TKECMATTRIBUTE : LO_TKECMATTRIBUTE) {

			ATTRIBUTE O_ATTRIBUTE = O_ModelMapper.map(O_TKECMATTRIBUTE, ATTRIBUTE.class);
			LO_ATTRIBUTE.add(O_ATTRIBUTE);
		}
		return LO_ATTRIBUTE;
	}

}
