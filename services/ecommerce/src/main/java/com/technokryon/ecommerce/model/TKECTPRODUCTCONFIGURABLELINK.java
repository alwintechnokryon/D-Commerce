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

import org.hibernate.annotations.ManyToAny;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="TKECT_PRODUCT_CONFIGURABLE_LINK")
@Data
@NoArgsConstructor
public class TKECTPRODUCTCONFIGURABLELINK {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="TKECTPCL_AG_ID")
	private Integer tkectpclAgId;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name="TKECTPCL_PRODUCT_ID", foreignKey = @ForeignKey(name="FK_TKECTPCL_PARENT_ID"))
	private TKECMPRODUCT tkectpclProductId;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name="TKECTPCL_PARENT_ID", foreignKey = @ForeignKey(name="FK_TKECTPCL_PRODUCT_ID"))
	private TKECMPRODUCT tkectpclParentId;
}
