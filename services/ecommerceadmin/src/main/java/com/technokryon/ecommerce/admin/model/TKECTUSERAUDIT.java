package com.technokryon.ecommerce.admin.model;

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
	private Integer uaAgId;

	@Column(name = "TKECTUA_USER_ID")
	private String uaUserId;

	@Column(name = "TKECTUA_LOGIN_TIME")
	private OffsetDateTime uaLoginTime;

	@Column(name = "TKECTUA_LOGOUT_TIME")
	private OffsetDateTime uaLogoutTime;

	@Column(name = "TKECTUA_USER_AGENT")
	private String uaUserAgent;

	@Column(name = "TKECTUA_LATTITUDE")
	private Double uaLattitude;

	@Column(name = "TKECTUA_LONGITUDE")
	private Double uaLongitude;

	@Column(name = "TKECTUA_IP")
	private String uaIp;

	@Column(name = "TKECTUA_API_KEY")
	private String uaApiKey;

}
