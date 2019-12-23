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
@Table(name = "TKECT_STATE")
@Data
@NoArgsConstructor
public class TKECTSTATE {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TKECTS_AG_ID")
	private Integer sAgId;

	@Column(name = "TKECTS_NAME")
	private String sName;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECTS_TKECMCN_AG_ID", foreignKey = @ForeignKey(name = "FK_TKECTS_TKECMCN_AG_ID"))
	private TKECMPRODUCT sTkecmcnAgId;

	@Column(name = "TKECTS_FIPS_CODE")
	private String sFipsCode;

}
