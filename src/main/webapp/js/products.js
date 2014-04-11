$(document).ready(function() {
	
	$.validator.addMethod(
	        "regex",
	        function(value, element, regexp) {
	            var re = new RegExp(regexp);
	            return re.test(value);
	        },
	        "Please check your input."
	);
	
	$("#productsForm").validate();
	$('#productsForm').find('.color').each(function() {
		$(this).rules('add', {
			required : true,
			messages : {
				required : "color is required."
			}
		});
	});

	$('#productsForm').find('.dateOfIssue').each(function() {
		$(this).rules('add', {
			required : true,
			regex: "^(0?[1-9]|[12][0-9]|3[01])-(0?[1-9]|1[012])-((19|20)\\d\\d)$",
			messages : {
				required : "date of issue is required.",
				regex: "invalid date format"
			}
		});
	});

	$('#productsForm').find('.model').each(function() {
		$(this).rules('add', {
			required : true,
			regex: "^[a-zA-Z]{2}[0-9]{3}$",
			messages : {
				required : "model is required.",
				regex: "2 letters 3 digits"
			}
		});
	});

	$('#productsForm').find('.producer').each(function() {
		$(this).rules('add', {
			required : true,
			messages : {
				required : "producer is required."
			}
		});
	});

	$('#productsForm').find('.price').each(function() {
		$(this).rules('add', {
			required : true,
			messages : {
				required : "price is required."
			}
		});
	});

	
});