package com.technokryon.ecommerce.admin.model;

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
@Table(name = "TKECT_USER_APPLY_GROUP")
@Data
@NoArgsConstructor

public class TKECTUSERAPPLYGROUP {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TKECTUAG_AG_ID")
	private Integer uagAgId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECTUAG_TKECMU_ID", foreignKey = @ForeignKey(name = "FK_TKECTUAG_TKECMU_ID"))
	private TKECMUSER uagTkecmuId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECTUAG_TKECMUG_ID", foreignKey = @ForeignKey(name = "FK_TKECTUAG_TKECMUG_ID"))
	private TKECMUSERGROUP uagTkecmugId;
	
	
	@Column(name = "TKECTUAG_CREATED_DATE")
	private OffsetDateTime uagCreatedDate;

	@Column(name = "TKECTUAG_CREATED_USER_ID")
	private String uagCreatedUserId;

	
}
