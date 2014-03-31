package com.epam.util;

import java.util.Map;


public class Validator {	
	
	public boolean wasSuccessful=false;

	public static boolean validate(String producer,String model, String color,
			String dateOfIssue, String price, String notInStock,Object errors) {
		System.out.println(producer);
		Map<String,String> err=(Map)errors;
		err.put("sorry", "sorry");
		return true;

	}
}
