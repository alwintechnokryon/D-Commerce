package com.technokryon.ecommerce.pojo;

import java.time.OffsetDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor

public class ProductShipment {

	String psmId;
	Integer psmTkectoiAgId;
	String psmTkecmsId;
	Float psmWeight;
	Integer psmQuantity;
	Integer psmShippingAddress;
	Integer psmBillingAddress;
	OffsetDateTime psmCreatedDate;
	OffsetDateTime psmModifiedDate;
	String psmStatus;
}
