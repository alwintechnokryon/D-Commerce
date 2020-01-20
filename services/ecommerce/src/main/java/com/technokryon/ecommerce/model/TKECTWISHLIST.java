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
@Table(name = "TKECT_WISH_LIST")
@Data
@NoArgsConstructor
public class TKECTWISHLIST {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TKECTWL_AG_ID")
	private Integer wlAgId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECTWL_TKECMP_ID", foreignKey = @ForeignKey(name = "FK_TKECTWL_TKECMP_ID"))
	private TKECMPRODUCT wlTkecmpId;

	@Column(name = "TKECTWL_USER_ID")
	private String wlUserId;

	@Column(name = "TKECTWL_CREATED_DATE")
	private OffsetDateTime wlCreatedDate;
}
