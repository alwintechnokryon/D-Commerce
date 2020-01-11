package com.technokryon.ecommerce.admin.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.technokryon.ecommerce.admin.model.TKECMSHIPPINGCOST;
import com.technokryon.ecommerce.admin.pojo.ShippingCost;

@Repository("AdminShippingDao")
@Component
@Transactional
public class AdminShippingDaoImpl implements AdminShippingDao {

	@Autowired
	SessionFactory O_SessionFactory;

	@Override
	public void addShippingCost(ShippingCost RO_ShippingCost) {

		Session O_Session = O_SessionFactory.getCurrentSession();
		TKECMSHIPPINGCOST O_TKECMSHIPPINGCOST = new TKECMSHIPPINGCOST();

		O_TKECMSHIPPINGCOST.setScDestPincode(RO_ShippingCost.getScDestPincode());
		O_TKECMSHIPPINGCOST.setScDestPincodeTo(RO_ShippingCost.getScDestPincodeTo());
		O_TKECMSHIPPINGCOST.setScPrice(RO_ShippingCost.getScPrice());
		O_TKECMSHIPPINGCOST.setScWeightFrom(RO_ShippingCost.getScWeightFrom());
		O_TKECMSHIPPINGCOST.setScWeightTo(RO_ShippingCost.getScWeightTo());
		O_Session.save(O_TKECMSHIPPINGCOST);
	}

}
