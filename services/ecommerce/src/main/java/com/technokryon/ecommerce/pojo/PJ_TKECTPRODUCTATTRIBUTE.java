package com.technokryon.ecommerce.pojo;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class PJ_TKECTPRODUCTATTRIBUTE {
	String tkectpaAgId;
	String tkectpaProductId;
	String tkectpaOptionAttributeId;
	OffsetDateTime tkectpaCreatedDate;
	String tkectpaCreatedUserId;
	String defaultYN;
	String SubProductId;
	List<PJ_TKECTOPTIONATTRIBUTE> LO_PJ_TKECTOPTIONATTRIBUTE;

}
