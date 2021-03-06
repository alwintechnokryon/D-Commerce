package com.technokryon.ecommerce.apidoc.dao;

import java.util.List;

import com.technokryon.ecommerce.apidoc.pojo.Api;
import com.technokryon.ecommerce.apidoc.pojo.Module;

public interface ApiDocumentDao {

	List<Module> getModuleList(Module module);

	List<Api> getDetailById(Module module);

	void addApi(Module module);

	void addParams(Api api);

}
