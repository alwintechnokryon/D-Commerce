package com.technokryon.ecommerce.pojo;

import java.time.OffsetDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PJ_TKECTUSERAUDIT {

	Integer agId;
	String userId;
	String userAgent;
	OffsetDateTime loginTime;
	OffsetDateTime logoutTime;
	Double latitude;
	Double longitude;
	String ip;
	String apiKey;
}
