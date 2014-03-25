<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">

	<xsl:output method="html" />

	<xsl:template match="/catalog">
		<ul>
			<xsl:apply-templates />
		</ul>
	</xsl:template>
	<xsl:template match="/catalog/category">
		<li>
			<xsl:variable name="name">
				<xsl:value-of select="@name" />
			</xsl:variable>
			<a href="FrontController.do?action=subcategoriesList&amp;name={$name}">
				<xsl:value-of select="@name" />
			</a>

			<xsl:value-of select="count(subCategories)"/>

		</li>
	</xsl:template>
</xsl:stylesheet>


