package com.technokryon.ecommerce.model;

import java.math.BigInteger;
import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TKECM_USER")
@Data
@NoArgsConstructor
public class TKECMUSER {

	@Id
	@Column(name = "TKECMU_ID")
	private String tkecmuId;

	@Column(name = "TKECMU_NAME")
	private String tkecmuName;

	@Column(name = "TKECMU_REG_TYPE")
	private String tkecmuRegType;

	@Column(name = "TKECMU_MAIL")
	private String tkecmuMail;

	@Column(name = "TKECMU_OTP")
	private Integer tkecmuOtp;

	@Column(name = "TKECMU_OTP_EXP")
	private OffsetDateTime tkecmuOtpExp;

	@Column(name = "TKECMU_HASH_KEY")
	private String tkecmuHashKey;

	@Column(name = "TKECMU_PASSWORD")
	private String tkecmuPassword;

	@Column(name = "TKECMU_CREATED_DATE")
	private OffsetDateTime tkecmuCreatedDate;

	@Column(name = "TKECMU_PHONE")
	private BigInteger tkecmuPhone;

	@Column(name = "TKECMU_COUNTRY_CODE")
	private Integer tkecmuCountryCode;

	@Column(name = "TKECMU_OTP_STATUS")
	private String tkecmuOtpStatus;
	
	@Column(name = "TKECMU_STATUS")
	private String tkecmuStatus;           
	
	@Column(name = "TKECMU_MODIFIED_DATE")
	private OffsetDateTime tkecmuModifideDate;
	
}
