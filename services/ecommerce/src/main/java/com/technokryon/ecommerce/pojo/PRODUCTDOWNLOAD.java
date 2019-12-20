package com.technokryon.ecommerce.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PRODUCTDOWNLOAD {

	Integer pdAgId;
	String pdProductId;
	String pdIsSharable;
	String pdUrl;
	String pdFile;
	String pdTitle;
}
