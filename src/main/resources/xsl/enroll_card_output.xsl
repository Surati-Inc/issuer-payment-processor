<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
	<xsl:output method="html" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
    <xsl:include href="/xsl/layout.xsl"/>
	<xsl:template match="page" mode="head">
		<title>
			<xsl:text>Issuer payment processor</xsl:text>
		</title>
	</xsl:template>
	<xsl:template match="page" mode="body">
	<h2><xsl:text>Card enrollment results</xsl:text></h2>
	<p>
		PAN = <xsl:value-of select="pan"/>
	</p>
	<h4>Output</h4>
	<p>
		<textarea rows="2" cols="100" disabled="disabled">
			<xsl:value-of select="message"/>
		</textarea>
	</p>
	<button type="button" onclick="history.back()">Go back</button>
	</xsl:template>
</xsl:stylesheet>
