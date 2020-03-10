package com.technokryon.ecommerce.apidoc.pojo;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Module {

	Integer mAgId;
	String mName;
	String mFlag;
	List<Api> api;

}
