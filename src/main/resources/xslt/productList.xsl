<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">

	<xsl:output method="html" />
	<xsl:template match="/catalog">

		<xsl:param name="catName" />
		<xsl:param name="subcatName" />
		<table border="1" cellpadding="6">
			<tr>
				<th>color</th>
				<th>date of issue</th>
				<th>model</th>
				<th>producer</th>
				<th>price</th>
			</tr>
			<xsl:for-each
				select="/catalog/category[@name=$catName]/subCategories[@name=$subcatName]/products/product">

				<tr>
					<td>
						<xsl:value-of select="color" />
					</td>
					<td>
						<xsl:value-of select="dateOfIssue" />
					</td>
					<td>
						<xsl:value-of select="model" />
					</td>
					<td>
						<xsl:value-of select="producer" />
					</td>
					<td>
						<xsl:choose>
							<xsl:when test="notInStock">
								not in stock
							</xsl:when>
							<xsl:otherwise>
								<xsl:value-of select="price" />
							</xsl:otherwise>
						</xsl:choose>
					</td>
				</tr>

			</xsl:for-each>
		</table>
		<a
			href="FrontController.do?action=newProduct&amp;catName={$catName}&amp;subcatName={$subcatName}">
			Add new product
		</a>
	</xsl:template>
</xsl:stylesheet>
	