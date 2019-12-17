package com.technokryon.ecommerce.model;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TKECM_PRODUCT")
@Data
@NoArgsConstructor
public class TKECMPRODUCT {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TKECMP_ID")
	private String id;

	@Column(name = "TKECMP_SKU", unique = true)
	private String sku;

	@Column(name = "TKECMP_NAME", unique = true)
	private String name;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECMP_CATEGORY_ID", foreignKey = @ForeignKey(name = "FK_TKECMP_CATEGORY_ID"))
	private TKECMCATEGORY categoryId;

	@Column(name = "TKECMP_WEIGHT")
	private Float weight;

	@Column(name = "TKECMP_QUANTITY")
	private Integer quantity;

	@Column(name = "TKECMP_SHORT_DESC")
	private String shortDesc;

	@Column(name = "TKECMP_LONG_DESC")
	private String longDesc;

	@Column(name = "TKECMP_COUNTRY_OF_MFG")
	private String countryOfMfg;

	@Column(name = "TKECMP_CREATED_DATE")
	private OffsetDateTime createdDate;

	@Column(name = "TKECMP_CREATED_USERID")
	private String createdUserId;

	@Column(name = "TKECMP_MODIFIED_DATE")
	private OffsetDateTime modifiedDate;

	@Column(name = "TKECMP_MODIFIED_USERID")
	private String mdifiedUserId;

	@Column(name = "TKECMP_STATUS")
	private String status;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECMP_TYPE", foreignKey = @ForeignKey(name = "FK_TKECMP_TYPE"))
	private TKECMPRODUCTTYPE type;

	@Column(name = "TKECMP_PRICE")
	private Double price;

	@Column(name = "TKECMP_DEFAULT_YN")
	private String defaultYN;

}
