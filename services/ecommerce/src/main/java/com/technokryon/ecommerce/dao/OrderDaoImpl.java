package com.technokryon.ecommerce.dao;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.model.TKECMORDER;
import com.technokryon.ecommerce.model.TKECMORDERSTATUS;
import com.technokryon.ecommerce.model.TKECMPRODUCT;
import com.technokryon.ecommerce.model.TKECMPRODUCTPAYMENTTYPE;
import com.technokryon.ecommerce.model.TKECMPRODUCTSHIPMENT;
import com.technokryon.ecommerce.model.TKECMSTORE;
import com.technokryon.ecommerce.model.TKECMUSER;
import com.technokryon.ecommerce.model.TKECTORDERADDRESS;
import com.technokryon.ecommerce.model.TKECTORDERITEM;
import com.technokryon.ecommerce.model.TKECTORDERSTATUSHISTORY;
import com.technokryon.ecommerce.model.TKECTPRODUCTATTRIBUTE;
import com.technokryon.ecommerce.model.TKECTUSERADDRESS;
import com.technokryon.ecommerce.pojo.OptionAttribute;
import com.technokryon.ecommerce.pojo.Order;
import com.technokryon.ecommerce.pojo.OrderAddress;
import com.technokryon.ecommerce.pojo.OrderItem;
import com.technokryon.ecommerce.pojo.Product;
import com.technokryon.ecommerce.pojo.ProductAttribute;

@Repository("OrderDao")
@Transactional
@Component
public class OrderDaoImpl implements OrderDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Boolean checkAvailableProductQuantity(List<Product> product) {

