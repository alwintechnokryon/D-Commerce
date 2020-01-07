package com.technokryon.ecommerce;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Pattern;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

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

	public static String getFilenamebyfile(MultipartFile file) {

		String rootPath = "E:\\Ecommerce_IMG";
		// String rootPath = BASE_PATH;
		if (!file.isEmpty()) {

			// Create the file on server
			Calendar cal = Calendar.getInstance();

			String name = stripExtension(file.getOriginalFilename()) + "-" + cal.getTimeInMillis() + "."
					+ FilenameUtils.getExtension(file.getOriginalFilename());

			// System.err.println(name);

			// String name = file.getOriginalFilename();

			try {

				byte[] bytes = file.getBytes();

				File dir = new File(rootPath);

				if (!dir.exists())

					dir.mkdirs();

				File serverFile = new File(dir.getAbsolutePath() + File.separator + name);

				// String path = "C:/Users/CVAR/Downloads/"+name;

				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));

				stream.write(bytes);

				stream.close();

				System.out.println("You successfully uploaded file=" + name + " on " + serverFile);

				return name;

			} catch (Exception e) {

				// System.out.println("You failed to upload " + name + " => " + e.getMessage());

				return null;

			}

		}
		return null;
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
