<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-nested" prefix="nested"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<nested:root name="productsForm">
		<nested:nest property="document">
			<nested:equal value="${productsForm.catName}"
				property="attributes[0].value">
				<nested:iterate property="children" id="subcategory" indexId="index">
					<tr>
						<td><nested:define id="subcategoryName"
								property="attributeValue(name)" />
							<h3>
								<a
									href="/XSL/productlist.do?method=productList&catName=${productForm.catName}&subcategoryIndex=${index}">
									<bean:write name="subcategoryName" />
								</a>
							</h3></td>
						<td>
							<h3>
								asdf
								<c:set var="total" value="${subcategory.children.size()}" />
								<c:out value="${total}" />
							</h3>
						</td>
					</tr>
				</nested:iterate>
			</nested:equal>
		</nested:nest>
	</nested:root>
</body>
</html>