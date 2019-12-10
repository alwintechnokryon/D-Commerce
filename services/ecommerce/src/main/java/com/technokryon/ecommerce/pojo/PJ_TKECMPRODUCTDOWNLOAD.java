package com.technokryon.ecommerce.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PJ_TKECMPRODUCTDOWNLOAD {

	Integer tkecmpdAgId;
	Integer tkecmpdProductId;
	String tkecmpdIsSharable;
	String tkecmpdUrl;
	String tkecmpdFile;
	String tkecmpdTitle;
}
