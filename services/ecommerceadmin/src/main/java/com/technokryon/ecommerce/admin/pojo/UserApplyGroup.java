package com.technokryon.ecommerce.admin.pojo;

import java.time.OffsetDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserApplyGroup {

	Integer uagAgId;
	String uagTkecmuId;
	String uagTkecmugId;
	OffsetDateTime uagCreatedDate;
	String uagCreatedUserId;

}
