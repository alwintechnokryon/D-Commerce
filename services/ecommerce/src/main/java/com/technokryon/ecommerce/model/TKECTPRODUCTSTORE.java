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
@Table(name = "TKECT_PRODUCT_STORE")
@Data
@NoArgsConstructor
public class TKECTPRODUCTSTORE {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TKECTPS_AG_ID")
	private Integer psAgId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECTPS_TKECMP_ID", foreignKey = @ForeignKey(name = "FK_TKECTPS_TKECMP_ID"))
	private TKECMPRODUCT psTkecmpId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECTPS_TKECMS_ID", foreignKey = @ForeignKey(name = "FK_TKECTPS_TKECMS_ID"))
	private TKECMSTORE psTkecmsId;

}
