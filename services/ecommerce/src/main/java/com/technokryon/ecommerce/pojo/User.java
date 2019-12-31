package com.technokryon.ecommerce.pojo;

import java.math.BigInteger;
import java.time.OffsetDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class User {

	String uId;
	String uName;
	String uRegType;
	String uMail;
	int uOtp;
	OffsetDateTime uOtpExp;
	String uHashKey;
	String uPassword;
	OffsetDateTime uCreatedDate;
	BigInteger uPhone;
	Integer uPhoneCode;
	String uOtpStatus;
	String uStatus;
	OffsetDateTime uModifideDate;
	String oldPassword;
	String apiKey;
}
