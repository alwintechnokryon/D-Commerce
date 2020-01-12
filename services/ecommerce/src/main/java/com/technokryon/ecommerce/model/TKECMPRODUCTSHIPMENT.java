package com.technokryon.ecommerce.model;

import java.time.OffsetDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TKECM_PRODUCT_SHIPMENT")
@Data
@NoArgsConstructor
public class TKECMPRODUCTSHIPMENT {

	@Id
	@Column(name = "TKECMPSM_ID")
	private String psmId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECMPSM_TKECTOI_AG_ID", foreignKey = @ForeignKey(name = "FK_TKECMPSM_TKECTOI_AG_ID"))
	private TKECTORDERITEM psmTkectoiAgId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECMPSM_TKECMS_ID", foreignKey = @ForeignKey(name = "FK_TKECMPSM_TKECMS_ID"))
	private TKECMSTORE psmTkecmsId;

	@Column(name = "TKECMPSM_WEIGHT")
	private Float psmWeight;

	@Column(name = "TKECMPSM_QUANTITY")
	private Integer psmQuantity;

	@Column(name = "TKECMPSM_SHIPPING_ADDRESS")
	private Integer psmShippingAddress;

	@Column(name = "TKECMPSM_BILLING_ADDRESS")
	private Integer psmBillingAddress;

	@Column(name = "TKECMPSM_CREATED_DATE")
	private OffsetDateTime psmCreatedDate;

	@Column(name = "TKECMPSM_MODIFIED_DATE")
	private OffsetDateTime psmModifiedDate;

	@Column(name = "TKECMPSM_STATUS")
	private String psmStatus;
}
