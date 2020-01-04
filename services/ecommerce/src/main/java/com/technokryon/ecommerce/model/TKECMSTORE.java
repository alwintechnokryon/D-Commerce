package com.technokryon.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TKECM_STORE")
@Data
@NoArgsConstructor
public class TKECMSTORE {

	@Id
	@Column(name = "TKECMS_ID")
	private String sId;

	@Column(name = "TKECMS_NAME")
	private String sName;

	@Column(name = "TKECMS_ACTIVE_YN")
	private String sActiveYN;

}
