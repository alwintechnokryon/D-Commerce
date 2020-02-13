package com.technokryon.ecommerce.apidoc.service;

import java.util.List;

import com.technokryon.ecommerce.apidoc.pojo.Api;
import com.technokryon.ecommerce.apidoc.pojo.Module;

public interface ApiDocumentService {

	List<Module> getModuleList(Module module);

	List<Api> getDetailById(Module module);

}
