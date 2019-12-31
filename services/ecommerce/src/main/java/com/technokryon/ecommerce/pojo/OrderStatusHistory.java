package com.technokryon.ecommerce.pojo;

import java.time.OffsetDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderStatusHistory {

	Integer oshAgId;
	String oshTkecmoId;
	String oshTkecmosStatus;
	String oshComment;
	OffsetDateTime oshCreatedDate;
	String oshCreatedUserId;
}
