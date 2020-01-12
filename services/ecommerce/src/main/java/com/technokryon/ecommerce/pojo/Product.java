package com.technokryon.ecommerce.pojo;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class Product {

	String pId;
	String pSku;
	String pName;
	String pTkecmcCategoryId;
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
	String pTkecmptId;
	Double pPrice;
	String pDefaultYN;
	Integer pageNumber;
	String attributeName;
	String attributeId;
	String pdIsSharable;
	String pdUrl;
	String pdFile;
	String pdTitle;
	String oTkecmsId;
	List<Category> LO_CATEGORY;
	List<ProductAttribute> LO_PRODUCTATTRIBUTE;
	List<OptionAttribute> LO_OPTIONATTRIBUTE;
	List<Image> LO_IMAGE;

}
