package com.technokryon.ecommerce.admin.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TKECM_ATTRIBUTE")
@Data
@NoArgsConstructor

public class TKECMATTRIBUTE {

	@Id
	@Column(name = "TKECMA_ID")
	private String  aId;

	@Column(name = "TKECMA_NAME")
	private String aName;

}
