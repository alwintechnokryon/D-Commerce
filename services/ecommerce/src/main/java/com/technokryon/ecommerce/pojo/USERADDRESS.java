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
	Integer uadTkectsAgId;
	String uadCity;
	String uadPostalCode;
	Integer uadTkecnAgId;
	String uadLandmark;
	String uadAddressType;
	OffsetDateTime uadCreatedDate;
	String uadCreatedUserId;
	OffsetDateTime uadModifiedDate;
	String uadModifiedUserId;
	Double uadLatitude;
	Double uadLongitude;
	String uadDefaultYN;

}
