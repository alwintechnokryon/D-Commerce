package com.technokryon.ecommerce.pojo;

import java.time.OffsetDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ORDERSTATUSHISTORY {

	Integer oshAgId;
	String oshOrderId;
	String oshStatus;
	String oshComment;
	OffsetDateTime oshCreatedDate;
	String oshCreatedUserId;
}
