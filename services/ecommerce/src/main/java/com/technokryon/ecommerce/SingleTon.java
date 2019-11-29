package com.technokryon.ecommerce;

import java.util.HashMap;
import java.util.Random;
import java.util.regex.Pattern;

import org.springframework.http.HttpStatus;

import com.technokryon.ecommerce.pojo.PJ_Response;

public class SingleTon {

	public static final String PASSWORD_RESET_MAIL_HEADER = "Ecommerce - OTP Verification";

	public static Boolean isEmailValid(String email) {

		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}

	public static Integer getRandomUserId() {
		// It will generate 6 digit random Number.
		// from 0 to 999999
		Random rnd = new Random();
		int number = rnd.nextInt(98999) + 100000;
		// this will convert any number sequence into 6 character.
		return number;

	}

	public static HashMap<String, Object> returnInfo(String message) {
		HashMap<String, Object> map = new HashMap<>();

		map.put("message", message);

		return map;
	}

	public static HashMap<String, Object> returnSuccess(HashMap<String, Object> map, String object) {

		map.put("message", object);

		return map;
	}

	public static HashMap<String, Object> returnTechnicalError(String message) {
		HashMap<String, Object> map = new HashMap<>();

		map.put("message", message);

		return map;
	}

	public static String stripExtension(final String s) {
		return s != null && s.lastIndexOf(".") > 0 ? s.substring(0, s.lastIndexOf(".")) : s;
	}

}
