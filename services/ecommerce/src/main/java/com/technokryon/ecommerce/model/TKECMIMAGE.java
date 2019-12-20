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
@Table(name = "TKECM_IMAGE")
@Data
@NoArgsConstructor
public class TKECMIMAGE {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TKECMI_AG_ID")
	private Integer iAgId;
	
	@ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECMI_PRODUCT_ID", foreignKey = @ForeignKey(name="FK_TKECMI_PRODUCT_ID"))
	private TKECMPRODUCT iProductId;
	
	@Column(name = "TKECMI_FILE_NAME")
	private String iFileName;
	
	@Column(name = "TKECMI_FILE_TYPE")
	private String iFileType;
	
	@Column(name = "TKECMI_URL")
	private String iUrl;
	
}
