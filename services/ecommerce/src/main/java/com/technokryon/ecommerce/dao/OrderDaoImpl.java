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
	private SessionFactory O_SessionFactory;

	@Autowired
	private ModelMapper O_ModelMapper;

	@Override
	public Boolean checkAvailableProductQuantity(List<Product> LO_PRODUCT) {

		for (Product O_Product : LO_PRODUCT) {

			TKECMPRODUCT O_TKECMPRODUCT = O_SessionFactory.getCurrentSession().get(TKECMPRODUCT.class,
					O_Product.getPId());

			if (O_Product.getPQuantity() > O_TKECMPRODUCT.getPQuantity()) {

				return false;
			}

		}
		return true;
	}

	@Override
	public String requestOrder(Order RO_Order) {

		Session O_Session = O_SessionFactory.openSession();
		Transaction O_Transaction = O_Session.beginTransaction();

		try {

			String getOrderId = "FROM TKECMORDER ORDER BY oId DESC";

			Query orderIdQuery = O_Session.createQuery(getOrderId);
			orderIdQuery.setMaxResults(1);
			TKECMORDER O_TKECMORDER1 = (TKECMORDER) orderIdQuery.uniqueResult();

			TKECMORDER O_TKECMORDER = new TKECMORDER();

			if (O_TKECMORDER1 == null) {

				O_TKECMORDER.setOId("TKECO00001");
			} else {

				String orderId = O_TKECMORDER1.getOId();
				Integer Ag = Integer.valueOf(orderId.substring(5));
				Ag++;

				System.err.println(Ag);
				O_TKECMORDER.setOId("TKECO" + String.format("%05d", Ag));
			}

			O_TKECMORDER.setOBaseAmount(RO_Order.getOBaseAmount());
			O_TKECMORDER.setODetectedAmount(RO_Order.getODetectedAmount());
			O_TKECMORDER.setOGrandTotal(RO_Order.getOGrandTotal());
			O_TKECMORDER.setOShippingAmount(RO_Order.getOShippingAmount());
			O_TKECMORDER.setODetectedShippingAmount(RO_Order.getODetectedShippingAmount());
			O_TKECMORDER.setOIsSendEmail(RO_Order.getOIsSendEmail());
			O_TKECMORDER.setOEmailId(RO_Order.getOEmailId());
			O_TKECMORDER.setOExpectedDelivery(RO_Order.getOExpectedDelivery());
			O_TKECMORDER.setOTkecmuId(O_Session.get(TKECMUSER.class, RO_Order.getOTkecmuId()));
			O_TKECMORDER.setOCreatedUserId(RO_Order.getOCreatedUserId());
			O_TKECMORDER.setOTaxAmount(RO_Order.getOTaxAmount());
			O_TKECMORDER.setOCurrencyCode(RO_Order.getOCurrencyCode());
			O_TKECMORDER.setOTkecmpptId(O_Session.get(TKECMPRODUCTPAYMENTTYPE.class, RO_Order.getOTkecmpptId()));

			if (RO_Order.getOTkecmpptId().trim().equals("TKECMPPT0001")) {
				O_TKECMORDER.setOTkecmosId(O_Session.get(TKECMORDERSTATUS.class, "TKECMOS0002"));
			} else {
				O_TKECMORDER.setOTkecmosId(O_Session.get(TKECMORDERSTATUS.class, "TKECMOS0001"));
			}
			O_Session.save(O_TKECMORDER);

			TKECTUSERADDRESS O_TKECTUSERADDRESS_BILL = O_Session.get(TKECTUSERADDRESS.class,
					RO_Order.getBillingAddress());

			TKECTORDERADDRESS O_TKECTORDERADDRESS_BILL = new TKECTORDERADDRESS();

			O_TKECTORDERADDRESS_BILL.setOaTkecmoId(O_Session.get(TKECMORDER.class, O_TKECMORDER.getOId()));
			O_TKECTORDERADDRESS_BILL.setOaName(O_TKECTUSERADDRESS_BILL.getUadName());
			O_TKECTORDERADDRESS_BILL.setOaEmailId(RO_Order.getOEmailId());
			O_TKECTORDERADDRESS_BILL.setOaPhone(O_TKECTUSERADDRESS_BILL.getUadPhone());
			O_TKECTORDERADDRESS_BILL.setOaAltenativePhone(O_TKECTUSERADDRESS_BILL.getUadAlternativePhone());
			O_TKECTORDERADDRESS_BILL.setOaAddress(O_TKECTUSERADDRESS_BILL.getUadAddress());
			O_TKECTORDERADDRESS_BILL.setOaTkectsAgId(O_TKECTUSERADDRESS_BILL.getUadTkectsAgId());
			O_TKECTORDERADDRESS_BILL.setOaCity(O_TKECTUSERADDRESS_BILL.getUadCity());
			O_TKECTORDERADDRESS_BILL.setOaPostalCode(O_TKECTUSERADDRESS_BILL.getUadPostalCode());
			O_TKECTORDERADDRESS_BILL.setOaAddressType(O_TKECTUSERADDRESS_BILL.getUadAddressType());
			O_TKECTORDERADDRESS_BILL.setOaFlagAddress("BILL");

			O_Session.save(O_TKECTORDERADDRESS_BILL);

			if (RO_Order.getShippingAddress() != null) {

				TKECTUSERADDRESS O_TKECTUSERADDRESS_SHIP = O_Session.get(TKECTUSERADDRESS.class,
						RO_Order.getShippingAddress());

				TKECTORDERADDRESS O_TKECTORDERADDRESS_SHIP = new TKECTORDERADDRESS();

				O_TKECTORDERADDRESS_SHIP.setOaTkecmoId(O_Session.get(TKECMORDER.class, O_TKECMORDER.getOId()));
				O_TKECTORDERADDRESS_SHIP.setOaName(O_TKECTUSERADDRESS_SHIP.getUadName());
				O_TKECTORDERADDRESS_SHIP.setOaEmailId(RO_Order.getOEmailId());
				O_TKECTORDERADDRESS_SHIP.setOaPhone(O_TKECTUSERADDRESS_SHIP.getUadPhone());
				O_TKECTORDERADDRESS_SHIP.setOaAltenativePhone(O_TKECTUSERADDRESS_SHIP.getUadAlternativePhone());
				O_TKECTORDERADDRESS_SHIP.setOaAddress(O_TKECTUSERADDRESS_SHIP.getUadAddress());
				O_TKECTORDERADDRESS_SHIP.setOaTkectsAgId(O_TKECTUSERADDRESS_SHIP.getUadTkectsAgId());
				O_TKECTORDERADDRESS_SHIP.setOaCity(O_TKECTUSERADDRESS_SHIP.getUadCity());
				O_TKECTORDERADDRESS_SHIP.setOaPostalCode(O_TKECTUSERADDRESS_SHIP.getUadPostalCode());
				O_TKECTORDERADDRESS_SHIP.setOaAddressType(O_TKECTUSERADDRESS_SHIP.getUadAddressType());
				O_TKECTORDERADDRESS_SHIP.setOaFlagAddress("SHIP");

				O_Session.save(O_TKECTORDERADDRESS_SHIP);
			}

			for (Product O_Product : RO_Order.getLO_PRODUCT()) {

				TKECMPRODUCT O_TKECMPRODUCT = O_Session.get(TKECMPRODUCT.class, O_Product.getPId());

				TKECTORDERITEM O_TKECTORDERITEM = new TKECTORDERITEM();

				O_TKECTORDERITEM.setOiTkecmoId(O_Session.get(TKECMORDER.class, O_TKECMORDER.getOId()));
				O_TKECTORDERITEM.setOiTkecmpId(O_Session.get(TKECMPRODUCT.class, O_TKECMPRODUCT.getPId()));
				O_TKECTORDERITEM.setOiSku(O_TKECMPRODUCT.getPSku());
				O_TKECTORDERITEM.setOiName(O_TKECMPRODUCT.getPName());
				O_TKECTORDERITEM.setOiWeight(O_TKECMPRODUCT.getPWeight());
				O_TKECTORDERITEM.setOiQuantity(O_Product.getPQuantity());
				O_TKECTORDERITEM.setOiPrice(O_TKECMPRODUCT.getPPrice());
				O_TKECTORDERITEM
						.setOiTkecmosId(O_Session.get(TKECMORDERSTATUS.class, O_TKECMORDER.getOTkecmosId().getOsId()));
				O_Session.save(O_TKECTORDERITEM);

				TKECTORDERSTATUSHISTORY O_TKECTORDERSTATUSHISTORY = new TKECTORDERSTATUSHISTORY();

				O_TKECTORDERSTATUSHISTORY.setOshTkectAgId(O_TKECTORDERITEM);
				O_TKECTORDERSTATUSHISTORY
						.setOshTkecmosId(O_Session.get(TKECMORDERSTATUS.class, O_TKECMORDER.getOTkecmosId().getOsId()));
				O_TKECTORDERSTATUSHISTORY.setOshCreatedDate(OffsetDateTime.now());
				O_TKECTORDERSTATUSHISTORY.setOshCreatedUserId(RO_Order.getOTkecmuId());
				O_Session.save(O_TKECTORDERSTATUSHISTORY);
			}

			for (Product O_Product : RO_Order.getLO_PRODUCT()) {

				TKECMPRODUCT O_TKECMPRODUCT = O_Session.get(TKECMPRODUCT.class, O_Product.getPId());

				O_TKECMPRODUCT.setPQuantity(O_TKECMPRODUCT.getPQuantity() - O_Product.getPQuantity());
				O_Session.update(O_TKECMPRODUCT);
			}
			O_Transaction.commit();
			O_Session.close();

			return O_TKECMORDER.getOId();
		} catch (Exception e) {
			e.printStackTrace();
			if (O_Transaction.isActive()) {
				O_Transaction.rollback();
			}
			O_Session.close();

			return null;

		}

	}

	@Override
	public String updateTransactionId(Order RO_Order) {

		Session O_Session = O_SessionFactory.openSession();
		Transaction O_Transaction = O_Session.beginTransaction();
		try {

			String orderId = "FROM TKECMORDER WHERE oId =:orderId AND oTkecmuId.uId =:userId";

			Query orderIdQuery = O_Session.createQuery(orderId);

			orderIdQuery.setParameter("orderId", RO_Order.getOId());
			orderIdQuery.setParameter("userId", RO_Order.getUserId());

			TKECMORDER O_TKECMORDER = (TKECMORDER) orderIdQuery.uniqueResult();

			if (O_TKECMORDER == null) {

				O_Transaction.commit();
				O_Session.close();
				return "No Order Available";
			}

			if (O_TKECMORDER.getOTkecmpptId().getPptId().equals("TKECMPPT0002")) {

				if (O_TKECMORDER.getOTransactionId() != null) {

					O_Transaction.commit();
					O_Session.close();
					return "Already Paid";

				}

				O_TKECMORDER.setOTkecmosId(O_Session.get(TKECMORDERSTATUS.class, "TKECMOS0002"));
				O_TKECMORDER.setOTransactionId(RO_Order.getOTransactionId());
				O_Session.update(O_TKECMORDER);

				String orderItemId = "FROM TKECTORDERITEM WHERE oiTkecmoId.oId =:orderId";

				String orderItemAgId = "FROM TKECTORDERSTATUSHISTORY WHERE oshTkectAgId.oiAgId =:orderItemAgId";

				Query orderItemIdQuery = O_Session.createQuery(orderItemId);

				orderItemIdQuery.setParameter("orderId", O_TKECMORDER.getOId());

				List<TKECTORDERITEM> LO_TKECTORDERITEM = (List<TKECTORDERITEM>) orderItemIdQuery.getResultList();

				for (TKECTORDERITEM O_TKECTORDERITEM : LO_TKECTORDERITEM) {

					O_TKECTORDERITEM.setOiTkecmosId(O_Session.get(TKECMORDERSTATUS.class, "TKECMOS0002"));
					O_Session.update(O_TKECTORDERITEM);

					Query orderItemAgIdQuery = O_Session.createQuery(orderItemAgId);

					orderItemAgIdQuery.setParameter("orderItemAgId", O_TKECTORDERITEM.getOiAgId());

					List<TKECTORDERSTATUSHISTORY> LO_TKECTORDERSTATUSHISTORY = (List<TKECTORDERSTATUSHISTORY>) orderItemAgIdQuery
							.getResultList();

					for (TKECTORDERSTATUSHISTORY O_TKECTORDERSTATUSHISTORY : LO_TKECTORDERSTATUSHISTORY) {

						O_TKECTORDERSTATUSHISTORY.setOshTkecmosId(O_Session.get(TKECMORDERSTATUS.class, "TKECMOS0002"));
						O_Session.update(O_TKECTORDERSTATUSHISTORY);
					}
				}
				O_Transaction.commit();
				O_Session.close();

			} else {
				O_Transaction.commit();
				O_Session.close();

				return "Order Placed In Cash On Delivery";

			}
		} catch (Exception e) {
			e.printStackTrace();
			if (O_Transaction.isActive()) {
				O_Transaction.rollback();
			}
			O_Session.close();

			return "Technical Error";
		}
		return null;
	}

	@Override
	public List<OrderItem> orderList(String uId) {

		String userId = "FROM TKECTORDERITEM WHERE oiTkecmoId.oTkecmuId.uId =:userId";

		String productAttribute = "FROM TKECTPRODUCTATTRIBUTE WHERE paTkecmpId.pId =:proAttrProductId ";

		List<OrderItem> LO_OrderItem = new ArrayList<>();

		Query userIdQuery = O_SessionFactory.getCurrentSession().createQuery(userId);

		userIdQuery.setParameter("userId", uId);

		List<TKECTORDERITEM> LO_TKECTORDERITEM = (List<TKECTORDERITEM>) userIdQuery.getResultList();

		for (TKECTORDERITEM O_TKECTORDERITEM : LO_TKECTORDERITEM) {

			OrderItem O_OrderItem = O_ModelMapper.map(O_TKECTORDERITEM, OrderItem.class);

			List<ProductAttribute> LO_ProductAttribute = new ArrayList<ProductAttribute>();

			Query productAttributeQuery = O_SessionFactory.getCurrentSession().createQuery(productAttribute);

			productAttributeQuery.setParameter("proAttrProductId", O_TKECTORDERITEM.getOiTkecmpId().getPId());

			ProductAttribute O_ProductAttribute = new ProductAttribute();

			List<TKECTPRODUCTATTRIBUTE> LO_TKECTPRODUCTATTRIBUTE = productAttributeQuery.getResultList();

			List<OptionAttribute> LO_OptionAttribute = new ArrayList<>();

			for (TKECTPRODUCTATTRIBUTE O_TKECTPRODUCTATTRIBUTE : LO_TKECTPRODUCTATTRIBUTE) {

				OptionAttribute O_OptionAttribute = new OptionAttribute();

				O_OptionAttribute.setOaTkecmaId(O_TKECTPRODUCTATTRIBUTE.getPaTkectoaId().getOaTkecmaId().getAId());
				O_OptionAttribute.setOaName(O_TKECTPRODUCTATTRIBUTE.getPaTkectoaId().getOaName());
				LO_OptionAttribute.add(O_OptionAttribute);
			}

			O_ProductAttribute.setLO_OPTIONATTRIBUTE(LO_OptionAttribute);

			LO_ProductAttribute.add(O_ProductAttribute);

			O_OrderItem.setO_ProductAttribute(LO_ProductAttribute);

			LO_OrderItem.add(O_OrderItem);
		}

		return LO_OrderItem;
	}

	@Override
	public OrderItem orderItemById(Integer oiAgId) {

		String orderId = "FROM TKECTORDERADDRESS WHERE oaTkecmoId.oId =:orderId";

		String productAttribute = "FROM TKECTPRODUCTATTRIBUTE WHERE paTkecmpId.pId =:proAttrProductId ";

		TKECTORDERITEM O_TKECTORDERITEM = O_SessionFactory.getCurrentSession().get(TKECTORDERITEM.class, oiAgId);

		OrderItem O_OrderItem = O_ModelMapper.map(O_TKECTORDERITEM, OrderItem.class);

		List<ProductAttribute> LO_ProductAttribute = new ArrayList<ProductAttribute>();

		Query productAttributeQuery = O_SessionFactory.getCurrentSession().createQuery(productAttribute);

		productAttributeQuery.setParameter("proAttrProductId", O_TKECTORDERITEM.getOiTkecmpId().getPId());

		ProductAttribute O_ProductAttribute = new ProductAttribute();

		List<TKECTPRODUCTATTRIBUTE> LO_TKECTPRODUCTATTRIBUTE = productAttributeQuery.getResultList();

		List<OptionAttribute> LO_OptionAttribute = new ArrayList<>();

		for (TKECTPRODUCTATTRIBUTE O_TKECTPRODUCTATTRIBUTE : LO_TKECTPRODUCTATTRIBUTE) {

			OptionAttribute O_OptionAttribute = new OptionAttribute();

			O_OptionAttribute.setOaTkecmaId(O_TKECTPRODUCTATTRIBUTE.getPaTkectoaId().getOaTkecmaId().getAId());
			O_OptionAttribute.setOaName(O_TKECTPRODUCTATTRIBUTE.getPaTkectoaId().getOaName());
			LO_OptionAttribute.add(O_OptionAttribute);
		}

		O_ProductAttribute.setLO_OPTIONATTRIBUTE(LO_OptionAttribute);

		LO_ProductAttribute.add(O_ProductAttribute);

		O_OrderItem.setO_ProductAttribute(LO_ProductAttribute);

		Query orderIdQuery = O_SessionFactory.getCurrentSession().createQuery(orderId);

		orderIdQuery.setParameter("orderId", O_OrderItem.getOiTkecmoId());

		List<OrderAddress> LO_OrderAddress = new ArrayList();

		List<TKECTORDERADDRESS> LO_TKECTORDERADDRESS = (List<TKECTORDERADDRESS>) orderIdQuery.getResultList();

		for (TKECTORDERADDRESS O_TKECTORDERADDRESS : LO_TKECTORDERADDRESS) {

			OrderAddress O_OrderAddress = O_ModelMapper.map(O_TKECTORDERADDRESS, OrderAddress.class);

			LO_OrderAddress.add(O_OrderAddress);
		}

		O_OrderItem.setO_OrderAddress(LO_OrderAddress);
		return O_OrderItem;
	}

	@Override
	public void orderCancel(Integer oshAgId, String uId) {

		Session O_Session = O_SessionFactory.openSession();
		Transaction O_Transaction = O_Session.beginTransaction();
		try {
			TKECTORDERITEM O_TKECTORDERITEM4 = O_Session.get(TKECTORDERITEM.class, oshAgId);

			if (O_TKECTORDERITEM4.getOiTkecmoId().getOTkecmuId().getUId().equals(uId)) {

				TKECMORDER O_TKECMORDER6 = O_Session.get(TKECMORDER.class, O_TKECTORDERITEM4.getOiTkecmoId().getOId());

				if (O_TKECMORDER6.getOTkecmpptId().equals("TKECMPPT0001")) {
//			String orderId = "FROM TKECMORDER WHERE oId =:orderId";
//
//			Query orderIdQuery = O_Session.createQuery(orderId);
//			orderIdQuery.setParameter("orderId", O_TKECTORDERITEM4.getOiTkecmoId().getOId());
					// try {
					String deleteOrderStatusHistory = "DELETE FROM TKECTORDERSTATUSHISTORY WHERE oshTkectAgId.oiAgId =:orderItemAgId";
					Query deleteOrderStatusHistoryQuery = O_Session.createQuery(deleteOrderStatusHistory);
					deleteOrderStatusHistoryQuery.setParameter("orderItemAgId", oshAgId);
					deleteOrderStatusHistoryQuery.executeUpdate();

					TKECTORDERITEM O_TKECTORDERITEM = O_Session.get(TKECTORDERITEM.class, oshAgId);

					String orderId = O_TKECTORDERITEM.getOiTkecmoId().getOId();

					O_TKECTORDERITEM.setOiTkecmpId(null);
					O_TKECTORDERITEM.setOiTkecmoId(null);
					O_TKECTORDERITEM.setOiTkecmosId(null);
					O_Session.delete(O_TKECTORDERITEM);

					String orderItemId = "FROM TKECTORDERITEM WHERE oiTkecmoId.oId =:orderItemId";
					Query orderItemIdQuery = O_Session.createQuery(orderItemId);
					orderItemIdQuery.setParameter("orderItemId", orderId);

					List<TKECTORDERITEM> LO_TKECTORDERITEM = orderItemIdQuery.getResultList();

					for (TKECTORDERITEM O_TKECTORDERITEM1 : LO_TKECTORDERITEM) {

						String deleteOrderStatusHistory1 = "FROM TKECTORDERSTATUSHISTORY WHERE oshTkectAgId.oiAgId =:orderItemAgId";
						Query deleteOrderStatusHistoryQuery1 = O_Session.createQuery(deleteOrderStatusHistory1);
						deleteOrderStatusHistoryQuery1.setParameter("orderItemAgId", O_TKECTORDERITEM1.getOiAgId());

						List<TKECTORDERSTATUSHISTORY> LO_TKECTORDERSTATUSHISTORY = deleteOrderStatusHistoryQuery1
								.getResultList();

						for (TKECTORDERSTATUSHISTORY O_TKECTORDERSTATUSHISTORY : LO_TKECTORDERSTATUSHISTORY) {

							O_TKECTORDERSTATUSHISTORY.setOshTkectAgId(null);
							O_TKECTORDERSTATUSHISTORY.setOshTkecmosId(null);
							O_Session.delete(O_TKECTORDERSTATUSHISTORY);

						}
						O_TKECTORDERITEM1.setOiTkecmpId(null);
						O_TKECTORDERITEM1.setOiTkecmoId(null);
						O_TKECTORDERITEM1.setOiTkecmosId(null);
						O_Session.delete(O_TKECTORDERITEM1);

					}

					String orderAddress = "FROM TKECTORDERADDRESS WHERE oaTkecmoId.oId =:orderId";
					Query orderAddressQuery = O_Session.createQuery(orderAddress);
					orderAddressQuery.setParameter("orderId", orderId);

					List<TKECTORDERADDRESS> LO_TKECTORDERADDRESS = orderAddressQuery.getResultList();

					for (TKECTORDERADDRESS O_TKECTORDERADDRESS : LO_TKECTORDERADDRESS) {

						O_TKECTORDERADDRESS.setOaTkecmoId(null);
						O_TKECTORDERADDRESS.setOaTkectsAgId(null);
						O_Session.delete(O_TKECTORDERADDRESS);
					}

					String deleteOrder = "FROM TKECMORDER WHERE oId =:orderId";
					Query deleteOrderQuery = O_Session.createQuery(deleteOrder);
					deleteOrderQuery.setParameter("orderId", orderId);

					TKECMORDER O_TKECMORDER = (TKECMORDER) deleteOrderQuery.uniqueResult();
					O_TKECMORDER.setOTkecmosId(null);
					O_TKECMORDER.setOTkecmpptId(null);
					O_TKECMORDER.setOTkecmuId(null);
					O_Session.delete(O_TKECMORDER);

					O_Transaction.commit();
					O_Session.close();

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (O_Transaction.isActive()) {
				O_Transaction.rollback();
			}
			O_Session.close();
		}
	}

}