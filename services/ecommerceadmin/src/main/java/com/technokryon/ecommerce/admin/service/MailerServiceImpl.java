package com.technokryon.ecommerce.admin.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("MailService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class MailerServiceImpl implements MailService {

	@Autowired
	private Environment env;

	@Override
	public void sendMail(String toaddress, String subject, String body) {

		final String username = env.getProperty("mail.username");
		final String password = env.getProperty("mail.password");

		Properties props = new Properties();

		if (env.getProperty("mail.auth").equals("Y")) {

			props.put("mail.smtp.auth", "true");

		}

		if (env.getProperty("mail.starttls").equals("Y")) {

			props.put("mail.smtp.starttls.enable", "true");
		}

		if (env.getProperty("mail.ssl").equals("Y")) {

			props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		}

		props.put("mail.smtp.host", env.getProperty("mail.host"));

		props.put("mail.smtp.port", env.getProperty("mail.port"));

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toaddress));
			message.setSubject(subject);
			message.setText(body);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public void sendSMS(BigInteger phone, Integer country, String contentText) {

		System.err.println(phone);
		System.err.println(country);

//		  String url = "http://api.msg91.com/api/sendhttp.php?mobiles=" + phone
//	                + "&authkey=274821AJjnkrCUfiJK5ccae9a7&message=" + contentText + "&sender=TEXTVO&country="+country;
//		  

		String url = null;
		try {
			url = "http://api.smsala.com/api/SendSMS?api_id=&api_password=5463h2nwGG&sms_type=T&encoding=T&sender_id=TSTALA&phonenumber="
					+ country + phone + "&textmessage=" + URLEncoder.encode(contentText, "UTF-8");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		System.out.println(url);

		try {

			URL myurl = new URL(url);

			HttpURLConnection con = (HttpURLConnection) myurl.openConnection();

			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "text/plain");
			con.setRequestProperty("Content-Language", "en-US");

			con.setUseCaches(false);
			con.setDoOutput(true);

			StringBuilder content;
			System.out.println(con.getURL());

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

			String line;
			content = new StringBuilder();

			while ((line = in.readLine()) != null) {
				content.append(line);
				content.append(System.lineSeparator());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
