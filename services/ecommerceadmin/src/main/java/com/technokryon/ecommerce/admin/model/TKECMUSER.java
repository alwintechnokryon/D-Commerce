package com.technokryon.ecommerce.admin.model;

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
	private String uId;

	@Column(name = "TKECMU_NAME")
	private String uName;

	@Column(name = "TKECMU_REG_TYPE")
	private String uRegType;

	@Column(name = "TKECMU_MAIL")
	private String uMail;

	@Column(name = "TKECMU_OTP")
	private Integer uOtp;

	@Column(name = "TKECMU_OTP_EXP")
	private OffsetDateTime uOtpExp;

	@Column(name = "TKECMU_HASH_KEY")
	private String uHashKey;

	@Column(name = "TKECMU_PASSWORD")
	private String uPassword;

	@Column(name = "TKECMU_CREATED_DATE")
	private OffsetDateTime uCreatedDate;

	@Column(name = "TKECMU_PHONE")
	private BigInteger uPhone;

	@Column(name = "TKECMU_PHONE_CODE")
	private Integer uPhoneCode;

	@Column(name = "TKECMU_OTP_STATUS")
	private String uOtpStatus;
	
	@Column(name = "TKECMU_STATUS")
	private String uStatus;           
	
	@Column(name = "TKECMU_MODIFIED_DATE")
	private OffsetDateTime uModifideDate;
	
}
