package com.technokryon.ecommerce.model;

import java.time.OffsetDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TKECM_ORDER")
@Data
@NoArgsConstructor
public class TKECMORDER {

	@Id
	@Column(name = "TKECMO_ID")
	private String oId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECMO_TKECMU_ID", foreignKey = @ForeignKey(name = "FK_TKECMO_TKECMU_ID"))
	private TKECMUSER oTkecmuId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECMO_TKECMS_ID", foreignKey = @ForeignKey(name = "FK_TKECMO_TKECMS_ID"))
	private TKECMSTORE oTkecmsId;

	@Column(name = "TKECMO_COUPEN_CODE")
	private String oCoupenCode;

	@Column(name = "TKECMO_BASE_AMOUNT")
	private Double oBaseAmount;

	@Column(name = "TKECMO_DETECTED_AMOUNT")
	private Double oDetectedAmount;

	@Column(name = "TKECMO_GRAND_TOTAL")
	private Double oGrandTotal;

	@Column(name = "TKECMO_SHIPPING_AMOUNT")
	private Double oShippingAmount;

	@Column(name = "TKECMO_DETECTED_SHIPPING_AMOUNT")
	private Double oDetectedShippingAmount;

	@Column(name = "TKECMO_TAX_AMOUNT")
	private Double oTaxAmount;

	@Column(name = "TKECMO_CURRENCY_CODE")
	private String oCurrencyCode;

	@Column(name = "TKECMO_IS_ORDER_CANCELLABLE")
	private String oIsOrderCancellable;

	@Column(name = "TKECMO_IS_AMOUNT_REFUNDABLE")
	private String oAmountRefundable;

	@Column(name = "TKECMO_REFUNDED_AMOUNT")
	private Double oRefundAmount;

	@Column(name = "TKECMO_IS_SEND_EMAIL")
	private String oIsSendEmail;

	@Column(name = "TKECMO_EMAIL_ID")
	private String oEmailId;

	@Column(name = "TKECMO_TRANSACTION_ID")
	private String oTransactionId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECMO_TKECMPPT_ID", foreignKey = @ForeignKey(name = "FK_TKECMO_TKECMPPT_ID"))
	private TKECMPRODUCTPAYMENTTYPE oTkecmpptId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "TKECMO_TKECMOS_ID", foreignKey = @ForeignKey(name = "FK_TKECMO_TKECMOS_ID"))
	private TKECMORDERSTATUS oTkecmosId;

	@Column(name = "TKECMO_CREATED_DATE")
	private OffsetDateTime oCreatedDate;

	@Column(name = "TKECMO_CREATED_USER_ID")
	private String oCreatedUserId;

	@Column(name = "TKECMO_EXPECTED_DELIVERY")
	private OffsetDateTime oExpectedDelivery;

	@Column(name = "TKECMO_DELIVERY_DATE")
	private OffsetDateTime oDeliveryDate;

}
