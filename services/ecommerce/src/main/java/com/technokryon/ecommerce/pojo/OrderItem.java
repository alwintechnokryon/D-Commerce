package com.technokryon.ecommerce.pojo;

import java.util.List;

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
	String oiTkecmosId;
	List<ProductAttribute> O_ProductAttribute;
	List<OrderAddress> O_OrderAddress;

}
