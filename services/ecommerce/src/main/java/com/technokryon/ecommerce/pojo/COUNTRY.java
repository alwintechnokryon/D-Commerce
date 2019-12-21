package com.technokryon.ecommerce.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class COUNTRY {

	Integer cnAgId;
	String cnName;
	@JsonIgnore
	String cnIso3;
	String cnIso2;
	String cnPhoneCode;
	@JsonIgnore
	String cnCapital;
	@JsonIgnore
	String cnCurrency;

}
