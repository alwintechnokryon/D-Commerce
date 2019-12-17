package com.technokryon.ecommerce.pojo;

import java.time.OffsetDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class OPTIONATTRIBUTE {

	String id;
	String attributeId;
	String name;
	OffsetDateTime createdDate;
	String createdUserId;
}
