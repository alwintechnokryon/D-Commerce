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
	private String TKECMU_ID;

	@Column(name = "TKECMU_NAME")
	private String TKECMU_NAME;

	@Column(name = "TKECMU_REG_TYPE")
	private String TKECMU_REG_TYPE;

	@Column(name = "TKECMU_MAIL")
	private String TKECMU_MAIL;

	@Column(name = "TKECMU_OTP")
	private Integer TKECMU_OTP;

	@Column(name = "TKECMU_OTP_EXP")
	private OffsetDateTime TKECMU_OTP_EXP;

	@Column(name = "TKECMU_HASH_KEY")
	private String TKECMU_HASH_KEY;

	@Column(name = "TKECMU_PASSWORD")
	private String TKECMU_PASSWORD;

	@Column(name = "TKECMU_CREATED_DATE")
	private OffsetDateTime TKECMU_CREATED_DATE;

	@Column(name = "TKECMU_PHONE")
	private BigInteger TKECMU_PHONE;

	@Column(name = "TKECMU_COUNTRY_CODE")
	private Integer TKECMU_COUNTRY_CODE;

	@Column(name = "TKECMU_OTP_STATUS")
	private String TKECMU_OTP_STATUS;
	
	@Column(name = "TKECMU_STATUS")
	private String TKECMU_STATUS;           
	
	@Column(name = "TKECMU_MOD_DATE")
	private OffsetDateTime TKECMU_MOD_DATE;
	
}
