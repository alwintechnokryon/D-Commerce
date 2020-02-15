package com.technokryon.ecommerce.apidoc.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.apidoc.model.APIDMMODULE;
import com.technokryon.ecommerce.apidoc.model.APIDTAPI;
import com.technokryon.ecommerce.apidoc.model.APIDTAPIPARAMS;
import com.technokryon.ecommerce.apidoc.pojo.Api;
import com.technokryon.ecommerce.apidoc.pojo.ApiParams;
import com.technokryon.ecommerce.apidoc.pojo.Module;

@Repository("ApiDocumentDao")
@Transactional
@Component
public class ApiDocumentDaoImpl implements ApiDocumentDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<Module> getModuleList(Module module) {

		List<Module> module1 = new ArrayList<>();

		String getModuleList = "FROM APIDMMODULE WHERE mFlag =:flag";

		Query getModuleListQuery = sessionFactory.getCurrentSession().createQuery(getModuleList);

		getModuleListQuery.setParameter("flag", module.getMFlag());

		List<APIDMMODULE> aPIDMMODULE = getModuleListQuery.getResultList();

		PropertyMap<APIDMMODULE, Module> propertyMap = new PropertyMap<APIDMMODULE, Module>() {
			protected void configure() {

				skip().setMFlag(null);
			}
		};
		TypeMap<APIDMMODULE, Module> typeMap = modelMapper.getTypeMap(APIDMMODULE.class, Module.class);

		if (typeMap == null) {
			modelMapper.addMappings(propertyMap);
		}

		for (APIDMMODULE aPIDMMODULE1 : aPIDMMODULE) {

			Module module2 = modelMapper.map(aPIDMMODULE1, Module.class);
			module1.add(module2);

		}

		return module1;
	}

	@Override
	public List<Api> getDetailById(Module module) {

		List<Api> api1 = new ArrayList<>();

		String getApiList = "FROM APIDTAPI WHERE aApidmmAgId.mAgId =:moduleAgId ";

		Query getApiListQuery = sessionFactory.getCurrentSession().createQuery(getApiList);

		getApiListQuery.setParameter("moduleAgId", module.getMAgId());

		List<APIDTAPI> aPIDTAPI = getApiListQuery.getResultList();

		for (APIDTAPI apidtapi1 : aPIDTAPI) {

			Api api2 = modelMapper.map(apidtapi1, Api.class);

			String getParamsList = "FROM APIDTAPIPARAMS WHERE apApidtaAgId.aAgId =:apiAgId";

			Query getParamsListQuery = sessionFactory.getCurrentSession().createQuery(getParamsList);

			getParamsListQuery.setParameter("apiAgId", apidtapi1.getAAgId());

			List<APIDTAPIPARAMS> aPIDTAPIPARAMS = getParamsListQuery.getResultList();

			List<ApiParams> apiParams = new ArrayList<>();

			for (APIDTAPIPARAMS aPIDTAPIPARAMS1 : aPIDTAPIPARAMS) {

				ApiParams apiParams1 = modelMapper.map(aPIDTAPIPARAMS1, ApiParams.class);

				apiParams.add(apiParams1);
			}
			api2.setApiParams(apiParams);

			api1.add(api2);

		}

		return api1;
	}

	@Override
	public void addApi(Module module) {

		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		try {

			APIDMMODULE aPIDMMODULE = new APIDMMODULE();

			aPIDMMODULE.setMName(module.getMName());
			aPIDMMODULE.setMFlag(module.getMFlag());
			session.save(aPIDMMODULE);

			for (Api api : module.getApi()) {

				APIDTAPI aPIDTAPI = new APIDTAPI();

				aPIDTAPI.setABody(api.getABody());
				aPIDTAPI.setADescription(api.getADescription());
				aPIDTAPI.setAExampleUrl(api.getAExampleUrl());
				aPIDTAPI.setAName(api.getAName());
				aPIDTAPI.setARequestHeader(api.getARequestHeader());
				aPIDTAPI.setAResponseCode(api.getAResponseCode());
				aPIDTAPI.setAResponseHeader(api.getAResponseHeader());
				aPIDTAPI.setAType(api.getAType());
				aPIDTAPI.setAUrl(api.getAUrl());
				aPIDTAPI.setAApidmmAgId(session.get(APIDMMODULE.class, aPIDMMODULE.getMAgId()));
				session.save(aPIDTAPI);

			}

		} catch (Exception e) {
			e.printStackTrace();
			if (transaction.isActive()) {
				transaction.rollback();
			}
			session.close();
		}
	}

	@Override
	public void addParams(Api api) {

		for (ApiParams apiParams : api.getApiParams()) {

			APIDTAPIPARAMS aPIDTAPIPARAMS = new APIDTAPIPARAMS();

			aPIDTAPIPARAMS.setApDatatype(apiParams.getApDatatype());
			aPIDTAPIPARAMS.setApDescription(apiParams.getApDescription());
			aPIDTAPIPARAMS.setApParamName(apiParams.getApParamName());
			aPIDTAPIPARAMS.setApApidtaAgId(sessionFactory.getCurrentSession().get(APIDTAPI.class, api.getAAgId()));
			aPIDTAPIPARAMS.setApRequired(apiParams.getApRequired());
			sessionFactory.getCurrentSession().save(aPIDTAPIPARAMS);
		}

	}
}
