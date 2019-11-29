package com.technokryon.ecommerce.service;

import java.math.BigInteger;

public interface MailService {

	void sendMail(String address,String header,String body);

//	void sendSMS(String phone, Integer country, String string);

	void sendSMS(BigInteger phone, Integer country, String contentText);
	
}

