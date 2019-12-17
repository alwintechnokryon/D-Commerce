package com.technokryon.ecommerce.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.technokryon.ecommerce.model.TKECMCATEGORY;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CATEGORY {

	String categoryId;
	String parentId;
	String categoryName;
	Integer categoryLevel;
	List<CATEGORY> childCategory;

	public CATEGORY(TKECMCATEGORY child_O_TKECMCATEGORY, List<CATEGORY> childCategories) {
		this.categoryId = child_O_TKECMCATEGORY.getCategoryId();
		this.parentId = child_O_TKECMCATEGORY.getParentId();
		this.categoryName = child_O_TKECMCATEGORY.getCategoryName();
		this.categoryLevel = child_O_TKECMCATEGORY.getCategoryLevel();
		this.childCategory = childCategories;
	}
}
