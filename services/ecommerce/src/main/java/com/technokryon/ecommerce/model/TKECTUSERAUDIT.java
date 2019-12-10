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
	private Integer tkectuaAgId;

	@Column(name = "TKECTUA_USER_ID")
	private String tkectuaUserId;

	@Column(name = "TKECTUA_LOGIN_TIME")
	private OffsetDateTime tkectuaLoginTime;

	@Column(name = "TKECTUA_LOGOUT_TIME")
	private OffsetDateTime tkectuaLogouttime;

	@Column(name = "TKECTUA_USER_AGENT")
	private String tkectuaUserAgent;

	@Column(name = "TKECTUA_LAT")
	private Double tkectuaLat;

	@Column(name = "TKECTUA_LONG")
	private Double tkectuaLong;

	@Column(name = "TKECTUA_IP")
	private String tkectuaIp;

	@Column(name = "TKECTUA_API_KEY")
	private String tkectuaApiKey;

}
