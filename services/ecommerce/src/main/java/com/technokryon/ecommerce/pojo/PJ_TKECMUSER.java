package com.technokryon.ecommerce.pojo;

import java.math.BigInteger;
import java.time.OffsetDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PJ_TKECMUSER {

	String tkecmuId;
	String tkecmuName;
	String tkecmuRegType;
	String tkecmuMail;
	int tkecmuOtp;
	OffsetDateTime tkecmuOtpExp;
	String tkecmuHashKey;
	String tkecmuPassword;
	OffsetDateTime tkecmuCreatedDate;
	BigInteger tkecmuPhone;
	Integer tkecmuCountryCode;
	String tkecmuOtpStatus;
	String tkecmuStatus;
	OffsetDateTime tkecmuModifideDate;
	String oldPassword;
	String apiKey;
}
