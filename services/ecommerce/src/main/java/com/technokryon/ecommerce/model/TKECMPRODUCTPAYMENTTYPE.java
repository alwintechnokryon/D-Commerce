package com.technokryon.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TKECM_PRODUCT_PAYMENT_TYPE")
@Data
@NoArgsConstructor
public class TKECMPRODUCTPAYMENTTYPE {

	@Id
	@Column(name = "TKECMPPT_ID")
	private String pptId;

	@Column(name = "TKECMPPT_TYPE")
	private String pptType;

}
