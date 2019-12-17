package com.technokryon.ecommerce.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IMAGE {

	Integer agId;
	String productId;
	String fileName;
	String fileType;
	String url;
}
