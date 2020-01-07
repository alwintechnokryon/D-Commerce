package com.technokryon.ecommerce.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TKECM_ROLE")
@Data
@NoArgsConstructor
public class TKECMROLE {

	@Id
	@Column(name = "TKECMR_ID")
	private String rId;

	@Column(name = "TKECMR_ROLE")
	private String rRole;

}
