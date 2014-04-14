<%@ include file="includes/tagLibs.jsp"%>
<html>
<head>
<title>Error</title>
<link
	href="${pageContext.request.contextPath}/css/bootstrap.min.css"
	rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/error_page.css"
	rel="stylesheet" />
</head>
<body>
	<div class="container">
		<div class="hero-unit">
			<h1>Error</h1>

			<p>File update attempt failed. Try again</p>

			<p>
				<a href="catalog.do?action=productList&catIndex=${productsForm.catIndex}&subcatIndex=${subcatIndex}"
					class="btn btn-primary btn-large"> to product list »</a>
			</p>
		</div>
		<hr>
		<footer>
			<p>Copyright © 2014. EPAM Systems. All Rights Reserved.</p>
		</footer>
	</div>
</body>
</html>