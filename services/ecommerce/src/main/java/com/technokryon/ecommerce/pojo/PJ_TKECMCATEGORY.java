package com.technokryon.ecommerce.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.technokryon.ecommerce.model.TKECMCATEGORY;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PJ_TKECMCATEGORY {

	String tkecmcCategoryId;
	String tkecmcParentId;
	String tkecmcCategoryName;
	Integer tkecmcCategoryLevel;
	List<PJ_TKECMCATEGORY> childCategory;

	public PJ_TKECMCATEGORY(TKECMCATEGORY child_O_TKECMCATEGORY, List<PJ_TKECMCATEGORY> childCategories) {
		this.tkecmcCategoryId = child_O_TKECMCATEGORY.getTkecmcCategoryId();
		this.tkecmcParentId = child_O_TKECMCATEGORY.getTkecmcParentId();
		this.tkecmcCategoryName = child_O_TKECMCATEGORY.getTkecmcCategoryName();
		this.tkecmcCategoryLevel = child_O_TKECMCATEGORY.getTkecmcCategoryLevel();
		this.childCategory = childCategories;
	}
}
