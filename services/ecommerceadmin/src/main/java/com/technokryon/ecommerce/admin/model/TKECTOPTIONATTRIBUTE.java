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

@Entity
@Table(name = "TKECT_OPTION_ATTRIBUTE")
@Data
@NoArgsConstructor

public class TKECTOPTIONATTRIBUTE {

	@Id
	@Column(name = "TKECTOA_ID")
	private String oaId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECTOA_TKECMA_ID", foreignKey = @ForeignKey(name = "FK_TKECTOA_TKECMA_ID"))
	private TKECMATTRIBUTE oaTkecmaId;

	@Column(name = "TKECTOA_NAME")
	private String oaName;

	@Column(name = "TKECTOA_CREATED_DATE")
	private OffsetDateTime oaCreatedDate;

	@Column(name = "TKECTOA_CREATED_USERID")
	private String oaCreatedUserId;

}
