package com.technokryon.ecommerce.pojo;

import java.time.OffsetDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WishList {

	Integer wlAgId;
	String wlTkecmpId;
	String wlUserId;
	OffsetDateTime wlCreatedDate;
}
