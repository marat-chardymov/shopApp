<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:validator="xalan://com.epam.util.Validator">

	<xsl:param name="catName" />
	<xsl:param name="subcatName" />

	<xsl:param name="producer" />
	<xsl:param name="model" />
	<xsl:param name="color" />
	<xsl:param name="dateOfIssue" />
	<xsl:param name="price" />
	<xsl:param name="notInStock" />
	
	<xsl:param name="errors" />

	<xsl:output method="xml" indent="yes" />

	<xsl:template match="/">
		<xsl:choose>
			<xsl:when
				test="validator:validate($producer,$model,$color,$dateOfIssue,$price,$notInStock,$errors)">
				<xsl:call-template name="copyNodes" />
			</xsl:when>
			<xsl:otherwise>

			</xsl:otherwise>
		</xsl:choose>
	</xsl:template>

	<xsl:template name="copyNodes" match="/|node()|@*">
		<xsl:copy>
			<xsl:apply-templates select="node()|@*" />
		</xsl:copy>
	</xsl:template>

	<xsl:template name="addProduct"
		match="/catalog/category[@name=$catName]/subCategories[@name=$subcatName]/products">
		<xsl:element name="products">
			<xsl:apply-templates />
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
					<xsl:when test="$notInStock='false'">
						<xsl:element name="price">
							<xsl:value-of select="$price" />
						</xsl:element>
					</xsl:when>
					<xsl:otherwise>
						<xsl:element name="notInStock"/>
					</xsl:otherwise>
				</xsl:choose>
			</xsl:element>
		</xsl:element>.
	</xsl:template>


</xsl:stylesheet>