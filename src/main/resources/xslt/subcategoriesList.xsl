<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">

	<xsl:output method="html" />
	<xsl:template match="/catalog">

		<xsl:param name="name" />
		<xsl:for-each select="/catalog/category[@name=$name]">
			<html>
			<head>
				<title>subcategories list</title>
				<link rel="stylesheet" href="css/bootstrap.min.css"/>
			</head>
			<body>
			<ul>
				<xsl:for-each select="/catalog/category[@name=$name]/subCategories">
					<xsl:variable name="subcatName">
						<xsl:value-of select="@name" />
					</xsl:variable>
					<li>
							<a href="FrontController.do?action=productList&amp;catName={$name}&amp;subcatName={$subcatName}">
								<xsl:value-of select="@name" />
							</a>
						
							<xsl:variable name="countProducts">
								<xsl:value-of
									select="count(/catalog/category[@name=$name]/subCategories[@name=$subcatName]//product)" />
							</xsl:variable>
							(
							<xsl:value-of select="$countProducts" />
							)
					</li>
				</xsl:for-each>
			</ul>
			<a
					href="FrontController.do?action=categoriesList"
					class="btn btn-default">Back</a>
			</body>
		</html>
		</xsl:for-each>

	</xsl:template>

</xsl:stylesheet>