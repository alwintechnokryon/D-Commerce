package com.technokryon.ecommerce.model;

import java.math.BigInteger;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TKECT_ORDER_ADDRESS")
@Data
@NoArgsConstructor
public class TKECTORDERADDRESS {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TKECTOA_AG_ID")
	private Integer oaAgId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECTOA_TKECMO_ID", foreignKey = @ForeignKey(name = "FK_TKECTOA_TKECMO_ID"))
	private TKECMORDER oaTkecmoId;

	@Column(name = "TKECTOA_NAME")
	private String oaName;

	@Column(name = "TKECTOA_EMAIL_ID")
	private String oaEmailId;

	@Column(name = "TKECTOA_PHONE")
	private BigInteger oaPhone;

	@Column(name = "TKECTOA_ALTERNATIVE_PHONE")
	private BigInteger oaAltenativePhone;

	@Column(name = "TKECTOA_ADDRESS")
	private String oaAddress;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECTOA_TKECTS_AG_ID", foreignKey = @ForeignKey(name = "FK_TKECTOA_TKECTS_AG_ID"))
	private TKECTSTATE oaTkectsAgId;

	@Column(name = "TKECTOA_CITY")
	private String oaCity;

	@Column(name = "TKECTOA_POSTAL_CODE")
	private String oaPostalCode;

	@Column(name = "TKECTOA_COUNTRY_ID")
	private Integer oaCountryId;

	@Column(name = "TKECTOA_FLAG_ADDRESS")
	private String oaFlagAddress;

	@Column(name = "TKECTOA_ADDRESS_TYPE")
	private String oaAddressType;

	@Column(name = "TKECTOA_LATITUDE")
	private Double oaLatitude;

	@Column(name = "TKECTOA_LONGITUDE")
	private Double oaLongitude;

}
