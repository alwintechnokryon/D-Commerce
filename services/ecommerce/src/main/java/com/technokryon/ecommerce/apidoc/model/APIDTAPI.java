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
@Table(name = "API_DT_API")
@Data
@NoArgsConstructor
public class APIDTAPI {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "APIDTA_AG_ID")
	private Integer aAgId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "APIDTA_APIDMM_AG_ID", foreignKey = @ForeignKey(name = "FK_APIDTA_APIDMM_AG_ID"))
	private APIDMMODULE aApidmmAgId;

	@Column(name = "APIDTA_API_NAME")
	private String aName;

	@Column(name = "APIDTA_DESCRIPTION")
	private String aDescription;

	@Column(name = "APIDTA_TYPE")
	private String aType;

	@Column(name = "APIDTA_URL")
	private String aUrl;

	@Column(name = "APIDTA_EXAMPLE_URL")
	private String aExampleUrl;

	@Column(name = "APIDTA_REQUEST_HEADER")
	private String aRequestHeader;

	@Column(name = "APIDTA_RESPONSE_HEADER")
	private String aResponseHeader;

	@Column(name = "APIDTA_BODY")
	private String aBody;

	@Column(name = "APIDTA_RESPONSE_CODE")
	private Integer aResponseCode;
}
