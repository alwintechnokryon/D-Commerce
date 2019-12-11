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
	private String tkectpaAgId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECTPA_PRODUCT_ID", foreignKey = @ForeignKey(name = "FK_TKECMP_CATEGORY_ID"))
	private TKECMPRODUCT tkectpaProductId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECTPA_OPTION_ATTRIBUTE_ID", foreignKey = @ForeignKey(name = "FK_TKECTPA_OPTION_ID"))
	private TKECTOPTIONATTRIBUTE tkectpaOptionAttributeId;

	@Column(name = "TKECTPA_CREATED_DATE")
	private String tkectpaCreatedDate;

	@Column(name = "TKECTPA_CREATED_USERID")
	private String tkectpaCreatedUserId;

}
