<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	version="1.0">

	<xsl:output method="html" />

	<xsl:template match="/catalog">

		<xsl:param name="name" />
		<xsl:for-each select="/catalog/category[@name=$name]">
			<table border="0" cellpadding="6">
				<xsl:for-each select="/catalog/category[@name=$name]/subCategories">
					<tr>
						<td>						
							<xsl:value-of select="@name" />
						</td>
						
					</tr>
				</xsl:for-each>
			</table>
		</xsl:for-each>

	</xsl:template>

</xsl:stylesheet>