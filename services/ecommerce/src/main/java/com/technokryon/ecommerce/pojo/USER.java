package com.technokryon.ecommerce.pojo;

import java.math.BigInteger;
import java.time.OffsetDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class USER {

	String id;
	String name;
	String regType;
	String mail;
	int otp;
	OffsetDateTime otpExp;
	String hashKey;
	String password;
	OffsetDateTime createdDate;
	BigInteger phone;
	Integer countryCode;
	String otpStatus;
	String status;
	OffsetDateTime modifideDate;
	String oldPassword;
	String apiKey;
}
