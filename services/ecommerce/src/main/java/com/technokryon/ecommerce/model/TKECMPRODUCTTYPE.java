package com.technokryon.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TKECM_PRODUCT_TYPE")
@Data
@NoArgsConstructor
public class TKECMPRODUCTTYPE {

	@Id
	@Column(name = "TKECMPT_ID")
	private String ptId;

	@Column(name = "TKECMPT_TYPE")
	private String ptType;

}
