<%@ include file="includes/tagLibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>product list</title>
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link rel="stylesheet" href="css/productList.css" />
</head>
<body>
	<nested:root name="productsForm">
		<nested:form
			action="/catalog.do?action=save&catIndex=${productsForm.catIndex}&subcatIndex=${productsForm.subcatIndex}">
			<div id="block">
				<table cellpadding="6" class="table" style="width: 600px;">

					<tr>
						<th>color</th>
						<th>date of issue</th>
						<th>model</th>
						<th>producer</th>
						<th>price</th>
					</tr>
					<nested:form
						action="/catalog.do?method=save&catIndex=${productsForm.catIndex}&subcatIndex=${productsForm.subcatIndex}">
						<nested:iterate id="tmpProduct"
							property="document.rootElement.children[${productsForm.catIndex}].children[${productsForm.subcatIndex}].children[0].children">
							<tr>
								<td><nested:text property="children[0].text" /></td>
								<td><nested:text property="children[1].text" /></td>
								<td><nested:text property="children[2].text" /></td>
								<td><nested:text property="children[3].text" /></td>
								<td><nested:text property="children[4].text" /></td>
							</tr>
						</nested:iterate>
					</nested:form>

				</table>
				<a
					href="catalog.do?action=subcategories&catIndex=${productsForm.catIndex}"
					class="btn btn-default" id="backBtn">Back</a> <input type="submit"
					value="Save" id="saveBtn" class="btn btn-default" /> <a
					href="catalog.do?action=add&catIndex=${productsForm.catIndex}&subcatIndex=${productsForm.subcatIndex}"
					class="btn btn-default">Add</a>
			</div>
		</nested:form>
	</nested:root>
</body>
</html>