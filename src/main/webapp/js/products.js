$(document).ready(function(){ 

	$("#productsForm").validate();
	$("#color").rules("add", {
	    required: true
	});
});