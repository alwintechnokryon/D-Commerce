package com.technokryon.ecommerce.apidoc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.apidoc.dao.ApiDocumentDao;
import com.technokryon.ecommerce.apidoc.pojo.Api;
import com.technokryon.ecommerce.apidoc.pojo.Module;

@Service("ApiDocumentService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ApiDocumentServiceImpl implements ApiDocumentService {

	@Autowired
	private ApiDocumentDao apiDocumentDao;

	@Override
	public List<Module> getModuleList(Module module) {

		return apiDocumentDao.getModuleList(module);
	}

	@Override
	public List<Api> getDetailById(Module module) {

		return apiDocumentDao.getDetailById(module);
	}

	@Override
	public void addApi(Module module) {
		
		apiDocumentDao.addApi(module);
		
	}

	@Override
	public void addParams(Api api) {
		
		apiDocumentDao.addParams(api);

		
	}

}
