package com.epam.util;

import java.util.Map;

public class Validator {

	public boolean wasSuccessful = false;

	public static boolean validate(String producer, String model, String color,
			String dateOfIssue, String price, boolean notInStock, Object errors) {
		Map<String, String> errorsMap = (Map) errors;
		validateProducer(producer, errorsMap);
		validateModel(model, errorsMap);
		validateColor(color, errorsMap);
		validateDateOfIssue(dateOfIssue, errorsMap);
		if (notInStock == false) {
			validatePrice(price, errorsMap);
		}
		if (errorsMap.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	private static void validateProducer(String producer,
			Map<String, String> errors) {
		// empty
		if (producer.isEmpty()) {
			errors.put("producerError", "producer field shouldn't be empty");
		}
	}

	private static void validateModel(String model, Map<String, String> errors) {
		// empty
		if (model.isEmpty()) {
			errors.put("modelError", "model field shouldn't be empty ");
			return;
		}
		// 2 letters 3 digits pattern
		if (!model.matches("^[a-zA-Z][a-zA-Z][0-9][0-9][0-9]$")) {
			errors.put("modelError",
					"model field should consist of two letters and three digits");
		}
	}

	private static void validateColor(String color, Map<String, String> errors) {
		// empty
		if (color.isEmpty()) {
			errors.put("colorError", "color field shouldn't be empty ");
		}
	}

	private static void validateDateOfIssue(String dateOfIssue,
			Map<String, String> errors) {
		// empty
		if (dateOfIssue.isEmpty()) {
			errors.put("dateOfIssueError",
					"dateOfIssue field shouldn't be empty ");
			return;
		}
		// dd-MM-YYYY pattern
		if (!dateOfIssue
				.matches("(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[012])-((19|20)\\d\\d)")) {
			errors.put("dateOfIssueError",
					"dateOfIssueError field should match pattern dd-MM-YYYY");
		}
	}

	private static void validatePrice(String price, Map<String, String> errors) {
		// empty
		if (price.isEmpty()) {
			errors.put("priceError",
					"price field shouldn't be empty or notInStock must be checked");
			return;
		}
		// dd-MM-YYYY pattern
		if (!price.matches("^-?\\d+$")) {
			errors.put("priceError", "price field should be integer");
		}
	}

}
