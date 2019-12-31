package com.technokryon.ecommerce.pojo;

import java.time.OffsetDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class UserSession {

	String usApiKey;
	String usTkecmuId;
	String usCreatedIp;
	OffsetDateTime usCreatedDate;
	String usAliveYN;
}
