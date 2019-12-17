package com.technokryon.ecommerce.pojo;

import java.time.OffsetDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class USERAUDIT {

	Integer agId;
	String userId;
	OffsetDateTime loginTime;
	OffsetDateTime logouttime;
	String userAgent;
	Double lattitude;
	Double longitude;
	String ip;
	String apiKey;
}
