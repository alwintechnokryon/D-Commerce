package com.technokryon.ecommerce.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.model.TKECMORDER;
import com.technokryon.ecommerce.model.TKECMPRODUCT;
import com.technokryon.ecommerce.model.TKECMUSER;
import com.technokryon.ecommerce.model.TKECTORDERADDRESS;
import com.technokryon.ecommerce.model.TKECTORDERITEM;
import com.technokryon.ecommerce.model.TKECTUSERADDRESS;
import com.technokryon.ecommerce.pojo.ORDER;

@Repository("OrderDao")
@Transactional
@Component
public class OrderDaoImpl implements OrderDao {

	@Autowired
	private SessionFactory O_SessionFactory;

	@Override
	public Boolean checkAvailableProductQuantity(String productId, Integer proQuantity) {

		String productQnty = "FROM TKECMPRODUCT WHERE pId=: productId";

		Query productQntyQuery = O_SessionFactory.getCurrentSession().createQuery(productQnty);

		productQntyQuery.setParameter("productId", productId);
		// productQntyQuery.setParameter("proQuantity", proQuantity);

		TKECMPRODUCT O_TKECMPRODUCT = (TKECMPRODUCT) productQntyQuery.uniqueResult();

		System.err.println("Product Total Quantity" + O_TKECMPRODUCT.getPQuantity());
		System.err.println("User Request Quantity" + proQuantity);

		if (proQuantity <= O_TKECMPRODUCT.getPQuantity()) {

			return true;
		}

		return false;
	}

