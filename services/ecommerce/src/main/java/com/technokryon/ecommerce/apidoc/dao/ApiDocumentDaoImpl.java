package com.technokryon.ecommerce.apidoc.dao;

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

import com.technokryon.ecommerce.apidoc.model.APIDMMODULE;
import com.technokryon.ecommerce.apidoc.model.APIDTAPI;
import com.technokryon.ecommerce.apidoc.model.APIDTAPIPARAMS;
import com.technokryon.ecommerce.apidoc.pojo.Api;
import com.technokryon.ecommerce.apidoc.pojo.ApiParams;
import com.technokryon.ecommerce.apidoc.pojo.Module;
import com.technokryon.ecommerce.model.TKECTSTATE;
import com.technokryon.ecommerce.pojo.State;

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
}
