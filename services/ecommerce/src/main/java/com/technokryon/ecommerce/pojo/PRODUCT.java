package com.technokryon.ecommerce.pojo;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class PRODUCT {

	String pId;
	String pSku;
	String pName;
	String pCategoryId;
	Float pWeight;
	Integer pQuantity;
	String pShortDesc;
	String pLongDesc;
	String pCountryOfMfg;
	OffsetDateTime pCreatedDate;
	String pCreatedUserId;
	OffsetDateTime pModifiedDate;
	String pModifiedUserId;
	String pStatus;
	Integer pType;
	Double pPrice;
	String pDefaultYN;
	Integer pageNumber;
	String attributeName;
	String attributeId;
	String pdIsSharable;
	String pdUrl;
	String pdFile;
	String pdTitle;
	List<CATEGORY> LO_CATEGORY;
	List<PRODUCTATTRIBUTE> LO_PRODUCTATTRIBUTE;
	List<OPTIONATTRIBUTE> LO_OPTIONATTRIBUTE;
	List<IMAGE> LO_IMAGE;

}
