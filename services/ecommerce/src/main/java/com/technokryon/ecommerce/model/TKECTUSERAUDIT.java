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
	private Integer agId;

	@Column(name = "TKECTUA_USER_ID")
	private String userId;

	@Column(name = "TKECTUA_LOGIN_TIME")
	private OffsetDateTime loginTime;

	@Column(name = "TKECTUA_LOGOUT_TIME")
	private OffsetDateTime logouttime;

	@Column(name = "TKECTUA_USER_AGENT")
	private String userAgent;

	@Column(name = "TKECTUA_LATTITUDE")
	private Double lattitude;

	@Column(name = "TKECTUA_LONGITUDE")
	private Double longitude;

	@Column(name = "TKECTUA_IP")
	private String ip;

	@Column(name = "TKECTUA_API_KEY")
	private String apiKey;

}
