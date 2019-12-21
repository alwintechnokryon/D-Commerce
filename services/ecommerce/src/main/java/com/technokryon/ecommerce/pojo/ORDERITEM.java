package com.technokryon.ecommerce.pojo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ORDERITEM {

	Integer oiAgId;
	String oiOrderId;
	String oiProductId;
	String oiSku;
	String oiName;
	Float oiWeight;
	Double oiPrice;
	Double oiTaxPercent;
	Double oiTaxAmount;
	String oiPaymentType;
	String oiTransactionId;

}
