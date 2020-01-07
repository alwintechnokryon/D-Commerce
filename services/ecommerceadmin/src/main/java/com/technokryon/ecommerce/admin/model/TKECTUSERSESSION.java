package com.technokryon.ecommerce.admin.model;

import java.time.OffsetDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "TKECT_USER_SESSION")

public class TKECTUSERSESSION {

	@Id
	@Column(name = "TKECTUS_API_KEY")
	private String usApiKey;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECTUS_TKECMU_ID", foreignKey = @ForeignKey(name = "FK_TKECTUS_TKECMU_ID"))
	private TKECMUSER usTkecmuId;

	@Column(name = "TKECTUS_CR_DATE")
	private OffsetDateTime usCreatedDate;

	@Column(name = "TKECTUS_CR_IP")
	private String usCreatedIp; 
	
	@Column(name = "TKECTUS_ALIVE_YN")
	private String usAliveYN;
}
