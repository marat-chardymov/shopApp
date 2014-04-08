<%@ include file="includes/tagLibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>subcategories list</title>
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link rel="stylesheet" href="css/app.css" />
</head>
<body>
	<div id="listBlock">
		<ul>
			<nested:root name="productsForm">
				<nested:nest property="document.rootElement">
					<nested:nest property="children[${productsForm.catIndex}]">
						<nested:iterate property="children" id="subcategory"
							indexId="subcatIndex">
							<li><nested:define id="subcategoryName"
									property="attributeValue(name)" /> <a
								href="catalog.do?action=productList&catIndex=${productsForm.catIndex}&subcat

Index=${subcatIndex}">
									<bean:write name="subcategoryName" />
							</a> (${subcategory.children[0].children.size()})</li>
						</nested:iterate>

					</nested:nest>
				</nested:nest>
			</nested:root>
		</ul>
		<a href="catalog.do?action=categories" class="btn btn-default">Back</a>
	</div>

</body>
</html>