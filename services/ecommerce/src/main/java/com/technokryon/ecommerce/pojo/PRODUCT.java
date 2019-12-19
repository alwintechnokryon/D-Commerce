package com.technokryon.ecommerce.pojo;

import java.time.OffsetDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class PRODUCT {

	String id;
	String sku;
	String name;
	String categoryId;
	Float weight;
	Integer quantity;
	String shortDesc;
	String longDesc;
	String countryOfMfg;
	OffsetDateTime createdDate;
	String createdUserId;
	OffsetDateTime modifiedDate;
	String modifiedUserId;
	String status;
	Integer type;
	Double price;
	Integer pageNumber;
	String defaultYN;
	String attributeName;
	String attributeId;
	String isSharable;
	String url;
	String file;
	String title;
	List<CATEGORY> LO_CATEGORY;
	List<PRODUCTATTRIBUTE> LO_PRODUCTATTRIBUTE;
	List<OPTIONATTRIBUTE> LO_OPTIONATTRIBUTE;
	List<IMAGE> LO_IMAGE;

}
