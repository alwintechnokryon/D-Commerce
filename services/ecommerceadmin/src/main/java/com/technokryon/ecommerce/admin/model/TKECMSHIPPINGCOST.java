package com.technokryon.ecommerce.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TKECM_SHIPPING_COST")
@Data
@NoArgsConstructor
public class TKECMSHIPPINGCOST {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TKECMSC_AG_ID")
	private Integer scAgId;

	@Column(name = "TKECMSC_DEST_PINCODE")
	private String scDestPincode;

	@Column(name = "TKECMSC_DEST_PINCODE_TO")
	private String scDestPincodeTo;

	@Column(name = "TKECMSC_PRICE")
	private Double scPrice;

	@Column(name = "TKECMSC_WEIGHT_FROM")
	private Double scWeightFrom;

	@Column(name = "TKECMSC_WEIGHT_TO")
	private Double scWeightTo;

}
