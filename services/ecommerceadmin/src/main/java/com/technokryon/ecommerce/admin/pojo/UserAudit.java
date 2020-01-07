package com.technokryon.ecommerce.admin.pojo;

import java.time.OffsetDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserAudit {

	Integer uaAgId;
	String uaTkecmuId;
	OffsetDateTime uaLoginTime;
	OffsetDateTime uaLogoutTime;
	String uaUserAgent;
	Double uaLattitude;
	Double uaLongitude;
	String uaIp;
	String uaApiKey;
}
