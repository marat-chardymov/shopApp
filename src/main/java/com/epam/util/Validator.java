package com.epam.util;


public class Validator {

	public boolean wasSuccessful = false;

	public static boolean validate(String producer, String model, String color,
			String dateOfIssue, String price, boolean notInStock,
			Object modelError, Object colorError, Object dateOfIssueError,
			Object priceError, Object producerError) {
		
		validateProducer(producer, (StringBuilder)producerError);
		validateModel(model, (StringBuilder)modelError);
		validateColor(color, (StringBuilder)colorError);
		validateDateOfIssue(dateOfIssue, (StringBuilder)dateOfIssueError);
		if (notInStock == false) {
			validatePrice(price, (StringBuilder)priceError);
		}
		if (producerError.toString().isEmpty() && modelError.toString().isEmpty()
				&& colorError.toString().isEmpty() && dateOfIssueError.toString().isEmpty()
				&& priceError.toString().isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	private static void validateProducer(String producer, StringBuilder producerError) {
		// empty
		if (producer.isEmpty()) {
			producerError.append("producer field shouldn't be empty");
		}
	}

	private static void validateModel(String model, StringBuilder modelError) {
		// empty
		if (model.isEmpty()) {
			modelError.append("model field shouldn't be empty ");
			return;
		}
		// 2 letters 3 digits pattern
		if (!model.matches("^[a-zA-Z]{2}[0-9]{3}$")) {
			modelError.append("model field should consist of two letters and three digits");
		}
	}

	private static void validateColor(String color, StringBuilder colorError) {
		// empty
		if (color.isEmpty()) {
			colorError.append("color field shouldn't be empty ");
		}
	}

	private static void validateDateOfIssue(String dateOfIssue,
			StringBuilder dateOfIssueError) {
		// empty
		if (dateOfIssue.isEmpty()) {
			dateOfIssueError.append("dateOfIssue field shouldn't be empty ");
			return;
		}
		// dd-MM-YYYY pattern
		if (!dateOfIssue
				.matches("(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[012])-((19|20)\\d\\d)")) {
			dateOfIssueError.append("dateOfIssueError field should match pattern dd-MM-YYYY");
		}
	}

	private static void validatePrice(String price, StringBuilder priceError) {
		// empty
		if (price.isEmpty()) {
			priceError.append("price field shouldn't be empty or notInStock must be checked");
			return;
		}
		// dd-MM-YYYY pattern
		if (!price.matches("^-?\\d+$")) {
			priceError.append("price field should be integer");
		}
	}

}
