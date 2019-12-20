package com.technokryon.ecommerce.pojo;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class PRODUCTATTRIBUTE {
	String paAgId;
	String paProductId;
	String paOptionAttributeId;
	OffsetDateTime paCreatedDate;
	String paCreatedUserId;
	String defaultYN;
	String SubProductId;
	List<OPTIONATTRIBUTE> LO_OPTIONATTRIBUTE;

}
