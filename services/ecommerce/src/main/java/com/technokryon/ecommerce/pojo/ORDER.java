package com.technokryon.ecommerce.pojo;

import java.time.OffsetDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ORDER {

	String oId;
	String oTkecmuId;
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
	String oPaymentType;
	OffsetDateTime oCreatedDate;
	String oCreatedUserId;
	OffsetDateTime oExpectedDelivery;
	OffsetDateTime oDeliveryDate;
	String productId;
	Integer proQuantity;
	Integer userAddressId;
	Integer billingAddress;
	Integer shippingAddress;

}
