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
			<nested:root name="productsForm">
				<nested:iterate property="document.rootElement.children"
					id="category" indexId="index">
					<nested:define id="categoryName" property="attributeValue(name)" />

					<li><a href="catalog.do?action=subcategories&catIndex=${index}">${categoryName}</a>
						<c:set var="sum" value="${0}" /> 
						<c:forEach var="subcategory" items="${category.children}">
							<c:set var="sum" value="${sum + subcategory.children[0].children.size()}" />
						</c:forEach> (<c:out value="${sum}" />)</li>
				</nested:iterate>
			</nested:root>
		</ul>
	</div>

</body>
</html>