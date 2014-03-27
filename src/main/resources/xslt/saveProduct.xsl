<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

	<xsl:param name="catName" />
	<xsl:param name="subcatName" />

	<xsl:param name="producer" />
	<xsl:param name="model" />
	<xsl:param name="color" />
	<xsl:param name="dateOfIssue" />
	<xsl:param name="price" />
	<xsl:param name="notInStock" />

	<xsl:output method="xml" indent="yes" />

	<xsl:template match="/|node()|@*">
		
			<xsl:copy>
				<xsl:apply-templates select="node()|@*" />
			</xsl:copy>
	
	</xsl:template>

	<xsl:template
		match="/catalog/category[@name=$catName]/subCategories[@name=$subcatName]/products">
		<xsl:copy>
			<xsl:apply-templates select="node()|@*" />
			<xsl:element name="product">
				<xsl:element name="producer">
					<xsl:value-of select="$producer" />
				</xsl:element>
				<xsl:element name="model">
					<xsl:value-of select="$model" />
				</xsl:element>
				<xsl:element name="dateOfIssue">
					<xsl:value-of select="$dateOfIssue" />
				</xsl:element>
				<xsl:element name="color">
					<xsl:value-of select="$color" />
				</xsl:element>
				<xsl:choose>
					<xsl:when test="$notInStock">
						<xsl:element name="price">
							<xsl:value-of select="$price" />
						</xsl:element>
					</xsl:when>
					<xsl:otherwise>
						<xsl:element name="notInStock" />
					</xsl:otherwise>
				</xsl:choose>
			</xsl:element>
		</xsl:copy>
	</xsl:template>

</xsl:stylesheet>