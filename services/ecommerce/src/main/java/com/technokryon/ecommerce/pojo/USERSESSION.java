package com.technokryon.ecommerce.pojo;

import java.time.OffsetDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class USERSESSION {

	String userId;
	String apiKey;
	String createdIp;
	OffsetDateTime createdDate;
	String aliveYN;
}
