<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" />
	<xsl:template match="/">
		<xsl:param name="catName" />
		<xsl:param name="subcatName" />
		
		<xsl:param name="model" />
		<xsl:param name="color" />
		<xsl:param name="dateOfIssue" />
		<xsl:param name="price" />
		<xsl:param name="producer" />
		<xsl:param name="notInStock" />
		
		<html>
			<head>
				<title>add new product</title>
			</head>
			<body>
				<form
					action="FrontController.do?action=saveProduct&amp;catName={$catName}&amp;subcatName={$subcatName}"
					method="POST">
					<table style="border: 1px black solid; width: 350px;">
						<tr>
							<td>
								<label for="model">model</label>
							</td>
							<td>
								<input type="text" id="model" name="model" value="{$model}"></input>
							</td>
						</tr>
						<tr>
							<td>
								<label for="color">color</label>
							</td>
							<td>
								<input type="text" id="color" name="color" value="{$color}"></input>
							</td>
						</tr>
						<tr>
							<td>
								<label for="dateOfIssue">date Of Issue</label>
							</td>
							<td>
								<input type="text" id="dateOfIssue" name="dateOfIssue" value="{$dateOfIssue}"></input>
							</td>
						</tr>
						<tr>
							<td>
								<label for="price">price</label>
							</td>
							<td>
								<input type="text" id="price" name="price" value="{$price}"></input>
							</td>
						</tr>
						<tr>
							<td>
								<label for="producer">producer</label>
							</td>
							<td>
								<input type="text" id="producer" name="producer" value="{$producer}"></input>
							</td>
						</tr>
						<tr>
							<td>notInStock</td>
							<td>
								<input type="text" id="notInStock" name="notInStock" value="{$notInStock}"></input>
							</td>
						</tr>
					</table>
					<input type="submit"/>
				</form>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>