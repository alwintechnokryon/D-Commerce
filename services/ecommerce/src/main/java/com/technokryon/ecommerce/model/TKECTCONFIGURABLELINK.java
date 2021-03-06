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
@Table(name = "TKECT_CONFIGURABLE_LINK")
@Data
@NoArgsConstructor
public class TKECTCONFIGURABLELINK {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TKECTCL_AG_ID")
	private Integer clAgId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECTCL_TKECMP_ID", foreignKey = @ForeignKey(name = "FK_TKECTCL_TKECMP_ID"))
	private TKECMPRODUCT clTkecmpId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECTCL_PARENT_ID", foreignKey = @ForeignKey(name = "FK_TKECTCL_PARENT_ID"))
	private TKECMPRODUCT clParentId;

}
