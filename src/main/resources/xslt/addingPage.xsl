<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method="html" />
	<xsl:template match="/">
		<html>
			<body>
				<form>
					<table style="border: 1px black solid; width: 350px;">
						<tr>
							<td>
								<label for="model">model</label>
							</td>
							<td>
								<input type="text" id="model"></input>
							</td>
						</tr>
						<tr>
							<td>
								<label for="color">color</label>
							</td>
							<td>
								<input type="text" id="color"></input>
							</td>
						</tr>
						<tr>
							<td>
								<label for="dateOfIssue">date Of Issue</label>
							</td>
							<td>
								<input type="text" id="dateOfIssue"></input>
							</td>
						</tr>
						<tr>
							<td>
								<label for="price">price</label>
							</td>
							<td>
								<input type="text" id="price"></input>
							</td>
						</tr>
						<tr>
							<td>
								<label for="producer">producer</label>
							</td>
							<td>
								<input type="text" id="producer"></input>
							</td>
						</tr>
						<tr>
							<td>notInStock</td>
							<td>
								<input type="text" id="notInStock"></input>
							</td>
						</tr>
					</table>
				</form>
			</body>
		</html>
	</xsl:template>
</xsl:stylesheet>