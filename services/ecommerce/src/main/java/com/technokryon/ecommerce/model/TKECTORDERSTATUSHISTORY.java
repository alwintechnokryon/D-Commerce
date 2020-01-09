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
@Table(name = "TKECT_ORDER_STATUS_HISTORY")
@Data
@NoArgsConstructor
public class TKECTORDERSTATUSHISTORY {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TKECTOSH_AG_ID")
	private Integer oshAgId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECTOSH_TKECTOI_AG_ID", foreignKey = @ForeignKey(name = "FK_TKECTOSH_TKECTOI_AG_ID"))
	private TKECTORDERITEM oshTkectAgId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECTOSH_TKECMOS_ID", foreignKey = @ForeignKey(name = "FK_TKECTOSH_TKECMOS_ID"))
	private TKECMORDERSTATUS oshTkecmosId;

	@Column(name = "TKECTOSH_COMMENT")
	private String oshComment;

	@Column(name = "TKECTOSH_CREATED_DATE")
	private OffsetDateTime oshCreatedDate;

	@Column(name = "TKECTOSH_CREATED_USER_ID")
	private String oshCreatedUserId;

}
