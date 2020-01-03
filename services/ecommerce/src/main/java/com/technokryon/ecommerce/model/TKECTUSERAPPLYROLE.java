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
@Table(name = "TKECT_USER_APPLY_ROLE")
@Data
@NoArgsConstructor
public class TKECTUSERAPPLYROLE {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TKECTUAR_AG_ID")
	private Integer uarAgId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECTUAR_TKECMU_ID", foreignKey = @ForeignKey(name = "FK_TKECTUAR_TKECMU_ID"))
	private TKECMUSER uarTkecmuId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECTUAR_TKECMR_ID", foreignKey = @ForeignKey(name = "FK_TKECTUAR_TKECMR_ID"))
	private TKECMROLE uarTkecmrId;

	@Column(name = "TKECTUAR_CREATED_DATE")
	private OffsetDateTime uarCreatedDate;

	@Column(name = "TKECTUAR_CREATED_USER_ID")
	private String uarCreatedUserId;
}
