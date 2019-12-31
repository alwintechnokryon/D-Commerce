package com.technokryon.ecommerce.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItem {

	Integer oiAgId;
	String oiTkecmoId;
	String oiTkecmpId;
	String oiSku;
	String oiName;
	Float oiWeight;
	Integer oiQuantity;
	Double oiPrice;
	Double oiTaxPercent;
	Double oiTaxAmount;
	String oiStatus;

}
