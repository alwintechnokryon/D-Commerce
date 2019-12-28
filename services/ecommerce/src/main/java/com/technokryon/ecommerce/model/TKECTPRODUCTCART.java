package com.technokryon.ecommerce.model;

import java.time.OffsetDateTime;

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
@Table(name = "TKECT_PRODUCT_CART")
@Data
@NoArgsConstructor
public class TKECTPRODUCTCART {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TKECTPC_AG_ID")
	private Integer pcAgId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECTPC_TKECMU_ID", foreignKey = @ForeignKey(name = "FK_TKECTPC_TKECMU_ID"))
	private TKECMUSER pcTkecmuId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECTPC_TKECMP_ID", foreignKey = @ForeignKey(name = "FK_TKECTPC_TKECMP_ID"))
	private TKECMPRODUCT pcTkecmpId;

	@Column(name = "TKECTPC_QUANTITY")
	private Integer pcQuantity;

	@Column(name = "TKECTPC_CART_SAVE_STATUS")
	private String pcCartSaveStatus;

	@Column(name = "TKECTPC_CREATED_DATE")
	private OffsetDateTime pcCreatedDate;

	@Column(name = "TKECTPC_CREATED_USER_ID")
	private String pcCreatedUserId;

}
