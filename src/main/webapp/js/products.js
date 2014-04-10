$(document).ready(function() {
	
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
			messages : {
				required : "date of issue is required."
			}
		});
	});

	$('#productsForm').find('.model').each(function() {
		$(this).rules('add', {
			required : true,
			messages : {
				required : "model is required."
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