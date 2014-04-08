<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Catalog</title>
<link rel="stylesheet" href="css/bootstrap.min.css" />
<link rel="stylesheet" href="css/app.css" />
</head>
<body>
	<div id="listBlock">
		<ul>
			<c:forEach items="${productsForm.document.rootElement.children}"
				var="category">
				<li><a
					href="catalog.do?action=subcategories&catName=${category.getAttributeValue('name')}">
						${category.getAttributeValue("name")}</a>
					(${category.children.size()})</li>
			</c:forEach>
		</ul>
	</div>

</body>
</html>