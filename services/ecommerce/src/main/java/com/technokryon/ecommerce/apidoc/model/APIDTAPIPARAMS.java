package com.technokryon.ecommerce.apidoc.model;

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
@Table(name="API_DT_API_PARAMS")
@Data
@NoArgsConstructor
public class APIDTAPIPARAMS {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "APIDTAP_AG_ID")
	private Integer apAgId;
		
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "APIDTAP_APIDTA_AG_ID", foreignKey = @ForeignKey(name = "FK_APIDTAP_APIDTA_AG_ID"))
	private APIDTAPI apApidtaAgId;
	
	@Column(name = "APIDTAP_PARAM_NAME")
	private String apParamName;
	
	@Column(name = "APIDTAP_DATA_TYPE")
	private String apDatatype;
	
	@Column(name = "APIDTAP_DESCRIPTION")
	private String apDescription;
	
	@Column(name = "APIDTAP_REQUIRED")
	private String apRequired;
	
}
