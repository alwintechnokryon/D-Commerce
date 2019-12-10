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
@Table(name="TKECT_PRODUCT_DOWNLOAD_SAMPLE")
@Data
@NoArgsConstructor
public class TKECTPRODUCTDOWNLOADSAMPLE {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="TKECTPDS_AG_ID")
	private Integer tkectpdsAgId;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name="TKECTPDS_PRODUCT_ID", foreignKey = @ForeignKey(name="FK_TKECTPDS_PRODUCT_ID"))
	private TKECMPRODUCT tkectpdsProductId;
	
	@Column(name="TKECTPDS_URL")
	private String tkectpdsUrl;
	
	@Column(name="TKECTPDS_TITLE")
	private String tkectpdsTitle;
	
	@Column(name="TKECTPDS_FILE")
	private String tkectpdsFile;
}
