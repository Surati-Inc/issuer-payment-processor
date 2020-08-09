<?xml version="1.0"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="2.0">
	<xsl:output method="html" include-content-type="no" doctype-system="about:legacy-compat" encoding="UTF-8" indent="yes"/>
    <xsl:include href="/xsl/layout.xsl"/>
	<xsl:template match="page" mode="head">
		<title>
			<xsl:text>Issuer payment processor - Enroll a card</xsl:text>
		</title>
	</xsl:template>
	<xsl:template match="page" mode="body">
	    <h1>
	      <xsl:text>Enroll a card</xsl:text>
	    </h1>
		<form action="/card/enroll" method="post">
	      <fieldset>
	        <label>
	          <xsl:text>Network</xsl:text><span style="color: red"> *</span>
	        </label>
	        <select name="network" required="">
	          <option value="">--Choose a network--</option>
	          <xsl:for-each select="networks/network">
	          	<option value="{id}"><xsl:value-of select="name"/></option>
	          </xsl:for-each>
	        </select>
	        <label>
	          <xsl:text>Primary account number (PAN)</xsl:text><span style="color: red"> *</span>
	        </label>
	        <input name="pan" type="text" size="50" maxlength="50" required="" placeholder="Example : 7845124789653278"/>
			<label>
	          <xsl:text>Validity date</xsl:text><span style="color: red"> *</span>
	        </label>
	        <input name="validity_date" type="text" size="50" required="" pattern="[0-9]{2}\/[0-9]{2}" placeholder="MM/yy"/>
	        <label>
	          <xsl:text>Cardholder</xsl:text><span style="color: red"> *</span>
	        </label>
	        <input name="card_holder" type="text" size="50" maxlength="50" required="" placeholder="Enter a fullname"/>
	        <label>
	          <xsl:text>Phone number</xsl:text><span style="color: red"> *</span>
	        </label>
	        <input name="phone" type="tel" size="25" required="" placeholder="Example : +22558962147"/>
	        <label>
	          <xsl:text>Cardholder login</xsl:text><span style="color: red"> *</span>
	        </label>
	        <input name="login" type="text" minlength="5" size="50" maxlength="50" required="" placeholder="Example : baudoliver7"/>
	        <label>
	          <xsl:text>Cardholder password</xsl:text><span style="color: red"> *</span>
	        </label>
	        <input name="password" type="password" minlength="5" size="50" maxlength="50" required="" placeholder="Enter a password (Example bwadd78)"/>	        

	        <div style="display: inline-block">
	        	<button type="submit">
		          <xsl:text>Enroll</xsl:text>
		        </button>
		        <button type="button" onclick="location.href='/'">
		          <xsl:text>Cancel</xsl:text>
		        </button>
	        </div>
	      </fieldset>
	    </form>
	</xsl:template>
</xsl:stylesheet>
