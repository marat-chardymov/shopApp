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

		<xsl:param name="modelError" />
		<xsl:param name="colorError" />
		<xsl:param name="dateOfIssueError" />
		<xsl:param name="priceError" />
		<xsl:param name="producerError" />
		<xsl:param name="notInStockError" />

		<html>
			<head>
				<title>add new product</title>
			</head>
			<body>
				<form
					action="FrontController.do?action=saveProduct&amp;catName={$catName}&amp;subcatName={$subcatName}"
					method="POST">
					<table style="border: none; width: 800px;">
						<tr>
							<td>
								<label for="model">model</label>
							</td>
							<td>
								<input type="text" id="model" name="model" value="{$model}"></input>
							</td>
							<td>
								<xsl:value-of select="$modelError" />
							</td>
						</tr>
						<tr>
							<td>
								<label for="color">color</label>
							</td>
							<td>
								<input type="text" id="color" name="color" value="{$color}"></input>
							</td>
							<td>
								<xsl:value-of select="$colorError" />
							</td>
						</tr>
						<tr>
							<td>
								<label for="dateOfIssue">date Of Issue</label>
							</td>
							<td>
								<input type="text" id="dateOfIssue" name="dateOfIssue"
									value="{$dateOfIssue}"></input>
							</td>
							<td>
								<xsl:value-of select="$dateOfIssueError" />
							</td>
						</tr>
						<tr>
							<td>
								<label for="producer">producer</label>
							</td>
							<td>
								<input type="text" id="producer" name="producer" value="{$producer}"></input>
							</td>
							<td>
								<xsl:value-of select="$producerError" />
							</td>
						</tr>
						<tr>
							<td>notInStock</td>
							<td>
								<input type="checkbox" name="notInStock" onclick="triggerPrice()" />
							</td>
							<td>
								<xsl:value-of select="$notInStockError" />
							</td>
						</tr>
						<tr>
							<td>
								<label for="price">price</label>
							</td>
							<td>
								<input type="text" name="price" id="price" 
									value="{$price}" />
							</td>
							<td>
								<xsl:value-of select="$priceError" />
							</td>
						</tr>
					</table>

					<input type="submit" value="submit" />
				</form>
				<script>
					function triggerPrice() {
					document.getElementById("price").disabled =
					!document.getElementById("price").disabled;
					}
				</script>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>