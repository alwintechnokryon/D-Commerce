package com.technokryon.ecommerce.pojo;

import java.time.OffsetDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class OptionAttribute {

	String oaId;
	//String aId;
	String oaTkecmaId;
	String oaName;
	OffsetDateTime oaCreatedDate;
	String oaCreatedUserId;
}
