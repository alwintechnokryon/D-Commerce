package com.technokryon.ecommerce.pojo;

import java.time.OffsetDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PJ_TKECTUSERAUDIT {

	Integer tkectuaAgId;
	String tkectuaUserId;
	OffsetDateTime tkectuaLoginTime;
	OffsetDateTime tkectuaLogouttime;
	String tkectuaUserAgent;
	Double tkectuaLat;
	Double tkectuaLong;
	String tkectuaIp;
	String tkectuaApiKey;
}
