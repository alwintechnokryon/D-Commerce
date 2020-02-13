package com.technokryon.ecommerce.apidoc.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "API_DM_MODULE")
@Data
@NoArgsConstructor
public class APIDMMODULE {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "APIDMM_AG_ID")
	private Integer mAgId;

	@Column(name = "APIDMM_NAME")
	private String mName;

	@Column(name = "APIDMM_FLAG")
	private String mFlag;

}
