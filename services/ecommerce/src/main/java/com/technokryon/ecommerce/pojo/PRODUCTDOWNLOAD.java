package com.technokryon.ecommerce.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PRODUCTDOWNLOAD {

	Integer agId;
	String productId;
	String isSharable;
	String url;
	String file;
	String title;
}
