<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" />
	<xsl:template match="/catalog" name="addingPage">
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

		<html>
			<head>
				<title>add new product</title>
				<link rel="stylesheet" href="css/bootstrap.min.css" />
				<link rel="stylesheet" href="css/addingPage.css" />
			</head>
			<body>
				<div id='form'>
					<form
						action="catalog.do?action=saveProduct&amp;catName={$catName}&amp;subcatName={$subcatName}"
						method="POST">
						<h4>Please,fill in the form below. Fields shouldn't be empty</h4>

						<table style="border: none; width: 800px;" class="table">
							<tr>
								<td>
									<label for="model">model (2 letters and 3 digits)</label>
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
									<label for="dateOfIssue">date of issue (dd-MM-YYYY)</label>
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
								<td>
									<label for="notInStock">notInStock</label>
								</td>
								<td>
									<input type="checkbox" name="notInStock" onclick="triggerPrice()" />
								</td>
								<td>
								</td>
							</tr>
							<tr>
								<td>
									<label for="price">price</label>
								</td>
								<td>
									<input type="text" name="price" id="price" value="{$price}" />
								</td>
								<td>
									<xsl:value-of select="$priceError" />
								</td>
							</tr>
						</table>
						<a
							href="catalog.do?action=productList&amp;catName={$catName}&amp;subcatName={$subcatName}"
							class="btn btn-default" id="cancel">Back</a>
						<input type="submit" value="Save" class="btn btn-default" id="save"/>

					</form>
				</div>
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