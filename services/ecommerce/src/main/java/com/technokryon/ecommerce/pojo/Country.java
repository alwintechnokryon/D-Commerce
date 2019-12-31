package com.technokryon.ecommerce.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Country {

	Integer cnAgId;
	String cnName;
	@JsonIgnore
	String cnIso3;
	String cnIso2;
	@JsonIgnore
	String cnPhoneCode;
	@JsonIgnore
	String cnCapital;
	@JsonIgnore
	String cnCurrency;

}
