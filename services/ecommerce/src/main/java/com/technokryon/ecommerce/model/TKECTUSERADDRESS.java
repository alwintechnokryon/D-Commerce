package com.technokryon.ecommerce.model;

import java.math.BigInteger;
import java.time.OffsetDateTime;

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
@Table(name = "TKECT_USER_ADDRESS")
@Data
@NoArgsConstructor
public class TKECTUSERADDRESS {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TKECTUA_AG_ID")
	private Integer uadAgId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECTUA_TKECMU_ID", foreignKey = @ForeignKey(name = "FK_TKECTUAD_TKECMU_ID"))
	private TKECMUSER uadTkecmuId;

	@Column(name = "TKECTUA_NAME")
	private String uadName;

	@Column(name = "TKECTUA_PHONE")
	private BigInteger uadPhone;

	@Column(name = "TKECTUA_ALTERNATIVE_PHONE")
	private BigInteger uadAlternativePhone;

	@Column(name = "TKECTUA_ADDRESS")
	private String uadAddress;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECTUA_TKECTS_AG_ID", foreignKey = @ForeignKey(name = "FK_TKECTUA_TKECTS_AG_ID"))
	private TKECTSTATE uadTkectsAgId;

	@Column(name = "TKECTUA_CITY")
	private String uadCity;

	@Column(name = "TKECTUA_POSTAL_CODE")
	private String uadPostalCode;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECTUA_TKECMCN_AG_ID", foreignKey = @ForeignKey(name = "FK_TKECTUA_TKECMCN_AG_ID"))
	private TKECMCOUNTRY uadTkecnAgId;

	@Column(name = "TKECTUA_LANDMARK")
	private String uadLandmark;

	@Column(name = "TKECTUA_ADDRESS_TYPE")
	private String uadAddressType;

	@Column(name = "TKECTUA_CREATED_DATE")
	private OffsetDateTime uadCreatedDate;

	@Column(name = "TKECTUA_CREATED_USER_ID")
	private String uadCreatedUserId;

	@Column(name = "TKECTUA_MODIFIED_DATE")
	private OffsetDateTime uadModifiedDate;

	@Column(name = "TKECTUA_MODIFIED_USER_ID")
	private String uadModifiedUserId;

	@Column(name = "TKECTUA_LATITUDE")
	private Double uadLatitude;

	@Column(name = "TKECTUA_LONGITUDE")
	private Double uadLongitude;

	@Column(name = "TKECTUA_DEFAULT_YN")
	private String uadDefaultYN;

}