		for (Product product1 : product) {

			TKECMPRODUCT tKECMPRODUCT = sessionFactory.getCurrentSession().get(TKECMPRODUCT.class, product1.getPId());

			if (product1.getPQuantity() > tKECMPRODUCT.getPQuantity()) {

				return false;
			}

		}
		return true;
	}

	@Override
	public String requestOrder(Order order) {

		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();

		try {

			String getOrderId = "FROM TKECMORDER ORDER BY oId DESC";

			String getProductShipmentId = "FROM TKECMPRODUCTSHIPMENT ORDER BY psmId DESC";

			Query orderIdQuery = session.createQuery(getOrderId);
			orderIdQuery.setMaxResults(1);
			TKECMORDER tKECMORDER1 = (TKECMORDER) orderIdQuery.uniqueResult();

			TKECMORDER tKECMORDER = new TKECMORDER();

			if (tKECMORDER1 == null) {

				tKECMORDER.setOId("TKECO00001");
			} else {

				String orderId = tKECMORDER1.getOId();
				Integer Ag = Integer.valueOf(orderId.substring(5));
				Ag++;

				System.err.println(Ag);
				tKECMORDER.setOId("TKECO" + String.format("%05d", Ag));
			}

			tKECMORDER.setOBaseAmount(order.getOBaseAmount());
			tKECMORDER.setODetectedAmount(order.getODetectedAmount());
			tKECMORDER.setOGrandTotal(order.getOGrandTotal());
			tKECMORDER.setOShippingAmount(order.getOShippingAmount());
			tKECMORDER.setODetectedShippingAmount(order.getODetectedShippingAmount());
			tKECMORDER.setOIsSendEmail(order.getOIsSendEmail());
			tKECMORDER.setOEmailId(order.getOEmailId());
			tKECMORDER.setOExpectedDelivery(order.getOExpectedDelivery());
			tKECMORDER.setOTkecmuId(session.get(TKECMUSER.class, order.getOTkecmuId()));
			tKECMORDER.setOCreatedUserId(order.getOCreatedUserId());
			tKECMORDER.setOTaxAmount(order.getOTaxAmount());
			tKECMORDER.setOCurrencyCode(order.getOCurrencyCode());
			tKECMORDER.setOTkecmpptId(session.get(TKECMPRODUCTPAYMENTTYPE.class, order.getOTkecmpptId()));
			tKECMORDER.setOTkecmsId(session.get(TKECMSTORE.class, order.getOTkecmsId()));

			if (order.getOTkecmpptId().trim().equals("TKECMPPT0001")) {
				tKECMORDER.setOTkecmosId(session.get(TKECMORDERSTATUS.class, "TKECMOS0002"));
			} else {
				tKECMORDER.setOTkecmosId(session.get(TKECMORDERSTATUS.class, "TKECMOS0001"));
			}
			session.save(tKECMORDER);

			TKECTUSERADDRESS tKECTUSERADDRESSBILL = session.get(TKECTUSERADDRESS.class, order.getBillingAddress());

			TKECTORDERADDRESS tKECTORDERADDRESSBILL1 = new TKECTORDERADDRESS();

			tKECTORDERADDRESSBILL1.setOaTkecmoId(session.get(TKECMORDER.class, tKECMORDER.getOId()));
			tKECTORDERADDRESSBILL1.setOaName(tKECTUSERADDRESSBILL.getUadName());
			tKECTORDERADDRESSBILL1.setOaEmailId(order.getOEmailId());
			tKECTORDERADDRESSBILL1.setOaPhone(tKECTUSERADDRESSBILL.getUadPhone());
			tKECTORDERADDRESSBILL1.setOaAltenativePhone(tKECTUSERADDRESSBILL.getUadAlternativePhone());
			tKECTORDERADDRESSBILL1.setOaAddress(tKECTUSERADDRESSBILL.getUadAddress());
			tKECTORDERADDRESSBILL1.setOaTkectsAgId(tKECTUSERADDRESSBILL.getUadTkectsAgId());
			tKECTORDERADDRESSBILL1.setOaCity(tKECTUSERADDRESSBILL.getUadCity());
			tKECTORDERADDRESSBILL1.setOaPostalCode(tKECTUSERADDRESSBILL.getUadPostalCode());
			tKECTORDERADDRESSBILL1.setOaAddressType(tKECTUSERADDRESSBILL.getUadAddressType());
			tKECTORDERADDRESSBILL1.setOaFlagAddress("BILL");

			session.save(tKECTORDERADDRESSBILL1);

			if (order.getShippingAddress() != null) {

				TKECTUSERADDRESS tKECTUSERADDRESSSHIP = session.get(TKECTUSERADDRESS.class,
						order.getShippingAddress());

				TKECTORDERADDRESS tKECTORDERADDRESSSHIP1 = new TKECTORDERADDRESS();

				tKECTORDERADDRESSSHIP1.setOaTkecmoId(session.get(TKECMORDER.class, tKECMORDER.getOId()));
				tKECTORDERADDRESSSHIP1.setOaName(tKECTUSERADDRESSSHIP.getUadName());
				tKECTORDERADDRESSSHIP1.setOaEmailId(order.getOEmailId());
				tKECTORDERADDRESSSHIP1.setOaPhone(tKECTUSERADDRESSSHIP.getUadPhone());
				tKECTORDERADDRESSSHIP1.setOaAltenativePhone(tKECTUSERADDRESSSHIP.getUadAlternativePhone());
				tKECTORDERADDRESSSHIP1.setOaAddress(tKECTUSERADDRESSSHIP.getUadAddress());
				tKECTORDERADDRESSSHIP1.setOaTkectsAgId(tKECTUSERADDRESSSHIP.getUadTkectsAgId());
				tKECTORDERADDRESSSHIP1.setOaCity(tKECTUSERADDRESSSHIP.getUadCity());
				tKECTORDERADDRESSSHIP1.setOaPostalCode(tKECTUSERADDRESSSHIP.getUadPostalCode());
				tKECTORDERADDRESSSHIP1.setOaAddressType(tKECTUSERADDRESSSHIP.getUadAddressType());
				tKECTORDERADDRESSSHIP1.setOaFlagAddress("SHIP");

				session.save(tKECTORDERADDRESSSHIP1);
			}

			for (Product product : order.getLO_PRODUCT()) {

				TKECMPRODUCT tKECMPRODUCT = session.get(TKECMPRODUCT.class, product.getPId());

				TKECTORDERITEM tKECTORDERITEM = new TKECTORDERITEM();

				tKECTORDERITEM.setOiTkecmoId(session.get(TKECMORDER.class, tKECMORDER.getOId()));
				tKECTORDERITEM.setOiTkecmpId(session.get(TKECMPRODUCT.class, tKECMPRODUCT.getPId()));
				tKECTORDERITEM.setOiSku(tKECMPRODUCT.getPSku());
				tKECTORDERITEM.setOiName(tKECMPRODUCT.getPName());
				tKECTORDERITEM.setOiWeight(tKECMPRODUCT.getPWeight());
				tKECTORDERITEM.setOiQuantity(product.getPQuantity());
				tKECTORDERITEM.setOiPrice(tKECMPRODUCT.getPPrice());
				tKECTORDERITEM
						.setOiTkecmosId(session.get(TKECMORDERSTATUS.class, tKECMORDER.getOTkecmosId().getOsId()));
				tKECTORDERITEM.setOiTkecmsId(session.get(TKECMSTORE.class, order.getOTkecmsId()));
				session.save(tKECTORDERITEM);

				if (order.getOTkecmpptId().trim().equals("TKECMPPT0001")) {
					TKECTORDERSTATUSHISTORY tKECTORDERSTATUSHISTORY = new TKECTORDERSTATUSHISTORY();

					tKECTORDERSTATUSHISTORY.setOshTkectAgId(tKECTORDERITEM);
					tKECTORDERSTATUSHISTORY
							.setOshTkecmosId(session.get(TKECMORDERSTATUS.class, tKECMORDER.getOTkecmosId().getOsId()));
					tKECTORDERSTATUSHISTORY.setOshCreatedDate(OffsetDateTime.now());
					tKECTORDERSTATUSHISTORY.setOshCreatedUserId(order.getOTkecmuId());
					session.save(tKECTORDERSTATUSHISTORY);

					Query getProductShipmentIdQuery = session.createQuery(getProductShipmentId);
					getProductShipmentIdQuery.setMaxResults(1);
					TKECMPRODUCTSHIPMENT tKECMPRODUCTSHIPMENT1 = (TKECMPRODUCTSHIPMENT) getProductShipmentIdQuery
							.uniqueResult();

					TKECMPRODUCTSHIPMENT tKECMPRODUCTSHIPMENT = new TKECMPRODUCTSHIPMENT();

					if (tKECMPRODUCTSHIPMENT1 == null) {

						tKECMPRODUCTSHIPMENT.setPsmId("TKECPSM00001");
					} else {

						String shipmentId = tKECMPRODUCTSHIPMENT1.getPsmId();
						Integer Ag = Integer.valueOf(shipmentId.substring(7));
						Ag++;

						// System.err.println(Ag);
						tKECMPRODUCTSHIPMENT.setPsmId("TKECPSM" + String.format("%05d", Ag));
					}

					tKECMPRODUCTSHIPMENT.setPsmTkectoiAgId(tKECTORDERITEM);
					tKECMPRODUCTSHIPMENT.setPsmTkecmsId(session.get(TKECMSTORE.class, order.getOTkecmsId()));
					tKECMPRODUCTSHIPMENT.setPsmWeight(tKECTORDERITEM.getOiWeight());
					tKECMPRODUCTSHIPMENT.setPsmQuantity(tKECTORDERITEM.getOiQuantity());
					tKECMPRODUCTSHIPMENT.setPsmCreatedDate(OffsetDateTime.now());
					tKECMPRODUCTSHIPMENT.setPsmStatus(tKECTORDERITEM.getOiTkecmosId().getOsId());
					session.save(tKECMPRODUCTSHIPMENT);
				}
			}

			for (Product product : order.getLO_PRODUCT()) {

				TKECMPRODUCT tKECMPRODUCT = session.get(TKECMPRODUCT.class, product.getPId());

				tKECMPRODUCT.setPQuantity(tKECMPRODUCT.getPQuantity() - product.getPQuantity());
				session.update(tKECMPRODUCT);
			}
			transaction.commit();
			session.close();

			return tKECMORDER.getOId();
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction.isActive()) {
				transaction.rollback();
			}
			session.close();

			return null;

		}

	}

	@Override
	public String updateTransactionId(Order order) {

		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {

			String orderId = "FROM TKECMORDER WHERE oId =:orderId AND oTkecmuId.uId =:userId";

			Query orderIdQuery = session.createQuery(orderId);

			orderIdQuery.setParameter("orderId", order.getOId());
			orderIdQuery.setParameter("userId", order.getUserId());

			TKECMORDER tKECMORDER = (TKECMORDER) orderIdQuery.uniqueResult();

			if (tKECMORDER == null) {

				transaction.commit();
				session.close();
				return "No Order Available";
			}

			if (tKECMORDER.getOTkecmpptId().getPptId().equals("TKECMPPT0002")) {

				if (tKECMORDER.getOTransactionId() != null) {

					transaction.commit();
					session.close();
					return "Already Paid";

				}

				tKECMORDER.setOTkecmosId(session.get(TKECMORDERSTATUS.class, "TKECMOS0002"));
				tKECMORDER.setOTransactionId(order.getOTransactionId());
				session.update(tKECMORDER);

				String orderItemId = "FROM TKECTORDERITEM WHERE oiTkecmoId.oId =:orderId";

				String orderItemAgId = "FROM TKECTORDERSTATUSHISTORY WHERE oshTkectAgId.oiAgId =:orderItemAgId";

				String getProductShipmentId = "FROM TKECMPRODUCTSHIPMENT ORDER BY psmId DESC";

				Query orderItemIdQuery = session.createQuery(orderItemId);

				orderItemIdQuery.setParameter("orderId", tKECMORDER.getOId());

				List<TKECTORDERITEM> tKECTORDERITEM = (List<TKECTORDERITEM>) orderItemIdQuery.getResultList();

				for (TKECTORDERITEM tKECTORDERITEM1 : tKECTORDERITEM) {

					tKECTORDERITEM1.setOiTkecmosId(session.get(TKECMORDERSTATUS.class, "TKECMOS0002"));
					session.update(tKECTORDERITEM1);

					Query orderItemAgIdQuery = session.createQuery(orderItemAgId);

					orderItemAgIdQuery.setParameter("orderItemAgId", tKECTORDERITEM1.getOiAgId());

					List<TKECTORDERSTATUSHISTORY> tKECTORDERSTATUSHISTORY = (List<TKECTORDERSTATUSHISTORY>) orderItemAgIdQuery
							.getResultList();

					for (TKECTORDERSTATUSHISTORY tKECTORDERSTATUSHISTORY1 : tKECTORDERSTATUSHISTORY) {

						tKECTORDERSTATUSHISTORY1.setOshTkecmosId(session.get(TKECMORDERSTATUS.class, "TKECMOS0002"));
						session.update(tKECTORDERSTATUSHISTORY1);
					}

					Query getProductShipmentIdQuery = session.createQuery(getProductShipmentId);
					getProductShipmentIdQuery.setMaxResults(1);
					TKECMPRODUCTSHIPMENT tKECMPRODUCTSHIPMENT1 = (TKECMPRODUCTSHIPMENT) getProductShipmentIdQuery
							.uniqueResult();

					TKECMPRODUCTSHIPMENT tKECMPRODUCTSHIPMENT = new TKECMPRODUCTSHIPMENT();

					if (tKECMPRODUCTSHIPMENT1 == null) {

						tKECMPRODUCTSHIPMENT.setPsmId("TKECPSM00001");
					} else {

						String shipmentId = tKECMPRODUCTSHIPMENT1.getPsmId();
						Integer Ag = Integer.valueOf(shipmentId.substring(7));
						Ag++;

						// System.err.println(Ag);
						tKECMPRODUCTSHIPMENT.setPsmId("TKECPSM" + String.format("%05d", Ag));
					}

					tKECMPRODUCTSHIPMENT.setPsmTkectoiAgId(tKECTORDERITEM1);
					tKECMPRODUCTSHIPMENT
							.setPsmTkecmsId(session.get(TKECMSTORE.class, tKECTORDERITEM1.getOiTkecmsId().getSId()));
					tKECMPRODUCTSHIPMENT.setPsmWeight(tKECTORDERITEM1.getOiWeight());
					tKECMPRODUCTSHIPMENT.setPsmQuantity(tKECTORDERITEM1.getOiQuantity());
					tKECMPRODUCTSHIPMENT.setPsmCreatedDate(OffsetDateTime.now());
					tKECMPRODUCTSHIPMENT.setPsmStatus(tKECTORDERITEM1.getOiTkecmosId().getOsId());
					session.save(tKECMPRODUCTSHIPMENT);
				}

				transaction.commit();
				session.close();

			} else {
				transaction.commit();
				session.close();

				return "Order Placed In Cash On Delivery";

			}
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction.isActive()) {
				transaction.rollback();
			}
			session.close();

			return "Technical Error";
		}
		return null;
	}

	@Override
	public List<OrderItem> orderList(String uId) {

		String userId = "FROM TKECTORDERITEM WHERE oiTkecmoId.oTkecmuId.uId =:userId";

		String productAttribute = "FROM TKECTPRODUCTATTRIBUTE WHERE paTkecmpId.pId =:proAttrProductId ";

		List<OrderItem> orderItem = new ArrayList<>();

		Query userIdQuery = sessionFactory.getCurrentSession().createQuery(userId);

		userIdQuery.setParameter("userId", uId);

		List<TKECTORDERITEM> tKECTORDERITEM = (List<TKECTORDERITEM>) userIdQuery.getResultList();

		for (TKECTORDERITEM tKECTORDERITEM1 : tKECTORDERITEM) {

			OrderItem orderItem1 = modelMapper.map(tKECTORDERITEM1, OrderItem.class);

			List<ProductAttribute> productAttribute1 = new ArrayList<ProductAttribute>();

			Query productAttributeQuery = sessionFactory.getCurrentSession().createQuery(productAttribute);

			productAttributeQuery.setParameter("proAttrProductId", tKECTORDERITEM1.getOiTkecmpId().getPId());

			ProductAttribute productAttribute2 = new ProductAttribute();

			List<TKECTPRODUCTATTRIBUTE> tKECTPRODUCTATTRIBUTE = productAttributeQuery.getResultList();

			List<OptionAttribute> optionAttribute = new ArrayList<>();

			for (TKECTPRODUCTATTRIBUTE tKECTPRODUCTATTRIBUTE1 : tKECTPRODUCTATTRIBUTE) {

				OptionAttribute optionAttribute1 = new OptionAttribute();

				optionAttribute1.setOaTkecmaId(tKECTPRODUCTATTRIBUTE1.getPaTkectoaId().getOaTkecmaId().getAId());
				optionAttribute1.setOaName(tKECTPRODUCTATTRIBUTE1.getPaTkectoaId().getOaName());
				optionAttribute.add(optionAttribute1);
			}

			productAttribute2.setLO_OPTIONATTRIBUTE(optionAttribute);

			productAttribute1.add(productAttribute2);

			orderItem1.setO_ProductAttribute(productAttribute1);

			orderItem.add(orderItem1);
		}

		return orderItem;
	}

	@Override
	public OrderItem orderItemById(Integer oiAgId) {

		String orderId = "FROM TKECTORDERADDRESS WHERE oaTkecmoId.oId =:orderId";

		String productAttribute = "FROM TKECTPRODUCTATTRIBUTE WHERE paTkecmpId.pId =:proAttrProductId ";

		TKECTORDERITEM tKECTORDERITEM = sessionFactory.getCurrentSession().get(TKECTORDERITEM.class, oiAgId);

		OrderItem orderItem = modelMapper.map(tKECTORDERITEM, OrderItem.class);

		List<ProductAttribute> productAttribute1 = new ArrayList<ProductAttribute>();

		Query productAttributeQuery = sessionFactory.getCurrentSession().createQuery(productAttribute);

		productAttributeQuery.setParameter("proAttrProductId", tKECTORDERITEM.getOiTkecmpId().getPId());

		ProductAttribute productAttribute2 = new ProductAttribute();

		List<TKECTPRODUCTATTRIBUTE> tKECTPRODUCTATTRIBUTE = productAttributeQuery.getResultList();

		List<OptionAttribute> optionAttribute = new ArrayList<>();

		for (TKECTPRODUCTATTRIBUTE tKECTPRODUCTATTRIBUTE2 : tKECTPRODUCTATTRIBUTE) {

			OptionAttribute optionAttribute1 = new OptionAttribute();

			optionAttribute1.setOaTkecmaId(tKECTPRODUCTATTRIBUTE2.getPaTkectoaId().getOaTkecmaId().getAId());
			optionAttribute1.setOaName(tKECTPRODUCTATTRIBUTE2.getPaTkectoaId().getOaName());
			optionAttribute.add(optionAttribute1);
		}

		productAttribute2.setLO_OPTIONATTRIBUTE(optionAttribute);

		productAttribute1.add(productAttribute2);

		orderItem.setO_ProductAttribute(productAttribute1);

		Query orderIdQuery = sessionFactory.getCurrentSession().createQuery(orderId);

		orderIdQuery.setParameter("orderId", orderItem.getOiTkecmoId());

		List<OrderAddress> orderAddress1 = new ArrayList();

		List<TKECTORDERADDRESS> tKECTORDERADDRESS = (List<TKECTORDERADDRESS>) orderIdQuery.getResultList();

		for (TKECTORDERADDRESS tKECTORDERADDRESS1 : tKECTORDERADDRESS) {

			OrderAddress orderAddress = modelMapper.map(tKECTORDERADDRESS1, OrderAddress.class);

			orderAddress1.add(orderAddress);
		}

		orderItem.setO_OrderAddress(orderAddress1);
		return orderItem;
	}

	@Override
	public void orderCancel(Integer oshAgId, String uId) {

		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		try {
			TKECTORDERITEM tKECTORDERITEM1 = session.get(TKECTORDERITEM.class, oshAgId);

			if (tKECTORDERITEM1.getOiTkecmoId().getOTkecmuId().getUId().equals(uId)) {

				TKECMORDER tKECMORDER = session.get(TKECMORDER.class, tKECTORDERITEM1.getOiTkecmoId().getOId());

				if (tKECMORDER.getOTkecmpptId().getPptId().equals("TKECMPPT0001")) {

					String deleteOrderStatusHistory = "DELETE FROM TKECTORDERSTATUSHISTORY WHERE oshTkectAgId.oiAgId =:orderItemAgId";
					Query deleteOrderStatusHistoryQuery = session.createQuery(deleteOrderStatusHistory);
					deleteOrderStatusHistoryQuery.setParameter("orderItemAgId", oshAgId);
					deleteOrderStatusHistoryQuery.executeUpdate();

					TKECTORDERITEM tKECTORDERITEM2 = session.get(TKECTORDERITEM.class, oshAgId);

					String orderId = tKECTORDERITEM2.getOiTkecmoId().getOId();

					tKECTORDERITEM2.setOiTkecmpId(null);
					tKECTORDERITEM2.setOiTkecmoId(null);
					tKECTORDERITEM2.setOiTkecmosId(null);
					session.delete(tKECTORDERITEM2);

					String orderItemId = "FROM TKECTORDERITEM WHERE oiTkecmoId.oId =:orderItemId";
					Query orderItemIdQuery = session.createQuery(orderItemId);
					orderItemIdQuery.setParameter("orderItemId", orderId);

					List<TKECTORDERITEM> tKECTORDERITEM = orderItemIdQuery.getResultList();

					for (TKECTORDERITEM tKECTORDERITEM3 : tKECTORDERITEM) {

						String deleteOrderStatusHistory1 = "FROM TKECTORDERSTATUSHISTORY WHERE oshTkectAgId.oiAgId =:orderItemAgId";
						Query deleteOrderStatusHistoryQuery1 = session.createQuery(deleteOrderStatusHistory1);
						deleteOrderStatusHistoryQuery1.setParameter("orderItemAgId", tKECTORDERITEM3.getOiAgId());

						List<TKECTORDERSTATUSHISTORY> tKECTORDERSTATUSHISTORY = deleteOrderStatusHistoryQuery1
								.getResultList();

						for (TKECTORDERSTATUSHISTORY tKECTORDERSTATUSHISTORY1 : tKECTORDERSTATUSHISTORY) {

							tKECTORDERSTATUSHISTORY1.setOshTkectAgId(null);
							tKECTORDERSTATUSHISTORY1.setOshTkecmosId(null);
							session.delete(tKECTORDERSTATUSHISTORY1);

						}
						tKECTORDERITEM3.setOiTkecmpId(null);
						tKECTORDERITEM3.setOiTkecmoId(null);
						tKECTORDERITEM3.setOiTkecmosId(null);
						session.delete(tKECTORDERITEM3);

					}

					String orderAddress = "FROM TKECTORDERADDRESS WHERE oaTkecmoId.oId =:orderId";
					Query orderAddressQuery = session.createQuery(orderAddress);
					orderAddressQuery.setParameter("orderId", orderId);

					List<TKECTORDERADDRESS> tKECTORDERADDRESS = orderAddressQuery.getResultList();

					for (TKECTORDERADDRESS tKECTORDERADDRESS1 : tKECTORDERADDRESS) {

						tKECTORDERADDRESS1.setOaTkecmoId(null);
						tKECTORDERADDRESS1.setOaTkectsAgId(null);
						session.delete(tKECTORDERADDRESS1);
					}

					String deleteOrder = "FROM TKECMORDER WHERE oId =:orderId";
					Query deleteOrderQuery = session.createQuery(deleteOrder);
					deleteOrderQuery.setParameter("orderId", orderId);

					TKECMORDER tKECMORDER1 = (TKECMORDER) deleteOrderQuery.uniqueResult();
					tKECMORDER1.setOTkecmosId(null);
					tKECMORDER1.setOTkecmpptId(null);
					tKECMORDER1.setOTkecmuId(null);
					session.delete(tKECMORDER1);

					transaction.commit();
					session.close();

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (transaction.isActive()) {
				transaction.rollback();
			}
			session.close();
		}
	}

}