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
@Table(name = "TKECM_PRODUCT_TYPE")
@Data
@NoArgsConstructor
public class TKECMPRODUCTTYPE {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TKECMPT_AG_ID")
	private Integer tkecmptAgId;

	@Column(name = "TKECMPT_TYPE")
	private String tkecmptType;

}
