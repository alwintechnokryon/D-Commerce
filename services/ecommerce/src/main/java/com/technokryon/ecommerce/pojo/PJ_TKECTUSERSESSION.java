package com.technokryon.ecommerce.pojo;

import java.time.OffsetDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class PJ_TKECTUSERSESSION {

	String tkectusUserId;
	String tkectusApiKey;
	String tkectusCreatedIp;
	OffsetDateTime tkectusCreatedDate;
	String tkectusAliveYN;
}
