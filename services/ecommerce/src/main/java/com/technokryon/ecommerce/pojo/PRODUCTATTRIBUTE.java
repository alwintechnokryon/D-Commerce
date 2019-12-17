package com.technokryon.ecommerce.pojo;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class PRODUCTATTRIBUTE {
	String agId;
	String productId;
	String optionAttributeId;
	OffsetDateTime createdDate;
	String createdUserId;
	String defaultYN;
	String SubProductId;
	List<OPTIONATTRIBUTE> LO_OPTIONATTRIBUTE;

}
