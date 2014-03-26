<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">

	<xsl:output method="html" />

	<xsl:template match="/catalog">

		<xsl:param name="name" />
		<xsl:for-each select="/catalog/category[@name=$name]">
			<table border="0" cellpadding="6">
				<xsl:for-each select="/catalog/category[@name=$name]/subCategories">
					<xsl:variable name="subcatName">
						<xsl:value-of select="@name" />
					</xsl:variable>
					<tr>
						<td>

							<a
								href="FrontController.do?action=productList&amp;catName={$name}&amp;subcatName={$subcatName}">
								<xsl:value-of select="@name" />
							</a>
						</td>
						<td>
							<xsl:variable name="countProducts">
								<xsl:value-of
									select="count(/catalog/category[@name=$name]/subCategories[@name=$subcatName]//product)" />
							</xsl:variable>
							(
							<xsl:value-of select="$countProducts" />
							)
						</td>
					</tr>
				</xsl:for-each>
			</table>
		</xsl:for-each>

	</xsl:template>

</xsl:stylesheet>