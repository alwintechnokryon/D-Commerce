package com.technokryon.ecommerce.pojo;

import java.util.List;

import com.technokryon.ecommerce.model.TKECMCATEGORY;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PJ_TKECMCATEGORY {

	String categoryId;
	String parentId;
	String categoryName;
	Integer categoryLevel;
	List<PJ_TKECMCATEGORY> childCategory;

	public PJ_TKECMCATEGORY(TKECMCATEGORY child_O_TKECMCATEGORY, List<PJ_TKECMCATEGORY> childCategories) {
		this.categoryId = child_O_TKECMCATEGORY.getTKECMC_CATEGORY_ID();
		this.parentId = child_O_TKECMCATEGORY.getTKECMC_PARENT_ID();
		this.categoryName = child_O_TKECMCATEGORY.getTKECMC_CATEGORY_NAME();
		this.categoryLevel = child_O_TKECMCATEGORY.getTKECMC_CATEGORY_LEVEL();
		this.childCategory = childCategories;
	}
}
