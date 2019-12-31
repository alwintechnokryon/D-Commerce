package com.technokryon.ecommerce.pojo;

import java.math.BigInteger;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderAddress {

	Integer oaAgId;
	String oaTkecmoId;
	String oaName;
	String oaEmailId;
	BigInteger oaPhone;
	BigInteger oaAltenativePhone;
	String oaAddress;
	Integer oaTkectsAgId;
	String oaCity;
	String oaPostalCode;
	Integer oaCountryId;
	String oaFlagAddress;
	String oaAddressType;
	Double oaLatitude;
	Double oaLongitude;

}
