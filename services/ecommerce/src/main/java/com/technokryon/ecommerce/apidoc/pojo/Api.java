package com.technokryon.ecommerce.apidoc.pojo;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Api {

	Integer aAgId;
	Integer aApidmmAgId;
	String aName;
	String aDescription;
	String aType;
	String aUrl;
	String aExampleUrl;
	String aRequestHeader;
	String aResponseHeader;
	String aBody;
	Integer aResponseCode;
	List<ApiParams> apiParams;
}
