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

import org.hibernate.annotations.GeneratorType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TKECM_PRODUCT_DOWNLOAD")
@Data
@NoArgsConstructor
public class TKECMPRODUCTDOWNLOAD {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TKECMPD_AG_ID")
	private Integer agId;

	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECMPD_PRODUCT_ID", foreignKey = @ForeignKey(name="FK_TKECMPD_PRODUCT_ID") )
	private TKECMPRODUCT productId;
	
	@Column(name = "TKECMPD_IS_SHARABLE")
	private String isSharable;
	
	@Column(name = "TKECMPD_URL")
	private String url;
	
	@Column(name = "TKECMPD_FILE")
	private String file;
	
	@Column(name = "TKECMPD_TITLE")
	private String title;
	
}
