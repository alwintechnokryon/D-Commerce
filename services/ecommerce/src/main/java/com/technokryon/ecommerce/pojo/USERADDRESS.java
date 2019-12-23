package com.technokryon.ecommerce.pojo;

import java.math.BigInteger;
import java.time.OffsetDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class USERADDRESS {

	Integer uadAgId;
	String uadTkecmuId;
	String uadName;
	BigInteger uadPhone;
	BigInteger uadAlternativePhone;
	String uadAddress;
	String uadStreet;
	String uadCity;
	String uadPostalCode;
	String uadCountryId;
	String uadType;
	OffsetDateTime uadCreatedDate;
	String uadCreatedUserId;
	OffsetDateTime uadModifiedDate;
	String uadModifiedUserId;

}
