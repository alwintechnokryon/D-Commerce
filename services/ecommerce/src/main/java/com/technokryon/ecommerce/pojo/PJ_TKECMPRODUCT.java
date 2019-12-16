package com.technokryon.ecommerce.pojo;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class PJ_TKECMPRODUCT {

	String tkecmpId;
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
	Integer tkecmpType;
	Double tkecmpPrice;
	Integer pageNumber;
	String tkecmpDefault;
	String attributeName;
	String attributeId;
	String tkecmpdIsSharable;
	String tkecmpdUrl;
	String tkecmpdFile;
	String tkecmpdTitle;
	List<PJ_TKECTPRODUCTATTRIBUTE> LO_PJ_TKECTPRODUCTATTRIBUTE;
	List<PJ_TKECTOPTIONATTRIBUTE> LO_PJ_TKECTOPTIONATTRIBUTE;
	List<PJ_TKECMIMAGE> LO_PJ_TKECMIMAGE;

}
