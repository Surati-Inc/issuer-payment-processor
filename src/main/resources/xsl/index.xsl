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
	<h2 style="text-align: center;"><xsl:text>Issuer payment processor</xsl:text></h2>
	<p>
		You are welcome ! This application represents a simulation of certains functionnalities of an issuer payment processor. All rules implemented respect <a href="https://en.wikipedia.org/wiki/ISO_8583" target="_blank">ISO 8583</a> norm. 
	</p>
	
	</xsl:template>
</xsl:stylesheet>
