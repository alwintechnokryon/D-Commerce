package com.technokryon.ecommerce.pojo;

import java.math.BigInteger;
import java.time.OffsetDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PJ_TKECMUSER {

	String userId;
	String name;
	String password;
	int otp;
	OffsetDateTime otpExp;
	String regType;
	String mail;
	String hashKey;
	BigInteger phoneNo;
	Integer countryCode;
	OffsetDateTime createdDate;
	String status;
	String otpStatus;
	OffsetDateTime modifiedDate;
	String oldPassword;
	String apiKey;
}
