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
		<div id="block">
			<table cellpadding="6" class="table" style="width: 600px;">

				<tr>
					<th>color</th>
					<th>date of issue</th>
					<th>model</th>
					<th>producer</th>
					<th>price</th>
				</tr>
				<logic:iterate id="tmpProduct" name="productsForm"
					property="document.rootElement.children[${productsForm.catIndex}].children[${productsForm.catIndex}].children[0].children"
					indexId="productId">
					<tr>
						<td><html:text name="tmpProduct" property="child(color).text" />
						</td>
						<td>
						<html:text name="tmpProduct" property="child(dateOfIssue).text"/>
						</td>
						<td>
						<html:text name="tmpProduct" property="child(model).text"/>
						</td>
						<td>
						<html:text name="tmpProduct" property="child(producer).text"/></td>
						<td>
						<html:text name="tmpProduct" property="child(price).text"/>
						</td>
					</tr>
				</logic:iterate>
			</table>
			<a href="catalog.do?action=subcategories&catIndex=${catIndex}"
				class="btn btn-default">Back</a> <a
				href="catalog.do?action==newProduct&catIndex={catIndex}&subcatIndex={$subcatIndex}"
				class="btn btn-default" id="add"> Add </a>
		</div>
	</nested:root>
</body>
</html>