	@Override
	public String requestOrder(ORDER RO_ORDER) {

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

			O_TKECMORDER.setOBaseAmount(RO_ORDER.getOBaseAmount());
			O_TKECMORDER.setODetectedAmount(RO_ORDER.getODetectedAmount());
			O_TKECMORDER.setOGrandTotal(RO_ORDER.getOGrandTotal());
			O_TKECMORDER.setOShippingAmount(RO_ORDER.getOShippingAmount());
			O_TKECMORDER.setODetectedShippingAmount(RO_ORDER.getODetectedShippingAmount());
			O_TKECMORDER.setOIsSendEmail(RO_ORDER.getOIsSendEmail());
			O_TKECMORDER.setOEmailId(RO_ORDER.getOEmailId());
			O_TKECMORDER.setOExpectedDelivery(RO_ORDER.getOExpectedDelivery());
			O_TKECMORDER.setOTkecmuId(O_Session.get(TKECMUSER.class, RO_ORDER.getOTkecmuId()));
			O_TKECMORDER.setOCreatedUserId(RO_ORDER.getOCreatedUserId());
			O_TKECMORDER.setOPaymentType(RO_ORDER.getOPaymentType());
			O_TKECMORDER.setOTaxAmount(RO_ORDER.getOTaxAmount());
			O_TKECMORDER.setOCurrencyCode(RO_ORDER.getOCurrencyCode());
			O_Session.save(O_TKECMORDER);

			TKECTUSERADDRESS O_TKECTUSERADDRESS_BILL = O_Session.get(TKECTUSERADDRESS.class,
					RO_ORDER.getBillingAddress());

			TKECTORDERADDRESS O_TKECTORDERADDRESS_BILL = new TKECTORDERADDRESS();

			O_TKECTORDERADDRESS_BILL.setOaTkecmoId(O_Session.get(TKECMORDER.class, O_TKECMORDER.getOId()));
			O_TKECTORDERADDRESS_BILL.setOaName(O_TKECTUSERADDRESS_BILL.getUadName());
			O_TKECTORDERADDRESS_BILL.setOaEmailId(RO_ORDER.getOEmailId());
			O_TKECTORDERADDRESS_BILL.setOaPhone(O_TKECTUSERADDRESS_BILL.getUadPhone());
			O_TKECTORDERADDRESS_BILL.setOaAltenativePhone(O_TKECTUSERADDRESS_BILL.getUadAlternativePhone());
			O_TKECTORDERADDRESS_BILL.setOaAddress(O_TKECTUSERADDRESS_BILL.getUadAddress());
			O_TKECTORDERADDRESS_BILL.setOaStreet(O_TKECTUSERADDRESS_BILL.getUadStreet());
			O_TKECTORDERADDRESS_BILL.setOaCity(O_TKECTUSERADDRESS_BILL.getUadCity());
			O_TKECTORDERADDRESS_BILL.setOaPostalCode(O_TKECTUSERADDRESS_BILL.getUadPostalCode());
			O_TKECTORDERADDRESS_BILL.setOaAddressType(O_TKECTUSERADDRESS_BILL.getUadType());
			O_TKECTORDERADDRESS_BILL.setOaFlagAddress("BILL");

			O_Session.save(O_TKECTORDERADDRESS_BILL);

			if (RO_ORDER.getShippingAddress() == null) {

				TKECTUSERADDRESS O_TKECTUSERADDRESS_SHIP = O_Session.get(TKECTUSERADDRESS.class,
						RO_ORDER.getShippingAddress());

				TKECTORDERADDRESS O_TKECTORDERADDRESS_SHIP = new TKECTORDERADDRESS();

				O_TKECTORDERADDRESS_SHIP.setOaTkecmoId(O_Session.get(TKECMORDER.class, O_TKECMORDER.getOId()));
				O_TKECTORDERADDRESS_SHIP.setOaName(O_TKECTUSERADDRESS_SHIP.getUadName());
				O_TKECTORDERADDRESS_SHIP.setOaEmailId(RO_ORDER.getOEmailId());
				O_TKECTORDERADDRESS_SHIP.setOaPhone(O_TKECTUSERADDRESS_SHIP.getUadPhone());
				O_TKECTORDERADDRESS_SHIP.setOaAltenativePhone(O_TKECTUSERADDRESS_SHIP.getUadAlternativePhone());
				O_TKECTORDERADDRESS_SHIP.setOaAddress(O_TKECTUSERADDRESS_SHIP.getUadAddress());
				O_TKECTORDERADDRESS_SHIP.setOaStreet(O_TKECTUSERADDRESS_SHIP.getUadStreet());
				O_TKECTORDERADDRESS_SHIP.setOaCity(O_TKECTUSERADDRESS_SHIP.getUadCity());
				O_TKECTORDERADDRESS_SHIP.setOaPostalCode(O_TKECTUSERADDRESS_SHIP.getUadPostalCode());
				O_TKECTORDERADDRESS_SHIP.setOaAddressType(O_TKECTUSERADDRESS_SHIP.getUadType());
				O_TKECTORDERADDRESS_SHIP.setOaFlagAddress("SHIP");

				O_Session.save(O_TKECTORDERADDRESS_SHIP);
			}

			TKECMPRODUCT O_TKECMPRODUCT = O_Session.get(TKECMPRODUCT.class, RO_ORDER.getProductId());

			TKECTORDERITEM O_TKECTORDERITEM = new TKECTORDERITEM();

			O_TKECTORDERITEM.setOiTkecmoId(O_Session.get(TKECMORDER.class, O_TKECMORDER.getOId()));
			O_TKECTORDERITEM.setOiTkecmpId(O_Session.get(TKECMPRODUCT.class, RO_ORDER.getProductId()));
			O_TKECTORDERITEM.setOiSku(O_TKECMPRODUCT.getPSku());
			O_TKECTORDERITEM.setOiName(O_TKECMPRODUCT.getPName());
			O_TKECTORDERITEM.setOiWeight(O_TKECMPRODUCT.getPWeight());
			O_TKECTORDERITEM.setOiPaymentType(RO_ORDER.getOPaymentType());
			O_TKECTORDERITEM.setOiPrice(O_TKECMPRODUCT.getPPrice());
			O_Session.save(O_TKECTORDERITEM);

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

}
