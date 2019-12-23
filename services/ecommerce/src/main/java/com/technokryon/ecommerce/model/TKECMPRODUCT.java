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

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TKECM_PRODUCT")
@Data
@NoArgsConstructor
public class TKECMPRODUCT {

	@Id
	@Column(name = "TKECMP_ID")
	private String pId;

	@Column(name = "TKECMP_SKU", unique = true)
	private String pSku;

	@Column(name = "TKECMP_NAME", unique = true)
	private String pName;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECMP_TKECMC_CATEGORY_ID", foreignKey = @ForeignKey(name = "FK_TKECMP_TKECMC_CATEGORY_ID"))
	private TKECMCATEGORY pTkecmcCategoryId;

	@Column(name = "TKECMP_WEIGHT")
	private Float pWeight;

	@Column(name = "TKECMP_QUANTITY")
	private Integer pQuantity;

	@Column(name = "TKECMP_SHORT_DESC")
	private String pShortDesc;

	@Column(name = "TKECMP_LONG_DESC")
	private String pLongDesc;

	@Column(name = "TKECMP_COUNTRY_OF_MFG")
	private String pCountryOfMfg;

	@Column(name = "TKECMP_CREATED_DATE")
	private OffsetDateTime pCreatedDate;

	@Column(name = "TKECMP_CREATED_USERID")
	private String pCreatedUserId;

	@Column(name = "TKECMP_MODIFIED_DATE")
	private OffsetDateTime pModifiedDate;

	@Column(name = "TKECMP_MODIFIED_USERID")
	private String pModifiedUserId;

	@Column(name = "TKECMP_STATUS")
	private String pStatus;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECMP_TKECMPT_AG_ID", foreignKey = @ForeignKey(name = "FK_TKECMP_TKECMPT_AG_ID"))
	private TKECMPRODUCTTYPE pTkecmptAgId;

	@Column(name = "TKECMP_PRICE")
	private Double pPrice;

	@Column(name = "TKECMP_DEFAULT_YN")
	private String pDefaultYN;

}
