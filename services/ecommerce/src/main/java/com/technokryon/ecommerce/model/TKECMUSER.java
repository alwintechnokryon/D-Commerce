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
	private String Id;

	@Column(name = "TKECMU_NAME")
	private String name;

	@Column(name = "TKECMU_REG_TYPE")
	private String regType;

	@Column(name = "TKECMU_MAIL")
	private String mail;

	@Column(name = "TKECMU_OTP")
	private Integer otp;

	@Column(name = "TKECMU_OTP_EXP")
	private OffsetDateTime otpExp;

	@Column(name = "TKECMU_HASH_KEY")
	private String hashKey;

	@Column(name = "TKECMU_PASSWORD")
	private String password;

	@Column(name = "TKECMU_CREATED_DATE")
	private OffsetDateTime createdDate;

	@Column(name = "TKECMU_PHONE")
	private BigInteger phone;

	@Column(name = "TKECMU_COUNTRY_CODE")
	private Integer countryCode;

	@Column(name = "TKECMU_OTP_STATUS")
	private String otpStatus;
	
	@Column(name = "TKECMU_STATUS")
	private String status;           
	
	@Column(name = "TKECMU_MODIFIED_DATE")
	private OffsetDateTime modifideDate;
	
}
