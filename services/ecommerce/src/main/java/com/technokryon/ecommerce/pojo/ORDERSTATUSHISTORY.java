package com.technokryon.ecommerce.pojo;

import java.time.OffsetDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ORDERSTATUSHISTORY {

	Integer oshAgId;
	String oshTkecmoId;
	String oshTkecmosStatus;
	String oshComment;
	OffsetDateTime oshCreatedDate;
	String oshCreatedUserId;
}
