package com.technokryon.ecommerce.pojo;

import java.time.OffsetDateTime;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Order {

	String oId;
	String oTkecmuId;
	String oTkecmsId;
	String oCoupenCode;
	Double oBaseAmount;
	Double oDetectedAmount;
	Double oGrandTotal;
	Double oShippingAmount;
	Double oDetectedShippingAmount;
	Double oTaxAmount;
	String oCurrencyCode;
	String oIsOrderCancellable;
	String oAmountRefundable;
	Double oRefundAmount;
	String oIsSendEmail;
	String oEmailId;
	String oTkecmpptId;
	String oTkecmosId;
	String oTransactionId;
	OffsetDateTime oCreatedDate;
	String oCreatedUserId;
	OffsetDateTime oExpectedDelivery;
	OffsetDateTime oDeliveryDate;
	String userId;
//	String productId;
//	Integer proQuantity;
	List<Product> LO_PRODUCT;
	Integer billingAddress;
	Integer shippingAddress;

}
