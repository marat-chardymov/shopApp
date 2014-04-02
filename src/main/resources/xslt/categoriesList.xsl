<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">

	<xsl:output method="html" />
	<xsl:template match="/catalog">
		<html>
			<head>
				<title>categories list</title>
				<link rel="stylesheet" href="css/bootstrap.min.css" />
			</head>
			<body>
				<ul>
					<xsl:for-each select="/catalog/category">
						<li>
							<xsl:variable name="name">
								<xsl:value-of select="@name" />
							</xsl:variable>
							<xsl:variable name="countProducts">
								<xsl:value-of
									select="count(/catalog/category[@name=$name]/subCategories//product)" />
							</xsl:variable>
							<a href="FrontController.do?action=subcategoriesList&amp;name={$name}">
								<xsl:value-of select="@name" />
							</a>

							(
							<xsl:value-of select="$countProducts" />
							)

						</li>
					</xsl:for-each>
				</ul>
			</body>
		</html>
	</xsl:template>

</xsl:stylesheet>


