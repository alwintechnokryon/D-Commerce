package com.technokryon.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TKECM_COUNTRY")
@Data
@NoArgsConstructor
public class TKECMCOUNTRY {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TKECMCN_AG_ID")
	private Integer cnAgId;

	@Column(name = "TKEMCN_NAME")
	private String cnName;

	@Column(name = "TKEMCN_ISO3")
	private String cnIso3;

	@Column(name = "TKEMCN_ISO2")
	private String cnIso2;

	@Column(name = "TKEMCN_PHONE_CODE")
	private String cnPhoneCode;

	@Column(name = "TKEMCN_CAPITAL")
	private String cnCapital;

	@Column(name = "TKEMCN_CURRENCY")
	private String cnCurrency;

}
