package com.technokryon.ecommerce.model;

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
@Table(name = "TKECT_ORDER_ITEM")
@Data
@NoArgsConstructor
public class TKECTORDERITEM {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TKECTOI_AG_ID")
	private Integer oiAgId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECTOI_TKECMO_ID", foreignKey = @ForeignKey(name = "FK_TKECTOI_TKECMO_ID"))
	private TKECMORDER oiTkecmoId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECTOI_TKECMP_ID", foreignKey = @ForeignKey(name = "FK_TKECTOI_TKECMP_ID"))
	private TKECMPRODUCT oiTkecmpId;

	@Column(name = "TKECTOI_SKU")
	private String oiSku;

	@Column(name = "TKECTOI_NAME")
	private String oiName;

	@Column(name = "TKECTOI_WEIGHT")
	private Float oiWeight;

	@Column(name = "TKECTOI_PRICE")
	private Double oiPrice;

	@Column(name = "TKECTOI_QUANTITY")
	private Integer oiQuantity;

	@Column(name = "TKECTOI_TAX_PERCENT")
	private Double oiTaxPercent;

	@Column(name = "TKECTOI_TAX_AMOUNT")
	private Double oiTaxAmount;

	@Column(name = "TKECTOI_STATUS")
	private String oiStatus;

}
