package com.technokryon.ecommerce.pojo;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class PJ_TKECMPRODUCT {

	Integer tkecmpAgId;
	String tkecmpSKU;
	String tkecmpName;
	String tkecmpCategoryId;
	Float tkecmpWeight;
	Integer tkecmpQuantity;
	String tkecmpShortDesc;
	String tkecmpLongDesc;
	String tkecmpCountryOfMfg;
	OffsetDateTime tkecmpCreatedDate;
	String tkecmpCreatedUserId;
	OffsetDateTime tkecmpModifiedDate;
	String tkecmpModifiedUserId;
	String tkecmpStatus;
	String tkecmpType;
	Float tkecmpSellingPrice;
	Float tkecmpOriginalPrice;
}
