package com.technokryon.ecommerce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TKECM_ORDER_STATUS")
@Data
@NoArgsConstructor
public class TKECMORDERSTATUS {

	@Id
	@Column(name = "TKECMOS_STATUS	")
	private String osStatus;

	@Column(name = "TKECMOS_LABEL")
	private String osLabel;
}
