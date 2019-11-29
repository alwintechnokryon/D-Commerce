package com.technokryon.ecommerce.model;

import java.time.OffsetDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TKECT_USER_AUDIT")
@Data
@NoArgsConstructor
public class TKECTUSERAUDIT {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TKECTUA_AG_ID")
	private Integer TKECTUA_AG_ID;

	@Column(name = "TKECTUA_USER_ID")
	private String TKECTUA_USER_ID;

	@Column(name = "TKECTUA_LOGIN_TIME")
	private OffsetDateTime TKECTUA_LOGIN_TIME;

	@Column(name = "TKECTUA_LOGOUT_TIME")
	private OffsetDateTime TKECTUA_LOGOUT_TIME;

	@Column(name = "TKECTUA_USER_AGENT")
	private String TKECTUA_USER_AGENT;

	@Column(name = "TKECTUA_LAT")
	private Double TKECTUA_LAT;

	@Column(name = "TKECTUA_LONG")
	private Double TKECTUA_LONG;

	@Column(name = "TKECTUA_IP")
	private String TKECTUA_IP;

	@Column(name = "TKECTUA_API_KEY")
	private String TKECTUA_API_KEY;

}
