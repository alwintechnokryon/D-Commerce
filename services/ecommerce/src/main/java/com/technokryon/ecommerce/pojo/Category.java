package com.technokryon.ecommerce.pojo;

import java.time.OffsetDateTime;
import java.util.List;

import com.technokryon.ecommerce.model.TKECMCATEGORY;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Category {

	String cCategoryId;
	String cParentId;
	String cCategoryName;
	Integer cCategoryLevel;
	OffsetDateTime cCreatedDate;
	String cCreatedUserId;
	List<Category> childCategory;

	public Category(TKECMCATEGORY child_O_TKECMCATEGORY, List<Category> childCategories) {
		this.cCategoryId = child_O_TKECMCATEGORY.getCCategoryId();
		this.cParentId = child_O_TKECMCATEGORY.getCParentId();
		this.cCategoryName = child_O_TKECMCATEGORY.getCCategoryName();
		this.cCategoryLevel = child_O_TKECMCATEGORY.getCCategoryLevel();
		this.childCategory = childCategories;
	}
}
