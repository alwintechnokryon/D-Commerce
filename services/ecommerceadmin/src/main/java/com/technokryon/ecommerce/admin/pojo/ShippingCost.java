package com.technokryon.ecommerce.admin.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShippingCost {

	Integer scAgId;
	String scDestPincode;
	String scDestPincodeTo;
	Double scPrice;
	Double scWeightFrom;
	Double scWeightTo;
}
