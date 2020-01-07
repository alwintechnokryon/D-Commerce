package com.technokryon.ecommerce.admin.pojo;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class ProductAttribute {
	String paAgId;
	String paTkecmpId;
	String paTkectoaId;
	OffsetDateTime paCreatedDate;
	String paCreatedUserId;
	String defaultYN;
	String SubProductId;
	List<OptionAttribute> LO_OPTIONATTRIBUTE;

}
