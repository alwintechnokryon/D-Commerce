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

@Data
@NoArgsConstructor
@Entity
@Table(name = "TKECT_USER_SESSION")

public class TKECTUSERSESSION {

	@Id
	@Column(name = "TKECTUS_API_KEY")
	private String apiKey;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECTUS_USER_ID", foreignKey = @ForeignKey(name = "FK_TKECTUS_USER_ID"))
	private TKECMUSER userId;

	@Column(name = "TKECTUS_CR_DATE")
	private OffsetDateTime createdDate;

	@Column(name = "TKECTUS_CR_IP")
	private String createdIp; 
	
	@Column(name = "TKECTUS_ALIVE_YN")
	private String aliveYN;
}
