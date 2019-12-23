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
@Table(name = "TKECT_PRODUCT_ATTRIBUTE")
@Data
@NoArgsConstructor
public class TKECTPRODUCTATTRIBUTE {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TKECTPA_AG_ID")
	private String paAgId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECTPA_TKECMP_ID", foreignKey = @ForeignKey(name = "FK_TKECTPA_TKECMP_ID"))
	private TKECMPRODUCT paTkecmpId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECTPA_TKECTOA_ID", foreignKey = @ForeignKey(name = "FK_TKECTPA_TKECTOA_ID"))
	private TKECTOPTIONATTRIBUTE paTkectoaId;

	@Column(name = "TKECTPA_CREATED_DATE")
	private String paCreatedDate;

	@Column(name = "TKECTPA_CREATED_USERID")
	private String paCreatedUserId;

}
