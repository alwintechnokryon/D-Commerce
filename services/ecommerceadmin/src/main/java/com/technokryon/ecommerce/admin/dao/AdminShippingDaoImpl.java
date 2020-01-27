package com.technokryon.ecommerce.admin.dao;

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
	SessionFactory sessionFactory;

	@Override
	public void addShippingCost(ShippingCost shippingCost) {

		TKECMSHIPPINGCOST tKECMSHIPPINGCOST = new TKECMSHIPPINGCOST();

		tKECMSHIPPINGCOST.setScDestPincode(shippingCost.getScDestPincode());
		tKECMSHIPPINGCOST.setScDestPincodeTo(shippingCost.getScDestPincodeTo());
		tKECMSHIPPINGCOST.setScPrice(shippingCost.getScPrice());
		tKECMSHIPPINGCOST.setScWeightFrom(shippingCost.getScWeightFrom());
		tKECMSHIPPINGCOST.setScWeightTo(shippingCost.getScWeightTo());
		sessionFactory.getCurrentSession().save(tKECMSHIPPINGCOST);
	}

}
