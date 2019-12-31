package com.technokryon.ecommerce.pojo;

import java.time.OffsetDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductCart {

	Integer pcAgId;
	String pcTkecmuId;
	String pcTkecmpId;
	Integer pcQuantity;
	String pcCartSaveStatus;
	OffsetDateTime pcCreatedDate;
	String pcCreatedUserId;
}
