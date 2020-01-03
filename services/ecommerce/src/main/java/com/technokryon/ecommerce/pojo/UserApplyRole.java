package com.technokryon.ecommerce.pojo;

import java.time.OffsetDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserApplyRole {

	Integer uarAgId;
	String uarTkecmuId;
	String uarTkecmrId;
	OffsetDateTime uarCreatedDate;
	String uarCreatedUserId;
}
